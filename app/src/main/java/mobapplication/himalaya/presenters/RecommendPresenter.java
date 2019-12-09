package mobapplication.himalaya.presenters;

import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobapplication.himalaya.interfaces.IRecommendPresenter;
import mobapplication.himalaya.interfaces.IRecommendViewCallback;
import mobapplication.himalaya.utils.Constants;
import mobapplication.himalaya.utils.LogUtil;

public class RecommendPresenter implements IRecommendPresenter {

    private static final String TAG = "RecommendPresenter";

    private List<IRecommendViewCallback> mCallback = new ArrayList<>();

    private RecommendPresenter(){

    }
    private static RecommendPresenter sInstance = null;

    /**
     * 获取单例对象
     * @return
     */
    public static RecommendPresenter getInstance(){
        if (sInstance == null) {
            synchronized (RecommendPresenter.class){
                if (sInstance == null) {
                    sInstance = new RecommendPresenter();
                }
            }
        }
        return sInstance;
    }


    /**
     * 获取推荐内容,sdk中的获取猜你喜欢专辑
     * 这个接口:3.10.6 获取猜你喜欢专辑
     */
    @Override
    public void getRecommendList() {
        //封装参数
        updateLoading();//发起请求
        Map<String, String> map = new HashMap<String, String>();
        //这个参数表示一页数据返回多少条
        map.put(DTransferConstants.LIKE_COUNT, Constants.COUNT_RECOMMAND +"");
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(GussLikeAlbumList gussLikeAlbumList) {
                //打印当前线程
                LogUtil.d(TAG,"thread name -->" + Thread.currentThread().getName());
                //获取数据成功
                if (gussLikeAlbumList != null) {
                    List<Album> albumList = gussLikeAlbumList.getAlbumList();
                    //数据回来,更新UI
                    //upRecommendUI(albumList);
                    handlerRecommendResult(albumList);
                }
            }

            @Override
            public void onError(int i, String s) {
                //获取数据出错
                LogUtil.d(TAG,"error -->" + i);
                LogUtil.d(TAG,"error msg -->" + s);
                handlerError();
            }
        });
    }

    private void handlerError() {
        if (mCallback != null) {
            for (IRecommendViewCallback callback : mCallback) {
                callback.onNetworkError();
            }
        }
    }

    private void handlerRecommendResult(List<Album> albumList) {
        //通知UI更新
        if (albumList != null) {
//            测试,清空一下,让界面显示空
//            albumList.clear();
            if (albumList.size() == 0) {
                for (IRecommendViewCallback callback : mCallback) {
                    callback.onEmpty();
                }
            }else {
                for (IRecommendViewCallback callback : mCallback) {
                    callback.onRecommendListLoaded(albumList);
                }
            }
        }
    }
    private void updateLoading(){
        for (IRecommendViewCallback callback : mCallback) {
            callback.onLoading();
        }
    }

    @Override
    public void registerViewCallback(IRecommendViewCallback callback) {
        if (mCallback != null && !mCallback.contains(callback)) {
            mCallback.add(callback);
        }
    }


    @Override
    public void unRegisterViewCallback(IRecommendViewCallback callback) {
        if (mCallback != null) {
            mCallback.remove(mCallback);
        }
    }
}
