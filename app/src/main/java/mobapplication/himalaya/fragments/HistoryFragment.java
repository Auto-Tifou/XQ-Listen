package mobapplication.himalaya.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobapplication.himalaya.R;

/**
 * 历史- fragment
 */

public class HistoryFragment extends BaseFragment {
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootView = layoutInflater.inflate(R.layout.fragment_history, container,false);
        return rootView;
    }
}
