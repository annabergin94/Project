package com.example.pracitcingrecievingbtc.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.pracitcingrecievingbtc.Presenter.BitcoinWalletPresenter;
import com.example.pracitcingrecievingbtc.R;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static Context context;
    public BitcoinWalletPresenter btcService;

    TextView tvMyAddress;
    Button btnViewAddress;
    Button btnSendBitcoin;
    Button btnReceiveBitcoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        Log.d(TAG, "creating btcService...");
        btcService = new BitcoinWalletPresenter(context);

        Log.d(TAG, "completed creating btcService");
        setContentView(R.layout.activity_main);

        // registering UI components
        tvMyAddress = findViewById(R.id.tvMyAddress);
        btnViewAddress = findViewById(R.id.btnViewAddress);
        btnSendBitcoin = findViewById(R.id.btnSendBitcoin);
        btnReceiveBitcoin = findViewById(R.id.btnReceiveBitcoin);

        btnViewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String address = "XXXXX";

                // use intent to open a new activity and convey the message to the system to start a new activity
                Intent intent = new Intent(MainActivity.this, com.example.pracitcingrecievingbtc.View.ViewWalletAddress.class);
                intent.putExtra("walletAddress", address); // key value pair
                startActivity(intent);
            }
        });

        btnSendBitcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnReceiveBitcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

   //     btcService.printWalletAddress();

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    // indication the user is leaving the activity/fragment
    @Override
    protected void onPause() {
        super.onPause();
    }
}