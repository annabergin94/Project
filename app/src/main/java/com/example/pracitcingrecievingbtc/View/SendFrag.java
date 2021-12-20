package com.example.pracitcingrecievingbtc.View;

import android.os.Bundle;
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


public class SendFrag extends Fragment implements View.OnClickListener {

    private static final String TAG = SendFrag.class.getSimpleName();
    EditText etAmountToSend; // user to enter amount to send
    EditText etRecipientAddress; // user to enter recipient address
    ImageButton btnSendBitcoin; // send bitcoin button
    TextView tvAvailableBalance; // displays balance
    BtcFormat f = BtcFormat.getCoinInstance(); // format balance

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
        // using the BTC format class to display the Bitcoin in a format that is easy for the user to understand
        String out = f.format(((MainActivity) this.getActivity()).getSetUp().getAvailableBalance(), 2, 3, 3) + " BTC available";
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
        if (view.getId() == R.id.btnSendBitcoin) {
            sendTransaction();
        }
    }


    public void sendTransaction() {
        //  storing the users amount and address input as String variables
        String address = etRecipientAddress.getText().toString();
        String amount = etAmountToSend.getText().toString();
        // passing the double amount entered by the user to the
        Coin coinAmount = Coin.parseCoin(amount);

        // if the address is 42 (which is set as the max length in the xml) and the amount is positive or 0
        if (etRecipientAddress.length() == 42 && coinAmount.isPositive() || etRecipientAddress.length() == 42 && coinAmount.equals(0)) {
            // notify the user the address must be less than 42 characters
            // ---> in the future it would be good to implement code that checked if the address was actually a real Bitcoin address
            String toastMessage = "Addresses must be less than 42 characters!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText((this.getActivity()).getApplicationContext(), toastMessage, duration);
            toast.show();
        }
        // if the user enters a positive amount to send and enters an address less than 42 characters
        else if (coinAmount.isPositive() && etRecipientAddress.length() < 42) {
            // update the edit text so it goes back to 0
            etAmountToSend.setText("0.0");
            // notify the user that the transaction is being sent
            CharSequence toastMessage = "Sending your transaction!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText((this.getActivity()).getApplicationContext(), toastMessage, duration);
            toast.show();
            // send the transaction, catching the error if it doesn't work
            try {
                ((MainActivity) this.getActivity()).getSetUp().send(address, amount);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // transactions been broadcast to the network, notify the user
            toastMessage = "Transaction complete!";
            toast = Toast.makeText(this.getActivity().getApplicationContext(), toastMessage, duration);
            toast.show();
        }
        // if the user doesn't enter an amount or address, or the address is too long
        else {
            if (etRecipientAddress.length() == 42 || etRecipientAddress.length() == 0) {
                CharSequence text = "Amount must be greater than 0 and address less than 42 characters!";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(this.getActivity().getApplicationContext(), text, duration);
                toast.show();
            } else {
                // the address is less than 42 chars long
                CharSequence text = "Amount must be greater than 0!";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(this.getActivity().getApplicationContext(), text, duration);
                toast.show();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
