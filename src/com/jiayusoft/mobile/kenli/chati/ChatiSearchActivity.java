package com.jiayusoft.mobile.kenli.chati;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import com.jiayusoft.mobile.kenli.R;
import com.jiayusoft.mobile.kenli.utils.DebugLog;
import com.jiayusoft.mobile.kenli.utils.GlobalData;
import com.jiayusoft.mobile.kenli.utils.app.BaseActivity;
import com.jiayusoft.mobile.kenli.utils.app.dialog.DialogListener;
import com.jiayusoft.mobile.kenli.utils.app.listener.HideKeyboardListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.LinkedList;

/**
 * Created by A on 2016/7/1.
 */
public class ChatiSearchActivity extends BaseActivity {
    @Bind(R.id.sp_quxian)
    EditText spQuxian;
    @Bind(R.id.sp_jiedao)
    EditText spJiedao;
    @Bind(R.id.sp_shequ)
    EditText spShequ;
    @Bind(R.id.et_nvfangxingming)
    EditText etNvfangxingming;
    @Bind(R.id.et_nvfangshenfenzheng)
    EditText etNvfangshenfenzheng;
    @Bind(R.id.et_nanfangxingming)
    EditText etNanfangxingming;
    @Bind(R.id.et_nanfangshenfenzheng)
    EditText etNanfangshenfenzheng;
    @Bind(R.id.et_pinyinxiangmu)
    EditText etPinyinxiangmu;
    @Bind(R.id.sp_shifouyichati)
    EditText spShifouyichati;
    @Bind(R.id.et_chatishijian_from)
    EditText etChatishijianFrom;
    @Bind(R.id.et_chatishijian_to)
    EditText etChatishijianTo;
    @Bind(R.id.et_nvfangchushengriqi_from)
    EditText etNvfangchushengriqiFrom;
    @Bind(R.id.et_nvfangchushengriqi_to)
    EditText etNvfangchushengriqiTo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.main_layout).setOnClickListener(new HideKeyboardListener(ChatiSearchActivity.this));
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_chati_search);
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
//            String content =
//                    WebServiceUtil.buildItem("xiancode", (String) spQuxian.getTag()) +
//                            WebServiceUtil.buildItem("jdcode", (String) spJiedao.getTag()) +
//                            WebServiceUtil.buildItem("jwhcode", (String) spShequ.getTag()) +
//                            WebServiceUtil.buildItem("xm", String.valueOf(etXingming.getText())) +
//                            WebServiceUtil.buildItem("sfzh", String.valueOf(etShenfenzheng.getText())) +
//                            WebServiceUtil.buildItem("flag", (String) spShifousuifang.getTag()) +
//                            WebServiceUtil.buildItem("sfrq", String.valueOf(etSuifangjiandingshijian.getText()));
            Bundle bundle = new Bundle();
