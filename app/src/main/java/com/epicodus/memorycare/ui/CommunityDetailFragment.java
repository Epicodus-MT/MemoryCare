package com.epicodus.memorycare.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.memorycare.Constants;
import com.epicodus.memorycare.R;
import com.epicodus.memorycare.models.Community;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommunityDetailFragment extends Fragment implements View.OnClickListener {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;
    private static final int REQUEST_IMAGE_CAPTURE = 111;

    @Bind(R.id.communityImageView) ImageView mImageLabel;
    @Bind(R.id.communityNameTextView) TextView mNameLabel;
    @Bind(R.id.communityTextView) TextView mCategoriesLabel;
    @Bind(R.id.ratingTextView) TextView mRatingLabel;
    @Bind(R.id.websiteTextView) TextView mWebsiteLabel;
    @Bind(R.id.phoneTextView) TextView mPhoneLabel;
    @Bind(R.id.addressTextView) TextView mAddressLabel;
    @Bind(R.id.saveCommunityButton) TextView mSaveCommunityButton;

    private Community mCommunity;
    private ArrayList<Community> mCommunities;
    private int mPosition;
    private String mSource;

    public static CommunityDetailFragment newInstance(ArrayList<Community> communities, Integer position, String source) {
        CommunityDetailFragment communityDetailFragment = new CommunityDetailFragment();
        Bundle args = new Bundle();

        args.putParcelable(Constants.EXTRA_KEY_COMMUNITIES, Parcels.wrap(communities));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);
        args.putString(Constants.KEY_SOURCE, source);

        communityDetailFragment.setArguments(args);
        return communityDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommunities = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_COMMUNITIES));
        mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
        mCommunity = mCommunities.get(mPosition);
        mSource = getArguments().getString(Constants.KEY_SOURCE);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_detail, container, false);
        ButterKnife.bind(this, view);

        if (!mCommunity.getImageUrl().contains("http")) {
            try {
                Bitmap image = decodeFromFirebaseBase64(mCommunity.getImageUrl());
                mImageLabel.setImageBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Picasso.with(view.getContext())
                    .load(mCommunity.getImageUrl())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mImageLabel);
        }

        if (mSource.equals(Constants.SOURCE_SAVED)) {
            mSaveCommunityButton.setVisibility(View.GONE);
        } else {
            mSaveCommunityButton.setOnClickListener(this);
        }


        mNameLabel.setText(mCommunity.getName());
        mCategoriesLabel.setText(android.text.TextUtils.join(", ", mCommunity.getCategories()));
        mRatingLabel.setText(Double.toString(mCommunity.getRating()) + "/5");
        mPhoneLabel.setText(mCommunity.getPhone());
        mAddressLabel.setText(android.text.TextUtils.join(", ", mCommunity.getAddress()));

        mWebsiteLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);

        mSaveCommunityButton.setOnClickListener(this);

        return view;
    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (mSource.equals(Constants.SOURCE_SAVED)) {
            inflater.inflate(R.menu.menu_photo, menu);
        }
        else {
            inflater.inflate(R.menu.menu_main, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_photo:
                onLaunchCamera();
            default:
                break;
        }
        return false;
    }

    public void onLaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageLabel.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_COMMUNITIES)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mCommunity.getPushId())
                .child("imageUrl");
        ref.setValue(imageEncoded);
    }

    @Override
    public void onClick(View v) {

        if (v == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mCommunity.getWebsite()));
            startActivity(webIntent);
        }

        if (v == mPhoneLabel) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + mCommunity.getPhone()));
            startActivity(phoneIntent);
        }

        if (v == mAddressLabel) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + mCommunity.getLatitude()
                            + "," + mCommunity.getLongitude()
                            + "?q=(" + mCommunity.getName() + ")"));
            startActivity(mapIntent);
        }

        if (v == mSaveCommunityButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference communityRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_COMMUNITIES)
                    .child(uid);

            DatabaseReference pushRef = communityRef.push();
            String pushId = pushRef.getKey();
            mCommunity.setPushId(pushId);
            pushRef.setValue(mCommunity);

            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }

    }

}
