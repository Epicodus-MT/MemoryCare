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

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
//        public TextView patientNameTextView;
//        public TextView categoryTextView;
//        public TextView ratingTextView;
//        public ImageView imageView;
//        public View layout;
//
//        public ViewHolder(View v) {
//            super(v);
//            layout = v;
//            patientNameTextView = (TextView) v.findViewById(R.id.patientNameTextView);
//            categoryTextView = (TextView) v.findViewById(R.id.categoryTextView);
//            ratingTextView = (TextView) v.findViewById(R.id.ratingTextView);
//            imageView = (ImageView) v.findViewById(R.id.patientImageView);
//        }
//    }

    public void add(int position, Patient item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerAdapter(List<Patient> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
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

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final FirebasePatientViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Patient patient = values.get(position);
        holder.bindPatient(patient);

        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = holder.getLayoutPosition();

                Intent intent = new Intent(holder.itemView.getContext(), PatientDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("patients", Parcels.wrap(values));

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

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}