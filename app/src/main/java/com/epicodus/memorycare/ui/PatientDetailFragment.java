package com.epicodus.memorycare.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.memorycare.Constants;
import com.epicodus.memorycare.R;
import com.epicodus.memorycare.models.Patient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PatientDetailFragment extends Fragment implements View.OnClickListener {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;

    @Bind(R.id.patientImageView) ImageView mImageLabel;
    @Bind(R.id.patientNameTextView) TextView mNameLabel;
    @Bind(R.id.communityTextView) TextView mCategoriesLabel;
    @Bind(R.id.ratingTextView) TextView mRatingLabel;
    @Bind(R.id.websiteTextView) TextView mWebsiteLabel;
    @Bind(R.id.phoneTextView) TextView mPhoneLabel;
    @Bind(R.id.addressTextView) TextView mAddressLabel;
    @Bind(R.id.savePatientButton) TextView mSavePatientButton;

    private Patient mPatient;

    public static PatientDetailFragment newInstance(Patient patient) {
        PatientDetailFragment patientDetailFragment = new PatientDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("patient", Parcels.wrap(patient));
        patientDetailFragment.setArguments(args);
        return patientDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPatient = Parcels.unwrap(getArguments().getParcelable("patient"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_detail, container, false);
        ButterKnife.bind(this, view);

       if (!mPatient.getImageUrl().isEmpty()) {
           Picasso.with(view.getContext())
                   .load(mPatient.getImageUrl())
                   .resize(MAX_WIDTH, MAX_HEIGHT)
                   .centerCrop()
                   .into(mImageLabel);
       }

        mNameLabel.setText(mPatient.getName());
        mCategoriesLabel.setText(android.text.TextUtils.join(", ", mPatient.getCategories()));
        mRatingLabel.setText(Double.toString(mPatient.getRating()) + "/5");
        mPhoneLabel.setText(mPatient.getPhone());
        mAddressLabel.setText(android.text.TextUtils.join(", ", mPatient.getAddress()));

        mWebsiteLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);

        mSavePatientButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        if (v == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mPatient.getWebsite()));
            startActivity(webIntent);
        }

        if (v == mPhoneLabel) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + mPatient.getPhone()));
            startActivity(phoneIntent);
        }

        if (v == mAddressLabel) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + mPatient.getLatitude()
                            + "," + mPatient.getLongitude()
                            + "?q=(" + mPatient.getName() + ")"));
            startActivity(mapIntent);
        }

        if (v == mSavePatientButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference patientRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_PATIENTS)
                    .child(uid);

            DatabaseReference pushRef = patientRef.push();
            String pushId = pushRef.getKey();
            mPatient.setPushId(pushId);
            pushRef.setValue(mPatient);

            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }

    }

}
