package mobapplication.himalaya.interfaces;

import mobapplication.himalaya.base.IBasePresenter;

/**
 * 创建 by Administrator in 2019/12/6 0006
 *
 * 说明 : 减少接口冗余,MVP模式
 * @Useage :
 **/
public interface IRecommendPresenter extends IBasePresenter <IRecommendViewCallback>{
    /**
     * 获取推荐内容
     */
    void getRecommendList();

}
