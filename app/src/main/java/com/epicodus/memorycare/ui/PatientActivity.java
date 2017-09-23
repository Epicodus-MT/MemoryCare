package com.epicodus.memorycare.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.epicodus.memorycare.R;
import com.epicodus.memorycare.RecyclerAdapter;
import com.epicodus.memorycare.models.Patient;
import com.epicodus.memorycare.services.YelpService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.util.List;

public class PatientActivity extends AppCompatActivity {
    public static final String TAG = PatientActivity.class.getSimpleName();
    @Bind(R.id.locationTextView) TextView mLocationTextView;
//    @Bind(R.id.listView) ListView mListView;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public ArrayList<Patient> mPatient = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        mLocationTextView.setText("Here are all the communities near: " + location);

        mRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }// define an adapter


        getPatients(location);
    }

    private void getPatients(String location) {
        final YelpService yelpService = new YelpService();
        yelpService.findPatient(location, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mPatient = yelpService.processResults(response);


                PatientActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mAdapter = new RecyclerAdapter(mPatient);
                        mRecyclerView.setAdapter(mAdapter);
//                        ArrayAdapter adapter = new ArrayAdapter(PatientActivity.this, android.R.layout.simple_list_item_1, patientNames);
//                        mListView.setAdapter(adapter);

                        for (Patient patient : mPatient) {
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



