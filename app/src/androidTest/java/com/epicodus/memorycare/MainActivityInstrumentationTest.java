package com.epicodus.memorycare;

/**
 * Created by Mara on 9/16/17.
 */

        import android.support.test.rule.ActivityTestRule;
        import android.view.View;

        import com.epicodus.memorycare.ui.CommunityActivity;

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


public class CommunityActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<CommunityActivity> activityTestRule =
            new ActivityTestRule<>(CommunityActivity.class);

    @Test
    public void listItemClickDisplaysToastWithCorrectCommunity() {
        View activityDecorView = activityTestRule.getActivity().getWindow().getDecorView();
        String communityName = "The Blakely Echo Lake";
        onData(anything())
                .inAdapterView(withId(R.id.listView))
                .atPosition(0)
                .perform(click());
        onView(withText(communityName)).inRoot(withDecorView(not(activityDecorView)))
                .check(matches(withText(communityName)));
    }

}
