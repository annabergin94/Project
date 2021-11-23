package com.example.pracitcingrecievingbtc.View;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pracitcingrecievingbtc.R;

public class WalletRecoveryFragment extends Fragment {

    private static final String TAG = WalletRecoveryFragment.class.getSimpleName();
    public static Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_wallet_recovery, container, false);
        TextView mnemonicTextView = (TextView) view.findViewById(R.id.textViewRecoveryMnemonic);
        Log.d(TAG, "Displaying the mnemonic for the wallet");
        mnemonicTextView.setText(((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getMnemonic());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}