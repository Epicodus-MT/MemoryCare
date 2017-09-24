package com.epicodus.memorycare.adapters;


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
import com.epicodus.memorycare.models.Patients;
import com.epicodus.memorycare.ui.PatientDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;
import java.util.ArrayList;



public class FirebasePatientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;

    public FirebasePatientViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindPatient(Patient patient) {
        ImageView patientImageView = (ImageView) mView.findViewById(R.id.patientImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.patientNameTextView);
        TextView categoryTextView = (TextView) mView.findViewById(R.id.categoryTextView);
        TextView ratingTextView = (TextView) mView.findViewById(R.id.ratingTextView);

        Picasso.with(mContext)
                .load(patient.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(patientImageView);

        nameTextView.setText(patient.getName());
        categoryTextView.setText(patient.getCategories().get(0));
        ratingTextView.setText("Rating: " + patient.getRating() + "/5");
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Patient> patients = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_PATIENTS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    patients.add(snapshot.getValue(Patient.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, PatientDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("patients", Parcels.wrap(patients));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}