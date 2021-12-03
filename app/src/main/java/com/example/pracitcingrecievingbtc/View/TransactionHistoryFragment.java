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
import org.bitcoinj.utils.BtcFormat;

import java.util.List;

public class TransactionHistoryFragment extends Fragment {

    TextView tvAvailableBalance;
    TextView tvRecentTransactions;
    BtcFormat f = BtcFormat.getInstance(); // format balance
    String transactionList = "";

    private static final String TAG = TransactionHistoryFragment.class.getSimpleName();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receive_bitcoin, container, false);

        // instantiate the entered amount to send
        tvAvailableBalance = view.findViewById(R.id.tvAvailableBalance);
        String out = f.format(((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getBalanceEstimated(),2,3,3) + " BTC";
        tvAvailableBalance.setText(out);


       transactionHistory();
        tvRecentTransactions = view.findViewById(R.id.tvRecentTransactions);
        tvRecentTransactions.setText(transactionList);

        return view;
    }

    public void transactionHistory(){
        int id = 0;
        // get the recent transactions from the wallet
        List<Transaction> recentTransactions = ((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getRecentTransactions();
        // for each of the recent transactions add them to the string variable transactionList to print
        for (Transaction transaction : recentTransactions ) {
            transactionList = transactionList + "Transaction id: " + transaction.getTxId().toString().substring(0,4) + "/t" + "Sent: " + transaction.getOutputs() + "     Time: " + transaction.getUpdateTime() + "\n";// print first five
        }
    }

//    public void printingTransactionHistory(){
//        for(Transaction transaction : transactionList){
//
//        }
//    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}