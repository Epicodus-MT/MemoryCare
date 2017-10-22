import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.epicodus.memorycare.Constants;
import com.epicodus.memorycare.util.OnStartDragListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;Communitypublic import java.util.Collections;

class FirebaseCommunityListAdapter extends FirebaseRecyclerAdapter<Community, FirebaseCommunityViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private ChildEventListener mChildEventListener;
    private Context mContext;
    private ArrayList<Community> mCommunities = new ArrayList<>();
    private int mOrientation;

    public FirebaseCommunityListAdapter(Class<Community> modelClass, int modelLayout,
                                         Class<FirebaseCommunityViewHolder> viewHolderClass,
                                         Query ref, OnStartDragListener onStartDragListener, Context context) {

        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mCommunities.add(dataSnapshot.getValue(Community.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void populateViewHolder(final FirebaseCommunityViewHolder viewHolder, Community model, int position) {
        viewHolder.bindCommunity(model);

        mOrientation = viewHolder.itemView.getResources().getConfiguration().orientation;
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            createDetailFragment(0);
        }

        viewHolder.mCommunityImageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }

        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int itemPosition = viewHolder.getAdapterPosition();
                if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    createDetailFragment(itemPosition);
                } else {
                    Intent intent = new Intent(mContext, CommunityDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                    intent.putExtra(Constants.EXTRA_KEY_COMMUNITIES, Parcels.wrap(mCommunities));
                    intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_SAVED);
                    mContext.startActivity(intent);
                }
            }
        });

    }

    private void createDetailFragment(int position) {
        CommunityDetailFragment detailFragment = CommunityDetailFragment.newInstance(mCommunities, position, Constants.SOURCE_SAVED);
        FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.communityDetailContainer, detailFragment);
        ft.commit();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mCommunities, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mCommunities.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase() {
        for (Community community : mCommunities) {
            int index = mCommunities.indexOf(community);
            DatabaseReference ref = getRef(index);
            ref.child("index").setValue(Integer.toString(index));
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
        setIndexInFirebase();
        mRef.removeEventListener(mChildEventListener);
    }
}
