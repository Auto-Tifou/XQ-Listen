package mobapplication.himalaya;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobapplication.himalaya.adapters.PlayerTrackPagerAdapter;
import mobapplication.himalaya.base.BaseActivity;
import mobapplication.himalaya.interfaces.IPlayerCallback;
import mobapplication.himalaya.presenters.PlayerPresenter;
import mobapplication.himalaya.utils.LogUtil;
import mobapplication.himalaya.views.SobPopWindow;

import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_LIST;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP;

/**
 * 创建 by Administrator in 2019/12/10 0010
 * <p>
 * 说明 :播放页面
 *
 * @Useage :
 **/
public class PlayerActivity extends BaseActivity implements IPlayerCallback, ViewPager.OnPageChangeListener {

    private static final String TAG = "PlayerActivity";
    private ImageView mControlBtn;
    private PlayerPresenter mPlayerPresenter;
    private SimpleDateFormat mMinFormat = new SimpleDateFormat("mm:ss");
    private SimpleDateFormat mHourFormat = new SimpleDateFormat("hh:mm:ss");
    private TextView mTotalDuration;
    private TextView mCurrentPosition;
    private SeekBar mDurationBar;
    private int mCurrentProgress = 0;
    private boolean mIsUserTouchProgressBar = false;
    private ImageView mPlayPreBtn;
    private ImageView mPlayNextBtn;
    private TextView mTrackTitleTv;
    private String mTrackTitleText;
    private ViewPager mTrackPagerView;
    private PlayerTrackPagerAdapter mTrackPagerAdapter;
    private boolean mIsUserSlidePager = false;
    private ImageView mPlayModeSwitchBtn;

    private XmPlayListControl.PlayMode mCurrentMode = PLAY_MODEL_LIST_LOOP;
    //
    private static Map<XmPlayListControl.PlayMode,XmPlayListControl.PlayMode> sPlayModeRule = new HashMap<>();

    //处理播放模式的切换
    //1.默认列表播放:PLAY_MODEL_LIST
    //2.列表循环:PLAY_MODEL_LIST_LOOP
    //3.随机播放:PLAY_MODEL_RANDOM
    //4.单曲循环:PLAY_MODEL_SINGLE_LOOP

    static {
        sPlayModeRule.put(PLAY_MODEL_LIST, PLAY_MODEL_LIST_LOOP);
        sPlayModeRule.put(PLAY_MODEL_LIST_LOOP,PLAY_MODEL_RANDOM);
        sPlayModeRule.put(PLAY_MODEL_RANDOM,PLAY_MODEL_SINGLE_LOOP);
        sPlayModeRule.put(PLAY_MODEL_SINGLE_LOOP,PLAY_MODEL_LIST);
    }

