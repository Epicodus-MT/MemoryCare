package com.epicodus.memorycare.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.epicodus.memorycare.Constants;
import com.epicodus.memorycare.models.Community;
import com.epicodus.memorycare.ui.CommunityDetailActivity;
import com.epicodus.memorycare.ui.CommunityDetailFragment;

import java.util.ArrayList;

public class CommunityPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Community> mCommunities;
    private String mSource;

    public CommunityPagerAdapter(FragmentManager fm, ArrayList<Community> communities, String source) {
        super(fm);
        mCommunities = communities;
        mSource = source;
    }

    @Override
    public Fragment getItem(int position) {
        return CommunityDetailFragment.newInstance(mCommunities, position, mSource);
    }

    @Override
    public int getCount() {
        return mCommunities.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCommunities.get(position).getName();
    }
}
