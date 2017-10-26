package com.epicodus.memorycare.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.epicodus.memorycare.R;
import com.epicodus.memorycare.adapters.RecyclerAdapter;
import com.epicodus.memorycare.models.Patient;
import com.epicodus.memorycare.services.YelpService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PatientActivity extends AppCompatActivity {
    public static final String TAG = PatientActivity.class.getSimpleName();
    @Bind(R.id.locationTextView) TextView mLocationTextView;
//    @Bind(R.id.listView) ListView mListView;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public ArrayList<Patient> mCommunities = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communities);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        mLocationTextView.setText("Here are all the communities near: " + location);

        mRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        getCommunities(location);
    }

    private void getCommunities(String location) {
        final YelpService yelpService = new YelpService();
        yelpService.findPatient(location, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mCommunities = yelpService.processResults(response);


                PatientActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mAdapter = new RecyclerAdapter(mCommunities);
                        mRecyclerView.setAdapter(mAdapter);

                        for (Patient patient : mCommunities) {
                            Log.d(TAG, "Name: " + patient.getName());
                            Log.d(TAG, "Phone: " + patient.getPhone());
                            Log.d(TAG, "Website: " + patient.getWebsite());
                            Log.d(TAG, "Image url: " + patient.getImageUrl());
                            Log.d(TAG, "Rating: " + Double.toString(patient.getRating()));
                            Log.d(TAG, "Address: " + android.text.TextUtils.join(", ", patient.getAddress()));
                            Log.d(TAG, "Categories: " + patient.getCategories().toString());
                        }
                    }
                });
            }
        });
    }
}
