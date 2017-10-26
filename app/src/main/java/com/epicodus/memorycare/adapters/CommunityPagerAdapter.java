package com.epicodus.memorycare.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.epicodus.memorycare.Constants;
import com.epicodus.memorycare.models.Patient;
import com.epicodus.memorycare.ui.PatientDetailActivity;
import com.epicodus.memorycare.ui.PatientDetailFragment;

import java.util.ArrayList;

public class PatientPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Patient> mCommunities;
    private String mSource;

    public PatientPagerAdapter(FragmentManager fm, ArrayList<Patient> communities, String source) {
        super(fm);
        mCommunities = communities;
        mSource = source;
    }

    @Override
    public Fragment getItem(int position) {
        return PatientDetailFragment.newInstance(mCommunities, position, mSource);
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
