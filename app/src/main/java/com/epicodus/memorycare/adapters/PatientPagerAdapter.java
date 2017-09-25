package com.epicodus.memorycare.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.epicodus.memorycare.models.Patient;
import com.epicodus.memorycare.ui.PatientDetailActivity;
import com.epicodus.memorycare.ui.PatientDetailFragment;

import java.util.ArrayList;

public class PatientPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Patient> mPatients;

    public PatientPagerAdapter(FragmentManager fm, ArrayList<Patient> patients) {
        super(fm);
        mPatients = patients;
    }

    @Override
    public Fragment getItem(int position) {
        return PatientDetailFragment.newInstance(mPatients.get(position));
    }

    @Override
    public int getCount() {
        return mPatients.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPatients.get(position).getName();
    }
}
