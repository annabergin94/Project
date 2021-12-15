//package com.example.pracitcingrecievingbtc;
//
//import androidx.test.core.app.ActivityScenario;
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.filters.SmallTest;
//import com.example.pracitcingrecievingbtc.View.MainActivity;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//
//
//// checking all the view interfaces exist
//@RunWith(AndroidJUnit4.class)
//@SmallTest
//public class FragmentTest {
//
//    ActivityScenario scenario;
//
//    // GIVEN
//    // specifying the activity being tested
//    @Rule
//    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);
//
//    // WHEN
//    // launching the activity being tested
//    @Test
//    public void testActivity () {
//        scenario = activityRule.getScenario();
//    }
//
//    // THEN
//    @Test
//    public void buttonTest(){
//        onView(withId(R.id.btnCallHistoricalPriceFrag)).perform(click());
//        onView(withId(R.id.btnCallingViewAddressFrag)).perform(click());
//        onView(withId(R.id.btnCallingSendBitcoinFrag)).perform(click());
//        onView(withId(R.id.btnCallingReceiveBitcoinFrag)).perform(click());
//    }
//}
//

//    @Before
//    public void setUp() throws InterruptedException {
//        scenario = activityRule.getScenario();
//        Thread.sleep(2000);
//        scenario.onActivity(activity ->
//                activity.replace(R.id.container, new MainActivity.PlaceholderFragment()));
//        Context.getInstance().setLogin(false);
//
//    }


//    @Test
//    public void testFragment () throws InterruptedException {
//        scenario.onFragment(fragment ->
//                fragment.getActivity().getSupportFragmentManager().beginTransaction());
//        Thread.sleep(500);
//    }

//    @Test
//    public void testFragment () throws InterruptedException {
//        FragmentScenario<MainActivity.PlaceholderFragment> fragmentScenario = FragmentScenario.launchInContainer<new MainActivity.PlaceholderFragment()>;
//        fragmentScenario.moveToState(Lifecycle.State.CREATED);
//    }

