package com.example.pracitcingrecievingbtc;

import android.content.Context; // connection between Android System and App Project https://www.geeksforgeeks.org/what-is-context-in-android/
import android.icu.lang.UScript;
import android.util.Log;
import android.widget.Toast;  // short notifications
import com.google.common.base.Joiner;

import org.bitcoinj.core.*;
import org.bitcoinj.core.listeners.BlocksDownloadedEventListener;
import org.bitcoinj.core.listeners.PeerDisconnectedEventListener;
import org.bitcoinj.core.listeners.PeerDiscoveredEventListener;
import org.bitcoinj.net.discovery.DnsDiscovery;

import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.SPVBlockStore;

import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.bitcoinj.script.Script.*;

public class BitcoinExample {

    private static final String TAG = BitcoinExample.class.getSimpleName();
    NetworkParameters networkParams;
    File walletFile;
    File spvBlockChainFile;
    Wallet wallet;
    PeerGroup peerGroup;
    Context context;
    int peerCount;

    // passing the connection between the project and android app
    public BitcoinExample(Context context) {
        this.context = context;
        networkParams = TestNet3Params.get(); // request testnet
        walletFile = new File(context.getFilesDir(), "TestWallet.wallet");
        spvBlockChainFile = new File(this.context.getFilesDir(), "TestSPV.dat"); // a node on blockchain stores headers
        System.out.println("setting up test network");
        wallet = initWallet(); // create or load wallet
        System.out.println("Now we are going to create or load a wallet");
        initWalletFromNetwork(); // syncing the blockchain
    }

    public Wallet initWallet() {
        Wallet myWallet;
        // if wallet file exists then load it otherwise create it
        if(walletFile.exists()){
            myWallet = loadWallet();
        } else {
            myWallet = createWallet();
        }
        System.out.println("Now we have loaded or created the wallet");
        System.out.println("Next we save the file and state its name and what it includes");

        // specify this wallet should be autosaved as needed
        myWallet.autosaveToFile(walletFile, 200, TimeUnit.MILLISECONDS, null);
        Log.d(TAG, "wallet file name: " + walletFile.getName());
            Log.d(TAG, "Number of keys in wallet: " + myWallet.getKeyChainGroupSize());
        Log.d(TAG, "wallet contents: " + myWallet);

        return myWallet;
    }

    public Wallet loadWallet() {
        System.out.println("load wallet");
        Wallet loadedWallet = null;
        try {
            Log.d(TAG, "existing wallet file name: " + walletFile.getName());
            Log.d(TAG, "existing wallet file size " + walletFile.length() + "bytes");
            loadedWallet = Wallet.loadFromFile(walletFile);
            Log.d(TAG, "Loaded wallet file from disk");
            CharSequence text = "Loaded wallet";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } catch (UnreadableWalletException e) {
            Log.e(TAG, "Could not parse existing wallet");
            e.printStackTrace();
        }
        return loadedWallet;
    }

    public Wallet createWallet() {
        System.out.println("create wallet");

        // NOT SURE WHERE THE SCRIPT COMES FROM BUT ITS MEANT OT BE
        Wallet createdWallet = Wallet.createDeterministic(networkParams, ScriptType);
        createdWallet.setDescription("Testing Example Code");
        try {
            createdWallet.saveToFile(walletFile);
            Log.d(TAG, "Created new wallet");
            CharSequence text = "Created new wallet";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } catch (IOException e) {
            Log.e(TAG, "Could not save wallet");
            e.printStackTrace();
        }

        // Recovery information
        DeterministicSeed seed = createdWallet.getKeyChainSeed();
        String recoverySeedWords = Joiner.on(" ").join(seed.getMnemonicCode());
        Log.d(TAG, "Recovery Seed words are: " + recoverySeedWords);
        Log.d(TAG, "Recovery Seed birthday is: " + seed.getCreationTimeSeconds());
//        createNotification("Please back up your new wallet",
//                "Touch to view recovery mnemonic",
//                "WalletRecoveryFragment");

        return createdWallet;
    }

    public void initWalletFromNetwork() {

        BlockStore blockStore = null;
        BlockChain chain = null;

        if (spvBlockChainFile.exists()) {
            Log.d(TAG, "Found existing blockchain file of size " + spvBlockChainFile.length() + "bytes");
        } else {
            Log.d(TAG, "No existing blockchain data found it may take a while to scan the blockchain ledger");
        }
        try {
            blockStore = new SPVBlockStore(networkParams, spvBlockChainFile);
            chain = new BlockChain(networkParams, this.wallet, blockStore);
            Log.d(TAG, "Known blockchain height: " + chain.getBestChainHeight());
        } catch (BlockStoreException e) {
            e.printStackTrace();
        }

        PeerGroup peerGroup = new PeerGroup(networkParams, chain);
        peerGroup.addBlocksDownloadedEventListener(new BlocksDownloadedEventListener() {
            @Override
            public void onBlocksDownloaded(Peer peer, Block block, @Nullable FilteredBlock filteredBlock, int blocksLeft) {
                Log.d(TAG, "Received block from " + peer.getAddress());
            }
        });

        peerGroup.addDiscoveredEventListener(new PeerDiscoveredEventListener() {
            @Override
            public void onPeersDiscovered(Set<PeerAddress> peerAddresses) {
                Log.d(TAG, peerCount + " peers, new peer: " + peerAddresses);
            }
        });

        peerGroup.addDisconnectedEventListener(new PeerDisconnectedEventListener() {
            @Override
            public void onPeerDisconnected(Peer peer, int peerCount) {
                Log.d(TAG, peerCount + " peers, lost peer: " + peer.getAddress());
            }
        });

        InetAddress localPeer = null;
        try {
            localPeer = InetAddress.getByName("192.168.1.67");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        peerGroup.addAddress(localPeer);
        peerGroup.addPeerDiscovery(new DnsDiscovery(networkParams));
        peerGroup.startAsync();

        this.wallet.addCoinsReceivedEventListener(new WalletCoinsReceivedEventListener() {
            @Override
            public void onCoinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
                Log.d(TAG, "Wallet Event: onCoinsReceived");
                Log.d(TAG, "Wallet received tx " + tx.getTxId());
                Log.d(TAG, "Wallet previous balance " + prevBalance);
                Log.d(TAG, "Wallet new balance " + newBalance);
                Log.d(TAG, tx.toString());
            }
        });
    }
}

