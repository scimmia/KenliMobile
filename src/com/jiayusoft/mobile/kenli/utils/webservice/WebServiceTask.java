package com.jiayusoft.mobile.kenli.utils.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import com.jiayusoft.mobile.kenli.utils.DebugLog;
//import com.jiayusoft.mobile.kenli.utils.webservice.xmljson.JSONObject;
//import com.jiayusoft.mobile.kenli.utils.webservice.xmljson.XML;
import org.apache.commons.lang3.StringUtils;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by ASUS on 2014/5/19.
 */
public class WebServiceTask extends AsyncTask<Void,Void,Void> {
    public ProgressDialog mpDialog;
    public Context mContent;
    public String msgToShow;
    SoapRequestStruct soapRequestStruct;
    public WebServiceListener webServiceListener;
    Exception exception;
    String responseString;

    public WebServiceTask(Context mContent, String msgToShow, SoapRequestStruct soapRequestStruct, WebServiceListener webServiceListener) {
        super();
        this.mContent = mContent;
        this.msgToShow = msgToShow;
        this.soapRequestStruct = soapRequestStruct;
        this.webServiceListener = webServiceListener;
        this.exception = null;
        this.responseString = null;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        mpDialog = new ProgressDialog(mContent);
//        mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条
        mpDialog.setMessage(msgToShow);
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
    protected Void doInBackground(Void... params) {
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
            SoapObject request = new SoapObject(soapRequestStruct.getServiceNameSpace(), soapRequestStruct.getMethodName());
            if (soapRequestStruct.getPropertys()!=null){
                for (PropertyInfo propertyInfo : soapRequestStruct.getPropertys()){
                    request.addProperty(propertyInfo);
                }
            }
            envelope.bodyOut = request;
            (new MarshalBase64()).register(envelope);
            HttpTransportSE transport = new HttpTransportSE(soapRequestStruct.getServiceUrl());
            transport.call(null, envelope);
            if (envelope.getResponse() != null) {
                SoapObject result = (SoapObject) envelope.bodyIn;
                responseString = result.getProperty(0).toString();
                DebugLog.e("responseString:   "+responseString);
            }
        } catch (IOException e) {
            this.exception = e;
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            this.exception = e;
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        mpDialog.dismiss();
        if(webServiceListener!=null){
            if (exception!=null){
                webServiceListener.onError(exception);
            }else{
                if (StringUtils.isEmpty(responseString)){
                    webServiceListener.onFailure("服务器连接失败，请稍后重试或联系管理员");
                }else {
                    webServiceListener.onSuccess(responseString);
                }
            }
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

    }
}