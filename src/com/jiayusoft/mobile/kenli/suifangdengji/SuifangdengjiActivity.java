package com.jiayusoft.mobile.kenli.suifangdengji;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import butterknife.Bind;
import com.jiayusoft.mobile.kenli.R;
import com.jiayusoft.mobile.kenli.utils.DebugLog;
import com.jiayusoft.mobile.kenli.utils.app.BaseActivity;
import com.jiayusoft.mobile.kenli.utils.app.listener.HideKeyboardListener;
import com.jiayusoft.mobile.kenli.utils.database.DBHelper;

/**
 * Created by Hi on 2016-5-10.
 */
public class SuifangdengjiActivity extends BaseActivity {
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
    @Bind(R.id.sp_quxian)
    EditText spQuxian;
    @Bind(R.id.sp_jiedao)
    EditText spJiedao;
    @Bind(R.id.sp_shequ)
    EditText spShequ;
    @Bind(R.id.main_layout)
    LinearLayout mainLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.main_layout).setOnClickListener(new HideKeyboardListener(SuifangdengjiActivity.this));
    }
    @Override
    protected void initContentView() {
        setContentView(R.layout.suifangdengji_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_suifangdengji, menu);
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