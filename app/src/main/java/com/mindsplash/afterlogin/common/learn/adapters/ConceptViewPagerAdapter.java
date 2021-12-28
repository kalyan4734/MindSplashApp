package com.mindsplash.afterlogin.common.learn.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mindsplash.afterlogin.common.learn.fragments.FragmentDefinition;
import com.mindsplash.afterlogin.common.learn.fragments.FragmentFormula;
import com.mindsplash.afterlogin.common.learn.fragments.FragmentQuickTips;
import com.mindsplash.afterlogin.common.learn.fragments.FragmentTheroems;
import com.mindsplash.network.model.Concept;
import com.mindsplash.network.model.ConceptListData;

public class ConceptViewPagerAdapter extends FragmentPagerAdapter {
    private String sid,cid;
    private ConceptListData concept;
    public  ConceptViewPagerAdapter(@NonNull FragmentManager fm, ConceptListData concept) {
        super(fm);
        this.concept = concept;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = FragmentDefinition.getInstance(concept.getDefinitions());
        } else if (position == 1) {
            fragment = FragmentTheroems.getInstance(concept.getTheorems());
        } else if (position == 2) {
            fragment = FragmentFormula.getInstance(concept.getFormulae());
        } else if (position == 3) {
            fragment = FragmentQuickTips.getInstance(concept.getQuick_tips());
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "DEFINITION";
        }
        else if (position == 1)
        {
            title = "THEOREMS";
        }
        else if (position == 2)
        {
            title = "FORMULAE";
        }else if (position == 3)
        {
            title = "QUICK TIPS";
        }
        return title;
    }
}
