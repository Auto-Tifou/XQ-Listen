package mobapplication.himalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

/**
 * 创建 by Administrator in 2019/12/10 0010
 * <p>
 * 说明 : 播放器UI回调的接口 情况改变
 *
 * @Useage :
 **/
public interface IPlayerCallback {

    /**
     * 开始播放
     */
    void onPlayStart();

    /**
     * 播放暂停
     */
    void onPlayPayse();

    /**
     * 播放停止
     */
    void onPlayStop();

    /**
     * 播放错误
     */
    void onPlayError();

    /**
     * 上一首播放
     */
    void onPrePlay(Track track);

    /**
     * 下一首播放
     */
    void nextPlay(Track track);

    /**
     * 播放列表数据加载完成
     *
     * @param list 播放器列表数据
     */
    void onListLoaded(List<Track> list);

    /**
     * 播放模式改变
     *
     * @param playMode
     */
    void onPlayModeChange(XmPlayListControl.PlayMode playMode);

    /**
     * 进度条改变
     *
     * @param currentProgress
     * @param total
     */
    void onProgressChange(long currentProgress, long total);

    /**
     * 广告正在加载
     */
    void onAdLoadong();

    /**
     * 广告结束
     */
    void onAdFinished();
}
