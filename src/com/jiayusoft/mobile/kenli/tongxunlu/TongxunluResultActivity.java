package com.jiayusoft.mobile.kenli.tongxunlu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jiayusoft.mobile.kenli.R;
import com.jiayusoft.mobile.kenli.utils.DebugLog;
import com.jiayusoft.mobile.kenli.utils.app.BaseActivity;
import com.jiayusoft.mobile.kenli.utils.webservice.SoapRequestStruct;
import com.jiayusoft.mobile.kenli.utils.webservice.WebServiceListener;
import com.jiayusoft.mobile.kenli.utils.webservice.WebServiceTask;
import com.jiayusoft.mobile.kenli.utils.webservice.WebServiceUtil;
import org.apache.commons.lang3.StringUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;

public class TongxunluResultActivity extends BaseActivity {

    ItemAdapter mItemAdapter;
    private LinkedList<Tongxunlu> mResults;

    @Bind(R.id.list_result)
    ListView mRvItems;
    @Bind(android.R.id.empty)
    TextView empty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResults = new LinkedList<>();
        mItemAdapter = new ItemAdapter();
        mRvItems.setEmptyView(empty);
        mRvItems.setAdapter(mItemAdapter);
        mRvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DebugLog.e(mResults.get(position).getName());
                LinkedList<String> numbers = new LinkedList<String>();
                if (StringUtils.isNotEmpty(mResults.get(position).getPhoneNumber())) {
                    numbers.add(mResults.get(position).getPhoneNumber());
                }
                if (StringUtils.isNotEmpty(mResults.get(position).getPhoneNumberB())) {
                    numbers.add(mResults.get(position).getPhoneNumberB());
                }

                final int[] selectedPos = {0};
                selectedPos[0] = 0;
                if (numbers.size() > 0) {
                    String[] numberList = new String[numbers.size()];
                    for (int i = 0; i < numbers.size(); i++) {
                        numberList[i] = numbers.get(i);
                    }
                    new AlertDialog.Builder(TongxunluResultActivity.this)
                            .setTitle(mResults.get(position).getName())
                            .setCancelable(true)
                            .setSingleChoiceItems(numberList, selectedPos[0], new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    selectedPos[0] = which;
                                }
                            })
                            .setNegativeButton("呼叫", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DebugLog.e(selectedPos[0] + "呼叫which" + which);
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    Uri data = Uri.parse("tel:" + numberList[selectedPos[0]]);
                                    intent.setData(data);
                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            })
                            .setNeutralButton("短信", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DebugLog.e(selectedPos[0] + "短信which" + which);
                                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                                    Uri data = Uri.parse("smsto:" + numberList[selectedPos[0]]);
                                    intent.setData(data);
                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("返回", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DebugLog.e(selectedPos[0] + "返回which" + which);
                                    dialog.dismiss();
                                }
                            })
                            .create()
                            .show();

                }

            }
        });

        initData();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_tongxunlu_result);
    }

    private void initData() {
        String content = getJsonBody();
        if (StringUtils.isNotEmpty(content)) {
            SoapRequestStruct soapRequestStruct = new SoapRequestStruct();
            soapRequestStruct.setServiceNameSpace(WS_NameSpace);
            soapRequestStruct.setServiceUrl(SERVICE_URL);
            soapRequestStruct.setMethodName(WS_Method_getAddresslist);

            String xmlString = WebServiceUtil.buildXml(content);
            soapRequestStruct.addProperty(WS_Property_Binding, xmlString);
            DebugLog.e("WS_Property_Binding: " + xmlString);

            new WebServiceTask(TongxunluResultActivity.this, "查询中...", soapRequestStruct, tongxunlulistListener).execute();
        }
    }

    WebServiceListener tongxunlulistListener = new WebServiceListener() {
        @Override
        public void onSuccess(String content) {
            if (StringUtils.isEmpty(content)) {
                return;
            }
            LinkedList<Tongxunlu> rows = new LinkedList<>();
            Tongxunlu row = null;
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(new StringReader(content));
                int event = parser.getEventType();
                String currentTag;
                while (event != XmlPullParser.END_DOCUMENT) {
                    switch (event) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            currentTag = parser.getName();
                            if (StringUtils.equalsIgnoreCase(currentTag, "row")) {
                                row = new Tongxunlu();
                            } else if (row != null) {
                                if (StringUtils.equalsIgnoreCase(currentTag, "jdname")) {
                                    row.setJiedao(parser.nextText());
                                } else if (StringUtils.equalsIgnoreCase(currentTag, "jwhname")) {
                                    row.setJuweihui(parser.nextText());
                                } else if (StringUtils.equalsIgnoreCase(currentTag, "xm")) {
                                    row.setName(parser.nextText());
                                } else if (StringUtils.equalsIgnoreCase(currentTag, "lxdh")) {
                                    row.setPhoneNumber(parser.nextText());
                                } else if (StringUtils.equalsIgnoreCase(currentTag, "lxdh2")) {
                                    row.setPhoneNumberB(parser.nextText());
                                }
                            }
                            break;
                        case XmlPullParser.TEXT:
                            break;
                        case XmlPullParser.END_TAG:
                            currentTag = parser.getName();
                            if (StringUtils.equalsIgnoreCase(currentTag, "row")) {
                                rows.add(row);
                            }
                            break;
                        default:
                            break;
                    }
                    event = parser.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mResults == null) {
                mResults = new LinkedList<>();
            }
            mResults.addAll(rows);
            mItemAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(String content) {
            showToast("查询失败，请稍后重试");
        }

        @Override
        public void onError(Exception exception) {
            showToast("网络异常，请稍后重试");
        }
    };


    class ItemAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mResults.size();
        }

        @Override
        public Object getItem(int position) {
            return mResults.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder;
            if (view != null) {
                holder = (ViewHolder) view.getTag();
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tongxunlu, parent, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }

            holder.tongxunliJiedao.setText(mResults.get(position).getJiedao());
            holder.tongzhiXingming.setText(mResults.get(position).getName());
            holder.tongzhiDianhua.setText(mResults.get(position).getPhoneNumber());
            return view;
        }

        class ViewHolder {
            @Bind(R.id.tongxunli_jiedao)
            TextView tongxunliJiedao;
            @Bind(R.id.tongzhi_xingming)
            TextView tongzhiXingming;
            @Bind(R.id.tongzhi_dianhua)
            TextView tongzhiDianhua;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}