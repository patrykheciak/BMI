package com.gmail.patrykheciak.lab1.bmi;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class BMItest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void providing66kgAnd177cmAndClickingCountDisplaysCorrectBMI() throws Exception {
        onView(withId(R.id.et_mass))
                .perform(typeText("66"));
        onView(withId(R.id.et_height))
                .perform(click()).perform(typeText("177"));
        onView(withId(R.id.btn_count))
                .perform(click());

        onView(withId(R.id.tv_bmi_value))
                .check(matches(withText("21.07")));
    }

    @Test
    public void providing66kgAnd1_77mAndClickingCountDisplaysCorrectBMI() throws Exception {
        onView(withId(R.id.et_mass))
                .perform(typeText("66"));

        // switch to metres
        onView(withId(R.id.sp_height_unit)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("m"))).perform(click());

        onView(withId(R.id.et_height))
                .perform(typeText("1.77"));


        onView(withId(R.id.btn_count))
                .perform(click());

        onView(withId(R.id.tv_bmi_value))
                .check(matches(withText("21.07")));
    }

    @Test
    public void providing66kgAsLbsAnd1_77mAndClickingCountDisplaysCorrectBMI() throws Exception {
        onView(withId(R.id.et_mass))
                .perform(typeText("66"));

        // switch to metres
        onView(withId(R.id.sp_height_unit)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("m"))).perform(click());

        onView(withId(R.id.et_height))
                .perform(typeText("1.77"));


        onView(withId(R.id.btn_count))
                .perform(click());

        onView(withId(R.id.tv_bmi_value))
                .check(matches(withText("21.07")));
    }

}
