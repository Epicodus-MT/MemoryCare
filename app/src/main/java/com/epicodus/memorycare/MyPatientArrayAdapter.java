package com.epicodus.memorycare;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MyPatientArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mPatient;
    private String[] mCommunities;

    public MyPatientArrayAdapter(Context mContext, int resource, String[] mPatient, String[] mCommunities) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mPatient = mPatient;
        this.mCommunities = mCommunities;
    }

    @Override
    public Object getItem(int position) {
        String patient = mPatient[position];
        String community = mCommunities[position];
        return String.format("%s \nType of community: %s", patient, community);
    }

    @Override
    public int getCount() {
        return mPatient.length;
    }
}
