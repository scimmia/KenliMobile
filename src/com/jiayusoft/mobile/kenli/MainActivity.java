package com.jiayusoft.mobile.kenli;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import butterknife.Bind;
//import com.jiayusoft.mobile.kenli.suifangdengji.ChaxunActivity;
//import com.jiayusoft.mobile.kenli.tongxunlu.TongxunluSearchActivity;
import com.jiayusoft.mobile.kenli.login.LoginActivity;
import com.jiayusoft.mobile.kenli.suifangdengji.ChaxunActivity;
import com.jiayusoft.mobile.kenli.tongxunlu.TongxunluSearchActivity;
import com.jiayusoft.mobile.kenli.utils.DebugLog;
import com.jiayusoft.mobile.kenli.utils.app.BaseActivity;
import com.jiayusoft.mobile.kenli.utils.app.clientinfo.ClientinfoAdapter;
import com.jiayusoft.mobile.kenli.utils.app.clientinfo.ClientinfoItem;
import com.jiayusoft.mobile.kenli.utils.app.viewPager.LoopViewPager;
import com.jiayusoft.mobile.kenli.utils.app.viewPager.transforms.*;

import java.util.ArrayList;


public class MainActivity extends BaseActivity {

    ArrayList<ClientinfoItem> iconItems;
    ClientinfoAdapter adapter;

    @Bind(R.id.logo_img)
    ImageView mLogoImg;
    @Bind(R.id.logo_imgs)
    LoopViewPager mLogoImgs;
    @Bind(R.id.grid_main)
    GridView mGridMain;

    String[] logoNames = {
            "http://rkjsj.kenli.gov.cn/__local/0/BD/CE/0B644A5556A30ED2E974B4DF69A_C86B407E_15212.jpg",
            "http://rkjsj.kenli.gov.cn/__local/A/13/81/28FE059A651E14CF22B64CAB52E_FB1AD48A_6503.jpg",
            "http://rkjsj.kenli.gov.cn/__local/9/D3/CE/C37B12210F5124EB675E7682583_CBB922BA_5C80.jpg",
            "http://rkjsj.kenli.gov.cn/__local/6/42/C2/B9A170546CFEDD18737CED565E9_BE123013_5C66.jpg",
            "http://rkjsj.kenli.gov.cn/images/11/05/29/dme31f7m41/1303b87ca99.jpg",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLogoImg.setImageResource(R.drawable.logo_sph);
        if (logoNames!=null){
            if (logoNames.length == 1){
                String imageUrl = "http://"
                        + PreferenceManager.getDefaultSharedPreferences(getBaseActivity()).getString(serverUrl, defaultServerUrl)
                        + String.format(logoImgUrl, logoNames[0]);
                BaseApplication.loadImage(getBaseActivity(),mLogoImg,imageUrl,R.drawable.logo_sph);
            }else{
                mLogoImgs.setAdapter(new SamplePagerAdapter(logoNames));
                mLogoImgs.setVisibility(View.VISIBLE);
                mLogoImg.setVisibility(View.GONE);
                handler.postDelayed(runnable, TIME); //每隔1s执行
            }
        }
        iconItems = new ArrayList<ClientinfoItem>();
        iconItems.add(new ClientinfoItem(ChaxunActivity.class, R.drawable.icon_main_suifangdengji, R.string.main_suifangdengji));
        iconItems.add(new ClientinfoItem(MainActivity.class, R.drawable.icon_main_wischaxun, R.string.main_wischaxun));
        iconItems.add(new ClientinfoItem(MainActivity.class, R.drawable.icon_main_chatiqingkuang, R.string.main_chatiqingkuangn));
        iconItems.add(new ClientinfoItem(MainActivity.class, R.drawable.icon_main_tongzhixiaoxi, R.string.main_tongzhixiaoxi));
        iconItems.add(new ClientinfoItem(TongxunluSearchActivity.class,R.drawable.icon_main_person,R.string.main_binganzhikong));
        iconItems.add(new ClientinfoItem(LoginActivity.class,R.drawable.icon_main_setting,R.string.main_shezhi));
        adapter = new ClientinfoAdapter(getBaseActivity(), iconItems);
        mGridMain.setAdapter(adapter);
        mGridMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DebugLog.e("position:" + position + iconItems.get(position).toString());
                switch(position){
                    case 5:
                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseActivity());
                        SharedPreferences.Editor spEd = sp.edit();
                        spEd.remove(loginAutoLogin);
                        spEd.apply();
                        beginActivity(LoginActivity.class);
                        finish();
                        break;
                    default:
                        Bundle bundle = new Bundle();
                        bundle.putInt(itemType, iconItems.get(position).getmTitleId());
                        beginActivity(iconItems.get(position).getLaunche(), bundle);
                        break;
                }
            }
        });
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_main);
    }


    static int i = 0;
    private int TIME = 3000;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, TIME);
                i = (i + 1) % TRANSFORM_CLASSES.size();
//                DebugLog.e("position---" + i);
                mLogoImgs.setPageTransformer(true, TRANSFORM_CLASSES.get(i).clazz.newInstance());

                int count = mLogoImgs.getAdapter().getCount();
                int index = mLogoImgs.getCurrentItem();
                index = (index + 1) % (count); //这里修改过
                mLogoImgs.setCurrentItem(index, true);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("exception...");
            }
        }
    };
    private static long back_pressed;

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis())
            super.onBackPressed();
        else
            showMessage("再按一次退出移动病案!");
        back_pressed = System.currentTimeMillis();
    }

    class SamplePagerAdapter extends PagerAdapter {
        String[] mItemsUrlList;

        public SamplePagerAdapter(String[] urls) {
            mItemsUrlList = urls;
        }

        @Override
        public int getCount() {
            return mItemsUrlList.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            ImageView photoView = new ImageView(container.getContext());
            photoView.setScaleType(ImageView.ScaleType.FIT_XY);

            String url = mItemsUrlList[position];
            BaseApplication.loadImage(getBaseActivity(),photoView,url,R.drawable.logo_sph);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    private static final class TransformerItem {

        final String title;
        final Class<? extends ViewPager.PageTransformer> clazz;

        public TransformerItem(Class<? extends ViewPager.PageTransformer> clazz) {
            this.clazz = clazz;
            title = clazz.getSimpleName();
        }

        @Override
        public String toString() {
            return title;
        }

    }

    private static final ArrayList<TransformerItem> TRANSFORM_CLASSES;

    static {
        TRANSFORM_CLASSES = new ArrayList<TransformerItem>();
        TRANSFORM_CLASSES.add(new TransformerItem(DefaultTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(AccordionTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(BackgroundToForegroundTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(CubeInTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(CubeOutTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(DepthPageTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(FlipHorizontalTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(FlipVerticalTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ForegroundToBackgroundTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(RotateDownTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(RotateUpTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ScaleInOutTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(StackTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(TabletTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ZoomInTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ZoomOutSlideTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ZoomOutTranformer.class));
    }

}
