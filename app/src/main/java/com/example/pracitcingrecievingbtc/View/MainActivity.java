package com.example.pracitcingrecievingbtc.View;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import com.example.pracitcingrecievingbtc.Presenter.BitcoinWalletPresenter;
import com.example.pracitcingrecievingbtc.R;
import org.bitcoinj.utils.BtcFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static Context context;
    public BitcoinWalletPresenter bitcoinWalletPresenter;
    private ViewAddressFragment viewAddressFrag;
    private SendBitcoinFragment sendBitcoinFrag;
    private TransactionHistoryFragment receiveBitcoinFrag;

    // called when the activity is first created to do all of the normal static setup
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // calling the helper method that sets the default theme to light
        checkingDayOrNightMode();

        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        Log.d(TAG, "1. Create a BitcoinWalletPresenter object that creates or loads the wallet and syncs the blockchain");
        bitcoinWalletPresenter = new BitcoinWalletPresenter(context);
        Log.d(TAG, "2. The wallet has been created/loaded and blockchain synced");
        setContentView(R.layout.activity_main);
        Log.d(TAG, "3. Launch the main user interface");

        // return the intent that start the activity and the fragment
        String nameOfFragment = getIntent().getStringExtra("fragmentName");
        Log.d(TAG, "specified fragment name is: " + nameOfFragment);

        // adding the placeholder fragment at run time to the container, checking container available first
        if ((savedInstanceState == null) && (nameOfFragment == null)) {
            Log.d(TAG, "4. Adding the placeholder fragment to main activity with buttons");
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
            Log.d(TAG, "5. Updating the wallet from the blockchain");
            bitcoinWalletPresenter.myWalletInitialisedFromNetwork();
            Log.d(TAG, "6. Finished updating the wallet from the blockchain");
        }

    public BitcoinWalletPresenter getBitcoinWalletPresenter() {
        return bitcoinWalletPresenter;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btnCallingViewAddressFrag) {
            viewAddress(view);
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

    // static fragment as a placeholder on the main UI
    // must be static to be properly recreated from instance state
    public static class PlaceholderFragment extends Fragment {

        BtcFormat f = BtcFormat.getCoinInstance(); // format balance
        SwitchCompat btnSwitchTheme;
        TextView tvAvailableBalance;
        TextView tvRealBalance;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main_activity, container, false);

            // view. used to call the id because we are in a fragment
            tvAvailableBalance = view.findViewById(R.id.tvAvailableBalance);
            String out = f.format(((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getAvailableBalance(),2,3, 3) + " BTC";
            tvAvailableBalance.setText("Balance: " + out);


            tvRealBalance = view.findViewById(R.id.tvRealBalance);
            double result = Double.parseDouble(f.format(((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getAvailableBalance(),2,3, 3)) * 51679.10;
            double roundedResult = Math.round(result*100.0)/100.0;
            tvRealBalance.setText("$" + String.format(String.valueOf(roundedResult)));

            btnSwitchTheme = view.findViewById(R.id.btnSwitchTheme);
            btnSwitchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // when switch button is click set night mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    // unchecked set light mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            });
            return view;
        }
    }

    // replace container fragment with the view wallet address fragment
    public void viewAddress(View view) {
        viewAddressFrag = new ViewAddressFragment();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, viewAddressFrag)
                .commit(); // perform the transaction as soon as its available on the UI thread
    }

    // replace container fragment with the sending Bitcoin fragment
    public void sendBitcoin(View view) {
        sendBitcoinFrag = new SendBitcoinFragment();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, sendBitcoinFrag)
                .commit();
    }

    // replace container fragment with the receiving Bitcoin fragment
    public void receiveBitcoin(View view) {
        receiveBitcoinFrag = new TransactionHistoryFragment();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, receiveBitcoinFrag)
                .commit();
    }

    // replace container fragment with the history of bitcoin prices fragment
    public void viewPrices(View view){
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, new HistoryOfPricesFragment())
                .commit();
    }

    // return to main menu from any fragment
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
            setTheme(R.style.Theme_Dark); // when dark mode is enabled, we use the dark theme
            Log.d(TAG, "theme is dark");
        } else {
            setTheme(R.style.Theme_Light);  // default app theme
            Log.d(TAG, "theme is light");
        }
    }
}

