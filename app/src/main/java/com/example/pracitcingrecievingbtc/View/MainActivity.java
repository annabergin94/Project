package com.example.pracitcingrecievingbtc.View;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.pracitcingrecievingbtc.Presenter.BitcoinWalletPresenter;
import com.example.pracitcingrecievingbtc.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static Context context;
    public BitcoinWalletPresenter btcService;
    private ViewAddressFragment viewAddressFrag;

    Button btnCallingViewAddressFrag;
    EditText etMyAddress;

    // called when the activity is first created
    // where you should do all of the normal static set up
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        Log.d(TAG, "Creating BitcoinWalletPresenter");
        btcService = new BitcoinWalletPresenter(context);
        Log.d(TAG, "Now the wallet has been created/loaded and blockchain synced");
        setContentView(R.layout.activity_main);

        String fragmentName = getIntent().getStringExtra("fragmentName");
        Log.d(TAG, "specified fragment name is: " + fragmentName);

        // adding a placeholder fragment to the main activity containing the buttons
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
            viewAddress(view);
            etMyAddress = findViewById(R.id.etMyAddress);
        }
        if(view.getId()==R.id.backToMainMenu){
            backToMainMenu(view);
        }
    }


//
//        tvWalletBalance.setOnClickListener(v -> {
//            String balance = btcService.getBalance();
//            // use intent to open a new activity and convey the message to the system to start a new activity
//            Intent intent = new Intent(MainActivity.this, ViewWalletAddress.class);
//            intent.putExtra("walletAddress", balance); // key value pair
//            startActivity(intent);
//        });
//
//
//        btnSendBitcoin.setOnClickListener(v -> {
//        });
//
//        btnReceiveBitcoin.setOnClickListener(v -> {
//
//        });

//        // listener to copy the wallet address
//        ivCopy.setOnClickListener(v -> {
//            ClipData clip = ClipData.newPlainText("My wallet address", tvMyAddress.getText().toString());
//            clipboardManager.setPrimaryClip(clip);
//            Toast.makeText(MainActivity.this, "Copied", Toast.LENGTH_SHORT).show();
//        });


    //   https://coderanch.com/t/632507/Error-fix-static-reference-static
    //   Called in fragments to access the btcService created above. you can use
    //   ((MainActivity)this.getActivity()).getBTCService() to access object from fragments **/
    // static fragment as a placeholder on the main UI
    // must be static to be properly recreated from instance state
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main_activity, container, false);
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
}








// mon morning
//public class MainActivity extends Activity {
//
//    private static final String TAG = MainActivity.class.getSimpleName();
//    public static Context context;
//    public BitcoinWalletPresenter btcService;
//    protected ClipboardManager clipboardManager;
//
//    TextView tvMyAddress;
//    TextView tvWalletBalance;
//    Button btnViewAddress;
//    Button btnSendBitcoin;
//    Button btnReceiveBitcoin;
//    ImageView imageQrCode;
//    Button ivCopy; // xml from sample
//
//
////
////        tvWalletBalance.setOnClickListener(v -> {
////            String balance = btcService.getBalance();
////            // use intent to open a new activity and convey the message to the system to start a new activity
////            Intent intent = new Intent(MainActivity.this, ViewWalletAddress.class);
////            intent.putExtra("walletAddress", balance); // key value pair
////            startActivity(intent);
////        });
////
////
////        btnSendBitcoin.setOnClickListener(v -> {
////        });
////
////        btnReceiveBitcoin.setOnClickListener(v -> {
////
////        });
//
////        // listener to copy the wallet address
////        ivCopy.setOnClickListener(v -> {
////            ClipData clip = ClipData.newPlainText("My wallet address", tvMyAddress.getText().toString());
////            clipboardManager.setPrimaryClip(clip);
////            Toast.makeText(MainActivity.this, "Copied", Toast.LENGTH_SHORT).show();
////        });
//
//    }
//
//    public void registeringUIComponents() {
//        tvMyAddress = findViewById(R.id.tvMyAddress);
//
//        btnSendBitcoin = findViewById(R.id.btnSendBitcoin);
//        btnReceiveBitcoin = findViewById(R.id.btnReceiveBitcoin);
//        tvWalletBalance = findViewById(R.id.tvWalletBalance);
//        //  ivCopy = findViewById(R.id.ivCopy);
//    }
//
//

