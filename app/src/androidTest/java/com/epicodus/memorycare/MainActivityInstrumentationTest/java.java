package com.epicodus.memorycare.MainActivityInstrumentationTest;

/**
 * Created by Mara on 9/9/17.
 */


import android.support.test.rule.ActivityTestRule;

import com.epicodus.memorycare.ui.MainActivity;
import com.epicodus.memorycare.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class MainActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateEditText() {
        onView(withId(R.id.locationEditText)).perform(typeText("September 15, 2016"))
                .check(matches(withText("September 15, 2016")));
    }

    @Test
    public void locationIsSentToPatientsActivity() {
        String location = "Portland";
        onView(withId(R.id.locationEditText)).perform(typeText(location));
        onView(withId(R.id.findPatientButton)).perform(click());
        onView(withId(R.id.locationTextView)).check(matches
                (withText("Here are all the services near: " + location)));
    }
}
