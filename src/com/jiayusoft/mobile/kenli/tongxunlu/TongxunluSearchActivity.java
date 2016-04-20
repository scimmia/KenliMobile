package com.jiayusoft.mobile.kenli.tongxunlu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import com.jiayusoft.mobile.kenli.R;
import com.jiayusoft.mobile.kenli.utils.DebugLog;
import com.jiayusoft.mobile.kenli.utils.app.BaseActivity;
import com.jiayusoft.mobile.kenli.utils.webservice.SoapRequestStruct;
import com.jiayusoft.mobile.kenli.utils.webservice.WebServiceTask;
import com.jiayusoft.mobile.kenli.utils.webservice.WebServiceUtil;
//import com.rengwuxian.materialedittext.MaterialEditText;

public class TongxunluSearchActivity extends BaseActivity {

//    @Bind(R.id.quxian)
//    MaterialEditText quxian;
//    @Bind(R.id.jiedaoxiangzhen)
//    MaterialEditText jiedaoxiangzhen;
//    @Bind(R.id.shequ)
//    MaterialEditText shequ;
//    @Bind(R.id.xingming)
//    MaterialEditText xingming;
//    @Bind(R.id.shenfenzheng)
//    MaterialEditText shenfenzheng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_tongxunlu_search);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tongxunlu_search, menu);
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
            searchTongxunlu();
//            beginActivity(TongxunluResultActivity.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @OnFocusChange({R.id.quxian,R.id.jiedaoxiangzhen,R.id.shequ})
//    void OnSignleFocusChange(MaterialEditText materialEditText,boolean focused) {
//        DebugLog.e("OnSignleFocusChange"+materialEditText.getHint());
//        if (focused) {
//            OnSingleClick(materialEditText);
//        }
//    }
//
//    @OnClick({R.id.quxian,R.id.jiedaoxiangzhen,R.id.shequ})
//    void OnSingleClick(MaterialEditText materialEditText) {
//        DebugLog.e("OnSingleClick"+materialEditText.getHint());
//
//        int maxBeforeDays = getResources().getInteger(R.integer.maxBeforeDays);
////        ChooseFromToDatesDialogFragment.newInstance(beginTime, endTime,maxBeforeDays).show(getFragmentManager(), "ChooseFromToDates");
//    }

    void searchTongxunlu(){
        String xmlString =
//                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><request>" +
//                        "<usercode>123</usercode>" +
//                        "<password>321</password>" +
//                        "<phone>1112321</phone></request></root>";
//                WebServiceUtil.buildXml("getXianinfo", "<code>2</code>");
//                WebServiceUtil.buildXml("getXianinfo", "<xiancode>370521</xiancode>");
                WebServiceUtil.buildXml("getXianinfo", "<jdcode>370521001</jdcode>");
//        new WebServiceTask(TongxunluSearchActivity.this, "检查新版本...", xmlString, null).execute();
        SoapRequestStruct soapRequestStruct = new SoapRequestStruct();
        soapRequestStruct.setServiceNameSpace(WS_NameSpace);
//        soapRequestStruct.setMethodName(WS_Method_getXianinfo);
//        soapRequestStruct.setMethodName(WS_Method_getJdinfo);
        soapRequestStruct.setMethodName(WS_Method_getJwhinfo);
        soapRequestStruct.setServiceUrl(SERVICE_URL);
//            soapRequestStruct.setServiceUrl("http://58.56.20.118:9090/oa/ws/ImpData");
        soapRequestStruct.addProperty(WS_Property_Binding,xmlString);
        DebugLog.e("WS_Property_Binding: " + xmlString);

        new WebServiceTask(TongxunluSearchActivity.this, "校验中...",soapRequestStruct, null).execute();

    }
}
