package com.example.pracitcingrecievingbtc.View;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pracitcingrecievingbtc.R;

public class ReceiveBitcoinFragment extends Fragment {

    TextView tvAmountReceived;
    TextView tvSenderAddress;

    private static final String TAG = ReceiveBitcoinFragment.class.getSimpleName();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receive_bitcoin, container, false);

        // instantiate the entered amount to send
        tvAmountReceived = view.findViewById(R.id.etAmountToSend);
        tvAmountReceived.setText(amountBeingSent);

        // instantiate the entered recipient address
        tvSenderAddress = view.findViewById(R.id.etRecipientAddress);
        tvSenderAddress.setText(recipientAddress.trim());

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

}