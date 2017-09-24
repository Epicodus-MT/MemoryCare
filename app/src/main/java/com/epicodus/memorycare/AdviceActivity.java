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
        String url = "https://www.google.com/about/stories/bike-around/?utm_source=google&utm_medium=hpp&utm_campaign=bikearound";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}


