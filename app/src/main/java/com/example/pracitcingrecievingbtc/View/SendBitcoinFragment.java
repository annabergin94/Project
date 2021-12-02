package com.example.pracitcingrecievingbtc.View;

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
import org.bitcoinj.core.Coin;
import org.bitcoinj.utils.BtcFormat;


public class SendBitcoinFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = SendBitcoinFragment.class.getSimpleName();
    EditText etAmountToSend; // user to enter amount to send
    EditText etRecipientAddress; // user to enter recipient address
    Button btnSendBitcoin; // send bitcoin button
    TextView tvAvailableBalance;
    BtcFormat f = BtcFormat.getInstance(); // format balance

    String amountBeingSent = "0.0";
    String recipientAddress = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragement_send_bitcoin, container, false);

        // instantiate the entered amount to send
        etAmountToSend = view.findViewById(R.id.etAmountToSend);
        etAmountToSend.setText(amountBeingSent);

        // instantiate the entered recipient address
        etRecipientAddress = view.findViewById(R.id.etRecipientAddress);
        etRecipientAddress.setText(recipientAddress.trim());

        // view. used to call the id because we are in a fragment
        tvAvailableBalance = view.findViewById(R.id.tvAvailableBalance);
        String out = f.format(((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getBalanceEstimated(),2,3,3) + " BTC";
        tvAvailableBalance.setText(out);

        // instantiate button
        btnSendBitcoin = view.findViewById(R.id.btnSendBitcoin);
        btnSendBitcoin.setOnClickListener(this);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnSendBitcoin) {
           sendTransaction();
            }
        }


    public void sendTransaction()  {

        String address = etRecipientAddress.getText().toString();
        String amount = etAmountToSend.getText().toString();
        Coin coinAmount = Coin.parseCoin(amount);

        if (coinAmount.isPositive()) {
            Log.d(TAG, "updating amountEdit textview");
            etAmountToSend.setText("0.0");
            Log.d(TAG, "sending transaction start Toast to UI");
            CharSequence text = "Sending your transaction";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText((this.getActivity()).getApplicationContext(), text, duration);
            toast.show();
            // performing transaction
            Log.d(TAG, "invoking sendTransactions on btcService");
            try {
               ((MainActivity) this.getActivity()).getBitcoinWalletPresenter().send(address, amount);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d(TAG, "sending update complete Toast to UI");
            text = "Sending transaction complete";
            toast = Toast.makeText(this.getActivity().getApplicationContext(), text, duration);
            toast.show();
        }
        else {
            Log.d(TAG, "non positive amount specified to send ");
            CharSequence text = "amount must be positive";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(this.getActivity().getApplicationContext(), text, duration);
            toast.show();
        }
    }
}