Communitypackage com.epicodus.memorycare.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.epicodus.memorycare.Constants;
import com.epicodus.memorycare.R;
import com.epicodus.memorycare.adapters.CommunityListAdapter;
import com.epicodus.memorycare.services.YelpService;
import com.epicodus.memorycare.models.Community;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommunityListActivity extends AppCompatActivity implements OnCommunitySelectedListener {
    private Integer mPosition;
    ArrayList<Community> mCommunities;
    String mSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communities);

        if (savedInstanceState != null) {

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mPosition = savedInstanceState.getInt(Constants.EXTRA_KEY_POSITION);
                mCommunities = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_KEY_COMMUNITIES));
                mSource = savedInstanceState.getString(Constants.KEY_SOURCE);

                if (mPosition != null && mCommunities != null) {
                    Intent intent = new Intent(this, CommunityDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, mPosition);
                    intent.putExtra(Constants.EXTRA_KEY_COMMUNITIES, Parcels.wrap(mCommunities));
                    intent.putExtra(Constants.KEY_SOURCE, mSource);
                    startActivity(intent);
                }

            }

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mPosition != null && mCommunities != null) {
            outState.putInt(Constants.EXTRA_KEY_POSITION, mPosition);
            outState.putParcelable(Constants.EXTRA_KEY_COMMUNITIES, Parcels.wrap(mCommunities));
            outState.putString(Constants.KEY_SOURCE, mSource);
        }

    }

    @Override
    public void onCommunitySelected(Integer position, ArrayList<Community> communities, String source) {
        mPosition = position;
        mCommunities = communities;
        mSource = source;
    }
}
