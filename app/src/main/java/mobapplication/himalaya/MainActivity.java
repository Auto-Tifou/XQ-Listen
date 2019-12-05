package mobapplication.himalaya;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobapplication.himalaya.adapters.IndicatorAdapter;
import mobapplication.himalaya.adapters.MainContentAdapter;
import mobapplication.himalaya.utils.LogUtil;

/**
 * 接入喜马拉雅SDK
 * 测试成功
 */
public class MainActivity extends FragmentActivity {
    private static final String TAG = "MainActivity";

    private MagicIndicator mMagicIndicator;
    private ViewPager mContentPager;
    private IndicatorAdapter mIndicatorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Ceshi();
        initEvent();
    }

//    点击事件
    private void initEvent() {
        mIndicatorAdapter.setOnIndicatorTapClickListener(new IndicatorAdapter.OnIndicatorTapClickListener() {
            @Override
            public void onTabClick(int index) {
                LogUtil.d(TAG,"click index is --->" +index);
                if (mContentPager != null) {
                    mContentPager.setCurrentItem(index);
                }
            }
        });
    }

    private void initView() {
        mMagicIndicator = findViewById(R.id.main_indicator);
//        背景色
        mMagicIndicator.setBackgroundColor(this.getResources().getColor(R.color.main_color));
//        创建indicator的适配器
        mIndicatorAdapter = new IndicatorAdapter(this);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);//自我调节,平分位置
        commonNavigator.setAdapter(mIndicatorAdapter);

//        ViewPager
        mContentPager = findViewById(R.id.content_pager);
////        滑动监听
//        mContentPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });


        //创建内容的适配器
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        MainContentAdapter mainContentAdapter = new MainContentAdapter(supportFragmentManager);
        mContentPager.setAdapter(mainContentAdapter);

//        ViewPager和indicator适配绑定
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator,mContentPager);
    }

    //    测试连接SDK成功代码
    private void Ceshi() {
        /**
         * 接入分类目录
         * 成功
         * 失败
         */
        Map<String, String> map = new HashMap<>();
        CommonRequest.getCategories(map, new IDataCallBack<CategoryList>() {
            @Override
//            成功
            public void onSuccess(CategoryList categoryList) {
                List<Category> categories = categoryList.getCategories();
                if (categories != null) {
                    int size = categories.size();
                    Log.d(TAG, "categories.size --->" + size);
                    for (Category category : categories) {
//                        Log.d(TAG,"categories --->" + category.getCategoryName());
                        LogUtil.d(TAG, "categories --->" + category.getCategoryName());
                    }
                }
            }

            @Override
//            失败
            public void onError(int i, String s) {
//                Log.e(TAG,"error code -->" + i +" error msg -->"+ s);
                LogUtil.d(TAG, "error code -->" + i +" error msg -->"+ s);
            }
        });
    }
}
