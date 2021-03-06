package com.example.pracitcingrecievingbtc;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import com.example.pracitcingrecievingbtc.UIs.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.containsString;


// checking all the view interfaces exist
@RunWith(AndroidJUnit4.class)
@SmallTest
public class SendTest {

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
    // https://developer.android.com/codelabs/android-basics-kotlin-write-instrumentation-tests#3
    // withId is a view component with the id passed to it
    @Test
    public void sendTest(){
        onView(withId(R.id.btnCallingSend)).perform(click());
        onView(withId(R.id.etAmountToSend)).perform(typeText("0.0005"));
        onView(withId(R.id.etRecipientAddress)).perform(typeText("aaaaaBaaaaBaaaIllllIssssIeeeeSddddEssssDSS"));
        onView(withId(R.id.btnSendBitcoin)).perform(closeSoftKeyboard(), scrollTo(), click());
        onView(withId(R.id.etAmountToSend)).check(matches(withText(containsString("0.0"))));
    }

}



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
//public class FragmentPlaceholderTest {
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

