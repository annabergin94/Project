package com.example.pracitcingrecievingbtc.View;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.pracitcingrecievingbtc.R;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.utils.BtcFormat;
import org.bitcoinj.wallet.SendRequest;


public class SendBitcoinFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = SendBitcoinFragment.class.getSimpleName();
    private ImageView ivCopy; // copy address emoji
    protected ClipboardManager clipboardManager;

    EditText etAmountToSend; // user to enter amount to send
    EditText etRecipientAddress; // user to enter recipient address
    Button btnSendBitcoin; // send bitcoin button
    Button btnScanQR;  // scan recipients QR code
    TextView tvAvailableBalance;
    BtcFormat btcFormat = BtcFormat.getInstance();

    String amountBeingSent = "0.0";
    String recipientAddress = "";

    NetworkParameters networkParams = TestNet3Params.get();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragement_send_bitcoin, container, false);

        // instantiate the enter amount to send
        etAmountToSend = (EditText) view.findViewById(R.id.etAmountToSend);
        etAmountToSend.setText(amountBeingSent);

        // instantiate the enter recipient address
        etRecipientAddress = (EditText) view.findViewById(R.id.etRecipientAddress);
        etRecipientAddress.setText(recipientAddress);

        // wallet balance
        tvAvailableBalance = (TextView) view.findViewById(R.id.tvAvailableBalance);
        tvAvailableBalance.setText(((MainActivity) this.getActivity()).getBtcService().getBalance());

        // instantiate button
        btnSendBitcoin = (Button) view.findViewById(R.id.btnSendBitcoin);
        btnSendBitcoin.setOnClickListener(this);

        // scan qr
        btnScanQR = (Button) view.findViewById(R.id.btnScanQR);
        btnScanQR.setOnClickListener(this);

        send();

        return view;
    }

    private void init(View view) {
        ivCopy = view.findViewById(R.id.copy);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void send() {

        // hard coded address and amount
//        LegacyAddress recipientAddress = LegacyAddress.fromBase58(networkParams, "mupBAFeT63hXfeeT4rnAUcpKHDkz1n4fdw");
//        System.out.println("Send money to: " + recipientAddress.toString());
//        Coin value = Coin.parseCoin("0.09");
//        Log.d(TAG, "sending 0.09");

        //   Context context = ((MainActivity) this.getActivity()).getApplicationContext();

        // setting address and amount to user inputs
        String address = etRecipientAddress.getText().toString();
        String amount = etAmountToSend.getText().toString();

        // pop up message if user doesn't enter amount or address
        if (address.isEmpty()) { // If no input for origin then set origin to current location
            Toast.makeText(getActivity(), "Please enter recipient address!", Toast.LENGTH_SHORT).show();
        }
        if (amount.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter an amount to send!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

//        Coin coinAmount = Coin.parseCoin(amount);
//        if (coinAmount.isPositive()) {
//            Log.d(TAG, "updating amountEdit textview");
//            etAmountToSend.setText("0.0");
//            Log.d(TAG, "sending transaction start Toast to UI");
//            CharSequence text = "Sending your transaction";
//            int duration = Toast.LENGTH_LONG;
//            Toast toast = Toast.makeText(((MainActivity) this.getActivity()).getApplicationContext(), text, duration);
//            toast.show();
//
//            // performing the transaction
//            // performing transaction
//            Log.d(TAG, "invoking sendTransactions on btcService");
//            String sendResult = ((MainActivity) this.getActivity()).getBtcService().sendTransaction(address, amount);
//            // updating UI
//            Log.d(TAG, "displaying wallet content");
//            tvAvailableBalance = (TextView) view.findViewById(R.id.tvAvailableBalance);
//            tvAvailableBalance.setText("Wallet Balance: " + btcFormat.format(((MainActivity) this.getActivity()).getBTCService().getBalanceEstimated()));
//            Log.d(TAG, "sending update complete Toast to UI");
//            text = "Sending transaction complete";
//            toast = Toast.makeText(((MainActivity) this.getActivity()).getApplicationContext(), text, duration);
//            toast.show();
//            Log.d(TAG, sendResult);
//
//            // check the user enters an amount greater than 0
//            // check the user has enough coins
//            // try to send the coins
//        }
//    }

    // switch statement to intercept clicks
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSendBitcoin:
                send();
                break;
            case R.id.btnScanQR:
             //   scanQr();
                break;
        }
    }
}