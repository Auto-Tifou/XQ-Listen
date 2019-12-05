package mobapplication.himalaya.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobapplication.himalaya.R;

/**
 * 推荐- fragment
 */

public class RecommendFragment extends BaseFragment {
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootView = layoutInflater.inflate(R.layout.fragment_recommend, container,false);
        return rootView;
    }
}
