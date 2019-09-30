package com.application.seb.projet5_mynews.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.application.seb.projet5_mynews.R;


public class PageAdapter extends FragmentStatePagerAdapter {

    public Context context;

    // Default Constructor
    public PageAdapter(FragmentManager mgr, Context context) {
        super(mgr);
        this.context = context;
    }

    //Number of page to show
    @Override
    public int getCount() {
        return(3);
    }

    // Page to return
    @Override
    public Fragment getItem(int position) {
        PageFragment fragment = null;
        switch (position){
            case 0:
                fragment = PageFragment.newInstance(position,new Bundle());
                break;
            case 1:
                fragment = PageFragment.newInstance(position,new Bundle());
                break;
            case 2:
                fragment = PageFragment.newInstance(position,new Bundle());
                break;
        }
        return fragment;

    }

    // Set page title
    @Override
    public CharSequence getPageTitle(int position) {

        String[] tab = {context.getResources().getString(R.string.top_stories_tabLayout_title), context.getResources().getString(R.string.most_popular_tabLayout_title), context.getResources().getString(R.string.business_tabLayout_title)};
        return tab[position];
    }

}
