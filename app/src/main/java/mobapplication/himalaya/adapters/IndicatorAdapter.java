package mobapplication.himalaya.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import mobapplication.himalaya.R;

/**
 * 适配器
 */
public class IndicatorAdapter extends CommonNavigatorAdapter {

    private final String[] mTitle;
    private OnIndicatorTapClickListener mOnTabClickListener;

    public IndicatorAdapter(Context context){
//        标题内容
        mTitle = context.getResources().getStringArray(R.array.indicator_title);
    }

    @Override
    public int getCount() {
        if (mTitle != null) {
            return mTitle.length;
        }
        return 0;
    }

//    内容
    public IPagerTitleView getTitleView(Context context,final int index){
//        创建View
        ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
//        设置一般情况下的颜色为灰色
        colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#aaffffff"));
//        设置选中情况下的颜色为黑色
        colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
//        单位sp
        colorTransitionPagerTitleView.setTextSize(18);
//        设置要显示的内容
        colorTransitionPagerTitleView.setText(mTitle[index]);
//        设置title的点击事件,如果点击了title,那么就选中下面的ViewPager到相应的index里面去,对应切换内容
        colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                index不一样就切换ViewPager的内容
                if (mOnTabClickListener != null) {
                    mOnTabClickListener.onTabClick(index);
                }
            }
        });
//        把这个创建好的ViewPager返回回去
        return colorTransitionPagerTitleView;
    }
    public IPagerIndicator getIndicator(Context context){
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        indicator.setColors(Color.parseColor("#ffffff"));
        return indicator;
    }

    public void setOnIndicatorTapClickListener(OnIndicatorTapClickListener listener){
        this.mOnTabClickListener = listener;
    }

    public interface OnIndicatorTapClickListener{
        void onTabClick(int index);
    }
}
