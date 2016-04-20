package com.jiayusoft.mobile.kenli;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jiayusoft.mobile.kenli.utils.GlobalData;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by ASUS on 2014/7/1.
 */
public class BaseApplication extends Application implements GlobalData {
    private static final String DB_NAME = "green.db";
    private static BaseApplication mInstance;

    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(mInstance == null)
            mInstance = this;
    }


    private static OkHttpClient okHttpClient;
    public static OkHttpClient getOkHttpClient(){
        if (okHttpClient == null){
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }

    public static void loadImage(Context context, ImageView imageView, String url, int loadingRes, int errorRes){
        try {
            if (context instanceof Activity){
                Glide.with((Activity)context)
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(loadingRes)
                        .error(errorRes)
                        .into(imageView);
            }else {
                Glide.with(context)
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(loadingRes)
                        .error(errorRes)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
            imageView.setImageResource(errorRes);
        }
    }

    public static void loadImage(Context context,ImageView imageView,String url,int errorRes){
        try {
            if (context instanceof Activity){
                Glide.with((Activity) context)
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(errorRes)
                        .into(imageView);
            }else {
                Glide.with(context)
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(errorRes)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
            imageView.setImageResource(errorRes);
        }
    }
}
