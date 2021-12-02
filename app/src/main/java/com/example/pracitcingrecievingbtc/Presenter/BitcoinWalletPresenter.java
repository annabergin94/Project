package com.example.pracitcingrecievingbtc.Presenter;

import android.content.Context; // connection between Android System and App Project https://www.geeksforgeeks.org/what-is-context-in-android/
import android.util.Log; // helps debugging by printing statements in the logcat
import com.example.pracitcingrecievingbtc.Contracts.Contract;
import com.google.common.base.Joiner; // part of Guava, central to BitcoinJ, appends results ie., skips spaces and returns a string
import org.bitcoinj.core.*; // contains classes for network messages like Block and Transaction, peer connectivity via PeerGroup, and block chain management
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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BitcoinWalletPresenter implements Contract.Presenter {

    private static final String TAG = BitcoinWalletPresenter.class.getSimpleName();
    private NetworkParameters networkParams;
    private File myWalletFile;
    private File blockchainFileSPVMode;
    private Wallet myWallet;
    private static PeerGroup groupOfDistinctPeers; // tries to maintain a constant num of connections to a set of distinct peers
    private Context context;
    private int peerCount;
    private Script.ScriptType outputScriptType;
    private Contract.View view;
    BlockStore blockStore; // to load blocks
    BlockChain chain = null; // will be used to implement the SPV mode of the Bitcoin protocol
    // can verify transactions without downloading the whole blockchain, just the headers


    public BitcoinWalletPresenter(Context context) {
        BriefLogFormatter.init(); // Activating BitcoinJ's logging using a Java logging formatter that writes more compact output than default
        this.view = view;
        this.context = context;
        settingUpNetworkAndFiles(); // connecting to testnet, creating a local file for the wallet and blockchain
        myWallet = initialisingWallet(); // create or load wallet
        myWalletInitialisedFromNetwork(); // syncing the blockchain
    }

    // a helper method for connecting to the test net, creating a file for the wallet and blockchain locally
    public void settingUpNetworkAndFiles(){
        networkParams = TestNet3Params.get(); // request testnet
        myWalletFile = new File(context.getFilesDir(), "TestWallet.wallet");
        blockchainFileSPVMode = new File(this.context.getFilesDir(), "TestSPV.dat"); // a node on blockchain stores headers

    }

    public Wallet initialisingWallet() {
        Wallet myWallet;
        if (myWalletFile.exists()) {
            myWallet = loadingWallet();
            Log.d(TAG, "Wallet exists, loading the wallet");
        } else {
            myWallet = creatingWallet();
            Log.d(TAG, "Wallet doesn't exist, creating it");
        }
        // the wallet must be autosaved
        myWallet.autosaveToFile(myWalletFile, 200, TimeUnit.MILLISECONDS, null); // saving the file by passing its name and what it includes
        Log.d(TAG, "A wallet " + myWalletFile.getName() + "with " + myWallet.getKeyChainGroupSize() + " keys.");
        Log.d(TAG, "My wallet includes the following contents " + myWallet);
        return myWallet;
    }

    public Wallet creatingWallet() {
        Log.d(TAG, "Creating a new wallet");
        Wallet createdWallet = null;
        // creating an empty wallet with a randomly chosen seed and no transactions keys will be derived from the seed
        createdWallet = Wallet.createDeterministic(networkParams, outputScriptType);
        createdWallet.setDescription("Project testing wallet");
        System.out.println(createdWallet.getIssuedReceiveAddresses()); // returns the keys issued
        try {
            createdWallet.saveToFile(myWalletFile);
            Log.d(TAG, "Created new wallet ");
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

    // once a wallet has been created, it is stored locally
    public Wallet loadingWallet() {
        Log.d(TAG, "Loading wallet");
        Wallet loadedWallet = null;
        try {
            Log.d(TAG, "the current wallet file: " + myWalletFile.getName() + " has a size " + myWalletFile.length() + " bytes");
            loadedWallet = Wallet.loadFromFile(myWalletFile);
            // add listeners to receive bitcoin and send bitcoin
            setupWalletListeners(loadedWallet);
            Log.d(TAG, "Loaded wallet file from disk");
            // wallet address is the hashed version of the public key, like an e-mail which is used to receive funds
            Log.d(TAG, "Current wallet address is: " + loadedWallet.currentReceiveAddress());
            Log.d(TAG, "Current wallet balance is: " + loadedWallet.getBalance());
        } catch (UnreadableWalletException e) {
            Log.e(TAG, "Sorry could not read wallet");
            e.printStackTrace();
        }
        return loadedWallet;
    }

    public void myWalletInitialisedFromNetwork() {
        checkingIfBlockchainSPVFileExists();
        downloadingTheBlockchainInSPVMode();
        downloadingPeerListeners();
    }

    // a helper method to check if a blockchain file already exists
    public void checkingIfBlockchainSPVFileExists(){
        if (blockchainFileSPVMode.exists()) {
            Log.d(TAG, "An existing blockchain has been found. It is " + blockchainFileSPVMode.length() + " bytes");
        } else {
            Log.d(TAG, "We cannot find any blockchain file. The sync will begin now and may take 1 hour.");
        }
    }

    // a helper method to download the blockchain
    public void downloadingTheBlockchainInSPVMode(){
        try {
            blockStore = new SPVBlockStore(networkParams, blockchainFileSPVMode);
            chain = new BlockChain(networkParams, this.myWallet, blockStore);
            Log.d(TAG, "The blockchain is at a height: " + chain.getBestChainHeight());
        } catch (BlockStoreException e) {
            e.printStackTrace();
        }
    }

    // a helper method to print wallet address
    public String printMyWalletAddress(){
        return myWallet.currentReceiveAddress().toString();
    }

    // a helper method to
    public void downloadingPeerListeners(){
        // BitcoinJ recommends using this constructor to create a PeerGroup for a given context and chain.
        // Blocks will be passed to the chain as they are broadcast and downloaded.
        groupOfDistinctPeers = new PeerGroup(networkParams, chain);
        // registering a listener that is invoked when blocks are downloaded
        groupOfDistinctPeers.addBlocksDownloadedEventListener((peer, block, filteredBlock, blocksLeft) ->
                Log.d(TAG, "The address of the peer downloaded is " + peer.getAddress()));
        // listening to events of peers being discovered peers are nodes on the network
        groupOfDistinctPeers.addDiscoveredEventListener(peerAddresses ->
                Log.d(TAG, "There are currently " + peerCount + " peers. The new peer is " + peerAddresses));
        // listening to events and indicating when a peer disconnects
        groupOfDistinctPeers.addDisconnectedEventListener((peer, peerCount) ->
                Log.d(TAG, "There are currently " + peerCount + " peers connected. The peer lost is " + peer.getAddress()));
        groupOfDistinctPeers.addPeerDiscovery(new DnsDiscovery(networkParams)); // look for peers
        groupOfDistinctPeers.setMaxConnections(10); // setting max num of peers to connect too, but need 8 to broadcast a transaction to network
        groupOfDistinctPeers.addWallet(myWallet); // adding wallet with keys before downloading the blockchain to make sure relevant parts are downloaded (if add wallet keys earlier than the current chain head, the relevant parts of the chain won't be downloaded
        groupOfDistinctPeers.startAsync(); // start syncing blockchain
        groupOfDistinctPeers.downloadBlockChain();
        groupOfDistinctPeers.stopAsync(); // stop syncing the blockchain

    }

    public PeerGroup getGroupOfDistinctPeers() {
        return groupOfDistinctPeers;
    }

    // recommend disclosing these to user
    public String getMnemonic() {
        DeterministicSeed seed = myWallet.getKeyChainSeed();
        String recoverySeedWords = Joiner.on(" ").join(seed.getMnemonicCode());
        Log.d(TAG, "Recovery Seed words are: " + recoverySeedWords);
        return recoverySeedWords;
    }

    // listens for coins sent to the user's wallet
    private void setupWalletListeners(Wallet wallet) {
        wallet.addCoinsReceivedEventListener((wallet1, tx, prevBalance, newBalance) -> {
            view.displayMyBalance(wallet.getBalance().toFriendlyString());
            Log.d(TAG, "The wallet receives coins");
            Log.d(TAG, "The transaction id is " + tx.getTxId().toString());
            Log.d(TAG, "Previous wallet balance: " + prevBalance);
            Log.d(TAG, "New wallet balance" + newBalance);
            Log.d(TAG, tx.toString());
        });
    }

    public void send(String address, String amount) throws Exception {

        Address to = Address.fromString(networkParams, address);
   ///     LegacyAddress to = LegacyAddress.fromBase58(networkParams, Hex.decode(address).toString());
        Log.d(TAG, "Send money to: " + to);
        Coin value = Coin.parseCoin(amount);
        Log.d(TAG, "Send " + amount + "coins" + to);
        try {
            Wallet.SendResult result = myWallet.sendCoins(groupOfDistinctPeers, to, value);
            Log.d(TAG, "coins sent");
            myWallet.saveToFile(myWalletFile);
            Log.d(TAG, "save to wallet");
            result.broadcastComplete.get();
            Log.d(TAG, "broadcast transaction");
        } catch (InsufficientMoneyException e) {
            Log.e(TAG, "wallet does not contain enough to send this amount");
            e.printStackTrace();
        }
    }

    public Coin getBalanceEstimated() {
        return myWallet.getBalance(Wallet.BalanceType.ESTIMATED);
    }

    public List<Transaction> getRecentTransactions() {
        return myWallet.getTransactionsByTime();
    }
}




