package com.mindsplash.afterlogin.fragments.search.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.mindsplash.afterlogin.fragments.search.GoogleSearchFragment;
import com.mindsplash.afterlogin.fragments.search.MindSplashSearchFragment;
import com.mindsplash.afterlogin.fragments.search.YoutubeSearchFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    String searchTerm ;

    public PagerAdapter(FragmentManager fm, int NumOfTabs,String searchTerm) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.searchTerm = searchTerm;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return MindSplashSearchFragment.newInstance(searchTerm);
            case 1:
                return GoogleSearchFragment.newInstance(searchTerm);

            case 2:
                return   YoutubeSearchFragment.newInstance(searchTerm);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}