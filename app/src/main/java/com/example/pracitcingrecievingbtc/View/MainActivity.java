package com.example.pracitcingrecievingbtc.View;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.pracitcingrecievingbtc.Presenter.BitcoinWalletPresenter;
import com.example.pracitcingrecievingbtc.R;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static Context context;
    public BitcoinWalletPresenter btcService;
    protected ClipboardManager clipboardManager;

    TextView tvMyAddress;
    TextView tvWalletBalance;
    Button btnViewAddress;
    Button btnSendBitcoin;
    Button btnReceiveBitcoin;
    ImageView imageQrCode;
    Button ivCopy; // xml from sample


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        Log.d(TAG, "creating btcService...");
        btcService = new BitcoinWalletPresenter(context);
        Log.d(TAG, "completed creating btcService");
        setContentView(R.layout.activity_main);
        registeringUIComponents(); // registering UI components

        btnViewAddress.setOnClickListener(v -> {
            String address = btcService.printWalletAddress();
            // use intent to open a new activity and convey the message to the system to start a new activity
            Intent intent = new Intent(MainActivity.this, ViewWalletAddress.class);
            intent.putExtra("walletAddress", address); // key value pair
            startActivity(intent);
        });

        tvWalletBalance.setOnClickListener(v -> {
            String balance = btcService.getBalance();
            // use intent to open a new activity and convey the message to the system to start a new activity
            Intent intent = new Intent(MainActivity.this, ViewWalletAddress.class);
            intent.putExtra("walletAddress", balance); // key value pair
            startActivity(intent);
        });


        btnSendBitcoin.setOnClickListener(v -> {
        });

        btnReceiveBitcoin.setOnClickListener(v -> {

        });

//        // listener to copy the wallet address
//        ivCopy.setOnClickListener(v -> {
//            ClipData clip = ClipData.newPlainText("My wallet address", tvMyAddress.getText().toString());
//            clipboardManager.setPrimaryClip(clip);
//            Toast.makeText(MainActivity.this, "Copied", Toast.LENGTH_SHORT).show();
//        });

    }

    public void registeringUIComponents() {
        tvMyAddress = findViewById(R.id.tvMyAddress);
        btnViewAddress = findViewById(R.id.btnViewAddress);
        btnSendBitcoin = findViewById(R.id.btnSendBitcoin);
        btnReceiveBitcoin = findViewById(R.id.btnReceiveBitcoin);
        tvWalletBalance = findViewById(R.id.tvWalletBalance);
        //  ivCopy = findViewById(R.id.ivCopy);
    }


    // static fragment as a placeholder on the main UI
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment(){
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main_activity, container, false);
        }
    }
}
