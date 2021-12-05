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

    private static final String TAG = TransactionHistoryFragment.class.getSimpleName();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receive_bitcoin, container, false);

       transactionHistory();
        tvRecentTransactions = view.findViewById(R.id.tvRecentTransactions);
        tvRecentTransactions.setText(transactionList);

        return view;
    }

    public void transactionHistory(){
        TransactionBag tx= ((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getMyWallet();

        // get the recent transactions from the wallet
        List<Transaction> recentTransactions = ((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getRecentTransactions();
        // counter
        int i = 1;
        // for each of the recent transactions add them to the string variable transactionList to print
        for (Transaction transaction : recentTransactions) {
            long fee = (transaction.getInputSum().getValue() > 0 ? transaction.getInputSum().getValue() - transaction.getOutputSum().getValue() : 0);
            // storing the transactions in a string so that it can be passed to setText() to display on UI
            transactionList = transactionList +
                    " " + i + ". Date: " + transaction.getUpdateTime().toString().substring(4,10) + "\n" +
                    " Amount sent to me: " + transaction.getValueSentToMe(tx).toFriendlyString() + "\n" +
                    " Amount Sent from me: " + transaction.getValueSentFromMe(tx).toFriendlyString() + "\n" +
                    " Fee: " + Coin.valueOf(fee).toFriendlyString() + "\n" +
                    "---------------------------------------------------------------------" +
                    "\n";


            System.out.println("Date and Time: " + transaction.getUpdateTime().toString());
            System.out.println("Amount Sent to me: " + transaction.getValueSentToMe(tx).toFriendlyString());
            System.out.println("Amount Sent from me: " + transaction.getValueSentFromMe(tx).toFriendlyString());
        //    long fee = (transaction.getInputSum().getValue() > 0 ? transaction.getInputSum().getValue() - transaction.getOutputSum().getValue() : 0);
            System.out.println("Fee: " + Coin.valueOf(fee).toFriendlyString());
            System.out.println("Transaction Depth: " + transaction.getConfidence().getDepthInBlocks());
            System.out.println("Transaction Blocks: " + transaction.getConfidence().toString());
            System.out.println("Tx Hex: " + transaction.getTxId().toString());
            System.out.println("Tx: " + transaction.toString());
            i++;
        }
    }

//    public void transactionHistory(){
//        TransactionBag tx = ((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getMyWallet();
//        Coin valueSentToMe;
//
//        // get the recent transactions from the wallet
//        List<Transaction> recentTransactions = ((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getRecentTransactions();
//        // for each of the recent transactions add them to the string variable transactionList to print
//        for (Transaction transaction : recentTransactions) {
//            // storing the transactions in a string so that it can be passed to setText() to display on UI
//            transactionList = transactionList + transaction.getUpdateTime().toString().substring(4,10) + "\n" +
//                    transaction.getOutputs() +
//                    "\n" + " and remaining balance after fees is " + transaction.getInputs()
//                    + ")" + "\n";// print first five
//            Log.d(TAG, "id:   " + transaction.getTxId() + "\t" + transaction.getUpdateTime().toString().substring(4,17) +  "total value (sends   " + transaction.getValueSentFromMe(((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getMyWallet()) + " and receives " +  transaction.getValueSentToMe(((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getMyWallet()).toFriendlyString());
//        }
//    }


//    public void transactionHistory(){
//        TransactionBag tx = ((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getMyWallet();
//        Coin valueSentToMe;
//
//        // get the recent transactions from the wallet
//        List<Transaction> recentTransactions = ((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getRecentTransactions();
//        // for each of the recent transactions add them to the string variable transactionList to print
//        for (Transaction transaction : recentTransactions) {
//            // storing the transactions in a string so that it can be passed to setText() to display on UI
//            transactionList = transactionList + transaction.getUpdateTime().toString().substring(4,10) + "\n" +
//                    "total value " + transaction.getValue(tx).toFriendlyString() + "\n" +
//                    " balance before sends (" + transaction.getValueSentFromMe(tx).toFriendlyString() +
//                    "\n" + " and remaining balance after fees is " + transaction.getValueSentToMe(tx).toFriendlyString()
//                    + ")" + "\n";// print first five
//            Log.d(TAG, "id:   " + transaction.getTxId() + "\t" + transaction.getUpdateTime().toString().substring(4,17) +  "total value (sends   " + transaction.getValueSentFromMe(((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getMyWallet()) + " and receives " +  transaction.getValueSentToMe(((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getMyWallet()).toFriendlyString());
//        }
//    }

//    public void storingBalances(){
//        Coin value = null;
//        String testlist = "";
//        TransactionBag tx = ((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getMyWallet();
//        List<Transaction> test = ((MainActivity)this.getActivity()).getBitcoinWalletPresenter().getRecentTransactions();
//        for (Transaction transactions : test) {
//            value = transactions.getValue(tx);
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