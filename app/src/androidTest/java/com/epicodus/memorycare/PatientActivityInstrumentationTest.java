package com.epicodus.memorycare;

/**
 * Created by Mara on 9/16/17.
 */

        import android.support.test.rule.ActivityTestRule;
        import android.view.View;

        import com.epicodus.memorycare.ui.PatientActivity;

        import org.junit.Rule;
        import org.junit.Test;

        import static android.support.test.espresso.Espresso.onData;
        import static android.support.test.espresso.Espresso.onView;
        import static android.support.test.espresso.action.ViewActions.click;
        import static android.support.test.espresso.assertion.ViewAssertions.matches;
        import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
        import static android.support.test.espresso.matcher.ViewMatchers.withId;
        import static android.support.test.espresso.matcher.ViewMatchers.withText;
        import static org.hamcrest.CoreMatchers.anything;
        import static org.hamcrest.core.IsNot.not;


public class PatientActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<PatientActivity> activityTestRule =
            new ActivityTestRule<>(PatientActivity.class);

    @Test
    public void listItemClickDisplaysToastWithCorrectPatient() {
        View activityDecorView = activityTestRule.getActivity().getWindow().getDecorView();
        String patientName = "The Blakely Echo Lake";
        onData(anything())
                .inAdapterView(withId(R.id.listView))
                .atPosition(0)
                .perform(click());
        onView(withText(patientName)).inRoot(withDecorView(not(activityDecorView)))
                .check(matches(withText(patientName)));
    }

}
