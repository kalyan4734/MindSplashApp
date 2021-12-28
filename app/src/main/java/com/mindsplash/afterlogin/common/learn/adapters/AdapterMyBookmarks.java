package com.mindsplash.afterlogin.common.learn.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mindsplash.afterlogin.common.learn.fragments.FragmentConcept;

import com.mindsplash.afterlogin.common.learn.fragments.FragmentQuation;
import com.mindsplash.afterlogin.common.learn.fragments.FragmentSubject;
import com.mindsplash.network.model.MyBookMarkResponse;

public class AdapterMyBookmarks extends FragmentPagerAdapter {
    private MyBookMarkResponse.BookmarkData data;

    public  AdapterMyBookmarks(@NonNull FragmentManager fm, MyBookMarkResponse.BookmarkData bookmarkData) {
        super(fm);
        this.data = bookmarkData;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = FragmentQuation.getInstance(data.getQuestions());
        } else if (position == 1) {
            fragment = FragmentConcept.getInstance(data.getConcepts());
        } else if (position == 2) {
            fragment = FragmentSubject.getInstance(data.getSubkects());
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Questions";
        }
        else if (position == 1)
        {
            title = "Concepts";
        }
        else if (position == 2)
        {
            title = "Subjects";
        }
        return title;
    }
}
