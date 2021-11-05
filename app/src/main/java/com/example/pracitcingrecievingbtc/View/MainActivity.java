package com.example.pracitcingrecievingbtc.View;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.example.pracitcingrecievingbtc.Presenter.BitcoinWalletPresenter;
import com.example.pracitcingrecievingbtc.R;



public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static Context context;
    public BitcoinWalletPresenter btcService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        Log.d(TAG, "creating btcService...");
        btcService = new BitcoinWalletPresenter(context);

        Log.d(TAG, "completed creating btcService");
        setContentView(R.layout.activity_main);


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