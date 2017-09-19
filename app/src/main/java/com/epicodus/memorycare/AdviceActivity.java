package com.epicodus.memorycare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AdviceActivity extends AppCompatActivity {
    @Bind(R.id.websiteButton) Button mWebsiteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        ButterKnife.bind(this);
        mWebsiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWebsite();
            }
        });
    }

    private void goToWebsite() {
        String url = "http://www.washington.edu/boundless/nursing-smart-home/?utm_source=" +
                "UW+News+Subscribers&utm_campaign=514a41b8d4-UW_Today_Wednesday_September_13_2017" +
                "&utm_medium=email&utm_term=0_0707cbc3f9-514a41b8d4-308761505";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}


