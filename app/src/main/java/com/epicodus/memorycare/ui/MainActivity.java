package com.epicodus.memorycare.ui;

import butterknife.Bind;
import butterknife.ButterKnife;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.epicodus.memorycare.Constants;
import com.epicodus.memorycare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
//import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.findCommunitiesButton) Button mFindCommunitiesButton;
    @Bind(R.id.locationEditText) EditText mLocationEditText;
    @Bind(R.id.appNameTextView) TextView mAppNameTextView;
    @Bind(R.id.findAdviceButton) Button mFindAdviceButton;
    @Bind(R.id.savedCommunitiesButton) Button mSavedCommunitiesButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface paprikaFont = Typeface.createFromAsset(getAssets(), "fonts/Paprika-Regular.ttf");
        mAppNameTextView.setTypeface(paprikaFont);

//        mAppNameTextView = (TextView) findViewById(R.id.appNameTextView);
//        mFindCommunityButton = (Button) findViewById(R.id.findCommunityButton);

        mFindCommunitiesButton.setOnClickListener(this);
        mFindAdviceButton.setOnClickListener(this);
        mSavedCommunitiesButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                } else {

                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View v) {
        if(v == mFindCommunitiesButton) {
            String location = mLocationEditText.getText().toString();
            if(mLocationEditText.getText().length()!= 5) {
                mLocationEditText.setError("Please enter a 5-digit US zip code");
            }


//            if(v == mFindCommunityButton) {
//                Intent intent = new Intent(MainActivity.this, CommunityListActivity.class);
//                startActivity(intent);
//            }


            else {
                Intent intent = new Intent(MainActivity.this, CommunityActivity.class);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        }
        if(v == mFindAdviceButton) {
            Intent intent = new Intent(MainActivity.this, AdviceActivity.class);
            startActivity(intent);
        }

        if (v == mSavedCommunitiesButton) {
            Intent intent = new Intent(MainActivity.this, SavedCommunityListActivity.class);
            startActivity(intent);
        }
    }
}
