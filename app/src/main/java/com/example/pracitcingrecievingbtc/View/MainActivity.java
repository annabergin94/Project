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

import com.example.pracitcingrecievingbtc.Presenter.BitcoinSetUp;
import com.example.pracitcingrecievingbtc.R;
import org.bitcoinj.utils.BtcFormat;


// this class controls what the app displays to the user
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName(); // prints class name to for debugging
    public static Context context;
    private BitcoinSetUp setUp;
    private AddressFrag addressFrag;
    private SendFrag sendFrag;
    private TransactionsFrag transactionsFrag;
    private BitcoinPriceFrag priceGraphFrag;

    // called when the application is open
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkingDayOrNightMode();  // calling the helper method that sets the display to light/dark
        super.onCreate(savedInstanceState);
        context = getApplicationContext(); // returns the context of the app, layer running behind the app
        Log.d(TAG, "BitcoinSetUp constructor creates/loads wallet and blockchain.");
        setUp = new BitcoinSetUp(context);
        Log.d(TAG, "Set up is complete!");
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Launching the Home UI.");
        String nameOfFrag = getIntent().getStringExtra("fragmentName"); // return the intent that start the activity and the fragment
        Log.d(TAG, "The fragment is  " + nameOfFrag);
        // checking the container is available to add the Home UI at run time
        if ((savedInstanceState == null) && (nameOfFrag == null)) {
            Log.d(TAG, "Adding fragment for the Home UI with buttons, balance, logo.");
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.container, new HomeFrag())
                    .commit();
        }
            Log.d(TAG, "Updating the wallet from the blockchain");
            setUp.connectingWalletToBlockchain();
            Log.d(TAG, "Update complete!");
        }

    public BitcoinSetUp getSetUp() {
        return setUp;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id==R.id.btnCallingAddressFrag) {
            viewAddress(view);
        }
        if(id==R.id.btnCallingHome){
            backToMainMenu(view);
        }
        if(id==R.id.btnCallingSend){
            viewSend(view);
        }
        if(id==R.id.btnCallingTransactions){
            viewTransactions(view);
        }
        if (id== R.id.btnCallingBitcoinPrices) {
            viewPrices(view);
        }
    }

    // static fragment as a placeholder on the main UI, must be static to be properly recreated from instance state
    public static class HomeFrag extends Fragment {

        BtcFormat f = BtcFormat.getCoinInstance(); // format balance
        SwitchCompat btnSwitchTheme;
        TextView tvBalanceBitcoins;
        TextView tvBalanceDollars;

        public HomeFrag() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main_activity, container, false);

            // view. used to call the id because we are in a fragment
            tvBalanceBitcoins = view.findViewById(R.id.tvAvailableBalance);
            String out = f.format(((MainActivity)this.getActivity()).getSetUp().getAvailableBalance(),2,3, 3) + " BTC";
            tvBalanceBitcoins.setText("Balance: " + out);
            tvBalanceDollars = view.findViewById(R.id.tvRealBalance);
            double result = Double.parseDouble(f.format(((MainActivity)this.getActivity()).getSetUp().getAvailableBalance(),2,3, 3)) * 51679.10;
            double roundedResult = Math.round(result*100.0)/100.0;
            tvBalanceDollars.setText("$" + String.format(String.valueOf(roundedResult)));
            btnSwitchTheme = view.findViewById(R.id.btnSwitchTheme);
            btnSwitchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); // when switch button is click set night mode
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);  // unchecked set light mode
                }
            });
            return view;
        }
    }

    // replace home frag with address frag
    public void viewAddress(View view) {
        addressFrag = new AddressFrag();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, addressFrag)
                .commit(); // perform the transaction as soon as its available on the UI thread
    }

    // replace home frag with send frag
    public void viewSend(View view) {
        sendFrag = new SendFrag();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, sendFrag)
                .commit();
    }

    // replace home frag with transaction frag
    public void viewTransactions(View view) {
        transactionsFrag = new TransactionsFrag();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, transactionsFrag)
                .commit();
    }

    // replace home frag with price history frag
    public void viewPrices(View view){
        priceGraphFrag = new BitcoinPriceFrag();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, priceGraphFrag)
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

    // replace any frag with home UI
    public void backToMainMenu(View view) {
        getSupportFragmentManager().beginTransaction() // display fragment on main
                .setReorderingAllowed(true)
                .replace(R.id.container, new HomeFrag())
                .commit();
    }

    // checking and setting the theme of the app
    public void checkingDayOrNightMode() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            // when night mode is equal to yes set dark theme
            setTheme(R.style.Theme_Dark); // when dark mode is enabled, use the dark theme
            Log.d(TAG, "Dark theme!");
        } else {
            setTheme(R.style.Theme_Light);  // default app theme
            Log.d(TAG, "Light theme!");
        }
    }
}

