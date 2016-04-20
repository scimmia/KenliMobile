package com.jiayusoft.mobile.kenli.suifangdengji;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import butterknife.Bind;
import com.jiayusoft.mobile.kenli.R;
import com.jiayusoft.mobile.kenli.utils.app.BaseActivity;
import com.jiayusoft.mobile.kenli.utils.app.widget.JiayuSpinner;

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Bundle bundle = new Bundle();
            bundle.putString(XMLBody,
                    "<xiancode>370521</xiancode>\n" +
                            "<jdcode>370521001</jdcode>\n" +
                            "<jwhcode>370521001001</jwhcode>\n" +
                            "<xm></xm>\n" +
                            "<sfzh></sfzh>\n" +
                            "<flag>0</flag>\n" +
                            "<sfrq></sfrq>");
            beginActivity(ChaxunResultActivity.class, bundle);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
