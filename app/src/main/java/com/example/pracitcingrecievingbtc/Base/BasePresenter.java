package com.example.pracitcingrecievingbtc.Base;

import org.bitcoinj.wallet.Wallet;

public interface BasePresenter {

    Wallet creatingWallet();
    Wallet loadingWallet();
    Wallet initialisingWallet();
    void myWalletInitialisedFromNetwork();

}
