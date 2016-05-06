package com.jiayusoft.mobile.kenli.suifangdengji;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import com.google.gson.Gson;
import com.jiayusoft.mobile.kenli.R;
import com.jiayusoft.mobile.kenli.utils.DebugLog;
import com.jiayusoft.mobile.kenli.utils.GlobalData;
import com.jiayusoft.mobile.kenli.utils.app.BaseActivity;
import com.jiayusoft.mobile.kenli.utils.app.dialog.DialogListener;
import com.jiayusoft.mobile.kenli.utils.app.listener.HideKeyboardListener;
import com.jiayusoft.mobile.kenli.utils.database.DBHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.LinkedList;

public class ChaxunActivity extends BaseActivity {
//    @Bind(R.id.sp_quxian)
//    MaterialEditText spQuxian;
//    @Bind(R.id.sp_jiedao)
//    MaterialEditText spJiedao;
//    @Bind(R.id.sp_shequ)
//    MaterialEditText spShequ;
//    @Bind(R.id.xingming)
//    MaterialEditText xingming;
//    @Bind(R.id.shenfenzheng)
//    MaterialEditText shenfenzheng;
//    @Bind(R.id.sp_shifousuifang)
//    MaterialEditText spShifousuifang;
//    @Bind(R.id.et_suifangjiandingshijian)
//    MaterialEditText etSuifangjiandingshijian;

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
    @Bind(R.id.sp_shifousuifang)
    EditText spShifousuifang;
    @Bind(R.id.et_suifangjiandingshijian)
    EditText etSuifangjiandingshijian;

    DBHelper mDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDBHelper = new DBHelper(ChaxunActivity.this);
        findViewById(R.id.main_layout).setOnClickListener(new HideKeyboardListener(ChaxunActivity.this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            mDBHelper.close();
        }catch (Exception e){
            DebugLog.e(e.getMessage());
        }
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_dengjichaxun);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chaxun, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        String sampleXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
//                + "<mobilegate>"
//                +"<timestamp>232423423423</timestamp>"
//                + "<txn>" + "Transaction" + "</txn>"
//                + "<amt>" + 0 + "</amt>"
//                + "</mobilegate>";
//        JSONObject jsonObj = null;
//        try {
//            jsonObj = XML.toJSONObject(sampleXml);
//        } catch (JSONException e) {
//            DebugLog.e("JSON exception"+ e.getMessage());
//            e.printStackTrace();
//        }
//
//        DebugLog.d("XML   "+ sampleXml);
//
//        DebugLog.d("JSON   "+jsonObj.toString());
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Bundle bundle = new Bundle();
//            bundle.putString(XMLBody,
//                    "<xiancode>370521</xiancode>\n" +
//                            "<jdcode>370521001</jdcode>\n" +
//                            "<jwhcode>370521001001</jwhcode>\n" +
//                            "<xm></xm>\n" +
//                            "<sfzh></sfzh>\n" +
//                            "<flag>0</flag>\n" +
//                            "<sfrq></sfrq>");
//            beginActivity(ChaxunResultActivity.class, bundle);
//            return true;
//        }


        test();
        return super.onOptionsItemSelected(item);
    }

    void test(){

///////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////
        DBHelper dbHelper = new DBHelper(ChaxunActivity.this);
        LinkedList<MutablePair<String,String>> m = dbHelper.getAddress("3705");
        DebugLog.e(new Gson().toJson(m));
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
    @OnFocusChange(R.id.sp_shifousuifang)
    void onsp_shifousuifangFocus(boolean focused) {
        if (focused) {
            onsp_shifousuifang();
        }
    }

    @OnClick(R.id.sp_shifousuifang)
    void onsp_shifousuifang() {
        DebugLog.e("onsp_shifousuifang");
        showSingleDialog("", GlobalData.shifouNames,
                GlobalData.shifouIDs, (String) spShifousuifang.getTag(), new DialogListener() {
                    @Override
                    public void onSelected(String name, String id) {
                        DebugLog.e(name +"\t"+ id);
                        spShifousuifang.setTag(id);
                        spShifousuifang.setText(name);
                    }

                    @Override
                    public void onClear() {
                        DebugLog.e("onClear");
                        spShifousuifang.setTag("");
                        spShifousuifang.setText("");
                    }
                });
    }

    @OnFocusChange(R.id.et_suifangjiandingshijian)
    void onet_suifangjiandingshijianFocus(boolean focused) {
        if (focused) {
            onet_suifangjiandingshijian();
        }
    }

    @OnClick(R.id.et_suifangjiandingshijian)
    void onet_suifangjiandingshijian() {
        DebugLog.e("onet_suifangjiandingshijian");
        showDateDialog((String) etSuifangjiandingshijian.getTag(), new DialogListener() {
            @Override
            public void onSelected(String name, String id) {
                DebugLog.e(name +"\t"+ id);
                etSuifangjiandingshijian.setTag(id);
                etSuifangjiandingshijian.setText(name);
            }

            @Override
            public void onClear() {
                DebugLog.e("onClear");
                etSuifangjiandingshijian.setTag("");
                etSuifangjiandingshijian.setText("");
            }
        });
    }

}
