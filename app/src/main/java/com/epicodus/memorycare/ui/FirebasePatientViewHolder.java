package com.epicodus.memorycare.ui;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.memorycare.models.Patient;
import com.epicodus.memorycare.ui.PatientDetailActivity;
import com.epicodus.memorycare.Constants;
import com.epicodus.memorycare.R;
import com.epicodus.memorycare.ui.PatientDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;
import java.util.ArrayList;



public class FirebasePatientViewHolder extends RecyclerView.ViewHolder {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;

    public FirebasePatientViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindPatient(Patient patient) {
        ImageView patientImageView = (ImageView) mView.findViewById(R.id.patientImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.patientNameTextView);
        TextView categoryTextView = (TextView) mView.findViewById(R.id.categoryTextView);
        TextView ratingTextView = (TextView) mView.findViewById(R.id.ratingTextView);

        if (!patient.getImageUrl().isEmpty()) {
            Picasso.with(mContext)
                    .load(patient.getImageUrl())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(patientImageView);
        }

        nameTextView.setText(patient.getName());
        categoryTextView.setText(patient.getCategories().get(0));
        ratingTextView.setText("Rating: " + patient.getRating() + "/5");
    }
}