package com.example.pracitcingrecievingbtc.Contracts;

import com.example.pracitcingrecievingbtc.Base.BasePresenter;
import com.example.pracitcingrecievingbtc.Base.BaseView;
import org.bitcoinj.core.listeners.BlocksDownloadedEventListener;

public interface Contract {

    interface View extends BaseView {

        void displayMyBalance(String myBalance);
        void displayMyWalletAddress(String myAddress);
        void displayMyAddress(String myAddress);
        void refresh();

    }

    interface Presenter extends BasePresenter {
    }
}
