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
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.not;


// checking all the view interfaces exist
@RunWith(AndroidJUnit4.class)
@SmallTest
public class FragmentTest {

    ActivityScenario scenario;

    // GIVEN
    // specifying the activity being tested
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    // WHEN
    // launching the activity being tested

    public void testActivity() {
        scenario = activityRule.getScenario();
    }

    // THEN
//    @Test
//    public void checkingViewAddressFrag() {
//        onView(withId(R.id.btnCallingViewAddressFrag)).perform(click());
//    }

    // works
    @Test
    public void checkingTransactionFrag() {
        onView(withId(R.id.btnCallingTransactions)).perform(click());
    }

    // works
    @Test
    public void checkingSendFrag () {
        onView(withId(R.id.btnCallingSend)).perform(click());
    }

//    @Test
//    public void checkingPriceHistoryFrag() {
//        onView(withId(R.id.btnCallHistoricalPriceFrag)).perform(click());
//    }

//        @Test
//        public void test() throws InterruptedException {
//            activityRule.getScenario().onActivity(activity -> {
//                onView(withId(R.id.sendBitcoinFrag)).check(matches(not(isDisplayed())));
//                onView(withId(R.id.historyOfPrices)).check(matches(not(isDisplayed())));
//                onView(withId(R.id.callingPlaceholderFrag)).check(matches(isDisplayed()));
//                onView(withId(R.id.transactionFrag)).check(matches(not(isDisplayed())));
//                onView(withId(R.id.addressFrag)).check(matches(not(not(isDisplayed()))));
//            });
//        }
}






