//package com.example.pracitcingrecievingbtc;
//
//import android.app.Activity;
//import androidx.appcompat.app.AppCompatDelegate;
//import androidx.fragment.app.FragmentActivity;
//import androidx.test.core.app.ActivityScenario;
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.filters.SmallTest;
//import com.example.pracitcingrecievingbtc.Presenter.BitcoinSetUp;
//import com.example.pracitcingrecievingbtc.View.MainActivity;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.filters.LargeTest;
//import org.bitcoinj.core.*;
//import org.bitcoinj.params.TestNet3Params;
//import org.bitcoinj.store.MemoryBlockStore;
//import org.bitcoinj.wallet.Wallet;
//import org.hamcrest.Matchers;
//import org.hamcrest.core.IsEqual;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.jupiter.api.Timeout;
//import org.junit.rules.ExpectedException;
//import org.junit.rules.TemporaryFolder;
//import org.junit.runner.RunWith;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static org.bitcoinj.wallet.Wallet.createBasic;
//import static org.hamcrest.Matchers.not;
//import static org.hamcrest.Matchers.startsWith;
//import static org.junit.Assert.assertNotNull;
//
//
//@RunWith(AndroidJUnit4.class)
//@SmallTest
//public class ViewAddressFragmentTest {
//
//    @Rule
//    public ExpectedException testingExceptions = ExpectedException.none();
//
//    private static NetworkParameters networkParams = TestNet3Params.get();
//    private static final String addressTest = "mkFQohBpy2HDXrCwyMrYL5RtfrmeiuuPY2";
//    private static final String sendAmountTest = "0.0";
//    private static File walletTest;
//    Context context;
//    private static Wallet myWallet = createBasic(networkParams);
//
//    @Rule
//    public TemporaryFolder folder = new TemporaryFolder();
//    private BitcoinSetUp b;
//
//    @Before
//    public void setUp() {
//        b = new BitcoinSetUp();
//        networkParams = TestNet3Params.get();
//        walletTest = new File("", "testwallet");
//    }
//
//    // throws an exception that complies with catcher
//    @Test
//    public void testMethod() throws Exception {
//        myWallet = createBasic(networkParams);
//        b.send(addressTest, sendAmountTest);
//        final String error = "Sorry, you've not got enough Bitcoins to complete this transaction!";
//        Exception expectedCause = new Exception(error);
//        testingExceptions.expectCause(IsEqual.equalTo(expectedCause));
//        throw new RuntimeException(expectedCause);
//    }
//
//    // throws an exception that complies with catcher
//    @Test
//    public void testMethod2() throws Exception {
//        myWallet = createBasic(networkParams);
//        b.send(addressTest, sendAmountTest);
//        final String error = "Sorry, you've not got enough Bitcoins to complete this transaction!";
//        AddressFormatException expectedCause = new AddressFormatException(error);
//        testingExceptions.expectCause(IsEqual.equalTo(expectedCause));
//        throw new RuntimeException(expectedCause);
//    }
//
//    // throws an exception that complies with catcher
//    @Test
//    public void testMethod3() throws Exception {
//        myWallet = createBasic(networkParams);
//       b.send(addressTest, sendAmountTest);
//        final String error = "Sorry, you've not got enough Bitcoins to complete this transaction!";
//        InsufficientMoneyException expectedCause = new InsufficientMoneyException(Coin.valueOf(0));
//        testingExceptions.expectCause(IsEqual.equalTo(expectedCause));
//        throw new RuntimeException(expectedCause);
//    }
//
//    @Test
//    public void testMethod4() throws Exception {
//        b.send(addressTest, sendAmountTest);
//    }
//}

//
//
////    ActivityScenario scenario;
////
////    // https://developer.android.com/reference/androidx/test/ext/junit/rules/ActivityScenarioRule
////    @Rule
////    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);
////
////    public void testFragmentManager() {
////        FragmentActivity activity = getActivity();
////        assertNotNull(activity.getSupportFragmentManager());
////    }
//
//
//
////    @Test
////    public void test() {
////        scenario = activityScenarioRule.getScenario();
////        scenario.onActivity(Activity::getFragmentManager
////    }
//
//
//
//
//
//
////    @Test
////    public void checkingDayOrNightMode() {
////        activityScenarioRule.getScenario().onActivity(MainActivity::checkingDayOrNightMode) {
////        }
////    }
//
//// timed out
////    @Test
////        public void test() throws InterruptedException {
////            activityScenarioRule.getScenario().onActivity(activity -> {
////                onView(withId(R.id.sendBitcoinFrag)).check(matches(not(isDisplayed())));
////      //          onView(withId(R.id.historyOfPrices)).check(matches(not(isDisplayed())));
////     //           onView(withId(R.id.placeholderFrag)).check(matches(isDisplayed()));
////      //          onView(withId(R.id.transactionFrag)).check(matches(not(isDisplayed())));
////      //          onView(withId(R.id.addressFrag)).check(matches(not(not(isDisplayed()))));
////            });
////        }
////
////
////
////
////
////     tues night
////    public void testGetResourceString() {
////        assertNotNull(mActivity.getResources().getString(R.string.a_new_address_will_appear_here_everytime_you_send_or_receive_bitcoin));
////    }
////
//// mon night
////    @Rule
//////    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);
//// //   public FragmentTestRule<?, FragmentWithoutActivityDependency> mFragmentTestRule = FragmentTestRule.create(FragmentWithoutActivityDependency.class);
////
////    @Before
////    public void setUp() {
////        ActivityScenario.launch(FragmentTestActivity::class.java)
////    }
////
////    @Test
////    public void testingClicks(){
////        onView(withId(R.id.btnCallHistoricalPriceFrag)).perform(click());
////    }
////
