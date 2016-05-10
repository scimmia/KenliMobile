package com.jiayusoft.mobile.kenli.suifangdengji;

//import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jiayusoft.mobile.kenli.R;
import com.jiayusoft.mobile.kenli.utils.DebugLog;
import com.jiayusoft.mobile.kenli.utils.app.BaseActivity;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;

public class ChaxunResultActivity extends BaseActivity {

    ItemAdapter mItemAdapter;
    private LinkedList<ChaXunResult.RootBean.ResponseBean.RowBean> mResults;

    @Bind(R.id.list_result)
    ListView mRvItems;
    @Bind(android.R.id.empty)
    TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResults = new LinkedList<>();
        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                String jsonBody = bundle.getString(JsonBody, "");
                if (StringUtils.isNotEmpty(jsonBody)) {
                    ChaXunResult chaXunResult = new Gson().fromJson(jsonBody, ChaXunResult.class);
                    mResults.clear();
                    mResults.addAll(chaXunResult.getRoot().getResponse().getRow());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                beginActivity(SuifangJibenxinxiActivity.class, bundle);
            }
        });
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_suifangchaxun_result);
    }

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

    //05-10 13:43:31.545 520-520/com.jiayusoft.mobile.kenli E/ChaxunResultActivity.java: [onItemClick:59]{"boyAmount":"1","femaleBirthday":"","femaleCardid":"","femaleId":"370521000000002152","femaleMaritalStatus":"","femaleName":"","girlAmount":"0","maleBirthday":"1981-01-14","maleCardid":"370521198101143617","maleMaritalStatus":"10","maleName":"饶国强","maritalChangeDate":"","orgid":"370521104207","orgname":"一村村委会"}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chaxun_new, menu);
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
