package com.example.pracitcingrecievingbtc.View;

import android.content.ClipData;
import android.content.ClipboardManager;
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
import org.bitcoinj.wallet.SendRequest;


public class SendBitcoinFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = SendBitcoinFragment.class.getSimpleName();
    private ImageView ivCopy; // copy address emoji
    protected ClipboardManager clipboardManager;

    TextView tvEnterAmount;
    TextView tvAvailableBalance;
    TextView tvRecipientAddress;
    EditText etAmountToSend; // user to enter amount to send
    EditText etRecipientAddress; // user to enter recipient address
    Button btnSendBitcoin; // send bitcoin button
    Button btnScanQR;  // scan recipients QR code

    NetworkParameters networkParams = TestNet3Params.get();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragement_send_bitcoin, container, false);

        // instantiate the enter amount to send
        etAmountToSend = (EditText) view.findViewById(R.id.etAmountToSend);
        etAmountToSend.setText("");

        // instantiate the enter recipient address
        etRecipientAddress = (EditText) view.findViewById(R.id.etRecipientAddress);
        etRecipientAddress.setText("0.0");

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

        // recipient address
        String address = etRecipientAddress.getText().toString();
        Log.d(TAG, "" + address);

        // hard coded
//        LegacyAddress recipientAddress = LegacyAddress.fromBase58(networkParams, "mupBAFeT63hXfeeT4rnAUcpKHDkz1n4fdw");
//        System.out.println("Send money to: " + recipientAddress.toString());


        // coins to send

        // user input is passed to parseCoin which makes it a readable form
        String amount = etAmountToSend.getText().toString();
   //     Coin coinAmount = Coin.parseCoin(amount);
        Log.d(TAG, "" + amount);

        // hard coded
//        Coin value = Coin.parseCoin("0.09");
//        Log.d(TAG, "sending 0.09");



        // check the user enters an amount greater than 0
        // check the user has enough coins


        // try to send the coins
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSendBitcoin) {
           // call a send transaction method
            Toast.makeText(getActivity(), "Bitcoin sent ", Toast.LENGTH_SHORT).show();
        }
        if (view.getId() == R.id.btnScanQR){
            // call a method that will scan QR code
        }
    }
}