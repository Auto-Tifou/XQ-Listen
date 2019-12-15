package mobapplication.himalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.word.HotWord;
import com.ximalaya.ting.android.opensdk.model.word.QueryResult;

import java.util.List;

/**
 * 创建 by Administrator in 2019/12/14 0014
 *
 * 说明 : 搜索回调更新UI接口
 * @Useage :
 **/
public interface ISearchCallback {

    /**
     * 搜索结果的回调方法
     * @param result
     */
    void onSearchResultLoaded(List<Album> result);

    /**
     * 获取推荐热词的回调方法
     * @param hotWordList
     */
    void onHotWordLoaded(List<HotWord> hotWordList);

    /**
     * 加载更多的结果返回
     * @param result  结果
     * @param isOkay true表示加载更多,false表示没有更多
     */
    void onLoadMoreResult(List<Album> result,boolean isOkay);

    /**
     * 联想关键词的结果回调方法
     * @param keyWordList
     */
    void onRecommendWordLoaded(List<QueryResult> keyWordList);

    /**
     * 搜索错误通知
     * @param errorCode
     * @param errorMag
     */
    void onError(int errorCode,String errorMag);
}
