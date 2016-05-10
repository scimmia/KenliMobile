package com.jiayusoft.mobile.kenli.tongxunlu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import com.jiayusoft.mobile.kenli.R;
import com.jiayusoft.mobile.kenli.utils.DebugLog;
import com.jiayusoft.mobile.kenli.utils.app.BaseActivity;
import com.jiayusoft.mobile.kenli.utils.app.dialog.DialogListener;
import com.jiayusoft.mobile.kenli.utils.app.listener.HideKeyboardListener;
import com.jiayusoft.mobile.kenli.utils.database.DBHelper;
import com.jiayusoft.mobile.kenli.utils.webservice.SoapRequestStruct;
import com.jiayusoft.mobile.kenli.utils.webservice.WebServiceTask;
import com.jiayusoft.mobile.kenli.utils.webservice.WebServiceUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.LinkedList;
//import com.rengwuxian.materialedittext.MaterialEditText;

public class TongxunluSearchActivity extends BaseActivity {

    @Bind(R.id.sp_quxian)
    EditText spQuxian;
    @Bind(R.id.sp_jiedao)
    EditText spJiedao;
    @Bind(R.id.sp_shequ)
    EditText spShequ;
    @Bind(R.id.et_xingming)
    EditText etXingming;
    @Bind(R.id.et_shenfenzheng)
    EditText etShenfenzheng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.main_layout).setOnClickListener(new HideKeyboardListener(TongxunluSearchActivity.this));

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

    String[] addressNames;
    String[] addressIDs;
    @OnFocusChange(R.id.sp_quxian)
    void onsp_quxianFocus(boolean focused) {
        if (focused) {
            onsp_quxian();
        }
    }

    @OnClick(R.id.sp_quxian)
    void onsp_quxian() {
        DebugLog.e("onsp_quxian");
        LinkedList<MutablePair<String,String>> m = mDBHelper.getAddress("3705");
        addressNames = new String[m.size()];
        addressIDs = new String[m.size()];
        for (int i=0;i<m.size();i++){
            addressNames[i] = m.get(i).getLeft();
            addressIDs[i] = m.get(i).getRight();
        }
        showSingleDialog("", addressNames,
                addressIDs, (String) spQuxian.getTag(), new DialogListener() {
                    @Override
                    public void onSelected(String name, String id) {
                        DebugLog.e(name +"\t"+ id);
                        spQuxian.setTag(id);
                        spQuxian.setText(name);
                        spJiedao.setTag("");
                        spJiedao.setText("");
                        spShequ.setTag("");
                        spShequ.setText("");
                    }

                    @Override
                    public void onClear() {
                        DebugLog.e("onClear");
                        spQuxian.setTag("");
                        spQuxian.setText("");
                        spJiedao.setTag("");
                        spJiedao.setText("");
                        spShequ.setTag("");
                        spShequ.setText("");
                    }
                });
    }

    @OnFocusChange(R.id.sp_jiedao)
    void onsp_jiedaoFocus(boolean focused) {
        if (focused) {
            onsp_jiedao();
        }
    }

    @OnClick(R.id.sp_jiedao)
    void onsp_jiedao() {
        DebugLog.e("onsp_jiedao");
        if (StringUtils.isEmpty((String) spQuxian.getTag())){
            showToast("请先选择区县");
            return;
        }
        LinkedList<MutablePair<String,String>> m = mDBHelper.getAddress((String) spQuxian.getTag());
        addressNames = new String[m.size()];
        addressIDs = new String[m.size()];
        for (int i=0;i<m.size();i++){
            addressNames[i] = m.get(i).getLeft();
            addressIDs[i] = m.get(i).getRight();
        }
        showSingleDialog("", addressNames,
                addressIDs, (String) spJiedao.getTag(), new DialogListener() {
                    @Override
                    public void onSelected(String name, String id) {
                        DebugLog.e(name +"\t"+ id);
                        spJiedao.setTag(id);
                        spJiedao.setText(name);
                        spShequ.setTag("");
                        spShequ.setText("");
                    }

                    @Override
                    public void onClear() {
                        DebugLog.e("onClear");
                        spJiedao.setTag("");
                        spJiedao.setText("");
                        spShequ.setTag("");
                        spShequ.setText("");
                    }
                });
    }

    @OnFocusChange(R.id.sp_shequ)
    void onsp_shequFocus(boolean focused) {
        if (focused) {
            onsp_shequ();
        }
    }

    @OnClick(R.id.sp_shequ)
    void onsp_shequ() {
        DebugLog.e("onsp_shequ");
        if (StringUtils.isEmpty((String) spJiedao.getTag())){
            showToast("请先选择街道(乡镇)");
            return;
        }
        LinkedList<MutablePair<String,String>> m = mDBHelper.getAddress((String) spJiedao.getTag());
        addressNames = new String[m.size()];
        addressIDs = new String[m.size()];
        for (int i=0;i<m.size();i++){
            addressNames[i] = m.get(i).getLeft();
            addressIDs[i] = m.get(i).getRight();
        }
        showSingleDialog("", addressNames,
                addressIDs, (String) spJiedao.getTag(), new DialogListener() {
                    @Override
                    public void onSelected(String name, String id) {
                        DebugLog.e(name +"\t"+ id);
                        spShequ.setTag(id);
                        spShequ.setText(name);
                    }

                    @Override
                    public void onClear() {
                        DebugLog.e("onClear");
                        spShequ.setTag("");
                        spShequ.setText("");
                    }
                });
    }
}
