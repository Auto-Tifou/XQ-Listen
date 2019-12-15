package mobapplication.himalaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.List;

import mobapplication.himalaya.R;

/**
 * 创建 by Administrator in 2019/12/11 0011
 * <p>
 * 说明 : 详情播放页面的图片adapter
 *
 * @Useage :
 **/
public class PlayerTrackPagerAdapter extends PagerAdapter {

    private List<Track> mData = new ArrayList<>();

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_track_page, container, false);
        container.addView(itemView);
        //设置数据
        //找到控件
        ImageView item = itemView.findViewById(R.id.track_pager_item);
        //设置内容图片
        Track track = mData.get(position);
        String coverUrlLarge = track.getCoverUrlLarge();

        //加载图片
        if (coverUrlLarge.trim().length() == 0) {
            //为0则加载默认图片
            Picasso.with(container.getContext()).load(R.drawable.picasso_error_bg).into(item);
        }else {
            Picasso.with(container.getContext()).load(coverUrlLarge).into(item);
        }
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void setData(List<Track> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }
}
