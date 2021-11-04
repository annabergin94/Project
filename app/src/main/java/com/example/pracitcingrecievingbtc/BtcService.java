// practicing using example from blog didnt work
//
//import android.content.Context; // need for Bitcoin Service
//
//import org.bitcoinj.core.ECKey;
//import org.bitcoinj.core.NetworkParameters;
//import org.bitcoinj.kits.WalletAppKit;
//import org.bitcoinj.params.MainNetParams;
//import org.bitcoinj.params.RegTestParams;
//import org.bitcoinj.params.TestNet3Params;
//import org.bitcoinj.utils.BriefLogFormatter;
//import org.bitcoinj.wallet.Wallet;
//
//
//import java.io.File;
//
//public class BtcService {
//
//    private String network = "testnet";
//    private NetworkParameters btcNetworkParams; // setting the network, we will work with testnet
//    private String btcFileLocation =null;
//    WalletAppKit wallet = null;
//    private String filePrefix = "";
//
//    File walletFile = null;
//    File spvBlockChainFile = null;
//    Context context = null;
//
//    public BtcService(Context context) {
//   //     BriefLogFormatter.init();
//        this.context = context;
//        if(network.equals("testnet")) { // when we are on the testnet network
//            btcNetworkParams = TestNet3Params.get(); // get the network
//        } else if(network.equals("regtest")) {
//            btcNetworkParams = RegTestParams.get();
//        } else {
//            btcNetworkParams = MainNetParams.get();
//            walletFile = new File(context.getFilesDir(), "TestNet3.wallet");
//            spvBlockChainFile = new File(this.context.getFilesDir(), "TestNet3SpvBlockChainFile.dat");
//        }
//    }
//
////    public void networkParameters(Context context) {
////        this.context = context;
////        if(network.equals("testnet")) { // when we are on the testnet network
////            btcNetworkParams = TestNet3Params.get(); // get the network
////        } else if(network.equals("regtest")) {
////            btcNetworkParams = RegTestParams.get();
////        } else {
////            btcNetworkParams = MainNetParams.get();
////            walletFile = new File(context.getFilesDir(), "TestNet3.wallet");
////            spvBlockChainFile = new File(this.context.getFilesDir(), "TestNet3SpvBlockChainFile.dat");
////        }
////    }
//
//    public WalletAppKit walletAppKit(NetworkParameters networkParameters) {
//        return new WalletAppKit(networkParameters, new File(btcFileLocation), filePrefix) {
//            @Override
//            protected void onSetupCompleted() {
//                if (wallet().getKeyChainGroupSize() < 1) {
//                    wallet().importKey(new ECKey());
//                }
//            }
//        };
//    }
//}