package com.epicodus.memorycare.adapters;

import java.util.List;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.memorycare.Constants;
import com.epicodus.memorycare.R;
import com.epicodus.memorycare.models.Patient;
import com.epicodus.memorycare.ui.AdviceActivity;
import com.epicodus.memorycare.ui.FirebasePatientViewHolder;
import com.epicodus.memorycare.ui.MainActivity;
import com.epicodus.memorycare.ui.PatientDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class RecyclerAdapter extends RecyclerView.Adapter<FirebasePatientViewHolder> {
    private List<Patient> values;

    public void add(int position, Patient item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }
    public RecyclerAdapter(List<Patient> myDataset) {
        values = myDataset;
    }

    @Override
    public FirebasePatientViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.patient_list_item, parent, false);

        // set the view's size, margins, paddings and layout parameters
        FirebasePatientViewHolder viewHolder = new FirebasePatientViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FirebasePatientViewHolder holder, final int position) {
        final Patient patient = values.get(position);
        holder.bindPatient(patient);

        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = holder.getLayoutPosition();

                Intent intent = new Intent(holder.itemView.getContext(), PatientDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("patients", Parcels.wrap(values));
                intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_FIND);

                holder.itemView.getContext().startActivity(intent);
            }
        });
//        holder.patientNameTextView.setText(patient.getName());
//
//        holder.layout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), PatientDetailActivity.class);
//                v.getContext().startActivity(intent);
//            }
//        });
//
//        holder.categoryTextView.setText(patient.getPhone());
//
//        if (!patient.getImageUrl().isEmpty()) {
//            Picasso.with(holder.layout.getContext())
//                    .load(patient.getImageUrl())
////                .resize(MAX_WIDTH, MAX_HEIGHT)
////                .centerCrop()
//                    .into(holder.imageView);
//        }
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}