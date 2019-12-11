package mobapplication.himalaya;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.text.SimpleDateFormat;
import java.util.List;

import mobapplication.himalaya.base.BaseActivity;
import mobapplication.himalaya.interfaces.IPlayerCallback;
import mobapplication.himalaya.presenters.PlayerPresenter;

/**
 * 创建 by Administrator in 2019/12/10 0010
 *
 * 说明 :播放页面
 * @Useage :
 **/
public class PlayerActivity extends BaseActivity implements IPlayerCallback {

    private ImageView mControlBtn;
    private PlayerPresenter mPlayerPresenter;
    private SimpleDateFormat mMinFormat = new SimpleDateFormat("mm:ss");
    private SimpleDateFormat mHourFormat = new SimpleDateFormat("hh:mm:ss");
    private TextView mTotalDuration;
    private TextView mCurrentPosition;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        //注册接口回调
        mPlayerPresenter = PlayerPresenter.getPlayerPresenter();
        mPlayerPresenter.registerViewCallback(this);
        initView();
        initEvent();
        startPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        if (mPlayerPresenter != null) {
            mPlayerPresenter.unRegisterViewCallback(this);
            mPlayerPresenter = null;
        }
    }

    //开始播放
    private void startPlay() {
        if (mPlayerPresenter != null) {
            mPlayerPresenter.play();
        }
    }

    /**
     * 给控件设置相关的事件
     */
    private void initEvent() {
        mControlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayerPresenter.isPlay()) {
                    //如果现在的状态是正在播放,那就暂停
                    mPlayerPresenter.pause();
                }else {
                    //如果现在的状态是非播放,那就让播放器播放节目
                    mPlayerPresenter.play();
                }
            }
        });
    }

    /**
     * 找到各个控件
     */
    private void initView() {
        mControlBtn = findViewById(R.id.play_or_pause_btn);
        mTotalDuration = findViewById(R.id.track_duration);
        mCurrentPosition = findViewById(R.id.current_position);
    }

    @Override
    public void onPlayStart() {
        //开始播放,修改UI成暂停按钮
        if (mControlBtn != null) {
            mControlBtn.setImageResource(R.drawable.img_bofang_zt);
        }
    }

    @Override
    public void onPlayPayse() {
        //暂停播放,UI设置回去
        if (mControlBtn != null) {
            mControlBtn.setImageResource(R.drawable.img_bofang_bf);
        }
    }

    @Override
    public void onPlayStop() {
        //停止播放修改回去UI
        if (mControlBtn != null) {
            mControlBtn.setImageResource(R.drawable.img_bofang_zt);
        }
    }

    @Override
    public void onPlayError() {

    }

    @Override
    public void onPrePlay(Track track) {

    }

    @Override
    public void nextPlay(Track track) {

    }

    @Override
    public void onListLoaded(List<Track> list) {

    }

    @Override
    public void onPlayModeChange(XmPlayListControl.PlayMode playMode) {

    }

    @Override
    public void onProgressChange(long currentProgress, long total) {
        //更新播放进度,更新进度条
        String totalDuration;
        String currentPosition;
        if (total>1000*60*60) {
            totalDuration = mHourFormat.format(total);
            currentPosition = mHourFormat.format(currentProgress);
        }else {
            totalDuration = mMinFormat.format(total);
            currentPosition = mMinFormat.format(currentProgress);
        }
        if (mTotalDuration != null) {
            mTotalDuration.setText(totalDuration);
        }
        //更新当前时间
        if (mCurrentPosition != null) {
            mCurrentPosition.setText(currentPosition);
        }
        //更新进度
    }

    @Override
    public void onAdLoadong() {

    }

    @Override
    public void onAdFinished() {

    }
}
