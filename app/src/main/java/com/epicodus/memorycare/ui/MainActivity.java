package com.epicodus.memorycare.ui;

import butterknife.Bind;
import butterknife.ButterKnife;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.epicodus.memorycare.R;
//import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.findPatientButton) Button mFindPatientButton;
    @Bind(R.id.locationEditText) EditText mLocationEditText;
    @Bind(R.id.appNameTextView) TextView mAppNameTextView;

    @Bind(R.id.findAdviceButton) Button mFindAdviceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface paprikaFont = Typeface.createFromAsset(getAssets(), "fonts/Paprika-Regular.ttf");
        mAppNameTextView.setTypeface(paprikaFont);

//        mAppNameTextView = (TextView) findViewById(R.id.appNameTextView);
//        mFindPatientButton = (Button) findViewById(R.id.findPatientButton);

        mFindPatientButton.setOnClickListener(this);
        mFindAdviceButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mFindPatientButton) {
            String location = mLocationEditText.getText().toString();
            if(mLocationEditText.getText().length()!= 5) {
                mLocationEditText.setError("Please enter a 5-digit US zip code");
            }
            else {
                Intent intent = new Intent(MainActivity.this, PatientActivity.class);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        }
        if(v == mFindAdviceButton) {
            Intent intent = new Intent(MainActivity.this, AdviceActivity.class);
            startActivity(intent);
        }
    }
}
