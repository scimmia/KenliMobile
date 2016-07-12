package com.jiayusoft.mobile.kenli.suifangdengji;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import com.google.gson.Gson;
import com.jiayusoft.mobile.kenli.BaseApplication;
import com.jiayusoft.mobile.kenli.R;
import com.jiayusoft.mobile.kenli.utils.DebugLog;
import com.jiayusoft.mobile.kenli.utils.GlobalData;
import com.jiayusoft.mobile.kenli.utils.app.BaseActivity;
import com.jiayusoft.mobile.kenli.utils.app.dialog.DialogListener;
import com.jiayusoft.mobile.kenli.utils.app.listener.HideKeyboardListener;
import com.jiayusoft.mobile.kenli.utils.io.FileUtil;
import com.jiayusoft.mobile.kenli.utils.webservice.SoapRequestStruct;
import com.jiayusoft.mobile.kenli.utils.webservice.WebServiceListener;
import com.jiayusoft.mobile.kenli.utils.webservice.WebServiceTask;
import com.jiayusoft.mobile.kenli.utils.webservice.WebServiceUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by A on 2016/5/31.
 */
public class SuifangUploadActivity extends BaseActivity {
    @Bind(R.id.isCondomAllergy)
    EditText isCondomAllergy;
    @Bind(R.id.isFeelSick)
    EditText isFeelSick;
    @Bind(R.id.isMensesPatient)
    EditText isMensesPatient;
    @Bind(R.id.isBlood)
    EditText isBlood;
    @Bind(R.id.bloodLast)
    EditText bloodLast;
    @Bind(R.id.bloodTimes)
    EditText bloodTimes;
    @Bind(R.id.isBreastPain)
    EditText isBreastPain;
    @Bind(R.id.getbookdate)
    EditText getbookdate;
    @Bind(R.id.lastMensesDate)
    EditText lastMensesDate;
    @Bind(R.id.pregnancyVisitTime)
    EditText pregnancyVisitTime;
    @Bind(R.id.isConceived)
    EditText isConceived;
    @Bind(R.id.hasBeenToldHealth)
    EditText hasBeenToldHealth;
    @Bind(R.id.bornStatus)
    EditText bornStatus;
    @Bind(R.id.bornDate)
    EditText bornDate;
    @Bind(R.id.childtype)
    EditText childtype;
    @Bind(R.id.isBodyOk)
    EditText isBodyOk;
    @Bind(R.id.isHot)
    EditText isHot;
    @Bind(R.id.isStomachPain)
    EditText isStomachPain;
    @Bind(R.id.isVaginaBlood)
    EditText isVaginaBlood;
    @Bind(R.id.isBabyHealthy)
    EditText isBabyHealthy;
    @Bind(R.id.birthControlAfterBorn)
    EditText birthControlAfterBorn;
    @Bind(R.id.isMensesOkPost)
    EditText isMensesOkPost;
    @Bind(R.id.isMensesChangePost)
    EditText isMensesChangePost;
    @Bind(R.id.isBloodPost)
    EditText isBloodPost;
    @Bind(R.id.isStomachPainPost)
    EditText isStomachPainPost;
    @Bind(R.id.isMensesCausePain)
    EditText isMensesCausePain;
    @Bind(R.id.isLeukorrhoeaChange)
    EditText isLeukorrhoeaChange;
    @Bind(R.id.isLeukorrhoeaWrong)
    EditText isLeukorrhoeaWrong;
    @Bind(R.id.isCircleLost)
    EditText isCircleLost;
    @Bind(R.id.hasToldCheckCircle)
    EditText hasToldCheckCircle;
    @Bind(R.id.type21IsHot)
    EditText type21IsHot;
    @Bind(R.id.type21RedPain)
    EditText type21RedPain;
    @Bind(R.id.type21IsMensesBlood)
    EditText type21IsMensesBlood;
    @Bind(R.id.type21IsFeelSick)
    EditText type21IsFeelSick;
    @Bind(R.id.type21IsStomachPain)
    EditText type21IsStomachPain;
    @Bind(R.id.type21IsWc)
    EditText type21IsWc;
    @Bind(R.id.type22IsOk)
    EditText type22IsOk;
    @Bind(R.id.type22IsMensesOk)
    EditText type22IsMensesOk;
    @Bind(R.id.type22IsKnifeOk)
    EditText type22IsKnifeOk;
    @Bind(R.id.type22IsPain)
    EditText type22IsPain;
    @Bind(R.id.type22IsMensesWrong)
    EditText type22IsMensesWrong;
    @Bind(R.id.type3IsTesticleOk)
    EditText type3IsTesticleOk;
    @Bind(R.id.type41IsPain)
    EditText type41IsPain;
    @Bind(R.id.type41IsBlood)
    EditText type41IsBlood;
    @Bind(R.id.type42IsOk)
    EditText type42IsOk;
    @Bind(R.id.type42IsPain)
    EditText type42IsPain;
    @Bind(R.id.type43IsBpChange)
    EditText type43IsBpChange;
    @Bind(R.id.type43IsWeightChange)
    EditText type43IsWeightChange;
    @Bind(R.id.type43IsYellow)
    EditText type43IsYellow;
    @Bind(R.id.type43IsMensesChange)
    EditText type43IsMensesChange;
    @Bind(R.id.type43IsMensesClose)
    EditText type43IsMensesClose;
    @Bind(R.id.type43IsLocationChange)
    EditText type43IsLocationChange;
    @Bind(R.id.type5IsKnifeOk)
    EditText type5IsKnifeOk;
    @Bind(R.id.type5IsMensesOk)
    EditText type5IsMensesOk;
    @Bind(R.id.type6IsMensesOk)
    EditText type6IsMensesOk;
    @Bind(R.id.type6IsTeachOther)
    EditText type6IsTeachOther;
    @Bind(R.id.phote_taked)
    ImageView photeTaked;
    @Bind(R.id.video_layout)
    RelativeLayout videoLayout;
    @Bind(R.id.video_taked)
    ImageView videoTaked;

