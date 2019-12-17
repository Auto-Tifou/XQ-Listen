package mobapplication.himalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.track.Track;

import mobapplication.himalaya.base.IBasePresenter;

public interface IHistoryPresenter extends IBasePresenter<IHistoryCallback> {

    /**
     * 获取历史内容.
     */
    void listHistories();

    /**
     * 添加历史
     *
     * @param track
     */
    void addHistory(Track track);

    /**
     * 删除历史
     *
     * @param track
     */
    void delHistory(Track track);

    /**
     * 清除历史
     */
    void cleanHistories();

}
