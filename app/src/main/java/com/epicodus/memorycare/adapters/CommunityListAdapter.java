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
import com.epicodus.memorycare.models.Community;
import com.epicodus.memorycare.ui.CommunityDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommunityListAdapter extends RecyclerView.Adapter<CommunityListAdapter.CommunityViewHolder> {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    private ArrayList<Community> mCommunities = new ArrayList<>();
    private Context mContext;
    private OnCommunitySelectedListener mOnCommunitySelectedListener;

    public CommunityListAdapter(Context context, ArrayList<Community> communities, OnCommunitySelectedListener communitySelectedListener) {
        mContext = context;
        mCommunities = communities;
        mOnCommunitySelectedListener = communitySelectedListener;
    }

    @Override
    public CommunityListAdapter.CommunityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_list_item, parent, false);
        CommunityViewHolder viewHolder = new CommunityViewHolder(view, mCommunities, mOnCommunitySelectedListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommunityListAdapter.CommunityViewHolder holder, int position) {
        holder.bindCommunity(mCommunities.get(position));
    }

    @Override
    public int getItemCount() {
        return mCommunities.size();
    }

    public class CommunityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.communityImageView) ImageView mCommunityImageView;
        @Bind(R.id.communityNameTextView) TextView mNameTextView;
        @Bind(R.id.categoryTextView) TextView mCategoryTextView;
        @Bind(R.id.ratingTextView) TextView mRatingTextView;

        private Context mContext;
        private int mOrientation;
        private ArrayList<Community> mCommunities = new ArrayList<>();
        private OnCommunitySelectedListener mCommunitySelectedListener;

        public CommunityViewHolder(View itemView, ArrayList<Community> communities, OnCommunitySelectedListener communitySelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContext = itemView.getContext();
            mOrientation = itemView.getResources().getConfiguration().orientation;
            mCommunities = communities;
            mCommunitySelectedListener = communitySelectedListener;

            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(0);
            }

            itemView.setOnClickListener(this);
        }

        public void bindCommunity(Community community) {

            Picasso.with(mContext)
                    .load(community.getImageUrl())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mCommunityImageView);

            mNameTextView.setText(community.getName());
            mCategoryTextView.setText(community.getCategories().get(0));
            mRatingTextView.setText("Rating: " + community.getRating() + "/5");
        }

        private void createDetailFragment(int position) {
            CommunityDetailFragment detailFragment = CommunityDetailFragment.newInstance(mCommunities, position, Constants.SOURCE_FIND);
            FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.communityDetailContainer, detailFragment);
            ft.commit();
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            mCommunitySelectedListener.onCommunitySelected(itemPosition, mCommunities, Constants.SOURCE_FIND);

            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(itemPosition);
            } else {
                Intent intent = new Intent(mContext, CommunityDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                intent.putExtra(Constants.EXTRA_KEY_COMMUNITIES, Parcels.wrap(mCommunities));
                intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_FIND);
                mContext.startActivity(intent);
            }
        }
    }
}
