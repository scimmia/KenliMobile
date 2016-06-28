package com.jiayusoft.mobile.kenli.tongzhixinxi;

import android.os.Bundle;
import android.util.Xml;
import android.widget.TextView;
import butterknife.Bind;
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

/**
 * Created by A on 2016/6/28.
 */
public class TongzhiDetailActivity extends BaseActivity {
    Tongzhi mTongzhi;
    @Bind(R.id.tongzhi_title)
    TextView tongzhiTitle;
    @Bind(R.id.tongzhi_editor)
    TextView tongzhiEditor;
    @Bind(R.id.tongzhi_createtime)
    TextView tongzhiCreatetime;
    @Bind(R.id.tongzhi_content)
    TextView tongzhiContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTongzhi = new Tongzhi();
        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                String jsonBody = bundle.getString(JsonBody, "");
                initDatas(jsonBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_tongzhi_detail);
    }

    void initDatas(String id){
        if (StringUtils.isEmpty(id)){
            showMessage("查询通知失败，请稍后重试");
        } else {
            SoapRequestStruct soapRequestStruct = new SoapRequestStruct();
            soapRequestStruct.setServiceNameSpace(WS_NameSpace);
            soapRequestStruct.setServiceUrl(SERVICE_URL);
            soapRequestStruct.setMethodName(WS_Method_getNoticeDetail);
            String content = WebServiceUtil.buildItem("id", id);

            String xmlString = WebServiceUtil.buildXml(content);
            soapRequestStruct.addProperty(WS_Property_Binding, xmlString);
            DebugLog.e("WS_Property_Binding: " + xmlString);

            new WebServiceTask(TongzhiDetailActivity.this, "查询中...", soapRequestStruct, tongzhiDetailListener).execute();

        }
    }

    WebServiceListener tongzhiDetailListener = new WebServiceListener() {
        @Override
        public void onSuccess(String content) {
            if (StringUtils.isEmpty(content)) {
                return;
            }
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
                            if (StringUtils.equalsIgnoreCase(currentTag, "id")) {
                                mTongzhi.setId(parser.nextText());
                            } else if (StringUtils.equalsIgnoreCase(currentTag, "title")) {
                                mTongzhi.setTitle(parser.nextText());
                            } else if (StringUtils.equalsIgnoreCase(currentTag, "fbr")) {
                                mTongzhi.setEditor(parser.nextText());
                            } else if (StringUtils.equalsIgnoreCase(currentTag, "lrsj")) {
                                mTongzhi.setCreateTime(parser.nextText());
                            } else if (StringUtils.equalsIgnoreCase(currentTag, "bz")) {
                                mTongzhi.setContent(parser.nextText());
                            }
                            break;
                        case XmlPullParser.TEXT:
                            break;
                        case XmlPullParser.END_TAG:
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
            tongzhiTitle.setText(mTongzhi.getTitle());
            tongzhiEditor.setText(mTongzhi.getEditor());
            tongzhiCreatetime.setText(mTongzhi.getCreateTime());
            tongzhiContent.setText(mTongzhi.getContent());
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

}