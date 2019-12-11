package mobapplication.himalaya.presenters;

import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.advertis.Advertis;
import com.ximalaya.ting.android.opensdk.model.advertis.AdvertisList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.advertis.IXmAdsStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import java.util.ArrayList;
import java.util.List;

import mobapplication.himalaya.base.BaseApplication;
import mobapplication.himalaya.interfaces.IPlayerCallback;
import mobapplication.himalaya.interfaces.IPlayerPresenter;
import mobapplication.himalaya.utils.LogUtil;

public class PlayerPresenter implements IPlayerPresenter, IXmAdsStatusListener, IXmPlayerStatusListener {


    private List<IPlayerCallback> mIPlayerCallbacks = new ArrayList<>();
    private static final String TAG = "PlayerPresenter";
    private final XmPlayerManager mPlayerManager;

    //设置单例
    private PlayerPresenter(){
        //拿到播放器实例
        mPlayerManager = XmPlayerManager.getInstance(BaseApplication.getAppContext());
        //广告相关的接口
        mPlayerManager.addAdsStatusListener(this);
        //注册播放器相关的接口
        mPlayerManager.addPlayerStatusListener(this);
    }
    private static PlayerPresenter sPlayerPresenter;

    public static PlayerPresenter getPlayerPresenter(){
        if (sPlayerPresenter == null) {
            synchronized (PlayerPresenter.class){
                //锁
                if (sPlayerPresenter == null) {
                    sPlayerPresenter = new PlayerPresenter();
                }
            }
        }
        return sPlayerPresenter;
    }

    private boolean isPlayListSet = false;
    //点击第几首播放
    public void setPlayList(List<Track>list,int playIndex){
        if (mPlayerManager != null) {
            mPlayerManager.setPlayList(list,playIndex);
            isPlayListSet = true;
        }else {
            LogUtil.d(TAG,"mPlayerManager is null");
        }

    }

    /**
     * 播放器相关情况
     */

    @Override
    public void play() {
        if (isPlayListSet) {
            mPlayerManager.play();
        }
    }

    @Override
    public void pause() {
        if (mPlayerManager != null) {
            mPlayerManager.pause();
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void playPre() {

    }

    @Override
    public void playNext() {

    }

    @Override
    public void switchPlayMode(XmPlayListControl.PlayMode mode) {

    }

    @Override
    public void getPlayList() {

    }

    @Override
    public void playByIndex(int index) {

    }

    @Override
    public void seekTo(int progress) {

    }

    @Override
    public boolean isPlay() {
        //返回当前是否正在播放
         return mPlayerManager.isPlaying();
    }

    @Override
    public void registerViewCallback(IPlayerCallback iPlayerCallback) {
        if (!mIPlayerCallbacks.contains(iPlayerCallback)) {
            mIPlayerCallbacks.add(iPlayerCallback);
        }
    }

    @Override
    public void unRegisterViewCallback(IPlayerCallback iPlayerCallback) {
        mIPlayerCallbacks.remove(iPlayerCallback);
    }

    //==========================广告相关回调方法 start======================
    @Override
    public void onStartGetAdsInfo() {
        //获取广告物料
        LogUtil.d(TAG,"onStartGetAdsInfo");
    }

    @Override
    public void onGetAdsInfo(AdvertisList advertisList) {
        //获取到广告列表
        LogUtil.d(TAG,"onGetAdsInfo");
    }

    @Override
    public void onAdsStartBuffering() {
        //正在缓冲
        LogUtil.d(TAG,"onAdsStartBuffering");
    }

    @Override
    public void onAdsStopBuffering() {
        //停止缓冲
        LogUtil.d(TAG,"onAdsStopBuffering");
    }

    @Override
    public void onStartPlayAds(Advertis advertis, int i) {
        //开始播放广告物料
        LogUtil.d(TAG,"onStartPlayAds");
    }

    @Override
    public void onCompletePlayAds() {
        //停止播放广告物料
        LogUtil.d(TAG,"onCompletePlayAds");
    }

    @Override
    public void onError(int what, int extra) {
        //错误
        LogUtil.d(TAG,"onError what -->"+ what +"onError extra -->" + extra);
    }

    //==========================广告相关回调方法 end======================


    //==========================播放器相关回调方法 start======================

    @Override
    public void onPlayStart() {
        LogUtil.d(TAG,"onPlayStart");
        for (IPlayerCallback iPlayerCallback : mIPlayerCallbacks) {
            iPlayerCallback.onPlayStart();
        }
    }

    @Override
    public void onPlayPause() {
        LogUtil.d(TAG,"onPlayPause");
        for (IPlayerCallback iPlayerCallback : mIPlayerCallbacks) {
            iPlayerCallback.onPlayPayse();
        }
    }

    @Override
    public void onPlayStop() {
        LogUtil.d(TAG,"onPlayStop");
        for (IPlayerCallback iPlayerCallback : mIPlayerCallbacks) {
            iPlayerCallback.onPlayStop();
        }
    }

    //播放完成等状态
    @Override
    public void onSoundPlayComplete() {
        LogUtil.d(TAG,"onSoundPlayComplete");
    }

    @Override
    public void onSoundPrepared() {
        LogUtil.d(TAG,"onSoundPrepared");
    }

    @Override
    public void onSoundSwitch(PlayableModel playableModel, PlayableModel playableModel1) {
        LogUtil.d(TAG,"onSoundSwitch");
    }

    //缓冲
    @Override
    public void onBufferingStart() {
        LogUtil.d(TAG,"onBufferingStart");
    }

    @Override
    public void onBufferingStop() {
        LogUtil.d(TAG,"onBufferingStop");
    }

    @Override
    public void onBufferProgress(int progress) {
        LogUtil.d(TAG,"onBufferProgress -->" + progress );
    }

    //播放进度
    @Override
    public void onPlayProgress(int currPos, int duration) {
        //单位是毫秒
        for (IPlayerCallback iPlayerCallback : mIPlayerCallbacks) {
            iPlayerCallback.onProgressChange(currPos,duration);
        }
        LogUtil.d(TAG,"onPlayProgress currPos--> " + currPos+"duration " + duration);

}

    @Override
    public boolean onError(XmPlayerException e) {
        LogUtil.d(TAG,"onError e -->" + e);
        return false;
    }

    //==========================播放器相关回调方法 end======================
}
