package com.jiayusoft.mobile.kenli.suifangdengji;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import butterknife.Bind;
import com.jiayusoft.mobile.kenli.R;
import com.jiayusoft.mobile.kenli.utils.DebugLog;
import com.jiayusoft.mobile.kenli.utils.GlobalData;
import com.jiayusoft.mobile.kenli.utils.app.BaseActivity;
import com.jiayusoft.mobile.kenli.utils.app.dialog.DialogListener;
import com.jiayusoft.mobile.kenli.utils.app.widget.JiayuSpinner;
import com.jiayusoft.mobile.kenli.utils.webservice.xmljson.JSONException;
import com.jiayusoft.mobile.kenli.utils.webservice.xmljson.JSONObject;
import com.jiayusoft.mobile.kenli.utils.webservice.xmljson.XML;

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
    JiayuSpinner spQuxian;
    @Bind(R.id.sp_jiedao)
    JiayuSpinner spJiedao;
    @Bind(R.id.sp_shequ)
    JiayuSpinner spShequ;
    @Bind(R.id.et_xingming)
    EditText etXingming;
    @Bind(R.id.et_shenfenzheng)
    EditText etShenfenzheng;
    @Bind(R.id.sp_shifousuifang)
    JiayuSpinner spShifousuifang;
    @Bind(R.id.et_suifangjiandingshijian)
    EditText etSuifangjiandingshijian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
//        showDateDialog(null, new DialogListener() {
//            @Override
//            public void onSelected(String name, String id) {
//                DebugLog.e(name + id);
//            }
//
//            @Override
//            public void onClear() {
//                DebugLog.e("onClear");
//
//            }
//        });

        showSingleDialog("选择区县", GlobalData.hunyinzhuangkuangNames,
                GlobalData.hunyinzhuangkuangIDs, GlobalData.hunyinzhuangkuangIDs[3], new DialogListener() {
            @Override
            public void onSelected(String name, String id) {
                DebugLog.e(name + id);

            }

            @Override
            public void onClear() {
                DebugLog.e("onClear");

            }
        });
    }
}
