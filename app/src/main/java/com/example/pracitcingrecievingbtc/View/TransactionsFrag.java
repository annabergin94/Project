package com.example.pracitcingrecievingbtc.View;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pracitcingrecievingbtc.R;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionBag;

import java.util.List;

public class TransactionsFrag extends Fragment {

    TextView tvDisplayTransactions; // displaying the transactions on the UI
    String formattingTransactions = ""; // variable to print transactions
    private static final String TAG = TransactionsFrag.class.getSimpleName();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_history, container, false);
        transactionHistory(); // formatting transactions and storing in a String variable
        tvDisplayTransactions = view.findViewById(R.id.tvRecentTransactions); // initialising transactions
        tvDisplayTransactions.setText(formattingTransactions); // displaying the formatted transactions in the UI
        return view;
    }

    public void transactionHistory(){
        // a transaction bag of all transactions in my wallet
        TransactionBag tx= ((MainActivity)this.getActivity()).getSetUp().getMyWallet();
        // get the recent transactions from the wallet
        List<Transaction> recentTransactions = ((MainActivity)this.getActivity()).getSetUp().getTransactions();
        // counter, in future not hard coded
        int i = 99;
        // for each of the recent transactions add them to the string variable transactionList to print
        for (Transaction transaction : recentTransactions) {
            Log.d(TAG, "Storing formatted transactions into a String to be passed to setText() to display on UI");

            formattingTransactions = formattingTransactions +
                    " " + i + ". Date: " + transaction.getUpdateTime().toString().substring(4,10) + "\n" +
                    "      Change: " + transaction.getValueSentToMe(tx).toFriendlyString() + "\n" +
                    "      Amount sent: " + transaction.getValueSentFromMe(tx).toFriendlyString() + "\n" +
                    "-------------------------------------------------------------"
            + "\n";
            i--;
            //printing addresses for test
            // System.out.println("Address history: " + ((MainActivity)this.getActivity()).getSetUp().getAllWalletAddresses());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
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