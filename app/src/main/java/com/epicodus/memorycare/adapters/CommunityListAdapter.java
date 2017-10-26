package com.epicodus.memorycare.adapters;

        import android.content.Context;
        import android.content.Intent;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.epicodus.memorycare.R;
        import com.epicodus.memorycare.models.Patient;
        import com.epicodus.memorycare.ui.PatientDetailActivity;
        import com.squareup.picasso.Picasso;

        import org.parceler.Parcels;

        import java.util.ArrayList;

        import butterknife.Bind;
        import butterknife.ButterKnife;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.PatientViewHolder> {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    private ArrayList<Patient> mPatients = new ArrayList<>();
    private Context mContext;

    public PatientListAdapter(Context context, ArrayList<Patient> patients) {
        mContext = context;
        mPatients = patients;
    }

    @Override
    public PatientListAdapter.PatientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_list_item, parent, false);
        PatientViewHolder viewHolder = new PatientViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PatientListAdapter.PatientViewHolder holder, int position) {
        holder.bindPatient(mPatients.get(position));
    }

    @Override
    public int getItemCount() {
        return mPatients.size();
    }

    public class PatientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.patientImageView) ImageView mPatientImageView;
        @Bind(R.id.patientNameTextView) TextView mNameTextView;
        @Bind(R.id.categoryTextView) TextView mCategoryTextView;
        @Bind(R.id.ratingTextView) TextView mRatingTextView;

        private Context mContext;

        public PatientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindPatient(Patient patient) {

            Picasso.with(mContext)
                    .load(patient.getImageUrl())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mPatientImageView);

            mNameTextView.setText(patient.getName());
            mCategoryTextView.setText(patient.getCategories().get(0));
            mRatingTextView.setText("Rating: " + patient.getRating() + "/5");
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();

            Intent intent = new Intent(mContext, PatientDetailActivity.class);
            intent.putExtra("position", itemPosition + "");
            intent.putExtra("communities", Parcels.wrap(mPatients));

            mContext.startActivity(intent);
        }
    }
}
