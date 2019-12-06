package mobapplication.himalaya.interfaces;

/**
 * 创建 by Administrator in 2019/12/6 0006
 *
 * 说明 : 减少接口冗余,MVP模式
 * @Useage :
 **/
public interface IRecommendPresenter {
    /**
     * 获取推荐内容
     */
    void getRecommendList();

    /**
     * 下拉刷新更多内容
     */
    void pullZRefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();

    /**
     * 注册UI的回调
     * @param callback
     */
    void  registerViewCallback(IRecommendViewCallback callback);

    /**
     * 取消UI的回调注册
     * @param callback
     */
    void unRegisterViewCallback(IRecommendViewCallback callback);

}
