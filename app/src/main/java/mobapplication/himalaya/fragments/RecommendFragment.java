package mobapplication.himalaya.fragments;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

import mobapplication.himalaya.R;
import mobapplication.himalaya.adapters.RecommendListAdapter;
import mobapplication.himalaya.interfaces.IRecommendViewCallback;
import mobapplication.himalaya.presenters.RecommendPresenter;
import mobapplication.himalaya.utils.LogUtil;
import mobapplication.himalaya.views.UILoader;

/**
 * 推荐- fragment
 */

public class RecommendFragment extends BaseFragment implements IRecommendViewCallback, UILoader.OnRetryClickListener {
    private static final String TAG = "RecommendFragment";
    private RecyclerView mRecommendRv;
    private View mRootView;
    private RecommendListAdapter mRecommendListAdapter;
    private RecommendPresenter mRecommendPresenter;
    private UILoader mUiLoader;

    @Override
    protected View onSubViewLoaded(final LayoutInflater layoutInflater, ViewGroup container) {
        mUiLoader = new UILoader(getContext()) {
            @Override
            protected View getSuccessView(ViewGroup container) {
                return createSuccessView(layoutInflater,container);
            }
        };
        //获取到逻辑层的对象
        mRecommendPresenter = RecommendPresenter.getInstance();
        //设置通知接口的注册
        mRecommendPresenter.registerViewCallback(this);
        //获取推荐列表
        mRecommendPresenter.getRecommendList();

        if (mUiLoader.getParent() instanceof ViewGroup){
            ((ViewGroup) mUiLoader.getParent()).removeView(mUiLoader);
        }

        mUiLoader.setOnRetryClickListener(this);
        //返回View,给界面显示
        return mUiLoader;
    }

    private View createSuccessView(LayoutInflater layoutInflater, ViewGroup container) {
        //View加载完成
        mRootView = layoutInflater.inflate(R.layout.fragment_recommend, container,false);
        //RecyclerView的使用
        //1.找到控件
        mRecommendRv = mRootView.findViewById(R.id.recommend_list);
        //2.设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //方向
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //间距
        mRecommendRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                //为适配转换为dp,UIUtil转换dp工具类
                outRect.top = UIUtil.dip2px(view.getContext(),5);
                outRect.bottom = UIUtil.dip2px(view.getContext(),5);
                outRect.left = UIUtil.dip2px(view.getContext(),5);
                outRect.right = UIUtil.dip2px(view.getContext(),5);
            }
        });
        mRecommendRv.setLayoutManager(linearLayoutManager);
        //3.设置适配器
        mRecommendListAdapter = new RecommendListAdapter();
        mRecommendRv.setAdapter(mRecommendListAdapter);
        return mRootView;
    }

    //成功页面
    @Override
    public void onRecommendListLoaded(List<Album> result) {
        LogUtil.d(TAG,"onRecommendListLoaded");
        //当我们获取到推荐内容的时候,这个方法就会被调用(成功了)
        //数据回来更新UI
        mRecommendListAdapter.setData(result);
        mUiLoader.updateStatus(UILoader.UIStatus.SUCCESS);
    }

    //网络错误页面
    @Override
    public void onNetworkError() {
        LogUtil.d(TAG,"onNetworkError");
        mUiLoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR);
    }

    //数据为空页面
    @Override
    public void onEmpty() {
        LogUtil.d(TAG,"onEmpty");
        mUiLoader.updateStatus(UILoader.UIStatus.EMPTY);
    }

    //加载中页面
    @Override
    public void onLoading() {
        LogUtil.d(TAG,"onLoading");
        mUiLoader.updateStatus(UILoader.UIStatus.LOADING);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //取消接口的注册,避免内存泄漏
        if (mRecommendPresenter != null) {
            mRecommendPresenter.unRegisterViewCallback(this);
        }
    }

    @Override
    public void onRetryClick() {
        //表示网络不佳时候,点击重试
        //重新获取数据
        if (mRecommendPresenter != null) {
            mRecommendPresenter.getRecommendList();
        }
    }
}
