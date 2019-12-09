package mobapplication.himalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public interface IAlbumDetailViewCallback {

    /**
     * 专辑详情内容加载出来
     * @param tracks
     */
    void onDetailListLoaded(List<Track> tracks);

    /**
     * 网络错误
     * @param errorCode
     * @param errorMsg
     */
    void onNetworkError(int errorCode, String errorMsg);

    /**
     * 把album传UI使用
     * @param album
     */
    void onAlbumLoaded(Album album);
}
