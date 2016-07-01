package com.jiayusoft.mobile.kenli.wis;

import android.app.Activity;
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
import com.jiayusoft.mobile.kenli.utils.webservice.WebServiceUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.LinkedList;

/**
 * Created by A on 2016/7/1.
 */
public class WisSearchActivity extends BaseActivity {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.main_layout).setOnClickListener(new HideKeyboardListener(WisSearchActivity.this));
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_wis_search);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chaxun, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            String content =
                    WebServiceUtil.buildItem("xiancode", (String) spQuxian.getTag())+
                            WebServiceUtil.buildItem("jdcode", (String) spJiedao.getTag())+
                            WebServiceUtil.buildItem("jwhcode", (String) spShequ.getTag())+
                            WebServiceUtil.buildItem("xm", String.valueOf(etXingming.getText()))+
                            WebServiceUtil.buildItem("sfzh", String.valueOf(etShenfenzheng.getText()));
            Bundle bundle = new Bundle();
            bundle.putString(JsonBody, content);
            beginActivity(WisResultActivity.class,bundle);
            return true;
        }


        return super.onOptionsItemSelected(item);
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
                        if (StringUtils.equals(id, (CharSequence) spQuxian.getTag())){
                            return;
                        }
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
                        if (StringUtils.equals(id, (CharSequence) spJiedao.getTag())){
                            return;
                        }
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
                        DebugLog.e(name +"\t"+ id);
                        if (StringUtils.equals(id, (CharSequence) spShequ.getTag())){
                            return;
                        }
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