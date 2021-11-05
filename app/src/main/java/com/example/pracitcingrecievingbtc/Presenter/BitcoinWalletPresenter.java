package com.example.pracitcingrecievingbtc.Presenter;

import android.content.Context; // connection between Android System and App Project https://www.geeksforgeeks.org/what-is-context-in-android/
import android.graphics.Bitmap;
import android.util.Log; // helps debugging by printing statements in the logcat
import com.example.pracitcingrecievingbtc.Contracts.Contract;
import com.google.common.base.Joiner; // part of Guava, central to BitcoinJ, appends results ie., skips spaces and returns a string

import org.bitcoinj.core.*; // contains classes for network messages like Block and Transaction, peer connectivity via PeerGroup, and block chain management
import org.bitcoinj.core.listeners.BlocksDownloadedEventListener; // listen to blocks being downloaded
import org.bitcoinj.core.listeners.PeerDisconnectedEventListener; // listen to peer disconnections
import org.bitcoinj.core.listeners.PeerDiscoveredEventListener; // list to peer connections
import org.bitcoinj.net.discovery.DnsDiscovery; // supports peer discovery through DNS

import org.bitcoinj.params.TestNet3Params; // testing network for blockchain developers
import org.bitcoinj.script.Script;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;

import org.bitcoinj.store.SPVBlockStore;
import org.bitcoinj.utils.BriefLogFormatter;
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

public class BitcoinWalletPresenter implements Contract.Presenter {

    private static final String TAG = BitcoinWalletPresenter.class.getSimpleName();
    private NetworkParameters networkParams;
    private File walletFile;
    private File spvBlockChainFile;
    private Wallet myWallet;
    private PeerGroup peerGroup; // tries to maintain a constant num of connections to a set of distinct peers
    private Context context;
    private int peerCount;
    private Script.ScriptType outputScriptType;
    private Contract.View view;


    // passing the connection between the project and android app
    public BitcoinWalletPresenter(Context context) {

        // Activating BitcoinJ's logging using a Java logging formatter that writes more compact output than default
        BriefLogFormatter.init();
        this.view = view;
        this.context = context;
        networkParams = TestNet3Params.get(); // request testnet
        walletFile = new File(context.getFilesDir(), "TestWallet.wallet");
        spvBlockChainFile = new File(this.context.getFilesDir(), "TestSPV.dat"); // a node on blockchain stores headers
        System.out.println("setting up test network");
        myWallet = initialisingWallet(); // create or load wallet
        System.out.println("Now we are going to create or load a wallet");
        initWalletFromNetwork(); // syncing the blockchain
    }

    // A wallet "knows how to find and save transactions relevant to a set of keys or scripts, calculate balances, and spend money"
    public Wallet initialisingWallet() {
        Wallet myWallet;
        // checking if a wallet file exists
        if (walletFile.exists()) {
            myWallet = loadingWallet(); // exists so load it
            Log.d(TAG, "we've loaded the wallet");
        } else {
            myWallet = creatingWallet(); // doesn't exist so create it
            Log.d(TAG, "we've created the wallet");
        }
        // the wallet must be autosaved
        myWallet.autosaveToFile(walletFile, 200, TimeUnit.MILLISECONDS, null); // saving the file by passing its name and what it includes
        // not sure getKeyChainGroupSize() is the right method come back to this****
        Log.d(TAG, "A wallet " + walletFile.getName() + "with " + myWallet.getKeyChainGroupSize() + " keys.");
        Log.d(TAG, "The contents of the wallet include " + myWallet);
        return myWallet;
    }

    // once a wallet has been created, it is stored locally
    public Wallet loadingWallet() {
        System.out.println("load wallet");
        Wallet loadedWallet = null; // intelliji underlines reassigned local variables
        try {
            Log.d(TAG, "the current wallet file: " + walletFile.getName() + " has a size " + walletFile.length() + " bytes");
            loadedWallet = Wallet.loadFromFile(walletFile);
            Log.d(TAG, "Loaded wallet file from disk");
            System.out.println(loadedWallet.getIssuedReceiveAddresses()); // printed wallet address
        } catch (UnreadableWalletException e) {
            Log.e(TAG, "Could not parse existing wallet");
            e.printStackTrace();
        }
        return loadedWallet;
    }


