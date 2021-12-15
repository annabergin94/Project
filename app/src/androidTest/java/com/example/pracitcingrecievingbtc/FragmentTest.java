//package com.example.pracitcingrecievingbtc;
//
//import android.content.Context;
//import androidx.fragment.app.FragmentActivity;
//import androidx.fragment.app.testing.FragmentScenario;
//import androidx.lifecycle.Lifecycle;
//import androidx.test.core.app.ActivityScenario;
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.filters.SmallTest;
//import com.example.pracitcingrecievingbtc.View.MainActivity;
//import com.example.pracitcingrecievingbtc.View.ViewAddressFragment;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.not;
//import static org.junit.Assert.assertTrue;
//
//@RunWith(AndroidJUnit4.class)
//@SmallTest
//public class FragmentTest {
//
//   // public FragmentScenario<MainActivity.PlaceholderFragment> scenario;
//   public ActivityScenario scenario;
//    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);
//
////    @Before
////    public void setUp() throws InterruptedException {
////        scenario = activityRule.getScenario();
////        Thread.sleep(2000);
////        scenario.onActivity(activity ->
////                activity.replace(R.id.container, new MainActivity.PlaceholderFragment()));
////        Context.getInstance().setLogin(false);
////
////    }
//
//
////    @Test
////    public void testFragment () throws InterruptedException {
////        scenario.onFragment(fragment ->
////                fragment.getActivity().getSupportFragmentManager().beginTransaction());
////        Thread.sleep(500);
////    }
//
//    @Test
//    public void testFragment () throws InterruptedException {
//        FragmentScenario<MainActivity.PlaceholderFragment> fragmentScenario = FragmentScenario.launchInContainer(MainActivity.PlaceholderFragment.class);
//        fragmentScenario.moveToState(Lifecycle.State.CREATED);
//    }
//
//
//}