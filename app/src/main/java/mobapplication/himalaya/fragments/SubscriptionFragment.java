package mobapplication.himalaya.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobapplication.himalaya.R;

/**
 * 订阅- fragment
 */

public class SubscriptionFragment extends BaseFragment{
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootView = layoutInflater.inflate(R.layout.fragment_subscription, container,false);
        return rootView;
    }
}
