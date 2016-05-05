package com.jiayusoft.mobile.kenli.utils.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import butterknife.ButterKnife;
import com.jiayusoft.mobile.kenli.R;
import com.jiayusoft.mobile.kenli.utils.GlobalData;
import com.jiayusoft.mobile.kenli.utils.app.dialog.DialogListener;
import com.jiayusoft.mobile.kenli.utils.eventbus.BusProvider;
import com.jiayusoft.mobile.kenli.utils.eventbus.event.MessageEvent;
import com.squareup.otto.Subscribe;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * Created by ASUS on 2014/11/14.
 */
public abstract class BaseActivity extends Activity implements GlobalData {
    Activity mBaseActivity;
    public Activity getBaseActivity(){
        return mBaseActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        ButterKnife.bind(this);
        mBaseActivity = BaseActivity.this;
//        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_launcher));
//        getActionBar().setDisplayShowHomeEnabled(true);
//        String logoColor = BaseApplication.getCurrentUser().getLogoColor();
//        if (StringUtils.isNotEmpty(logoColor)){
//            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(logoColor)));
//        }
    }

    protected abstract void initContentView();
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    protected Object showMessageListener;
    @Override
    public void onResume() {
        super.onResume();

        // Register ourselves so that we can provide the initial value.
        BusProvider.getInstance().register(this);
        showMessageListener = new Object() {
            @Subscribe
            public void showMessage(final MessageEvent event) {
                if (event!= null){
                    BaseActivity.this.showMessage(event.getMessage());
                }
            }
        };
        BusProvider.getInstance().register(showMessageListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        cancelMessage();
        cancelProgress();
        // Always unregister when an object no longer should be on the bus.
        BusProvider.getInstance().unregister(this);
        BusProvider.getInstance().unregister(showMessageListener);
    }


//    private AppMsg appMsg;
//    public void showMessage(MessageEvent event){
//        switch (event.getMessageType()){
//            case MessageEvent.STYLE_ALERT:
//                showMessage(event.getMessage(),AppMsg.STYLE_ALERT);
//                break;
//            case MessageEvent.STYLE_CONFIRM:
//                showMessage(event.getMessage(),AppMsg.STYLE_CONFIRM);
//                break;
//            case MessageEvent.STYLE_INFO:
//                showMessage(event.getMessage(),AppMsg.STYLE_INFO);
//                break;
//        }
//    }

    public void showMessage(String message){
//        showMessage(message,AppMsg.STYLE_INFO);
        showToast(message);
    }
//    public void showMessage(String message, AppMsg.Style style){
//        cancelMessage();
//        appMsg = AppMsg.makeText(getBaseActivity(), message, style);
//        appMsg.setAnimation(android.R.anim.fade_in, android.R.anim.fade_out);
//        appMsg.show();
//
//    }
    public void cancelMessage(){
//        if (appMsg!=null && appMsg.isShowing()){
//            appMsg.cancel();
//        }
        cancelToast();
    }

    public ProgressDialog mProgressDialog;
    /**
     * Shows the progress UI and hides the login form.
     */
    public void showProgress(String message) {
        cancelMessage();
        if (mProgressDialog == null){
            mProgressDialog = new ProgressDialog(getBaseActivity(), ProgressDialog.THEME_HOLO_LIGHT);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
    }
    public void cancelProgress(){
        if (mProgressDialog!=null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    private Toast appMsg;
    public void showToast(String message){
        cancelToast();
        appMsg = Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT);
        appMsg.show();

    }
    public void cancelToast(){
        if (appMsg!=null){
            appMsg.cancel();
        }
    }

    public void beginActivity(Class<?> launche){
        beginActivity(launche,null);
    }
    public void beginActivity(Class<?> launche, Bundle bundle){
        Intent intent = new Intent( getBaseActivity(), launche);
        if (bundle!=null){
            intent.putExtras(bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent);
    }

    Context getActivity(){
        return BaseActivity.this;
    }
    public AlertDialog mAlertDialog;
    public void showSingleDialog(String title,String[] itemNames,String[] itemIDs,String itemSelected,DialogListener dialogListener){
        if (mAlertDialog != null){
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
//        String[] itemNames = getResource(itemTpye);
//        String[] itemIDs = getResource(itemTpye);
        if (itemNames != null && itemIDs != null && itemNames.length == itemIDs.length){
            int selectedPos = ArrayUtils.indexOf(itemNames,itemSelected);
            mAlertDialog = new AlertDialog.Builder(BaseActivity.this).setTitle(title)
                    .setSingleChoiceItems(itemNames, selectedPos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (dialogListener != null){
                                dialogListener.onSelected(itemNames[which],itemIDs[which]);
                            }
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消选择", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (dialogListener != null){
                                dialogListener.onClear();
                            }
                        }
                    })
                    .setNeutralButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            mAlertDialog.show();
        }
    }

    public void showMutilDialog(int itemTpye,String title,String[] itemNames,String[] itemIDs,String itemSelected,DialogListener dialogListener){
        if (mAlertDialog != null){
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
        if (itemNames != null && itemIDs != null && itemNames.length == itemIDs.length){
            boolean[] tempResult = new boolean[itemIDs.length];

            int selectedPos = ArrayUtils.indexOf(itemNames,itemSelected);
            mAlertDialog = new AlertDialog.Builder(BaseActivity.this).setTitle(title)
                    .setSingleChoiceItems(itemNames, selectedPos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (dialogListener != null){
                                dialogListener.onSelected(itemNames[which],itemIDs[which]);
                            }
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消选择", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (dialogListener != null){
                                dialogListener.onClear();
                            }
                        }
                    })
                    .setNeutralButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            mAlertDialog.show();
        }
    }

    public void showDateDialog(String itemSelected,DialogListener dialogListener){
        if (mAlertDialog != null){
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
        RelativeLayout relativeLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.dialog_pick_time,null);
        DatePicker datePicker = (DatePicker) relativeLayout.findViewById(R.id.datePickerBegin);
        if (NumberUtils.isNumber(itemSelected)){
            long timeTemp = NumberUtils.toLong(itemSelected);
            if (timeTemp > 0){
                datePicker.getCalendarView().setDate(timeTemp);
            }
        }
        mAlertDialog = new AlertDialog.Builder(getActivity()).setTitle("选择日期")
                .setView(relativeLayout)
                .setNegativeButton("取消", null)
                .setNeutralButton("清空", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dialogListener != null){
                            dialogListener.onClear();
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (dialogListener != null){
                            long datetemp = 0;
                            try {
                                datetemp = datePicker.getCalendarView().getDate();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            dialogListener.onSelected(DateFormatUtils.ISO_DATE_FORMAT.format(datetemp),""+datetemp);
                        }
                    }
                })
                .create();
        mAlertDialog.show();
    }
}
