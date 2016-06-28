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

/*
public class TongxunluResultActivity extends BaseActivity {
    private LinkedList<Tongxunlu> mTongxunlus;
    ItemAdapter mItemAdapter;

    @Bind(R.id.list_result)
    RecyclerView mRvItems;
    @Bind(android.R.id.empty)
    TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTongxunlus = new LinkedList<Tongxunlu>();
        mItemAdapter = new ItemAdapter();
        mRvItems.setHasFixedSize(true);
        mRvItems.addItemDecoration(new RecyclerView.ItemDecoration() {

            Paint paint = new Paint();

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);
                for (int i = 0, size = parent.getChildCount(); i < size; i++) {
                    View child = parent.getChildAt(i);
                    c.drawLine(child.getLeft(), child.getBottom(), child.getRight(), child.getBottom(), paint);
                }
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
            }
        });
        mRvItems.setLayoutManager(new LinearLayoutManager(TongxunluResultActivity.this));
        mRvItems.setAdapter(mItemAdapter);
        initDatas();
    }

    void initDatas(){
        mTongxunlus.clear();
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","",""));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","张骁","13705310000"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","邓力夫","13705310001"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","蒲卉子","13705310002"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","彭雅琪","13705310003"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","罗秋蒙","13705310004"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","邹诗雨","13705310005"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","刘强","13705310006"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","徐牧","13705310007"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","付超","13705310008"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","王彬焱","13705310009"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","敖倩莹","13705310010"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","潘柯佚","13705310011"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","巫慧一","13705310012"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","张文韬","13705310013"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","冷天","13705310014"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","夏圆圆","13705310015"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","尹婷","13705310016"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","艾米莉","13705310017"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","姚璞阳","13705310018"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","宋宇蒙","13705310019"));
        mTongxunlus.add(new Tongxunlu("垦利街道办事处","邱雨","13705310020"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","",""));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","何丽","13705310021"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","李俊伶","13705310022"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","龙裔","13705310023"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","肖博约","13705310024"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","雷虹宇","13705310025"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","董紫璇","13705310026"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","段雨君","13705310027"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","董细薇","13705310028"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","黄俊夫","13705310029"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","刘张","13705310030"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","严蓉","13705310031"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","陈肖逍","13705310032"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","段赫迪","13705310033"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","冉利娜","13705310034"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","栾慧国","13705310035"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","高倩玉","13705310036"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","陈城","13705310037"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","王俊文","13705310038"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","祝艺珈","13705310039"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","王雪茹","13705310040"));
        mTongxunlus.add(new Tongxunlu("永兴街道居委会","李宜阳","13705310041"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","",""));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","蓝林森","13705310042"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","朱非","13705310043"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","杜羊岑","13705310044"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","黄丹丹","13705310045"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","李翾鹤","13705310046"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","何佩遥","13705310047"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","李元媛","13705310048"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","张晋荣","13705310049"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","杜晓煜","13705310050"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","易佳民","13705310051"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","段雪薇","13705310052"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","宋子豪","13705310053"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","钟博文","13705310054"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","边成姝","13705310055"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","张人月","13705310056"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","赵梦莎","13705310057"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","张中垟","13705310058"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","吴柳琴","13705310059"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","朱悦","13705310060"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","曾骁杲","13705310061"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","赵瑞轩","13705310062"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","何林峰","13705310063"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","李昊","13705310064"));
        mTongxunlus.add(new Tongxunlu("新兴社区居委会","杨雅迪","13705310065"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","",""));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","魏鑫","13705310066"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","和平社区居委会张芮铖","13705310067"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","赵苑竹","13705310068"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","潘冰倩","13705310069"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","刘芳利","13705310070"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","王梦媛","13705310071"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","赵岑","13705310072"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","朱俊哲","13705310073"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","周丹涵","13705310074"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","鲜艾琳","13705310075"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","鲁雪坤","13705310076"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","蒋俊豪","13705310077"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","吴雪瑞","13705310078"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","杨菲尔","13705310079"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","柳倩","13705310080"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","王谊偲","13705310081"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","邓宸","13705310082"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","丁冠华","13705310083"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","白里拉","13705310084"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","李宜雯","13705310085"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","赵卓","13705310086"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","虞尧","13705310087"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","陈思鹏","13705310088"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","毛健男","13705310089"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","陶柯宇","13705310090"));
        mTongxunlus.add(new Tongxunlu("和平社区居委会","陈奕桥","13705310091"));
        mTongxunlus.add(new Tongxunlu("胜兴社区居委会","",""));
        mTongxunlus.add(new Tongxunlu("胜兴社区居委会","陈育琦","13705310092"));
        mTongxunlus.add(new Tongxunlu("胜兴社区居委会","何蔓诗","13705310093"));
        mTongxunlus.add(new Tongxunlu("胜兴社区居委会","徐波","13705310094"));
        mTongxunlus.add(new Tongxunlu("胜兴社区居委会","李静雨","13705310095"));
        mTongxunlus.add(new Tongxunlu("胜兴社区居委会","梁方茹","13705310096"));
        mTongxunlus.add(new Tongxunlu("胜兴社区居委会","王芸静","13705310097"));
        mTongxunlus.add(new Tongxunlu("胜兴社区居委会","陈薪羽","13705310098"));
        mTongxunlus.add(new Tongxunlu("胜兴社区居委会","王彦淇","13705310099"));

        if (mTongxunlus.size()==0){
            mRvItems.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }else{
            mRvItems.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        }
        mItemAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_tongxunlu_result);
    }

    class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            switch (i){
                case 1:
                    return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(
                            R.layout.item_tongxunlu, null));
                case 0:
                default:
                    return new HeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(
                            R.layout.tongxunlu_head, null));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder itemViewHolder, int position) {
            switch (getItemViewType(position)){
                case 1:
                    ((ItemViewHolder)itemViewHolder).mDeccribe.setText(mTongxunlus.get(position).getName());
                    ((ItemViewHolder)itemViewHolder).mNumber.setText(mTongxunlus.get(position).getPhoneNumber());
                    break;
                case 0:
                default:
                    ((HeaderViewHolder)itemViewHolder).mDeccribe.setText(mTongxunlus.get(position).getOffice());
                    break;
            }
        }
        @Override
        public int getItemViewType(int position) {
            if (mTongxunlus!=null && position>=0 && mTongxunlus.size()>position){
                Tongxunlu tongxunlu = mTongxunlus.get(position);
                if (StringUtils.isEmpty(tongxunlu.getName()) && StringUtils.isEmpty(tongxunlu.getPhoneNumber())){
                    return 0;
                }else {
                    return 1;
                }
            }
            return 0;
        }
        @Override
        public int getItemCount() {
            return mTongxunlus.size();
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView mDeccribe;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mDeccribe = (TextView) itemView.findViewById(R.id.title);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mDeccribe;
        TextView mNumber;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mDeccribe = (TextView) itemView.findViewById(R.id.name);
            mNumber = (TextView) itemView.findViewById(R.id.phonenumber);
            itemView.setOnClickListener(this);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onClick(View view) {
            DebugLog.e("onClick" + getLayoutPosition());
            DebugLog.e("onClick" + getAdapterPosition());
            DebugLog.e(mTongxunlus.get(getAdapterPosition()).toString());
        }
    }
}
 */