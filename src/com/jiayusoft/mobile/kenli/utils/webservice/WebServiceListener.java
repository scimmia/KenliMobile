package com.jiayusoft.mobile.kenli.utils.webservice;

/**
 * Created by ASUS on 2014/5/19.
 */
public interface WebServiceListener {
    public void onSuccess(String content);
    public void onFailure(String content);
    public void onError(Exception exception);
}
