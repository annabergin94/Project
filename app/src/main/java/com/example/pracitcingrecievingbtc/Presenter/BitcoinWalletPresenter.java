package com.example.pracitcingrecievingbtc.Presenter;

import android.content.Context; // connection between Android System and App Project https://www.geeksforgeeks.org/what-is-context-in-android/
import android.util.Log; // helps debugging by printing statements in the logcat
import com.example.pracitcingrecievingbtc.Contracts.Contract;
import com.google.common.base.Joiner; // part of Guava, central to BitcoinJ, appends results ie., skips spaces and returns a string

import org.bitcoinj.core.*; // contains classes for network messages like Block and Transaction, peer connectivity via PeerGroup, and block chain management
import org.bitcoinj.core.listeners.BlocksDownloadedEventListener; // listen to blocks being downloaded
import org.bitcoinj.core.listeners.PeerDisconnectedEventListener; // listen to peer disconnections
import org.bitcoinj.core.listeners.PeerDiscoveredEventListener; // list to peer connections
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.net.discovery.DnsDiscovery; // supports peer discovery through DNS

import org.bitcoinj.params.TestNet3Params; // testing network for blockchain developers
import org.bitcoinj.script.Script;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;

import org.bitcoinj.store.SPVBlockStore;
import org.bitcoinj.utils.BriefLogFormatter;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.WalletTransaction;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
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
    private ECKey key;



    public BitcoinWalletPresenter(Context context) {

        BriefLogFormatter.init(); // Activating BitcoinJ's logging using a Java logging formatter that writes more compact output than default
        this.view = view;
        this.context = context;
        networkParams = TestNet3Params.get(); // request testnet
        walletFile = new File(context.getFilesDir(), "TestWallet.wallet");
        spvBlockChainFile = new File(this.context.getFilesDir(), "TestSPV.dat"); // a node on blockchain stores headers
        System.out.println("setting up test network");
        myWallet = initialisingWallet(); // create or load wallet
        System.out.println("Now we are going to create or load a wallet");
        initWalletFromNetwork(); // syncing the blockchain
        //printWalletAddress();
    }

    public Wallet initialisingWallet() {
        Wallet myWallet;
        if (walletFile.exists()) {
            myWallet = loadingWallet(); // wallet exists, load it
            Log.d(TAG, "we've loaded the wallet");
        } else {
            myWallet = creatingWallet(); // wallet doesn't exist so create it
            Log.d(TAG, "we've created the wallet");
        }
        // the wallet must be autosaved
        myWallet.autosaveToFile(walletFile, 200, TimeUnit.MILLISECONDS, null); // saving the file by passing its name and what it includes
        Log.d(TAG, "A wallet " + walletFile.getName() + "with " + myWallet.getKeyChainGroupSize() + " keys.");
        Log.d(TAG, "The contents of the wallet include " + myWallet);
        return myWallet;
    }

    // once a wallet has been created, it is stored locally
    public Wallet loadingWallet() {
        System.out.println("load wallet");
        Wallet loadedWallet = null;
        try {
            Log.d(TAG, "the current wallet file: " + walletFile.getName() + " has a size " + walletFile.length() + " bytes");
            loadedWallet = Wallet.loadFromFile(walletFile);
            // add listeners to receive bitcoin and send bitcoin
            setupWalletListeners(loadedWallet);
            Log.d(TAG, "Loaded wallet file from disk");
            //         System.out.println(loadedWallet.getIssuedReceiveAddresses()); // printed wallet address
            // wallet address is the hashed version of the public key, like an e-mail which is used to receive funds
            Log.d(TAG, "Current wallet address is: " + loadedWallet.currentReceiveAddress());
            Log.d(TAG, "Current wallet balance is: " + loadedWallet.getBalance());
        } catch (UnreadableWalletException e) {
            Log.e(TAG, "Could not parse existing wallet");
            e.printStackTrace();
        }
        return loadedWallet;
    }

    public Wallet creatingWallet() {
        Log.d(TAG, "Creating a new wallet");
        Wallet createdWallet = null;
        // creating an empty wallet with a randomly chosen seed and no transactions keys will be derived from the seed
        createdWallet = Wallet.createDeterministic(networkParams, outputScriptType);
        createdWallet.setDescription("Project Wallet Test");
        System.out.println(createdWallet.getIssuedReceiveAddresses()); // returns the keys issued
        try {
            createdWallet.saveToFile(walletFile);
            Log.d(TAG, "Created new wallet ");
            // add some UI notification
        } catch (IOException e) {
            Log.e(TAG, "Could not save wallet ");
            e.printStackTrace();
        }
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
            blockStore = new SPVBlockStore(networkParams, spvBlockChainFile);
            chain = new BlockChain(networkParams, this.myWallet, blockStore);
            Log.d(TAG, "Known blockchain height: " + chain.getBestChainHeight());
        } catch (BlockStoreException e) {
            e.printStackTrace();
        }
        //"Creates a PeerGroup for the given context and chain. Blocks will be passed to the chain as they are broadcast and downloaded.
        // This is probably the constructor you want to use.
        // https://bitcoinj.org/javadoc/0.14/org/bitcoinj/core/PeerGroup.html#PeerGroup-org.bitcoinj.core.Context-org.bitcoinj.core.AbstractBlockChain-"
        peerGroup = new PeerGroup(networkParams, chain);
        // registering a listener that is invoked when blocks are downloaded
        peerGroup.addBlocksDownloadedEventListener((peer, block, filteredBlock, blocksLeft) ->
                Log.d(TAG, "Peer address from block is " + peer.getAddress()));
        // listening to events of peers being discovered peers are nodes on the network
        peerGroup.addDiscoveredEventListener(peerAddresses ->
                Log.d(TAG, "There are currently " + peerCount + " peers. The new peer is " + peerAddresses));
        // listening to events and indicating when a peer disconnects
        peerGroup.addDisconnectedEventListener((peer, peerCount) ->
                Log.d(TAG, "There are currently " + peerCount + " peers connected. The peer lost is " + peer.getAddress()));
        // "A PeerAddress holds an IP address and port number representing the network location
        // of a peer in the Bitcoin P2P network. It exists primarily for serialization purposes.
        //Instances of this class are not safe for use by multiple threads."
        // https://bitcoinj.org/javadoc/0.15/org/bitcoinj/core/PeerAddress.html
        // IP address
        InetAddress localPeer = null;
        try {
            localPeer = InetAddress.getByName("192.168.1.66"); // return an instance of local host
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        peerGroup.addAddress(localPeer);
        peerGroup.addPeerDiscovery(new DnsDiscovery(networkParams));
        peerGroup.setMaxConnections(10); // setting max num of peers to connect too
        peerGroup.addWallet(myWallet); // adding wallet with keys before downloading the blockchain to make sure relevant parts are downloaded (if add wallet keys earlier than the current chain head, the relevant parts of the chain won't be downloaded
        peerGroup.startAsync();
        peerGroup.downloadBlockChain();
        peerGroup.stopAsync();
    }

