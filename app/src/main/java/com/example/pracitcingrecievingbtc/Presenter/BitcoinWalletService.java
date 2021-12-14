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



// a service class performs long running opertions in the background of an application
// https://developer.android.com/guide/components/services#:~:text=The%20Service%20class%20is%20the%20base%20class%20for,of%20any%20activity%20that%20your%20application%20is%20running.
public class BitcoinWalletService {

    private static final String TAG = BitcoinWalletService.class.getSimpleName();
    private NetworkParameters networkParams;
    private File myWalletFile;
    private File blockchainFileSPVMode;
    private Wallet myWallet;
    private static PeerGroup groupOfDistinctPeers; // tries to maintain a constant num of connections to a set of distinct peers
    private Context context;
    private int peerCount;
    private Script.ScriptType outputScriptType;
    BlockStore blockStore; // to load blocks
    BlockChain chain = null; // will be used to implement the SPV mode of the Bitcoin protocol
    // can verify transactions without downloading the whole blockchain, just the headers


    public BitcoinWalletService(Context context) {
        BriefLogFormatter.init(); // Activating BitcoinJ's logging using a Java logging formatter that writes more compact output than default
        this.context = context;
        settingUpNetworkAndFiles(); // connecting to testnet, creating a local file for the wallet and blockchain
        myWallet = initialisingWallet(); // creating or loading the wallet
        myWalletInitialisedFromNetwork(); // syncing the blockchain
    }

    public BitcoinWalletService(){

    }

    // a helper method for connecting to the test net, creating a file for the wallet and blockchain locally
    public void settingUpNetworkAndFiles(){
        networkParams = TestNet3Params.get(); // request testnet
        myWalletFile = new File(context.getFilesDir(), "TestWallet.wallet");
        blockchainFileSPVMode = new File(this.context.getFilesDir(), "TestSPV.dat"); // a node on blockchain stores headers

    }

    // initialise the wallet by checking if it exists
    public Wallet initialisingWallet() {
        Wallet myWallet;
        // exists, load it
        if (myWalletFile.exists()) {
            myWallet = loadingWallet();
            Log.d(TAG, "Wallet exists, loading the wallet");
        } else {
            // doesn't exist, create it
            myWallet = creatingWallet();
            Log.d(TAG, "Wallet doesn't exist, creating it");
        }
        // autosave the file
        myWallet.autosaveToFile(myWalletFile, 200, TimeUnit.MILLISECONDS, null); // saving the file by passing its name and what it includes
        Log.d(TAG, "A wallet " + myWalletFile.getName() + "exists with the following info: " + myWallet);
        return myWallet;
    }

    // not wallet has been found so we create one
    public Wallet creatingWallet() {
        Log.d(TAG, "Creating a new wallet");
        Wallet createdWallet;
        // creating an empty wallet with a randomly chosen seed and no transactions keys will be derived from the seed
        createdWallet = Wallet.createDeterministic(networkParams, outputScriptType);
        createdWallet.setDescription("Softcoin wallet prototype");
        // returns the keys issued
        System.out.println(createdWallet.getIssuedReceiveAddresses());
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

    // a helper method that combines the helper methods used for implementing the Bitcoin protocol
    public void myWalletInitialisedFromNetwork() {
        checkingIfBlockchainSPVFileExists();
        downloadingTheBlockchainInSPVMode();
        downloadingPeerListeners();
    }

    // a helper method to check if a blockchain file already exists
    public void checkingIfBlockchainSPVFileExists(){
        if (blockchainFileSPVMode.exists()) {
            Log.d(TAG, "An existing blockchain has been found!");
        } else {
            Log.d(TAG, "We cannot find any blockchain file. The sync will begin now and may take 1 hour.");
        }
    }

    // a helper method to download the blockchain
    public void downloadingTheBlockchainInSPVMode(){
        try {
            blockStore = new SPVBlockStore(networkParams, blockchainFileSPVMode);
            chain = new BlockChain(networkParams, this.myWallet, blockStore);
        } catch (BlockStoreException e) {
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
                Log.d(TAG, "The address of the peer downloaded is " + peer.getAddress()));
        // listening to events of peers being discovered peers are nodes on the network
        groupOfDistinctPeers.addDiscoveredEventListener(peerAddresses ->
                Log.d(TAG, "There are currently " + peerCount + " peers. The new peer is " + peerAddresses));
        // listening to events and indicating when a peer disconnects
        groupOfDistinctPeers.addDisconnectedEventListener((peer, peerCount) ->
                Log.d(TAG, "There are currently " + peerCount + " peers connected. The peer lost is " + peer.getAddress()));
        // looking for peers
        groupOfDistinctPeers.addPeerDiscovery(new DnsDiscovery(networkParams));
        // setting max num of peers to connect too, but need 8 to broadcast a transaction to network
        groupOfDistinctPeers.setMaxConnections(10);
        // adding wallet with keys before downloading the blockchain to make sure relevant parts are downloaded
        // (if add wallet keys earlier than the current chain head, the relevant parts of the chain won't be downloaded)
        groupOfDistinctPeers.addWallet(myWallet);
        // start syncing the blockchain in SPV mode which means only the headers of blocks willl be downloaded
        groupOfDistinctPeers.startAsync();
        // prints the % of the blockchain left in the download
        groupOfDistinctPeers.downloadBlockChain();
        // stops syncing once the blockchain has been downloaded
        groupOfDistinctPeers.stopAsync();
    }

    // listens for coins sent to the user's wallet
    private void setupWalletListeners(Wallet myWallet) {
        myWallet.addCoinsReceivedEventListener((wallet1, tx, prevBalance, newBalance) -> {
        });
    }

    // a send method that is passed the recipient address and amount of Bitcoins
    // passed user inputs in the Send UI
    public void send(String address, String amount) throws Exception {
        // creating recipient address object
        Address to = Address.fromString(networkParams, address);
        Log.d(TAG, "Recipients address: " + to);
        // creating a coin object that is passed the amount the user enters into the UI
        Coin value = Coin.parseCoin(amount);
        Log.d(TAG, "Sending " + amount + "coins" + to);
        // checks that there is enough BTC available before a
        try {
            // broadcasting the transaction to the network using the sendCoins() from the Wallet class
            Wallet.SendResult result = myWallet.sendCoins(groupOfDistinctPeers, to, value);
            Log.d(TAG, "Coins have been sent.");
            myWallet.saveToFile(myWalletFile);
            Log.d(TAG, "Saves the updated wallet with the transaction request.");
            result.broadcastComplete.get();
            Log.d(TAG, "The transaction has been broadcast to the network.");
        } catch (InsufficientMoneyException e) {
            Log.e(TAG, "Sorry, you've not got enough Bitcoins to complete this transaction!");
            e.printStackTrace();
        }
    }

    // A getter used to print the available wallet balance
    public Coin getAvailableBalance() {
        return myWallet.getBalance(Wallet.BalanceType.AVAILABLE);
    }

    // A getter used to print a list of the transactions in chronological order on the Transaction UI
    public List<Transaction> getRecentTransactions() {
        return myWallet.getTransactionsByTime();
    }

    // A getter used to print a list of the history of wallet address for Test 2
    // this proves that a new address is automatically generated after every transaction
    public List<Address> getAllWalletAddresses() {
        return myWallet.getIssuedReceiveAddresses();
    }

    // A getter to use the wallet to print the transactions in the Transaction UI
    public Wallet getMyWallet() {
        return myWallet;
    }

    // a helper method used to display the current wallet address
    public String printMyWalletAddress(){
        return myWallet.currentReceiveAddress().toString();
    }
}




