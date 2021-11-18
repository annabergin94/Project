package com.example.pracitcingrecievingbtc.View;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import com.example.pracitcingrecievingbtc.Presenter.BitcoinWalletPresenter;
import com.example.pracitcingrecievingbtc.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static Context context;
    public BitcoinWalletPresenter btcService;

    private ViewAddressFragment viewAddressFrag;
    private SendBitcoinFragment sendBitcoinFrag;
    private ReceiveBitcoinFragment receiveBitcoinFrag;

    public Button btnCallingViewAddressFrag;

    EditText etMyAddress;
    TextView tvWalletBalance;
    SwitchCompat btnSwitchTheme;

    // called when the activity is first created
    // where you should do all of the normal static set up
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // check condition
        checkingDayOrNightMode();

        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        Log.d(TAG, "Creating BitcoinWalletPresenter");
        btcService = new BitcoinWalletPresenter(context);
        Log.d(TAG, "Now the wallet has been created/loaded and blockchain synced");
        setContentView(R.layout.activity_main);

        String fragmentName = getIntent().getStringExtra("fragmentName");
        Log.d(TAG, "specified fragment name is: " + fragmentName);

        // adding the placeholder fragment at run time
        // check the container is available
        if ((savedInstanceState == null) && (fragmentName == null)) {
            Log.d(TAG, "Adding the placeholder fragment to main activity with buttons");
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    // add fragement to container defined in xml
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        if (fragmentName != null) {
            if (fragmentName.equals("WalletRecoveryFragment")) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.container, new WalletRecoveryFragment())  // add fragement to container defined in xml
                        .commit();
            }
            Log.d(TAG, "starting to update wallet from blockchain...");
            btcService.initWalletFromNetwork();
            Log.d(TAG, "completed updating wallet from blockchain");
        }
    }


    public BitcoinWalletPresenter getBtcService() {
        return btcService;
    }


    public void onClick(View view) {
        if (view.getId() == R.id.btnCallingViewAddressFrag) {
            Log.d(TAG, "clicks view address");
            viewAddress(view);
            etMyAddress = findViewById(R.id.etMyAddress);
        }
        if(view.getId()==R.id.backToMainMenu){
            backToMainMenu(view);
        }
        if(view.getId()==R.id.btnCallingSendBitcoinFrag){
            sendBitcoin(view);
        }
        if(view.getId()==R.id.btnCallingReceiveBitcoinFrag){
            receiveBitcoin(view);
        }
        if (view.getId() == R.id.btnCallHistoricalPriceFrag) {
            viewPrices(view);
        }
    }


    //   https://coderanch.com/t/632507/Error-fix-static-reference-static
    //   Called in fragments to access the btcService created above. you can use
    //   ((MainActivity)this.getActivity()).getBTCService() to access object from fragments **/
    // static fragment as a placeholder on the main UI
    // must be static to be properly recreated from instance state
    public static class PlaceholderFragment extends Fragment {

        SwitchCompat btnSwitchTheme;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main_activity, container, false);

            btnSwitchTheme = (SwitchCompat) view.findViewById(R.id.btnSwitchTheme);
            btnSwitchTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // checking conditions
                    if (isChecked) {
                        // when switch button is click
                        // set night mode
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    } else {
                        // unchecked set light mode
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                }
            });
            return view;
        }
    }

    // called when the user clicks the Wallet Balance button
    public void viewAddress(View view) {
        viewAddressFrag = new ViewAddressFragment();
        // display fragment as main
        //need to do getActivity because static  https://coderanch.com/t/632507/Error-fix-static-reference-static
        getSupportFragmentManager().beginTransaction() // display fragment on main
                .setReorderingAllowed(true)
                .replace(R.id.container, viewAddressFrag)
                .commit(); // perform the transaction as soon as its available on the UI thread
    }

    public void sendBitcoin(View view) {
        sendBitcoinFrag = new SendBitcoinFragment();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, sendBitcoinFrag)
                .commit();
    }

    public void receiveBitcoin(View view) {
        receiveBitcoinFrag = new ReceiveBitcoinFragment();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, receiveBitcoinFrag)
                .commit();
    }

    public void viewPrices(View view){
        getSupportFragmentManager().beginTransaction() // display fragment on main
                .setReorderingAllowed(true)
                .replace(R.id.container, new HistoryOfPricesFragment())
                .commit();
    }

    // back to main menu from all UIs
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    // called when a user presses back to main menu on any UI
    public void backToMainMenu(View view) {
        getSupportFragmentManager().beginTransaction() // display fragment on main
                .setReorderingAllowed(true)
                .replace(R.id.container, new PlaceholderFragment())
                .commit();
    }

    public void checkingDayOrNightMode() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            // when night mode is equal to yes set dark theme
            setTheme(R.style.Theme_Dark); //when dark mode is enabled, we use the dark theme
            Log.d(TAG, "theme is dark");
        } else {
            setTheme(R.style.Theme_Light);  //default app theme
            Log.d(TAG, "theme is light");
        }
    }



}

