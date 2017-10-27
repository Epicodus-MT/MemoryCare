package com.epicodus.memorycare.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.epicodus.memorycare.Constants;
import com.epicodus.memorycare.R;
import com.epicodus.memorycare.models.Community;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedCommunityListActivity extends AppCompatActivity {
    private DatabaseReference mCommunityReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_communities);
        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mCommunityReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_COMMUNITIES)
                .child(uid);

//        if landscape {
//            communityDetailFragment = findViewById(R.id.communityDetailContainer);
//
//            Bundle args = new Bundle();
//
//            args.putParcelable(Constants.EXTRA_KEY_COMMUNITIES, Parcels.wrap(communities));
//            args.putInt(Constants.EXTRA_KEY_POSITION, position);
//            args.putString(Constants.KEY_SOURCE, source);
//
//            communityDetailFragment.setArguments(args);
//        }

        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Community, FirebaseCommunityViewHolder>
                (Community.class, R.layout.community_list_item, FirebaseCommunityViewHolder.class,
                        mCommunityReference) {

            @Override
            protected void populateViewHolder(final FirebaseCommunityViewHolder viewHolder,
                                              final Community model, final int position) {
                viewHolder.bindCommunity(model);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(viewHolder.itemView.getContext(), CommunityDetailActivity.class);
                        intent.putExtra("position", position + "");
                        ArrayList<Community> communities = new ArrayList<Community>();
                        communities.add(model);
                        intent.putExtra("communities", Parcels.wrap(communities));
                        intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_SAVED);

                        viewHolder.itemView.getContext().startActivity(intent);
                    }
                });
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