    ChaXunResult mChaXunResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.main_layout).setOnClickListener(new HideKeyboardListener(SuifangUploadActivity.this));
        String jsonBody = getJsonBody();
        if (StringUtils.isNotEmpty(jsonBody)) {
            mChaXunResult = new Gson().fromJson(jsonBody, ChaXunResult.class);
        } else {
            mChaXunResult = null;
        }
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.suifangupload_activity);
    }


    @OnFocusChange({R.id.isCondomAllergy, R.id.isFeelSick, R.id.isMensesPatient, R.id.isBlood, R.id.isBreastPain, R.id.pregnancyVisitTime, R.id.isConceived, R.id.hasBeenToldHealth, R.id.bornStatus, R.id.childtype, R.id.isBodyOk, R.id.isHot, R.id.isStomachPain, R.id.isVaginaBlood, R.id.isBabyHealthy, R.id.birthControlAfterBorn, R.id.isMensesOkPost, R.id.isMensesChangePost, R.id.isBloodPost, R.id.isStomachPainPost, R.id.isMensesCausePain, R.id.isLeukorrhoeaChange, R.id.isLeukorrhoeaWrong, R.id.isCircleLost, R.id.hasToldCheckCircle, R.id.type21IsHot, R.id.type21RedPain, R.id.type21IsMensesBlood, R.id.type21IsFeelSick, R.id.type21IsStomachPain, R.id.type21IsWc, R.id.type22IsOk, R.id.type22IsMensesOk, R.id.type22IsKnifeOk, R.id.type22IsPain, R.id.type22IsMensesWrong, R.id.type3IsTesticleOk, R.id.type41IsPain, R.id.type41IsBlood, R.id.type42IsOk, R.id.type42IsPain, R.id.type43IsBpChange, R.id.type43IsWeightChange, R.id.type43IsYellow, R.id.type43IsMensesChange, R.id.type43IsMensesClose, R.id.type43IsLocationChange, R.id.type5IsKnifeOk, R.id.type5IsMensesOk, R.id.type6IsMensesOk, R.id.type6IsTeachOther})
    public void onFocusChange(View view, boolean focus) {
        if (focus) {
            onClick(view);
        }
    }

    @OnClick({R.id.isCondomAllergy, R.id.isFeelSick, R.id.isMensesPatient, R.id.isBlood, R.id.isBreastPain, R.id.pregnancyVisitTime, R.id.isConceived, R.id.hasBeenToldHealth, R.id.bornStatus, R.id.childtype, R.id.isBodyOk, R.id.isHot, R.id.isStomachPain, R.id.isVaginaBlood, R.id.isBabyHealthy, R.id.birthControlAfterBorn, R.id.isMensesOkPost, R.id.isMensesChangePost, R.id.isBloodPost, R.id.isStomachPainPost, R.id.isMensesCausePain, R.id.isLeukorrhoeaChange, R.id.isLeukorrhoeaWrong, R.id.isCircleLost, R.id.hasToldCheckCircle, R.id.type21IsHot, R.id.type21RedPain, R.id.type21IsMensesBlood, R.id.type21IsFeelSick, R.id.type21IsStomachPain, R.id.type21IsWc, R.id.type22IsOk, R.id.type22IsMensesOk, R.id.type22IsKnifeOk, R.id.type22IsPain, R.id.type22IsMensesWrong, R.id.type3IsTesticleOk, R.id.type41IsPain, R.id.type41IsBlood, R.id.type42IsOk, R.id.type42IsPain, R.id.type43IsBpChange, R.id.type43IsWeightChange, R.id.type43IsYellow, R.id.type43IsMensesChange, R.id.type43IsMensesClose, R.id.type43IsLocationChange, R.id.type5IsKnifeOk, R.id.type5IsMensesOk, R.id.type6IsMensesOk, R.id.type6IsTeachOther})
    public void onClick(View view) {
        String[] itemNames = GlobalData.shifouNames;
        String[] itemIDs = GlobalData.shifouIDs;

        switch (view.getId()) {
            case R.id.pregnancyVisitTime:
                itemNames = GlobalData.fangshijiluNames;
                itemIDs = GlobalData.fangshijiluIDs;
                break;
            case R.id.bornStatus:
                itemNames = GlobalData.shengyuqingkuangNames;// TODO: 2016/6/1  
                itemIDs = GlobalData.shengyuqingkuangIDs;
                break;
            case R.id.childtype:
                itemNames = GlobalData.haiciNames;
                itemIDs = GlobalData.haiciIDs;
                break;
            case R.id.birthControlAfterBorn:
                itemNames = GlobalData.biyuncuoshiNames;
                itemIDs = GlobalData.biyuncuoshiIDs;
                break;
        }

        if (view instanceof EditText) {
            EditText editText = (EditText) view;

            showSingleDialog("", itemNames, itemIDs, (String) editText.getTag(), new DialogListener() {
                @Override
                public void onSelected(String name, String id) {
//                    DebugLog.e(name + "\t" + id);
                    editText.setTag(id);
                    editText.setText(name);
                }

                @Override
                public void onClear() {
//                    DebugLog.e("onClear");
                    editText.setTag("");
                    editText.setText("");
                }
            });
        }
    }

    @OnFocusChange({R.id.getbookdate, R.id.lastMensesDate, R.id.bornDate})
    public void onDateFocusChange(View view, boolean focus) {
        if (focus) {
            onDateClick(view);
        }
    }

    @OnClick({R.id.getbookdate, R.id.lastMensesDate, R.id.bornDate})
    public void onDateClick(View view) {
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            showDateDialog((String) editText.getTag(), new DialogListener() {
                @Override
                public void onSelected(String name, String id) {
//                    DebugLog.e(name +"\t"+ id);
                    editText.setTag(id);
                    editText.setText(name);
                }

                @Override
                public void onClear() {
                    DebugLog.e("onClear");
                    editText.setTag("");
                    editText.setText("");
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_suifangupload, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
//            beginActivity(SuifangdengjiActivity.class);
            DebugLog.e((String) isCondomAllergy.getTag());
            DebugLog.e((String) isFeelSick.getTag());
            DebugLog.e((String) isMensesPatient.getTag());
            uploadSuifang();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void uploadSuifang() {
        SoapRequestStruct soapRequestStruct = new SoapRequestStruct();
        soapRequestStruct.setServiceNameSpace(WS_NameSpace);
        soapRequestStruct.setServiceUrl(SERVICE_URL);
        soapRequestStruct.setMethodName(WS_Method_uploadsave);
        String content =
                WebServiceUtil.buildItem("femaleId", mChaXunResult.getFemaleId()) +
                        WebServiceUtil.buildItem("femaleName", mChaXunResult.getFemaleName()) +
                        WebServiceUtil.buildItem("femaleBirthday", mChaXunResult.getFemaleBirthday()) +
                        WebServiceUtil.buildItem("femaleMaritalStatus", mChaXunResult.getFemaleMaritalStatus()) +
                        WebServiceUtil.buildItem("maleName", mChaXunResult.getMaleName()) +
                        WebServiceUtil.buildItem("maleBirthday", mChaXunResult.getMaleBirthday()) +
                        WebServiceUtil.buildItem("maleMaritalStatus", mChaXunResult.getMaleMaritalStatus()) +
                        WebServiceUtil.buildItem("maritalChangeDate", mChaXunResult.getMaritalChangeDate()) +
                        WebServiceUtil.buildItem("boyAmount", mChaXunResult.getBoyAmount()) +
                        WebServiceUtil.buildItem("girlAmount", mChaXunResult.getGirlAmount()) +
//                        WebServiceUtil.buildItem("longTimeMeasure", mChaXunResult.getti)+
//                        WebServiceUtil.buildItem("fixDate", mChaXunResult.get)+
                        WebServiceUtil.buildItem("orgid", mChaXunResult.getOrgid()) +
                        WebServiceUtil.buildItem("orgname", mChaXunResult.getOrgname()) +
//                        WebServiceUtil.buildItem("visitDate", mChaXunResult.getvi)+
                        WebServiceUtil.buildItem("isCondomAllergy", (String) isCondomAllergy.getTag()) +
                        WebServiceUtil.buildItem("isFeelSick", (String) isFeelSick.getTag()) +
                        WebServiceUtil.buildItem("isMensesPatient", (String) isMensesPatient.getTag()) +
                        WebServiceUtil.buildItem("isBlood", (String) isBlood.getTag()) +
                        WebServiceUtil.buildItem("bloodLast", String.valueOf(bloodLast.getText())) +
                        WebServiceUtil.buildItem("bloodTimes", String.valueOf(bloodTimes.getText())) +
                        WebServiceUtil.buildItem("isBreastPain", (String) isBreastPain.getTag()) +
                        WebServiceUtil.buildItem("getbookdate", getbookdate.getText().toString()) +
                        WebServiceUtil.buildItem("lastMensesDate", lastMensesDate.getText().toString()) +
                        WebServiceUtil.buildItem("pregnancyVisitTime", (String) pregnancyVisitTime.getTag()) +
                        WebServiceUtil.buildItem("isConceived", (String) isConceived.getTag()) +
                        WebServiceUtil.buildItem("hasBeenToldHealth", (String) hasBeenToldHealth.getTag()) +
                        WebServiceUtil.buildItem("bornStatus", (String) bornStatus.getTag()) +
                        WebServiceUtil.buildItem("bornDate", bornDate.getText().toString()) +
                        WebServiceUtil.buildItem("childtype", (String) childtype.getTag()) +
                        WebServiceUtil.buildItem("isBodyOk", (String) isBodyOk.getTag()) +
                        WebServiceUtil.buildItem("isHot", (String) isHot.getTag()) +
                        WebServiceUtil.buildItem("isStomachPain", (String) isStomachPain.getTag()) +
                        WebServiceUtil.buildItem("isVaginaBlood", (String) isVaginaBlood.getTag()) +
                        WebServiceUtil.buildItem("isBabyHealthy", (String) isBabyHealthy.getTag()) +
                        WebServiceUtil.buildItem("birthControlAfterBorn", (String) birthControlAfterBorn.getTag()) +
                        WebServiceUtil.buildItem("isMensesOkPost", (String) isMensesOkPost.getTag()) +
                        WebServiceUtil.buildItem("isMensesChangePost", (String) isMensesChangePost.getTag()) +
                        WebServiceUtil.buildItem("isBloodPost", (String) isBloodPost.getTag()) +
                        WebServiceUtil.buildItem("isStomachPainPost", (String) isStomachPainPost.getTag()) +
                        WebServiceUtil.buildItem("isMensesCausePain", (String) isMensesCausePain.getTag()) +
                        WebServiceUtil.buildItem("isLeukorrhoeaChange", (String) isLeukorrhoeaChange.getTag()) +
                        WebServiceUtil.buildItem("isLeukorrhoeaWrong", (String) isLeukorrhoeaWrong.getTag()) +
                        WebServiceUtil.buildItem("isCircleLost", (String) isCircleLost.getTag()) +
                        WebServiceUtil.buildItem("hasToldCheckCircle", (String) hasToldCheckCircle.getTag()) +
                        WebServiceUtil.buildItem("type21IsHot", (String) type21IsHot.getTag()) +
                        WebServiceUtil.buildItem("type21RedPain", (String) type21RedPain.getTag()) +
                        WebServiceUtil.buildItem("type21IsMensesBlood", (String) type21IsMensesBlood.getTag()) +
                        WebServiceUtil.buildItem("type21IsFeelSick", (String) type21IsFeelSick.getTag()) +
                        WebServiceUtil.buildItem("type21IsStomachPain", (String) type21IsStomachPain.getTag()) +
                        WebServiceUtil.buildItem("type21IsWc", (String) type21IsWc.getTag()) +
                        WebServiceUtil.buildItem("type22IsOk", (String) type22IsOk.getTag()) +
                        WebServiceUtil.buildItem("type22IsMensesOk", (String) type22IsMensesOk.getTag()) +
                        WebServiceUtil.buildItem("type22IsKnifeOk", (String) type22IsKnifeOk.getTag()) +
                        WebServiceUtil.buildItem("type22IsPain", (String) type22IsPain.getTag()) +
                        WebServiceUtil.buildItem("type22IsMensesWrong", (String) type22IsMensesWrong.getTag()) +
                        WebServiceUtil.buildItem("type3IsTesticleOk", (String) type3IsTesticleOk.getTag()) +
                        WebServiceUtil.buildItem("type41IsPain", (String) type41IsPain.getTag()) +
                        WebServiceUtil.buildItem("type41IsBlood", (String) type41IsBlood.getTag()) +
                        WebServiceUtil.buildItem("type42IsOk", (String) type42IsOk.getTag()) +
                        WebServiceUtil.buildItem("type42IsPain", (String) type42IsPain.getTag()) +
                        WebServiceUtil.buildItem("type43IsBpChange", (String) type43IsBpChange.getTag()) +
                        WebServiceUtil.buildItem("type43IsWeightChange", (String) type43IsWeightChange.getTag()) +
                        WebServiceUtil.buildItem("type43IsYellow", (String) type43IsYellow.getTag()) +
                        WebServiceUtil.buildItem("type43IsMensesChange", (String) type43IsMensesChange.getTag()) +
                        WebServiceUtil.buildItem("type43IsMensesClose", (String) type43IsMensesClose.getTag()) +
                        WebServiceUtil.buildItem("type43IsLocationChange", (String) type43IsLocationChange.getTag()) +
                        WebServiceUtil.buildItem("type5IsKnifeOk", (String) type5IsKnifeOk.getTag()) +
                        WebServiceUtil.buildItem("type5IsMensesOk", (String) type5IsMensesOk.getTag()) +
                        WebServiceUtil.buildItem("type6IsMensesOk", (String) type6IsMensesOk.getTag()) +
                        WebServiceUtil.buildItem("type6IsTeachOther", (String) type6IsTeachOther.getTag());

        String xmlString = WebServiceUtil.buildXml(content);
        xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><root><request><femaleId>370521105207000017</femaleId><femaleName>秦玉玲</femaleName><femaleBirthday>1968-02-20</femaleBirthday><femaleMaritalStatus>21</femaleMaritalStatus><maleName>刘明安</maleName><maleBirthday>1969-03-12</maleBirthday><maleMaritalStatus>21</maleMaritalStatus><maritalChangeDate>1991-03-07</maritalChangeDate><boyAmount>1</boyAmount><girlAmount>1</girlAmount><orgid>370521105207</orgid><orgname>永胜村委会</orgname><isCondomAllergy>1</isCondomAllergy><isFeelSick>1</isFeelSick><isMensesPatient>0</isMensesPatient><isBlood>0</isBlood><bloodLast>25</bloodLast><bloodTimes>25</bloodTimes><isBreastPain>1</isBreastPain><getbookdate>2016-02-12</getbookdate><lastMensesDate>2016-05-12</lastMensesDate><pregnancyVisitTime>7</pregnancyVisitTime><isConceived>1</isConceived><hasBeenToldHealth>0</hasBeenToldHealth><bornStatus>1</bornStatus><bornDate>1999-01-12</bornDate><childtype>5</childtype><isBodyOk>0</isBodyOk><isHot>1</isHot><isStomachPain>0</isStomachPain><isVaginaBlood>1</isVaginaBlood><isBabyHealthy>0</isBabyHealthy><birthControlAfterBorn>600</birthControlAfterBorn><isMensesOkPost>1</isMensesOkPost><isMensesChangePost>1</isMensesChangePost><isBloodPost>0</isBloodPost><isStomachPainPost>1</isStomachPainPost><isMensesCausePain>1</isMensesCausePain><isLeukorrhoeaChange>1</isLeukorrhoeaChange><isLeukorrhoeaWrong>1</isLeukorrhoeaWrong><isCircleLost>1</isCircleLost><hasToldCheckCircle>0</hasToldCheckCircle><type21IsHot>0</type21IsHot><type21RedPain>0</type21RedPain><type21IsMensesBlood>1</type21IsMensesBlood><type21IsFeelSick>0</type21IsFeelSick><type21IsStomachPain>1</type21IsStomachPain><type21IsWc>0</type21IsWc><type22IsOk>1</type22IsOk><type22IsMensesOk>0</type22IsMensesOk><type22IsKnifeOk>0</type22IsKnifeOk><type22IsPain>1</type22IsPain><type22IsMensesWrong>0</type22IsMensesWrong><type3IsTesticleOk>0</type3IsTesticleOk><type41IsPain>0</type41IsPain><type41IsBlood>0</type41IsBlood><type42IsOk>0</type42IsOk><type42IsPain>1</type42IsPain><type43IsBpChange>1</type43IsBpChange><type43IsWeightChange>0</type43IsWeightChange><type43IsYellow>0</type43IsYellow><type43IsMensesChange>0</type43IsMensesChange><type43IsMensesClose>1</type43IsMensesClose><type43IsLocationChange>1</type43IsLocationChange><type5IsKnifeOk>0</type5IsKnifeOk><type5IsMensesOk>0</type5IsMensesOk><type6IsMensesOk>0</type6IsMensesOk><type6IsTeachOther>0</type6IsTeachOther><imageName>123</imageName><videoName>321</videoName><fixDate>2016-06-11</fixDate><longTimeMeasure>321</longTimeMeasure><visitDate>2016-01-01 02:08:42</visitDate></request></root>";
        soapRequestStruct.addProperty(WS_Property_Binding, xmlString);
//        soapRequestStruct.addProperty("image","");
//        soapRequestStruct.addProperty("video","");
        DebugLog.e("WS_Property_Binding: " + xmlString);

        new WebServiceTask(SuifangUploadActivity.this, "提交中...", soapRequestStruct, uploadListener).execute();

    }

    WebServiceListener uploadListener = new WebServiceListener() {
        @Override
        public void onSuccess(String content) {
//            try {
//                Bundle bundle = new Bundle();
//                bundle.putString(JsonBody,content);
//                beginActivity(ChaxunResultActivity.class, bundle);
//            } catch (Exception e) {
//                DebugLog.e("JSON exception"+ e.getMessage());
//                e.printStackTrace();
//            }
            DebugLog.d("XML   " + content);
            if (StringUtils.contains(content,"<result>0</result>")) {
                showToast("提交成功。");
            }else{
                showToast("提交失败，请稍后重试");
            }
        }

        @Override
        public void onFailure(String content) {
            showToast("提交失败，请稍后重试");
        }

        @Override
        public void onError(Exception exception) {
            showToast("网络异常，请稍后重试");
        }
    };

    final String picTempPath = picFolder + "temp.jpg";
    final String picPath = picFolder + "photo.jpg";
    final String videoTempPath = picFolder + "temp.mp4";
    final int addPhoto = 102;
    final int addVideo = 103;

    //拍照
    @OnClick(R.id.photo_add)
    public void onAddPhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            File file = new File(picTempPath);
            if (file.exists()) {
                file.delete();
            }
            FileUtil.createFile(picTempPath);
            Uri fileUri = Uri.fromFile(file);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(takePictureIntent, addPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.photo_delete)
    public void onDeletePhoto(View view) {
        try {
            photeTaked.setVisibility(View.GONE);
            File file = new File(picPath);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //摄像
    @OnClick(R.id.video_add)
    public void onAddVideo(View view) {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        try {
            File file = new File(videoTempPath);
            if (file.exists()) {
                file.delete();
            }
            FileUtil.createFile(videoTempPath);
            Uri fileUri = Uri.fromFile(file);
            takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT , 10);
            takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(takeVideoIntent, addVideo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.video_delete)
    public void onDeleteVideo(View view) {
        try {
            videoLayout.setVisibility(View.GONE);
            File file = new File(videoTempPath);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.video_taked)
    public void onPreviewVideo(View view) {
        try {
            File file = new File(videoTempPath);
            if (file.exists()) {
                Uri uri = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "video/mp4");
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case addPhoto:
                    try {
                        DebugLog.e("addPhoto");
                        File file = new File(picPath);
                        if (file.exists()) {
                            file.delete();
                        }
                        saveBitmapToFile(new File(picTempPath), picPath);
                        photeTaked.setVisibility(View.VISIBLE);
                        BaseApplication.loadImage(SuifangUploadActivity.this,photeTaked,picPath,R.drawable.ic_empty);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case addVideo:
                    try {
                        DebugLog.e("addVideo");
                        videoLayout.setVisibility(View.VISIBLE);
                        BaseApplication.loadImage(SuifangUploadActivity.this,videoTaked,videoTempPath,R.drawable.ic_empty);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private String saveBitmapToFile(File file, String newpath) {
        try {
            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            File aa = new File(newpath);
            FileOutputStream outputStream = new FileOutputStream(aa);
            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            String filepath = aa.getAbsolutePath();
            DebugLog.e("getAbsolutePath" + aa.getAbsolutePath());

            return filepath;
        } catch (Exception e) {
            return null;
        }
    }
}