package com.epicodus.memorycare.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.epicodus.memorycare.Constants;
import com.epicodus.memorycare.R;
import com.epicodus.memorycare.adapters.CommunityPagerAdapter;
import com.epicodus.memorycare.models.Community;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class CommunityDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private CommunityPagerAdapter adapterViewPager;
    ArrayList<Community> mCommunities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);
        ButterKnife.bind(this);

        mCommunities = Parcels.unwrap(getIntent().getParcelableExtra("communities"));

        int startingPosition = Integer.parseInt(getIntent().getStringExtra("position"));
        String source = getIntent().getStringExtra(Constants.KEY_SOURCE);

        adapterViewPager = new CommunityPagerAdapter(getSupportFragmentManager(), mCommunities, source);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
