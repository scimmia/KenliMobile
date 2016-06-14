package com.jiayusoft.mobile.kenli.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.jiayusoft.mobile.kenli.MainActivity;
import com.jiayusoft.mobile.kenli.R;
import com.jiayusoft.mobile.kenli.utils.DebugLog;
import com.jiayusoft.mobile.kenli.utils.app.BaseActivity;
import com.jiayusoft.mobile.kenli.utils.app.listener.HideKeyboardListener;
import com.jiayusoft.mobile.kenli.utils.eventbus.event.HttpEvent;
import com.jiayusoft.mobile.kenli.utils.webservice.*;
import com.squareup.otto.Subscribe;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;


public class LoginActivity extends BaseActivity {

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_login);
    }

    @Bind(R.id.login_et_username)
    EditText mLoginEtUsername;
    @Bind(R.id.login_et_password)
    EditText mLoginEtPassword;
    @Bind(R.id.login_btn_sign)
    Button mLoginBtnSignOnline;
    @Bind(R.id.main_layout)
    RelativeLayout mLoginLayout;
    @Bind(R.id.login_cb_save_password)
    CheckBox mLoginCbSavePassword;
    @Bind(R.id.login_cb_auto_login)
    CheckBox mLoginCbAutoLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginLayout.setOnClickListener(new HideKeyboardListener(getBaseActivity()));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseActivity());
        mLoginEtUsername.setText(sharedPreferences.getString(LOGIN_USER_NAME, null));
        mLoginEtPassword.setText(sharedPreferences.getString(LOGIN_PASSWORD, null));
        mLoginCbSavePassword.setChecked(sharedPreferences.getBoolean(LOGIN_SAVE_PASSWORD, false));
        mLoginCbAutoLogin.setChecked(sharedPreferences.getBoolean(loginAutoLogin, false));

        if (sharedPreferences.getString(serverUrl, null) == null) {
            SharedPreferences.Editor spEd = sharedPreferences.edit();
            spEd.putString(serverUrl, defaultServerUrl);
            spEd.apply();
        }
        if (sharedPreferences.getBoolean(loginAutoLogin, false)) {
            attemptLogin();
        }
    }

    @OnClick(R.id.login_btn_sign)
    public void attemptLogin() {
        if (TextUtils.isEmpty(mLoginEtUsername.getText())) {
            mLoginEtUsername.setError(getString(R.string.error_field_required));
            mLoginEtUsername.requestFocus();
        } else {
            // Reset errors.
            mLoginEtUsername.setError(null);
            mLoginEtPassword.setError(null);

            login();
        }
    }

    @OnCheckedChanged(R.id.login_cb_auto_login)
    public void onAutoLogin(boolean checked) {
        if (checked && !mLoginCbSavePassword.isChecked()) {
            mLoginCbSavePassword.toggle();
        }
    }

    @Subscribe
    public void onHttpEvent(HttpEvent event) {
        if (event == null || StringUtils.isEmpty(event.getResponse())) {
            showMessage("网络连接错误，请稍后重试。");
        } else {
            int tag = event.getTag();
            switch (tag) {
                case tagDownloadNewFile:
                    if (StringUtils.isNotEmpty(event.getResponse())) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(new File(event.getResponse())), "application/vnd.android.package-archive");
                        startActivity(intent);
                    }
                    break;
            }
        }
    }

    void startMainActivity() {
        DebugLog.e("startMainActivity");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseActivity());
        SharedPreferences.Editor spEd = sp.edit();
        spEd.putString(LOGIN_USER_NAME, mLoginEtUsername.getText().toString());
        if (mLoginCbSavePassword.isChecked()) {
            spEd.putBoolean(LOGIN_SAVE_PASSWORD, true);
            spEd.putString(LOGIN_PASSWORD, mLoginEtPassword.getText().toString());
        } else {
            spEd.remove(LOGIN_SAVE_PASSWORD);
            spEd.remove(LOGIN_PASSWORD);
        }
        spEd.putBoolean(loginAutoLogin, mLoginCbAutoLogin.isChecked());
        spEd.apply();
        beginActivity(MainActivity.class);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            beginActivity(MainActivity.class);
            //// TODO: 2016/5/31
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void checkUpdate() {
        HashMap<String, String> formBody = new HashMap<String, String>();
        String versionTemp = "0";
        try {
            PackageManager pm = getBaseActivity().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getBaseActivity().getPackageName(), 0);
            versionTemp = pi.versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        SoapRequestStruct soapRequestStruct = new SoapRequestStruct();
        soapRequestStruct.setServiceNameSpace(WS_NameSpace);
        soapRequestStruct.setServiceUrl(SERVICE_URL);
        soapRequestStruct.setMethodName(WS_Method_checkUpdate);
        String content =
                WebServiceUtil.buildItem(appname, defaultSoftName)+
                        WebServiceUtil.buildItem(versionCode, versionTemp);

        String xmlString =
                WebServiceUtil.buildXml(content);
        soapRequestStruct.addProperty(WS_Property_Binding,xmlString);
        DebugLog.e("WS_Property_Binding: " + xmlString);

        new WebServiceTask(LoginActivity.this, "检查更新...",soapRequestStruct, checkUpdateListener).execute();
    }
    WebServiceListener checkUpdateListener = new WebServiceListener() {
        @Override
        public void onSuccess(String content) {
            if (StringUtils.isEmpty(content)){
                return;
            }
            String needupdate = "0";
            String versionName = "";
            String updateInfo = "";
            String downloadURL = "";
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(new StringReader(content));
                int event = parser.getEventType();
                String currentTag;
                while (event != XmlPullParser.END_DOCUMENT) {
                    switch (event) {
                        case XmlPullParser.START_TAG:
                            currentTag = parser.getName();
                            if (StringUtils.equalsIgnoreCase(currentTag,"needupdate")){
                                needupdate = parser.nextText();
                            } else if (StringUtils.equalsIgnoreCase(currentTag,"versionname")){
                                versionName = parser.nextText();
                            } else if (StringUtils.equalsIgnoreCase(currentTag,"downloadurl")){
                                downloadURL = parser.nextText();
                            } else if (StringUtils.equalsIgnoreCase(currentTag,"updateinfo")){
                                updateInfo = parser.nextText();
                            }
                            break;
                        default:
                            break;
                    }
                    event = parser.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (StringUtils.equalsIgnoreCase(needupdate,"0")){
                login();
            } else if (StringUtils.equalsIgnoreCase(needupdate,"1")){
                String finalDownloadURL = downloadURL;
                new AlertDialog.Builder(getBaseActivity())
                        .setTitle("升级信息提示")
                        .setMessage("发现新版本：" + versionName
                                + "\n更新内容：" + updateInfo)
                        .setNegativeButton("现在升级", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new HttpDownloadTask(
                                        getBaseActivity(), "下载中...", tagDownloadNewFile,
                                        finalDownloadURL,
                                        updateFolder + defaultSoftName+".apk").execute();
                            }
                        })
                        .setPositiveButton("以后再说", null)
                        .setCancelable(false)
                        .show();
            }
        }

        @Override
        public void onFailure(String content) {
            login();
        }

        @Override
        public void onError(Exception exception) {
            login();
        }
    };

    void login() {
        String userName = mLoginEtUsername.getText().toString();
        String password = mLoginEtPassword.getText().toString();

        SoapRequestStruct soapRequestStruct = new SoapRequestStruct();
        soapRequestStruct.setServiceNameSpace(WS_NameSpace);
        soapRequestStruct.setServiceUrl(SERVICE_URL);
        soapRequestStruct.setMethodName(WS_Method_loginVerify);
        String content =
                WebServiceUtil.buildItem("usercode", userName)+
                        WebServiceUtil.buildItem("password", password);

        String xmlString =
                WebServiceUtil.buildXml(content);
        soapRequestStruct.addProperty(WS_Property_Binding,xmlString);
        DebugLog.e("WS_Property_Binding: " + xmlString);

        new WebServiceTask(LoginActivity.this, "登陆中...",soapRequestStruct, loginListener).execute();

    }

    WebServiceListener loginListener = new WebServiceListener() {
        @Override
        public void onSuccess(String content) {
            if (StringUtils.isEmpty(content)){
                return;
            }
            String state = "";
            String result = "";
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(new StringReader(content));
                int event = parser.getEventType();
                String currentTag;
                while (event != XmlPullParser.END_DOCUMENT) {
                    switch (event) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            currentTag = parser.getName();
                            if (StringUtils.equalsIgnoreCase(currentTag,"state")){
                                state = parser.nextText();
                            } else if (StringUtils.equalsIgnoreCase(currentTag,"result")){
                                result = parser.nextText();
                            }
                            break;
                        case XmlPullParser.TEXT:
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                        default:
                            break;
                    }
                    event = parser.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (NumberUtils.isNumber(state)){
                int i = NumberUtils.toInt(state);
                if (i == 0){
                    startMainActivity();
                }else {
                    showMessage(result);
                }
            }
        }

        @Override
        public void onFailure(String content) {
            showToast("登陆失败，请稍后重试");
        }

        @Override
        public void onError(Exception exception) {
            showToast("网络异常，请稍后重试");
        }
    };
}