package mobapplication.himalaya.interfaces;

import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import mobapplication.himalaya.base.IBasePresenter;

/**
 * 创建 by Administrator in 2019/12/10 0010
 *
 * 说明 : 播放情况接口
 * @Useage :
 **/
public interface IPlayerPresenter extends IBasePresenter<IPlayerCallback> {

    /**
     * 播放
     */
    void play();

    /**
     * 暂停
     */
    void pause();

    /**
     * 停止播放
     */
    void stop();

    /**
     * 上一首
     */
    void playPre();

    /**
     * 下一首
     */
    void playNext();

    /**
     * 切换模式
     * @param mode
     */
    void switchPlayMode(XmPlayListControl.PlayMode mode);

    /**
     * 获取播放列表
     */
    void getPlayList();

    /**
     * 根据节目位置进行播放
     * @param index 节目在列表中的位置
     */
    void playByIndex(int index);

    /**
     * 切换播放进度
     * @param progress
     */
    void seekTo(int progress);

    /**
     * 判断播放器是否在播放
     * @return
     */
    boolean isPlay();
}
