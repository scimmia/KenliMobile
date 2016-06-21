package com.jiayusoft.mobile.kenli.utils.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.SparseBooleanArray;
import com.google.gson.Gson;
import com.jiayusoft.mobile.kenli.utils.DebugLog;
import com.jiayusoft.mobile.kenli.utils.GlobalData;
import com.jiayusoft.mobile.kenli.utils.eventbus.BusProvider;
import com.squareup.okhttp.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ASUS on 2015/2/9.
 */
public class HttpUploadTask extends AsyncTask<Void,Integer,String> implements GlobalData {

    ProgressDialog mpDialog;
    Context mContent;

    int mTag;
    ArrayList<String> selectedFiles;
    int fileSize;
    SparseBooleanArray mResults;
    int mUploadType;

    public HttpUploadTask(Context mContent, int mTag,  ArrayList<String> selectedFiles, int uploadtype) {
        this.mContent = mContent;
        this.mTag = mTag;
        this.selectedFiles = selectedFiles;
        fileSize = selectedFiles==null?0:selectedFiles.size();
        mResults = new SparseBooleanArray(fileSize);
        mUploadType = uploadtype;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        mpDialog = new ProgressDialog(mContent);
//        mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条
        mpDialog.setMessage("开始上传...");
        mpDialog.setCancelable(true);

        mpDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                cancel(true);
            }
        });
        mpDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mpDialog.setMessage(String.format("正在上传第%d个，共%d个", values[0], fileSize));
    }

    @Override
    protected String doInBackground(Void... params) {
        OkHttpClient okHttpClient = new OkHttpClient();
//        RequestBody formBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),"");
        for (int i = 0;i<fileSize && !isCancelled();i++) {
            publishProgress(i+1);
            MultipartBuilder f = new MultipartBuilder().type(MultipartBuilder.FORM);
            File fileTemp = new File(selectedFiles.get(i));
            if (fileTemp.getName().endsWith(".png")) {
                f.addFormDataPart("file", fileTemp.getName(),
                        RequestBody.create(MediaType.parse("image/png"), fileTemp));
            } else if (fileTemp.getName().endsWith(".jpg") || fileTemp.getName().endsWith(".jpeg")) {
                f.addFormDataPart("file", fileTemp.getName(),
                        RequestBody.create(MediaType.parse("image/jpeg"), fileTemp));
            }

            RequestBody formBody = f.build();
            Request request = new Request.Builder().tag(mTag)
                    .url(uploadFileUrl)
                    .post(formBody)
                    .build();
            if (request != null) {
                try {
                    mCall = okHttpClient.newCall(request);
                    Response response = mCall.execute();
                    if (response.isSuccessful()) {
                        String temp = response.body().string();
                        DebugLog.e(temp);
                        mResults.put(i,StringUtils.equals(temp,"success"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mpDialog.dismiss();
        DebugLog.e(result);
        DebugLog.e(new Gson().toJson(mResults));
    }

    Call mCall;
    @Override
    protected void onCancelled() {
        super.onCancelled();
        DebugLog.e("onCancelled");
        if (mCall!=null){
            mCall.cancel();
        }
    }
}
