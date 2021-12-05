package com.example.pracitcingrecievingbtc.View;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pracitcingrecievingbtc.R;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionBag;
import org.bitcoinj.utils.BtcFormat;

import java.util.List;

public class TransactionHistoryFragment extends Fragment {

    TextView tvAvailableBalance;
    TextView tvRecentTransactions;
    BtcFormat f = BtcFormat.getInstance(); // format balance
    String transactionList = "";
    Transaction transaction;

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
        TransactionBag tx = ((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getMyWallet();
        Coin valueSentToMe;

        // get the recent transactions from the wallet
        List<Transaction> recentTransactions = ((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getRecentTransactions();
        // for each of the recent transactions add them to the string variable transactionList to print
        for (Transaction transaction : recentTransactions ) {
            transactionList = transactionList + transaction.getUpdateTime().toString().substring(4,10) + "\n" +
                    "total value " + transaction.getValue(((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getMyWallet()).toFriendlyString() +
                    " sends (" + transaction.getValueSentFromMe(tx).toFriendlyString() +
                    "\n" + " and receives " + transaction.getValueSentToMe(tx).toFriendlyString()
                    + ")" + "\n";// print first five


            Log.d(TAG, "id:   " + transaction.getTxId() + "\t" + transaction.getUpdateTime().toString().substring(4,17) +  "total value (sends   " + transaction.getValueSentFromMe(((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getMyWallet()) + " and receives " +  transaction.getValueSentToMe(((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getMyWallet()).toFriendlyString());


            // TRANSACTION BAG
            // ((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getMyWallet())

            // don't work
//            Log.d(TAG, String.valueOf(transaction.getConfidence()));
// null doesn't work  Log.d(TAG, transaction.getExchangeRate().toString());
//            Log.d(TAG, transaction.getFee().toString());

            // not sure
       //     Log.d(TAG, transaction.getOutputs().toString());
      //      Log.d(TAG, transaction.getInputs().toString());
      //      Log.d(TAG, transaction.getUpdateTime().toString());
      //      Log.d(TAG, transaction.getPurpose().toString());
      //      Log.d(TAG, String.valueOf(transaction.getVersion()));
       //     Log.d(TAG, transaction.getWTxId().toString());
       //     Log.d(TAG, transaction.toString());

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