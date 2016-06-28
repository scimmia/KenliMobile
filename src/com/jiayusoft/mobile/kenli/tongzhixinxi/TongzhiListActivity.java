package com.jiayusoft.mobile.kenli.tongzhixinxi;

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

/**
 * Created by A on 2016/6/28.
 */
public class TongzhiListActivity extends BaseActivity {
    ItemAdapter mItemAdapter;
    private LinkedList<Tongzhi> mResults;

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
                DebugLog.e(mResults.get(position).getId());
                Bundle bundle = new Bundle();
                bundle.putString(JsonBody, mResults.get(position).getId());
                beginActivity(TongzhiDetailActivity.class, bundle);
            }
        });

        initData();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_tongzhilist);
    }

    void initData() {
        SoapRequestStruct soapRequestStruct = new SoapRequestStruct();
        soapRequestStruct.setServiceNameSpace(WS_NameSpace);
        soapRequestStruct.setServiceUrl(SERVICE_URL);
        soapRequestStruct.setMethodName(WS_Method_getNoticeList);
        String content = WebServiceUtil.buildItem("xiancode", "370521");

        String xmlString = WebServiceUtil.buildXml(content);
        soapRequestStruct.addProperty(WS_Property_Binding, xmlString);
        DebugLog.e("WS_Property_Binding: " + xmlString);

        new WebServiceTask(TongzhiListActivity.this, "查询中...", soapRequestStruct, tongzhilistListener).execute();

    }

    WebServiceListener tongzhilistListener = new WebServiceListener() {
        @Override
        public void onSuccess(String content) {
            if (StringUtils.isEmpty(content)) {
                return;
            }
            LinkedList<Tongzhi> rows = new LinkedList<>();
            Tongzhi row = null;
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
                                row = new Tongzhi();
                            } else if (row != null) {
                                if (StringUtils.equalsIgnoreCase(currentTag, "id")) {
                                    row.setId(parser.nextText());
                                } else if (StringUtils.equalsIgnoreCase(currentTag, "title")) {
                                    row.setTitle(parser.nextText());
                                } else if (StringUtils.equalsIgnoreCase(currentTag, "fbr")) {
                                    row.setEditor(parser.nextText());
                                } else if (StringUtils.equalsIgnoreCase(currentTag, "lrsj")) {
                                    row.setCreateTime(parser.nextText());
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tongzhi, parent, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }

            holder.tongzhiTitle.setText(mResults.get(position).getTitle());
            holder.tongzhiEditor.setText(mResults.get(position).getEditor());
            holder.tongzhiCreatetime.setText(mResults.get(position).getCreateTime());
            return view;
        }

        class ViewHolder {
            @Bind(R.id.tongzhi_title)
            TextView tongzhiTitle;
            @Bind(R.id.tongzhi_editor)
            TextView tongzhiEditor;
            @Bind(R.id.tongzhi_createtime)
            TextView tongzhiCreatetime;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}