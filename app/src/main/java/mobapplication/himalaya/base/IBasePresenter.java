package mobapplication.himalaya.base;

public interface IBasePresenter<T> {

    //注册UI回调接口
    void  registerViewCallback(T t);

    //取消注册UI回调接口
    void unRegisterViewCallback(T t);
}
