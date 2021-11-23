package com.example.pracitcingrecievingbtc.Contracts;

import com.example.pracitcingrecievingbtc.Base.BasePresenter;
import com.example.pracitcingrecievingbtc.Base.BaseView;
import org.bitcoinj.core.listeners.BlocksDownloadedEventListener;
import org.bitcoinj.wallet.Wallet;

public interface Contract {

    interface View extends BaseView {

        void displayMyBalance(String myBalance);

    }

    interface Presenter extends BasePresenter {
        Wallet initialisingWallet();
        Wallet loadingWallet();
        Wallet creatingWallet();
        void myWalletInitialisedFromNetwork();
        String printMyWalletAddress();
    }
}
