package mobapplication.himalaya.utils;

import java.util.HashMap;
import java.util.Map;

import mobapplication.himalaya.fragments.BaseFragment;
import mobapplication.himalaya.fragments.HistoryFragment;
import mobapplication.himalaya.fragments.RecommendFragment;
import mobapplication.himalaya.fragments.SubscriptionFragment;

/**
 * 创建fragment和缓存已经创建的fragment
 */
public class FragmentCreator {

    public final static int INDEX_RECOMMEDF = 0;
    public final static int INDEX_SUBSCRIPTION = 1;
    public final static int INDEX_HISTORY = 2;

    public final static int PAGE_GOUNT = 3;
//    集合
    private static Map<Integer, BaseFragment>sCache = new HashMap<>();

    public static BaseFragment getFragment(int index){
        //看缓存里有没有
        BaseFragment baseFragment = sCache.get(index);
        if (baseFragment != null) {
            return baseFragment;
        }
        switch (index){
            case INDEX_RECOMMEDF:
                baseFragment = new RecommendFragment();
                break;
            case INDEX_SUBSCRIPTION:
                baseFragment = new SubscriptionFragment();
                break;
            case INDEX_HISTORY:
                baseFragment = new HistoryFragment();
                break;
        }
//        保存到缓存中
        sCache.put(index,baseFragment);
//        返回
        return baseFragment;
    }
}