    private ImageView mPlayListBtn;
    private SobPopWindow mSobPopWindow;
    private ValueAnimator mEnterBgAnimator;
    private ValueAnimator mOutBgAnimator;
    public final int BG_ANIMATION_DURATION = 500;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        initView();
        //注册接口回调
        mPlayerPresenter = PlayerPresenter.getPlayerPresenter();
        mPlayerPresenter.registerViewCallback(this);
        //在界面初始化以后,才去获取数据
        mPlayerPresenter.getPlayList();
        initEvent();
        initBgAnimation();
    }

    //动画透明度渐变变化
    private void initBgAnimation() {
        mEnterBgAnimator = ValueAnimator.ofFloat(1.0f,0.7f);
        mEnterBgAnimator.setDuration(BG_ANIMATION_DURATION);
        mEnterBgAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                LogUtil.d(TAG,"value -->" + animation.getAnimatedValue());
                //处理背景透明度
                updateBgAlpha(value);
            }
        });
        //退出的动画渐变
        mOutBgAnimator = ValueAnimator.ofFloat(0.7f,1.0f);
        mOutBgAnimator.setDuration(BG_ANIMATION_DURATION);
        mOutBgAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                LogUtil.d(TAG,"value -->" + animation.getAnimatedValue());
                //处理背景透明度
                updateBgAlpha(value);
            }
        });

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

    /**
     * 给控件设置相关的事件
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initEvent() {
        mControlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayerPresenter.isPlay()) {
                    //如果现在的状态是正在播放,那就暂停
                    mPlayerPresenter.pause();
                } else {
                    //如果现在的状态是非播放,那就让播放器播放节目
                    mPlayerPresenter.play();
                }
            }
        });
        //进度随拖动变化
        mDurationBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
                if (isFromUser) {
                    mCurrentProgress = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mIsUserTouchProgressBar = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mIsUserTouchProgressBar = false;
                //手离开拖动进度条更进度
                mPlayerPresenter.seekTo(mCurrentProgress);
            }
        });

        //点击播放上一个节目
        mPlayPreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayerPresenter != null) {
                    mPlayerPresenter.playPre();
                }
            }
        });

        //点击播放下一个节目
        mPlayNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayerPresenter != null) {
                    mPlayerPresenter.playNext();
                }
            }
        });

        //滑动切换图片
        mTrackPagerView.addOnPageChangeListener(this);

        mTrackPagerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        mIsUserSlidePager = true;
                    break;
                    case MotionEvent.ACTION_UP:

                    break;
                }
                return false;
            }
        });
        mPlayModeSwitchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //根据当前的mode获取到下一给mode
                XmPlayListControl.PlayMode playMode = sPlayModeRule.get(mCurrentMode);
                //修改播放模式
                if (mPlayerPresenter != null) {
                    mPlayerPresenter.switchPlayMode(playMode);
                }
            }
        });

        mPlayListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示播放列表
                mSobPopWindow.showAtLocation(v, Gravity.BOTTOM,0,0);
                //修改背景透明,渐变过程

            }
        });
        mSobPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //pop窗体消失以后,恢复透明度
                mOutBgAnimator.start();
            }
        });

        mSobPopWindow.setPlayListItemClickListener(new SobPopWindow.PlayListItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //说明播放列表里的item被点击
                if (mPlayerPresenter != null) {
                    mPlayerPresenter.playByIndex(position);
                }
            }
        });
    }

    public void updateBgAlpha(float alpha){
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = alpha;
        window.setAttributes(attributes);
    }

    /**
     * 根据当前的状态更新播放模式图标
     * PLAY_MODEL_LIST
     * PLAY_MODEL_LIST_LOOP
     * PLAY_MODEL_RANDOM
     * PLAY_MODEL_SINGLE_LOOP
     */
    private void updatePlayModeBtnImg() {
        int resId = R.drawable.img_bofang_xh;
        switch (mCurrentMode){
            case PLAY_MODEL_LIST:
                resId = R.drawable.img_bofang_xh;
                Toast.makeText(this,"顺序播放",Toast.LENGTH_SHORT).show();
                break;
            case PLAY_MODEL_RANDOM:
                resId = R.drawable.img_bofang_sj;
                Toast.makeText(this,"随机播放",Toast.LENGTH_SHORT).show();
                break;
            case PLAY_MODEL_LIST_LOOP:
                resId = R.drawable.img_bofang_lb;
                Toast.makeText(this,"列表播放",Toast.LENGTH_SHORT).show();
                break;
            case PLAY_MODEL_SINGLE:
                resId = R.drawable.img_bofang_xzlb;
                Toast.makeText(this,"单曲播放",Toast.LENGTH_SHORT).show();
                break;
        }
        mPlayModeSwitchBtn.setImageResource(resId);
    }

    /**
     * 找到各个控件
     */
    private void initView() {
        mControlBtn = findViewById(R.id.play_or_pause_btn);
        mTotalDuration = findViewById(R.id.track_duration);
        mCurrentPosition = findViewById(R.id.current_position);
        mDurationBar = findViewById(R.id.track_seek_bar);
        mPlayPreBtn = findViewById(R.id.play_pre);
        mPlayNextBtn = findViewById(R.id.play_next);
        mTrackTitleTv = findViewById(R.id.track_title);
        //第一条节目标题不判空赋值
        if (!TextUtils.isEmpty(mTrackTitleText)) {
            mTrackTitleTv.setText(mTrackTitleText);
        }
        mTrackPagerView = findViewById(R.id.track_pager_view);
        //创建适配器
        mTrackPagerAdapter = new PlayerTrackPagerAdapter();
        //设置适配器
        mTrackPagerView.setAdapter(mTrackPagerAdapter);
        //切换播放模式按钮
        mPlayModeSwitchBtn = findViewById(R.id.player_mode_switch_btn);
        //播放列表
        mPlayListBtn = findViewById(R.id.player_list);
        mSobPopWindow = new SobPopWindow();
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
        LogUtil.d(TAG, "onListLoaded -- >" + list);
        //把数据设置到适配器
        if (mTrackPagerAdapter != null) {
            mTrackPagerAdapter.setData(list);
        }
        //数据回来以后,要给播放列表
        if (mSobPopWindow != null) {
            mSobPopWindow.setListData(list);
        }
    }

    @Override
    public void onPlayModeChange(XmPlayListControl.PlayMode playMode) {
        //更新播放模式并且修改UI
        mCurrentMode = playMode;
        updatePlayModeBtnImg();


    }

    @Override
    public void onProgressChange(int currentDuration, int total) {
        mDurationBar.setMax(total);
        //更新播放进度,更新进度条
        String totalDuration;
        String currentPosition;
        if (total > 1000 * 60 * 60) {
            totalDuration = mHourFormat.format(total);
            currentPosition = mHourFormat.format(currentDuration);
        } else {
            totalDuration = mMinFormat.format(total);
            currentPosition = mMinFormat.format(currentDuration);
        }
        if (mTotalDuration != null) {
            mTotalDuration.setText(totalDuration);
        }
        //更新当前时间
        if (mCurrentPosition != null) {
            mCurrentPosition.setText(currentPosition);
        }
        //更新进度
        if (!mIsUserTouchProgressBar) {
            mDurationBar.setProgress(currentDuration);
        }
    }

    @Override
    public void onAdLoading() {

    }

    @Override
    public void onAdFinished() {

    }

    @Override
    public void onTrackUpdate(Track track, int playIndex) {
        this.mTrackTitleText = track.getTrackTitle();
        if (mTrackTitleTv != null) {
            //设置当前节目标题
            mTrackTitleTv.setText(mTrackTitleText);
        }
        //当节目改变的时候,我们就获取到当前播放中播放数量
        //当前的节目改变以后,要修改页面的图片
        if (mTrackPagerView != null) {
            mTrackPagerView.setCurrentItem(playIndex, true);
        }

        //修改播放列表里的播放位置
        if (mSobPopWindow != null) {
            mSobPopWindow.setCurrentPlayPosition(playIndex);
        }
    }

    //滑动
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        LogUtil.d(TAG,"position -->" +position);
        //当页面选中的时候,切换播放的内容
        if (mPlayerPresenter != null && mIsUserSlidePager) {
            mPlayerPresenter.playByIndex(position);
        }
        mIsUserSlidePager = false;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
