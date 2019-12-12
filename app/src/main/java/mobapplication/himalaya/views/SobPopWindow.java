package mobapplication.himalaya.views;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

import mobapplication.himalaya.R;
import mobapplication.himalaya.adapters.PlayListAdapter;
import mobapplication.himalaya.base.BaseApplication;

/**
 * 播放列表pop的显示
 */
public class SobPopWindow extends PopupWindow{

    private final View mPopView;
    private TextView mCloseBtn;
    private RecyclerView mTracksList;
    private PlayListAdapter mPlayListAdapter;

    public SobPopWindow(){
        //设置宽高
        super(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //注意:设置setOutsideTouchable之前,先要设置setBackgroundDrawable,
        //否则点击外部无法关闭pop
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(true);
        //View载进来
        mPopView = LayoutInflater.from(BaseApplication.getAppContext()).inflate(R.layout.pop_play_list, null);
        //设置内容
        setContentView(mPopView);
        //设置窗口进入和弹出的动画
        setAnimationStyle(R.style.pop_animation);
        initView();
        initEvent();
    }

    //点击pop里面关闭按钮pop窗口消失
    private void initEvent() {
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SobPopWindow.this.dismiss();
            }
        });
    }

    /**
     * 给适配器创建数据
     * @param data
     */
    public void setListData(List<Track> data){
        if (mPlayListAdapter != null) {
            mPlayListAdapter.setData(data);
        }
    }

    /**
     * 节目列表同步联动
     * @param position
     */
    public void setCurrentPlayPosition(int position){
        if (mPlayListAdapter != null) {
            mPlayListAdapter.setCurrentPlayPosition(position);
            mTracksList.scrollToPosition(position);
        }
    }

    private void initView() {
        mCloseBtn = mPopView.findViewById(R.id.play_list_close_Btn);
        //找到控件
        mTracksList = mPopView.findViewById(R.id.play_list_rv);
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(BaseApplication.getAppContext());
        mTracksList.setLayoutManager(layoutManager);
        //设置适配器
        mPlayListAdapter = new PlayListAdapter();
        mTracksList.setAdapter(mPlayListAdapter);
    }

    public void setPlayListItemClickListener(PlayListItemClickListener listener){
        mPlayListAdapter.setOnItemClickListener(listener);
    }

    public interface PlayListItemClickListener{
        void onItemClick(int position);
    }
}
