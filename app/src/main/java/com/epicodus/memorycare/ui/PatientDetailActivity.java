package com.epicodus.memorycare.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.epicodus.memorycare.R;
import com.epicodus.memorycare.adapters.PatientPagerAdapter;
import com.epicodus.memorycare.models.Patient;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class PatientDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private PatientPagerAdapter adapterViewPager;
    ArrayList<Patient> mPatients = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);
        ButterKnife.bind(this);

        mPatients = Parcels.unwrap(getIntent().getParcelableExtra("patients"));

        int startingPosition = Integer.parseInt(getIntent().getStringExtra("position"));

        adapterViewPager = new PatientPagerAdapter(getSupportFragmentManager(), mPatients);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}