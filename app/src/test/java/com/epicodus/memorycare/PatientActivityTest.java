package com.epicodus.memorycare;

/**
 * Created by Mara on 9/8/17.
 */

        import android.os.Build;
        import android.widget.ListView;

        import com.epicodus.memorycare.ui.CommunityActivity;

        import org.junit.Before;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.robolectric.Robolectric;
        import org.robolectric.RobolectricGradleTestRunner;
        import org.robolectric.annotation.Config;

        import static junit.framework.Assert.assertEquals;
        import static junit.framework.Assert.assertNotNull;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)

public class CommunityActivityTest {
    private CommunityActivity activity;
    private ListView mCommunityListView;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(CommunityActivity.class);
        mCommunityListView = (ListView) activity.findViewById(R.id.listView);
    }

    @Test
    public void communityListViewPopulates() {
        assertNotNull(mCommunityListView.getAdapter());
        assertEquals(mCommunityListView.getAdapter().getCount(), 15);
    }

}
