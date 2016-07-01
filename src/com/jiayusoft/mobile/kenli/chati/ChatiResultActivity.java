package com.jiayusoft.mobile.kenli.chati;

import android.app.Activity;
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
import com.google.gson.Gson;
import com.jiayusoft.mobile.kenli.R;
import com.jiayusoft.mobile.kenli.suifangdengji.ChaXunResult;
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
 * Created by A on 2016/7/1.
 */
public class ChatiResultActivity extends BaseActivity {
    ItemAdapter mItemAdapter;
    private LinkedList<ChaXunResult> mResults;

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
                String json = new Gson().toJson(mResults.get(position));
                DebugLog.e(json);
                Bundle bundle = new Bundle();
                bundle.putString(JsonBody,json);
//                beginActivity(WisDetailActivity.class, bundle);
            }
        });
        initData();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_chati_result);
    }

    private void initData() {
        String content = getJsonBody();
        if (StringUtils.isNotEmpty(content)) {
            SoapRequestStruct soapRequestStruct = new SoapRequestStruct();
            soapRequestStruct.setServiceNameSpace(WS_NameSpace);
            soapRequestStruct.setServiceUrl(SERVICE_URL);
            soapRequestStruct.setMethodName(WS_Method_getYlfninfo);

            String xmlString = WebServiceUtil.buildXml(content);
            soapRequestStruct.addProperty(WS_Property_Binding, xmlString);
            DebugLog.e("WS_Property_Binding: " + xmlString);

            new WebServiceTask(ChatiResultActivity.this, "查询中...", soapRequestStruct, funvlistListener).execute();
        }
    }

    WebServiceListener funvlistListener = new WebServiceListener() {
        @Override
        public void onSuccess(String content) {
            if (StringUtils.isEmpty(content)) {
                return;
            }
            LinkedList<ChaXunResult> rows = new LinkedList<>();
            ChaXunResult row = null;
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
                            if (StringUtils.equalsIgnoreCase(currentTag,"row")){
                                row = new ChaXunResult();
                            } else if (row != null){
                                if (StringUtils.equalsIgnoreCase(currentTag,"femaleId")){
                                    row.setFemaleId(parser.nextText());
                                } else if (StringUtils.equalsIgnoreCase(currentTag,"femaleName")){
                                    row.setFemaleName(parser.nextText());
                                } else if (StringUtils.equalsIgnoreCase(currentTag,"femaleCardid")){
                                    row.setFemaleCardid(parser.nextText());
                                } else if (StringUtils.equalsIgnoreCase(currentTag,"femaleBirthday")){
                                    row.setFemaleBirthday(parser.nextText());
                                } else if (StringUtils.equalsIgnoreCase(currentTag,"femaleMaritalStatus")){
                                    row.setFemaleMaritalStatus(parser.nextText());
                                } else if (StringUtils.equalsIgnoreCase(currentTag,"maleName")){
                                    row.setMaleName(parser.nextText());
                                } else if (StringUtils.equalsIgnoreCase(currentTag,"maleCardid")){
                                    row.setMaleCardid(parser.nextText());
                                } else if (StringUtils.equalsIgnoreCase(currentTag,"maleBirthday")){
                                    row.setMaleBirthday(parser.nextText());
                                }else if (StringUtils.equalsIgnoreCase(currentTag,"maleMaritalStatus")){
                                    row.setMaleMaritalStatus(parser.nextText());
                                }else if (StringUtils.equalsIgnoreCase(currentTag,"maritalChangeDate")){
                                    row.setMaritalChangeDate(parser.nextText());
                                }else if (StringUtils.equalsIgnoreCase(currentTag,"boyAmount")){
                                    row.setBoyAmount(parser.nextText());
                                }else if (StringUtils.equalsIgnoreCase(currentTag,"girlAmount")){
                                    row.setGirlAmount(parser.nextText());
                                }else if (StringUtils.equalsIgnoreCase(currentTag,"orgid")){
                                    row.setOrgid(parser.nextText());
                                }else if (StringUtils.equalsIgnoreCase(currentTag,"orgname")){
                                    row.setOrgname(parser.nextText());
                                }
                            }
                            break;
                        case XmlPullParser.TEXT:
                            break;
                        case XmlPullParser.END_TAG:
                            currentTag = parser.getName();
                            if (StringUtils.equalsIgnoreCase(currentTag,"row")) {
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
            if (mResults == null){
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dengjichaxun_item, parent, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }

            holder.name.setText(mResults.get(position).getFemaleName());
            holder.cardid.setText(mResults.get(position).getFemaleCardid());
            holder.child.setText("现有男孩数：" + mResults.get(position).getBoyAmount() +
                    "\t现有女孩数：" + mResults.get(position).getGirlAmount());
            return view;
        }

        class ViewHolder {
            @Bind(R.id.name)
            TextView name;
            @Bind(R.id.cardid)
            TextView cardid;
            @Bind(R.id.child)
            TextView child;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}