package com.example.pracitcingrecievingbtc;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import com.example.pracitcingrecievingbtc.View.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


// checking all the view interfaces exist
@RunWith(AndroidJUnit4.class)
@SmallTest
public class FragmentPlaceholderTest {

        ActivityScenario scenario;

        // GIVEN
        // specifying the activity being tested
        @Rule
        public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

        // WHEN
        // launching the activity being tested

        public void testActivity () {
            scenario = activityRule.getScenario();
        }

        // THEN
        @Test
        public void checkingViewsExist(){
            onView(withId(R.id.btnCallingBitcoinPrices)).check(matches(isEnabled()));
            onView(withId(R.id.btnCallingAddressFrag)).check(matches(isEnabled()));
            onView(withId(R.id.btnCallingSend)).check(matches(isEnabled()));
            onView(withId(R.id.btnCallingTransactions)).check(matches(isEnabled()));
        }
}