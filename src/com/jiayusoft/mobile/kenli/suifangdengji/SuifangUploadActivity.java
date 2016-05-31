package com.jiayusoft.mobile.kenli.suifangdengji;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jiayusoft.mobile.kenli.R;
import com.jiayusoft.mobile.kenli.utils.app.BaseActivity;
import com.jiayusoft.mobile.kenli.utils.app.listener.HideKeyboardListener;

import butterknife.Bind;
import butterknife.OnClick;

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
    @Bind(R.id.main_layout)
    LinearLayout mainLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.main_layout).setOnClickListener(new HideKeyboardListener(SuifangUploadActivity.this));
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.suifangupload_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_suifangupload, menu);
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