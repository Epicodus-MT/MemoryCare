package com.epicodus.memorycare;

/**
 * Created by Mara on 9/8/17.
 */

        import android.os.Build;
        import android.widget.ListView;

        import com.epicodus.memorycare.ui.PatientActivity;

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

public class PatientActivityTest {
    private PatientActivity activity;
    private ListView mPatientListView;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(PatientActivity.class);
        mPatientListView = (ListView) activity.findViewById(R.id.listView);
    }

    @Test
    public void restaurantListViewPopulates() {
        assertNotNull(mPatientListView.getAdapter());
        assertEquals(mPatientListView.getAdapter().getCount(), 15);
    }

}