//            bundle.putString(JsonBody, content);
            beginActivity(ChatiResultActivity.class, bundle);
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
        LinkedList<MutablePair<String, String>> m = mDBHelper.getAddress("3705");
        addressNames = new String[m.size()];
        addressIDs = new String[m.size()];
        for (int i = 0; i < m.size(); i++) {
            addressNames[i] = m.get(i).getLeft();
            addressIDs[i] = m.get(i).getRight();
        }
        showSingleDialog("", addressNames,
                addressIDs, (String) spQuxian.getTag(), new DialogListener() {
                    @Override
                    public void onSelected(String name, String id) {
                        if (StringUtils.equals(id, (CharSequence) spQuxian.getTag())) {
                            return;
                        }
                        DebugLog.e(name + "\t" + id);
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
        if (StringUtils.isEmpty((String) spQuxian.getTag())) {
            showToast("请先选择区县");
            return;
        }
        LinkedList<MutablePair<String, String>> m = mDBHelper.getAddress((String) spQuxian.getTag());
        addressNames = new String[m.size()];
        addressIDs = new String[m.size()];
        for (int i = 0; i < m.size(); i++) {
            addressNames[i] = m.get(i).getLeft();
            addressIDs[i] = m.get(i).getRight();
        }
        showSingleDialog("", addressNames,
                addressIDs, (String) spJiedao.getTag(), new DialogListener() {
                    @Override
                    public void onSelected(String name, String id) {
                        DebugLog.e(name + "\t" + id);
                        if (StringUtils.equals(id, (CharSequence) spJiedao.getTag())) {
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
        if (StringUtils.isEmpty((String) spJiedao.getTag())) {
            showToast("请先选择街道(乡镇)");
            return;
        }
        LinkedList<MutablePair<String, String>> m = mDBHelper.getAddress((String) spJiedao.getTag());
        addressNames = new String[m.size()];
        addressIDs = new String[m.size()];
        for (int i = 0; i < m.size(); i++) {
            addressNames[i] = m.get(i).getLeft();
            addressIDs[i] = m.get(i).getRight();
        }
        showSingleDialog("", addressNames,
                addressIDs, (String) spJiedao.getTag(), new DialogListener() {
                    @Override
                    public void onSelected(String name, String id) {
                        DebugLog.e(name + "\t" + id);
                        DebugLog.e(name + "\t" + id);
                        if (StringUtils.equals(id, (CharSequence) spShequ.getTag())) {
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

    @OnFocusChange(R.id.sp_shifouyichati)
    void onsp_shifouyichatiFocus(boolean focused) {
        if (focused) {
            onsp_shifouyichati();
        }
    }

    @OnClick(R.id.sp_shifouyichati)
    void onsp_shifouyichati() {
        DebugLog.e("onsp_shifousuifang");
        showSingleDialog("", GlobalData.shifoubuxianNames,
                GlobalData.shifoubuxianIDs, (String) spShifouyichati.getTag(), new DialogListener() {
                    @Override
                    public void onSelected(String name, String id) {
                        DebugLog.e(name + "\t" + id);
                        spShifouyichati.setTag(id);
                        spShifouyichati.setText(name);
                    }

                    @Override
                    public void onClear() {
                        DebugLog.e("onClear");
                        spShifouyichati.setTag("");
                        spShifouyichati.setText("");
                    }
                });
    }

    @OnFocusChange(R.id.et_chatishijian_from)
    void onet_chatishijian_fromFocus(boolean focused) {
        if (focused) {
            onet_chatishijian_from();
        }
    }

    @OnClick(R.id.et_chatishijian_from)
    void onet_chatishijian_from() {
        DebugLog.e("onet_chatishijian_from");
        showDateDialog((String) etChatishijianFrom.getTag(), new DialogListener() {
            @Override
            public void onSelected(String name, String id) {
                DebugLog.e(name +"\t"+ id);
                etChatishijianFrom.setTag(id);
                etChatishijianFrom.setText(name);
            }

            @Override
            public void onClear() {
                DebugLog.e("onClear");
                etChatishijianFrom.setTag("");
                etChatishijianFrom.setText("");
            }
        });
    }

    @OnFocusChange(R.id.et_chatishijian_to)
    void onet_chatishijian_toFocus(boolean focused) {
        if (focused) {
            onet_chatishijian_to();
        }
    }

    @OnClick(R.id.et_chatishijian_to)
    void onet_chatishijian_to() {
        DebugLog.e("onet_chatishijian_to");
        showDateDialog((String) etChatishijianTo.getTag(), new DialogListener() {
            @Override
            public void onSelected(String name, String id) {
                DebugLog.e(name +"\t"+ id);
                etChatishijianTo.setTag(id);
                etChatishijianTo.setText(name);
            }

            @Override
            public void onClear() {
                DebugLog.e("onClear");
                etChatishijianTo.setTag("");
                etChatishijianTo.setText("");
            }
        });
    }

    @OnFocusChange(R.id.et_nvfangchushengriqi_from)
    void onet_nvfangchushengriqi_fromFocus(boolean focused) {
        if (focused) {
            onet_nvfangchushengriqi_from();
        }
    }

    @OnClick(R.id.et_nvfangchushengriqi_from)
    void onet_nvfangchushengriqi_from() {
        DebugLog.e("onet_nvfangchushengriqi_from");
        showDateDialog((String) etNvfangchushengriqiFrom.getTag(), new DialogListener() {
            @Override
            public void onSelected(String name, String id) {
                DebugLog.e(name +"\t"+ id);
                etNvfangchushengriqiFrom.setTag(id);
                etNvfangchushengriqiFrom.setText(name);
            }

            @Override
            public void onClear() {
                DebugLog.e("onClear");
                etNvfangchushengriqiFrom.setTag("");
                etNvfangchushengriqiFrom.setText("");
            }
        });
    }

    @OnFocusChange(R.id.et_nvfangchushengriqi_to)
    void onet_nvfangchushengriqi_toFocus(boolean focused) {
        if (focused) {
            onet_nvfangchushengriqi_to();
        }
    }

    @OnClick(R.id.et_nvfangchushengriqi_to)
    void onet_nvfangchushengriqi_to() {
        DebugLog.e("onet_nvfangchushengriqi_to");
        showDateDialog((String) etNvfangchushengriqiTo.getTag(), new DialogListener() {
            @Override
            public void onSelected(String name, String id) {
                DebugLog.e(name +"\t"+ id);
                etNvfangchushengriqiTo.setTag(id);
                etNvfangchushengriqiTo.setText(name);
            }

            @Override
            public void onClear() {
                DebugLog.e("onClear");
                etNvfangchushengriqiTo.setTag("");
                etNvfangchushengriqiTo.setText("");
            }
        });
    }
}