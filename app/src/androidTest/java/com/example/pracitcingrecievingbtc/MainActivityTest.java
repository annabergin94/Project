package com.example.pracitcingrecievingbtc;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import com.example.pracitcingrecievingbtc.View.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

    @RunWith(AndroidJUnit4.class)
    @SmallTest
    public class MainActivityTest {

        ActivityScenario scenario;

        @Rule
        public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

        @Test
        public void testActivity () {
            scenario = activityRule.getScenario();
        }
    }
