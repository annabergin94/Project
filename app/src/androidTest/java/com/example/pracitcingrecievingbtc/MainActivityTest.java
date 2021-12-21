package com.example.pracitcingrecievingbtc;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import com.example.pracitcingrecievingbtc.UIs.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

// check the activity launches without crashing
@RunWith(AndroidJUnit4.class)
    @SmallTest
    public class MainActivityTest {

        ActivityScenario scenario;

        // GIVEN
        // specifying the activity being tested
        @Rule
        public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

        // WHEN, THEN
        // check the activity launches without crashing
        @Test
        public void testActivity () {
            scenario = activityRule.getScenario();
        }
    }
