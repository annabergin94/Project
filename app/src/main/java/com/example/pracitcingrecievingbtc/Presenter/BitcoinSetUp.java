package com.example.pracitcingrecievingbtc.Presenter;

import android.content.Context; // connection between Android System and App Project https://www.geeksforgeeks.org/what-is-context-in-android/
import android.util.Log; // helps debugging by printing statements in the logcat
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
import java.util.concurrent.TimeUnit;



// this class performs is needed for the wallet
public class BitcoinSetUp {

    private static final String TAG = BitcoinSetUp.class.getSimpleName(); // debugging tag
    private NetworkParameters networkParams;
    private File myWalletFile;
    private File blockchainFileSPVMode;
    private Wallet myWallet;
    private static PeerGroup groupOfDistinctPeers; // tries to maintain a constant num of connections to a set of distinct peers
    private Context context;
    private int peerCount;
    private Script.ScriptType outputScriptType;
    BlockStore blockStore; // to load blocks
    BlockChain chain = null; // use to implement the SPV mode of the Bitcoin protocol
    // can verify transactions without downloading the whole blockchain, just the headers
    private Coin value;
    private Address to;
    private Wallet loadedWallet;


    // this all happens when the user opens the application, but before the Home UI is displayed
    public BitcoinSetUp(Context context) {
        // BitcoinJ's Java logging formatter to write more compact output in the terminal
        BriefLogFormatter.init();
        // android instantiate the context
        this.context = context;
        // connect to test network
        networkParams = TestNet3Params.get();
        // create local files for wallet and blockchain
        myWalletFile = new File(context.getFilesDir(), "TestWallet.wallet");
        blockchainFileSPVMode = new File(this.context.getFilesDir(), "TestSPV.dat");
        myWallet = initialisingWallet(); // checks if a wallet already exists and create/load the wallet
        myWalletInitialisedFromNetwork(); // syncing the blockchain
    }

    // initialising the wallet, loading it if it exists, creating it if it doesn't
    public Wallet initialisingWallet() {
        if (myWalletFile.exists()) {
            myWallet = loadingWallet(); // exists
        } else {
            myWallet = creatingWallet(); // doesn't exist
        }
        // saving the wallet
        myWallet.autosaveToFile(myWalletFile, 200, TimeUnit.MILLISECONDS, null); // saving the file by passing its name and what it includes
        Log.d(TAG, "A wallet " + myWalletFile.getName() + "exists with the following info: " + myWallet);
        return myWallet;
    }

