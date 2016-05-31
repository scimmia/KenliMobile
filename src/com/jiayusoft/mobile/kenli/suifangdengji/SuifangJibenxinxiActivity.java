package com.jiayusoft.mobile.kenli.suifangdengji;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import butterknife.Bind;
import com.google.gson.Gson;
import com.jiayusoft.mobile.kenli.R;
import com.jiayusoft.mobile.kenli.utils.GlobalData;
import com.jiayusoft.mobile.kenli.utils.app.BaseActivity;
import com.jiayusoft.mobile.kenli.utils.app.listener.HideKeyboardListener;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Hi on 2016-5-10.
 */
public class SuifangJibenxinxiActivity extends BaseActivity {
    @Bind(R.id.et_nvfangxingming)
    EditText etNvfangxingming;
    @Bind(R.id.et_nvfangshenfenzheng)
    EditText etNvfangshenfenzheng;
    @Bind(R.id.et_nvfang_hunyin)
    EditText etNvfangHunyin;
    @Bind(R.id.et_nvfang_chusheng)
    EditText etNvfangChusheng;
    @Bind(R.id.et_nanfangxingming)
    EditText etNanfangxingming;
    @Bind(R.id.et_nanfangshenfenzheng)
    EditText etNanfangshenfenzheng;
    @Bind(R.id.et_nanfang_hunyin)
    EditText etNanfangHunyin;
    @Bind(R.id.et_nanfang_chusheng)
    EditText etNanfangChusheng;
    @Bind(R.id.et_hunyinbiandongshijian)
    EditText etHunyinbiandongshijian;
    @Bind(R.id.et_nanhaishu)
    EditText etNanhaishu;
    @Bind(R.id.et_nvhaishu)
    EditText etNvhaishu;
    @Bind(R.id.et_changxiaocuoshizhonglei)
    EditText etChangxiaocuoshizhonglei;
    @Bind(R.id.et_luoshishijian)
    EditText etLuoshishijian;
    @Bind(R.id.luoshidanwei)
    EditText luoshidanwei;
    @Bind(R.id.main_layout)
    LinearLayout mainLayout;

    ChaXunResult mChaXunResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.main_layout).setOnClickListener(new HideKeyboardListener(SuifangJibenxinxiActivity.this));
        try {
            mChaXunResult = null;
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                String jsonBody = bundle.getString(JsonBody, "");
                if (StringUtils.isNotEmpty(jsonBody)) {
                    mChaXunResult = new Gson().fromJson(jsonBody, ChaXunResult.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mChaXunResult != null) {
            etNvfangxingming.setText(mChaXunResult.getFemaleName());
            etNvfangshenfenzheng.setText(mChaXunResult.getFemaleCardid());
            int temp = ArrayUtils.indexOf(GlobalData.hunyinzhuangkuangIDs, mChaXunResult.getFemaleMaritalStatus());
            if (temp >= 0 && temp < GlobalData.hunyinzhuangkuangNames.length) {
                etNvfangHunyin.setText(GlobalData.hunyinzhuangkuangNames[temp]);
            }
            etNvfangChusheng.setText(mChaXunResult.getFemaleBirthday());

            etNanfangxingming.setText(mChaXunResult.getMaleName());
            etNanfangshenfenzheng.setText(mChaXunResult.getMaleCardid());
            temp = ArrayUtils.indexOf(GlobalData.hunyinzhuangkuangIDs, mChaXunResult.getMaleMaritalStatus());
            if (temp >= 0 && temp < GlobalData.hunyinzhuangkuangNames.length) {
                etNanfangHunyin.setText(GlobalData.hunyinzhuangkuangNames[temp]);
            }
            etNanfangChusheng.setText(mChaXunResult.getMaleBirthday());

            etHunyinbiandongshijian.setText(mChaXunResult.getMaritalChangeDate());
            etNanhaishu.setText(mChaXunResult.getBoyAmount());
            etNvhaishu.setText(mChaXunResult.getGirlAmount());
//            temp = ArrayUtils.indexOf(GlobalData.biyuncuoshiIDs, mChaXunResult.get());
//            if (temp >= 0 && temp < GlobalData.biyuncuoshiNames.length) {
//                etChangxiaocuoshizhonglei.setText(GlobalData.biyuncuoshiNames[temp]);
//            }
//            etLuoshishijian.setText(mChaXunResult.get);
            luoshidanwei.setText(mChaXunResult.getOrgname());
        }
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.suifangjibenxinxi_activity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chaxun_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            beginActivity(SuifangdengjiActivity.class);

            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}