package com.jiayusoft.mobile.kenli.wis;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import butterknife.Bind;
import com.google.gson.Gson;
import com.jiayusoft.mobile.kenli.R;
import com.jiayusoft.mobile.kenli.suifangdengji.ChaXunResult;
import com.jiayusoft.mobile.kenli.utils.GlobalData;
import com.jiayusoft.mobile.kenli.utils.app.BaseActivity;
import com.jiayusoft.mobile.kenli.utils.app.listener.HideKeyboardListener;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by A on 2016/7/1.
 */
public class WisDetailActivity extends BaseActivity {
    @Bind(R.id.et_nvfangxingming)
    EditText etNvfangxingming;
    @Bind(R.id.et_nvfangshenfenzheng)
    EditText etNvfangshenfenzheng;
    @Bind(R.id.et_nvfang_hunyin)
    EditText etNvfangHunyin;
    @Bind(R.id.et_nvfang_chusheng)
    EditText etNvfangChusheng;
    @Bind(R.id.et_hunyinbiandongshijian)
    EditText etHunyinbiandongshijian;
    @Bind(R.id.et_nanhaishu)
    EditText etNanhaishu;
    @Bind(R.id.et_nvhaishu)
    EditText etNvhaishu;
    @Bind(R.id.luoshidanwei)
    EditText luoshidanwei;
    @Bind(R.id.main_layout)
    LinearLayout mainLayout;

    ChaXunResult mChaXunResult;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.main_layout).setOnClickListener(new HideKeyboardListener(WisDetailActivity.this));
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

            etHunyinbiandongshijian.setText(mChaXunResult.getMaritalChangeDate());
            etNanhaishu.setText(mChaXunResult.getBoyAmount());
            etNvhaishu.setText(mChaXunResult.getGirlAmount());
            luoshidanwei.setText(mChaXunResult.getOrgname());
        }
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_wis_detail);
    }
}