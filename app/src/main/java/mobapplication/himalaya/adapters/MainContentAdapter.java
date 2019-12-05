package mobapplication.himalaya.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import mobapplication.himalaya.utils.FragmentCreator;

/**
 * 适配器
 */
public class MainContentAdapter extends FragmentPagerAdapter {

    public MainContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentCreator.getFragment(position);
    }

    @Override
    public int getCount() {
        return FragmentCreator.PAGE_GOUNT;
    }
}
