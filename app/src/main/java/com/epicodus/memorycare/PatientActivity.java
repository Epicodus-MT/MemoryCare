package com.epicodus.memorycare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    @Bind(R.id.listView) ListView mListView;

    public ArrayList<Patient> mPatient = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        mLocationTextView.setText("Here are all the communities near: " + location);

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
                        String[] patientNames = new String[mPatient.size()];
                        for (int i = 0; i < patientNames.length; i++) {
                            patientNames[i] = mPatient.get(i).getName();
                        }
                        ArrayAdapter adapter = new ArrayAdapter(PatientActivity.this, android.R.layout.simple_list_item_1, patientNames);
                        mListView.setAdapter(adapter);

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



