package com.example.pracitcingrecievingbtc.View;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.pracitcingrecievingbtc.Presenter.BitcoinWalletPresenter;
import com.example.pracitcingrecievingbtc.R;

// an intent always starts an activity
public class ViewWalletAddress extends AppCompatActivity {

    TextView tvMyAddress;
    BitcoinWalletPresenter p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wallet_address);
        tvMyAddress = findViewById(R.id.tvMyAddress);
        String address = getIntent().getStringExtra("walletAddress");
        tvMyAddress.setText("Wallet address: " + address);
    }
}