//    // printing the wallet addresses
//    public String printWalletAddress(){
//        String currentReceiveaddress =  myWallet.currentReceiveAddress().toString();
//        Log.d(TAG, "Key address on the TestNet blockchain is " + currentReceiveaddress); // 32 chars: mzg1ZfMDiaSFPLME11mGCCM6h7ivoJXPHA
//        ECKey currentReceiveKey = myWallet.currentReceiveKey();
//        String privateKey = currentReceiveKey.getPrivateKeyEncoded(networkParams).toBase58();
//        String publicKey = Utils.HEX.encode(currentReceiveKey.getPubKeyHash());
//        Log.d(TAG, "Private key is: " + privateKey); // 52 chars: cNKyvSXaiJYzHbNLnJJw7kd8XDvWTyuWJ3dXF2jacGMczKGaCmVL
//        Log.d(TAG, "Public key is: " + publicKey); // d220dcca07230dac35dfa9ea4f3683e407e0b004
//        return currentReceiveaddress;
//    }

    public String printMyWalletAddress(){
        String currentReceiveaddress =  myWallet.currentReceiveAddress().toString();
        return currentReceiveaddress;
    }

    // printing the wallet balance
    public String getBalance() {
        return myWallet.getBalance().toString();
    }

    // recommend disclosing these to user
    public String getMnemonic() {
        DeterministicSeed seed = myWallet.getKeyChainSeed();
        String recoverySeedWords = Joiner.on(" ").join(seed.getMnemonicCode());
        Log.d(TAG, "Recovery Seed words are: " + recoverySeedWords);
        return recoverySeedWords;
    }

    private void setupWalletListeners(Wallet wallet) {
        wallet.addCoinsReceivedEventListener((wallet1, tx, prevBalance, newBalance) -> {
            view.displayMyBalance(wallet.getBalance().toFriendlyString());
            Log.d(TAG, "HERE");
         //   if(tx.getPurpose() == Transaction.Purpose.UNKNOWN)
        //        view.showToastMessage("Receive " + newBalance.minus(prevBalance).toFriendlyString());
        });
        wallet.addCoinsSentEventListener((wallet12, tx, prevBalance, newBalance) -> {
        //    view.displayMyBalance(wallet.getBalance().toFriendlyString());
        //    view.clearAmount();
        //    view.displayRecipientAddress(null);
        //    view.showToastMessage("Sent " + prevBalance.minus(newBalance).minus(tx.getFee()).toFriendlyString());
        });
    }
}