    // creating an empty wallet with a randomly chosen seed (12 word phrase) and no transactions
    public Wallet creatingWallet() {
        Wallet createdWallet = Wallet.createDeterministic(networkParams, outputScriptType);
        createdWallet.setDescription("Softcoin wallet prototype");
        try {
            // save the wallet to the file created locally
            createdWallet.saveToFile(myWalletFile);
            Log.d(TAG, "A wallet has been created and saved locally!");
        } catch (IOException e) {
            Log.e(TAG, "Couldn't save the created wallet!");
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
        try {
            loadedWallet = Wallet.loadFromFile(myWalletFile);
            // add listeners to receive bitcoin and send bitcoin
            setupWalletListeners(loadedWallet);
            Log.d(TAG, "Loaded wallet file from disk");
            // wallet address is the hashed version of the public key, like an e-mail which is used to receive funds
            Log.d(TAG, "Current wallet address is: " + loadedWallet.currentReceiveAddress());
            Log.d(TAG, "The history of wallet addresses is: " + loadedWallet.getIssuedReceiveAddresses());
            Log.d(TAG, "Current wallet balance is: " + loadedWallet.getBalance());
        } catch (UnreadableWalletException e) {
            Log.e(TAG, "Sorry, we can't read the wallet. Please check if the file is corrupted.");
            e.printStackTrace();
        }
        return loadedWallet;
    }

    // a helper method that combines the helper methods used for implementing the Bitcoin protocol
    public void myWalletInitialisedFromNetwork() {
        checkingIfBlockchainSPVFileExists();
        downloadingTheBlockchainInSPVMode();
        downloadingPeerListeners();
    }

    // a helper method to check if a blockchain file already exists
    public void checkingIfBlockchainSPVFileExists(){
        if (blockchainFileSPVMode.exists()) {
            Log.d(TAG, "An blockchain file has been found!");
        } else {
            Log.d(TAG, "Sorry we can't find a blockchain file. Syncing will begin now and take around one and a half hours!");
        }
    }

    // a helper method to download the blockchain
    public void downloadingTheBlockchainInSPVMode(){
        try {
            blockStore = new SPVBlockStore(networkParams, blockchainFileSPVMode);
            chain = new BlockChain(networkParams, this.myWallet, blockStore);
        } catch (BlockStoreException e) {
            Log.d(TAG, "BlockStoreException, you might be out of disk space!");
            e.printStackTrace();
        }
    }

    // a helper method to sync the blockchain and connect it to the created wallet
    public void downloadingPeerListeners(){
        // BitcoinJ recommends using this constructor to create a PeerGroup for a given context and chain.
        // Blocks will be passed to the chain as they are broadcast and downloaded.
        groupOfDistinctPeers = new PeerGroup(networkParams, chain);
        // registering a listener that is invoked when blocks are downloaded
        groupOfDistinctPeers.addBlocksDownloadedEventListener((peer, block, filteredBlock, blocksLeft) ->
                Log.d(TAG, "Downloaded peers address: " + peer.getAddress()));
        // listening to events of peers being discovered peers are nodes on the network
        groupOfDistinctPeers.addDiscoveredEventListener(peerAddresses ->
                Log.d(TAG, "Number of peers connected = " + peerCount + " peers. New peer connected is " + peerAddresses));
        // listening to events and indicating when a peer disconnects
        groupOfDistinctPeers.addDisconnectedEventListener((peer, peerCount) ->
                Log.d(TAG, "Number of peers connected = " + peerCount + " peers connected. Peer disconnected is " + peer.getAddress()));
        // looking for peers
        groupOfDistinctPeers.addPeerDiscovery(new DnsDiscovery(networkParams));
        // setting max num of peers connections to 10, 8 needed to broadcast a transaction to network
        groupOfDistinctPeers.setMaxConnections(10);
        // adding wallet with keys before downloading the blockchain to make sure relevant parts are downloaded
        // (if wallet keys are added earlier than the current chain head, the relevant parts of the chain won't be downloaded)
        groupOfDistinctPeers.addWallet(myWallet);
        groupOfDistinctPeers.startAsync(); // syncs the blockchain in SPV mode (headers only)
        groupOfDistinctPeers.downloadBlockChain(); // prints the % of the blockchain downloaded
        groupOfDistinctPeers.stopAsync(); // stop syncing, blockchain downloaded
    }

    // listens for coins sent to the user's wallet
    private void setupWalletListeners(Wallet myWallet) {
        myWallet.addCoinsReceivedEventListener((wallet1, tx, prevBalance, newBalance) -> {
        });
    }

    // pass the recipient address and amount of Bitcoins which are user inputs from the Send UI
    public void send(String address, String amount) throws Exception {
        to = Address.fromString(networkParams, address); // creating recipient address object
        value = Coin.parseCoin(amount); // creating a coin object using the double amount entered by the user in the Send UI
        // checking there is enough coins in the wallet
        try {
            // broadcasting the transaction to the network using the sendCoins() from the Wallet class
            Wallet.SendResult result = myWallet.sendCoins(groupOfDistinctPeers, to, value);
            myWallet.saveToFile(myWalletFile); // now the coins have been sent, update the wallet with the transaction
            result.broadcastComplete.get();  // transaction broadcast to the network
        } catch (InsufficientMoneyException e) {
            Log.d(TAG, "Sorry, you've not got enough Bitcoins to complete this transaction!");
        }
    }

    // A getter used to print the available wallet balance
    public Coin getAvailableBalance() {
        return myWallet.getBalance(Wallet.BalanceType.AVAILABLE);
    }

    // A getter used to print a list of the transactions in chronological order on the Transaction UI
    public List<Transaction> getTransactions() {
        return myWallet.getTransactionsByTime();
    }

    // used to print the history of addresses as a test in the thesis to show that
    // a new address is automatically generated after every transaction
    public List<Address> getAllWalletAddresses() {
        return myWallet.getIssuedReceiveAddresses();
    }

    // to print transactions of the wallet on the Transaction UI
    public Wallet getMyWallet() {
        return myWallet;
    }

    // to print the current address
    public String myWalletAddress(){
        return myWallet.currentReceiveAddress().toString();
    }
}




