package mobapplication.himalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

/**
 * 创建 by Administrator in 2019/12/6 0006
 *
 * 说明 : 通知UI更新
 * @Useage :
 **/
public interface IRecommendViewCallback {

    /**
     * 获取推荐内容的结果
     * @param result
     */
    void onRecommendListLoaded(List<Album> result);

    /**
     * 下拉刷新
     * @param result
     */
    void onRefreshMore(List<Album> result);

    /**
     * 上拉加载更多
     * @param result
     */
    void onLoaderMore(List<Album> result);
}
