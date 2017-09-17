package com.epicodus.memorycare;

import butterknife.Bind;
import butterknife.ButterKnife;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PatientActivity extends AppCompatActivity {
    @Bind(R.id.locationTextView) TextView mLocationTextView;
    @Bind(R.id.listView) ListView mListView;
    private String[] patient = new String[] {"The Blakely Echo Lake", "Best Care Manor",
            "River of Life Home Care", "Summer Haven", "Gold Autumn", "The Perpetual Help",
            "Golden Hill Adult Family Home", "Anca's Adult Family Home", "Hillwood Senior Care",
            "Echo Lake Adult Family Home"};

    private String[] communities = new String[] {"Assisted Living", "Nursing Homes", "Hospice",
            "Leisure", "Independent Living", "Continuing Care Retirement", "Retirement Villages",
            "55+", "Senior Apartments", "Retiremesnt"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        ButterKnife.bind(this);
        mListView = (ListView) findViewById(R.id.listView);
        mLocationTextView = (TextView) findViewById(R.id.locationTextView);

        ArrayAdapter adapter = new MyPatientArrayAdapter(this, android.R.layout.simple_list_item_1, patient, communities);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String patient = ((TextView)view).getText().toString();
                Toast.makeText(PatientActivity.this, patient, Toast.LENGTH_LONG).show();
            }
        });


        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        mLocationTextView.setText("Here are all the communities near: " + location);
    }
}



