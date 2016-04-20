package com.jiayusoft.mobile.kenli.suifangdengji;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.Bind;
import com.jiayusoft.mobile.kenli.R;
import com.jiayusoft.mobile.kenli.utils.DebugLog;
import com.jiayusoft.mobile.kenli.utils.app.BaseActivity;
import com.jiayusoft.mobile.kenli.utils.webservice.SoapRequestStruct;
import com.jiayusoft.mobile.kenli.utils.webservice.WebServiceTask;
import com.jiayusoft.mobile.kenli.utils.webservice.WebServiceUtil;
import org.apache.commons.lang3.StringUtils;

public class ChaxunResultActivity extends BaseActivity {

//    @Bind(R.id.list_result)
//    RecyclerView mRvItems;
    @Bind(android.R.id.empty)
    TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String xmlBody = bundle.getString(XMLBody, "");
            if (StringUtils.isNotEmpty(xmlBody)) {
                searchSuifang(xmlBody);
            }
        }
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_suifangchaxun_result);
    }

    void searchSuifang(String xmlBody){
        String xmlString = WebServiceUtil.buildXml(xmlBody);

        SoapRequestStruct soapRequestStruct = new SoapRequestStruct();
        soapRequestStruct.setServiceNameSpace(WS_NameSpace);
//        soapRequestStruct.setMethodName(WS_Method_getXianinfo);
//        soapRequestStruct.setMethodName(WS_Method_getJdinfo);
        soapRequestStruct.setMethodName(WS_Method_getYlfninfo);
        soapRequestStruct.setServiceUrl(SERVICE_URL);
//            soapRequestStruct.setServiceUrl("http://58.56.20.118:9090/oa/ws/ImpData");
        soapRequestStruct.addProperty(WS_Property_Binding,xmlString);
        DebugLog.e("WS_Property_Binding: " + xmlString);

        new WebServiceTask(ChaxunResultActivity.this, "查询中...",soapRequestStruct, null).execute();

    }
}