    public Wallet creatingWallet() {
        Log.d(TAG, "Creating a new wallet");
        Wallet createdWallet = null;
        createdWallet = Wallet.createDeterministic(networkParams, outputScriptType); // ****** WRONG
        createdWallet.setDescription("Project Wallet Test");
        try {
            createdWallet.saveToFile(walletFile);
            Log.d(TAG, "Created new wallet ");
            // add some UI notification
        } catch (IOException e) {
            Log.e(TAG, "Could not save wallet ");
            e.printStackTrace();
        }
        // the seeds and mnemonic codes return keys and addresses derived deterministically from a seed
        //using the algorithms laid out in BIP 32 and BIP 39 (a standard uses when defining 12 words mnemonic code
        // from https://bitcoinj.org/working-with-the-wallet
        DeterministicSeed seed = createdWallet.getKeyChainSeed();
        String recoverySeedWords = Joiner.on(" ").join(seed.getMnemonicCode());
        long seedBirthday = seed.getCreationTimeSeconds();

        Log.d(TAG, "The recovery seed birthday is " + seedBirthday);
        Log.d(TAG, "The recovery seed words include " + recoverySeedWords);
        return createdWallet;
    }

    public void initWalletFromNetwork() {

        BlockStore blockStore;
        BlockChain chain = null; // declare an object to sore and understand blockchain

        // SPV is specific to Bitcoin it allows a user to verify that the transaction has been included in the blockchain without downloading the entire blockchain
        if (spvBlockChainFile.exists()) {
            Log.d(TAG, "An existing blockchain file has been located and is of size " + spvBlockChainFile.length() + " bytes");
        } else {
            Log.d(TAG, "No existing blockchain data found it may take a while to scan the blockchain ledger");
        }
        try {
           // blockStore = new MemoryBlockStore(networkParams);
            blockStore = new SPVBlockStore(networkParams, spvBlockChainFile);
            chain = new BlockChain(networkParams, this.myWallet, blockStore);
          //  chain = new BlockChain(networkParams, blockStore);

            //  best known height of the chain, short for getChainHead().getHeight()
            // NOT SURE IF THIS SHOULD BE 0, need to look into this more
            Log.d(TAG, "Known blockchain height: " + chain.getBestChainHeight());
        } catch (BlockStoreException e) {
            e.printStackTrace();
        }
        //"Creates a PeerGroup for the given context and chain. Blocks will be passed to the chain as they are broadcast and downloaded.
        // This is probably the constructor you want to use.
        // https://bitcoinj.org/javadoc/0.14/org/bitcoinj/core/PeerGroup.html#PeerGroup-org.bitcoinj.core.Context-org.bitcoinj.core.AbstractBlockChain-"
        peerGroup = new PeerGroup(networkParams, chain);

        // registering a listener that is invoked when blocks are downloaded
        peerGroup.addBlocksDownloadedEventListener(new BlocksDownloadedEventListener() {
            @Override
            public void onBlocksDownloaded(Peer peer, Block block, @Nullable FilteredBlock filteredBlock, int blocksLeft) {
                Log.d(TAG, "Peer address from block is " + peer.getAddress());
            }
        });

        // listening to events of peers being discovered
        peerGroup.addDiscoveredEventListener(new PeerDiscoveredEventListener() {
            @Override
            // peers are nodes on the network
            public void onPeersDiscovered(Set<PeerAddress> peerAddresses) {
                Log.d(TAG, "There are currently " + peerCount + " peers. The new peer is " + peerAddresses);
            }
        });

        // listening to events and indicating when a peer disconnects
        peerGroup.addDisconnectedEventListener(new PeerDisconnectedEventListener() {
            @Override
            public void onPeerDisconnected(Peer peer, int peerCount) {
                Log.d(TAG, "There are currently " + peerCount + " peers connected. The peer lost is " + peer.getAddress());
            }
        });

        // "A PeerAddress holds an IP address and port number representing the network location
        // of a peer in the Bitcoin P2P network. It exists primarily for serialization purposes.
        //Instances of this class are not safe for use by multiple threads."
        // https://bitcoinj.org/javadoc/0.15/org/bitcoinj/core/PeerAddress.html

        // IP address
        InetAddress localPeer = null;
        try {
            // I put in my own IP address here, but I think this may be wrong
            // Need to work out which IP address goes there
            localPeer = InetAddress.getByName("192.168.1.66"); // return an instance of local host
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // https://bitcoinj.org/javadoc/0.15.10/org/bitcoinj/core/PeerGroup.html#addAddress-java.net.InetAddress-
        // short hand adding peer address
        peerGroup.addAddress(localPeer);
        peerGroup.addPeerDiscovery(new DnsDiscovery(networkParams));
        peerGroup.addWallet(myWallet);
        peerGroup.startAsync();
        peerGroup.downloadBlockChain();

        this.myWallet.addCoinsReceivedEventListener(new WalletCoinsReceivedEventListener() {
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

