# Project

Wallet-App Kit is highly recommended for anyone who does not have much programming experience on BitcoinJ.

I tried working with some old code from 2015 and trying to create a wallet from it just changing out some depreciated methods etc. It seems to have actually done something compared to all the examples and my code with the wallet app kit. 


**The Logcat is below. There are some error messages, but it actually creates a wallet, connects to the test net, but it is not able to find any peers. This could be an issue with what I'm doing with the code.  **

Peer group is defined in the API as 

_Running a set of connections to the P2P network, brings up connections to replace disconnected nodes and manages the interaction between them all. Most applications will want to use one of these.
PeerGroup tries to maintain a constant number of connections to a set of distinct peers. Each peer runs a network listener in its own thread. When a connection is lost, a new peer will be tried after a delay as long as the number of connections less than the maximum.
Connections are made to addresses from a provided list. When that list is exhausted, we start again from the head of the list.
The PeerGroup can broadcast a transaction to the currently connected set of peers. It can also handle download of the blockchain from peers, restarting the process when peers die.
PeerGroup implements the Service interface. This means before it will do anything, you must call the Service.start() method (which returns a future) or Service.startAndWait() method, which will block until peer discovery is completed and some outbound connections have been initiated (it will return before handshaking is done, however). You should call Service.stop() when finished. Note that not all methods of PeerGroup are safe to call from a UI thread as some may do network IO, but starting and stopping the service should be fine._



11/04 10:26:20: Launching 'MainActivity' on Pixel 2 XL API 23.
Install successfully finished in 14 s 234 ms.
$ adb shell am start -n "com.example.practicingrecievingbtc/com.example.pracitcingrecievingbtc.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
Connected to process 6796 on device 'Pixel_2_XL_API_23 [emulator-5554]'.
Capturing and displaying logcat messages from application. This behavior can be disabled in the "Logcat output" section of the "Debugger" settings page.
W/System: ClassLoader referenced unknown path: /data/app/com.example.practicingrecievingbtc-2/lib/x86
D/MainActivity: creating btcService...
W/System.err: SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
    SLF4J: Defaulting to no-operation (NOP) logger implementation
    SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
I/System.out: setting up test network
I/System.out: load wallet
D/BitcoinExample: the current wallet file: TestWallet.wallethas a size685bytes
W/UnsafeUtil: platform method missing - proto runtime falling back to safer methods: java.lang.NoSuchMethodException: getByte [class java.lang.Object, long]
D/BitcoinExample: Loaded wallet file from disk
    we've loaded the wallet
    A walletTestWallet.walletwith270keys.
D/BitcoinExample: The contents of the wallet include Wallet
    Balances:
      0.00 BTC ESTIMATED
      0.00 BTC AVAILABLE
      0.00 BTC ESTIMATED_SPENDABLE
      0.00 BTC AVAILABLE_SPENDABLE
    Transactions:
      0 pending
      0 unspent
      0 spent
      0 dead
    Last seen best block: -1 (time unknown): null
    
    Keys:
    Earliest creation time: 2021-11-04T01:16:32Z
    Seed birthday:     1635988592  [2021-11-04T01:16:32Z]
    Ouput script type: P2PKH
    Key to watch:      tpubDA6fHUB9pmGWEp9spds9bVqTCbZiw1wBddTjGo7Xos7kigg1Ngr8ogArUf9rYbCU5RM5vLuoCvz6KX36q6ZnJXrHjiXmmUQyzq3QV9nSY8t
    Lookahead siz/thr: 100/33
      addr:n4JHMMvX85hgnCvMaZkqC9ifuLpHJgHUZ3  hash160:f9e564be42fdb9b1008af9b99aa8cf2991531b6e  (M, root)
      addr:miSHG7Ls8F1mh8gQKn1mjp24Xt83JcgwP1  hash160:2006214584c689f8cd48c4bec0194443cb090cdc  (M/0H, account)
      addr:mkNUZas9AVzE9fvVkbpc1jn2VTmZhJyF2w  hash160:353e26ca557ba0a3adf2185cfcede323758942b9  (M/0H/0, external)
      addr:muvrkhWrED4KeRHeJY1qPnfEnEghoNfoDy  hash160:9e171e76a13b5038ec982bba141aaaae3213f3f2  (M/0H/1, internal)
I/System.out: Now we are going to create or load a wallet
D/BitcoinExample: An existing blockchain file has been located and is of size641024 bytes
D/BitcoinExample: Known blockchain height: 0
D/MainActivity: completed creating btcService
D/BitcoinExample: There are currently 0 peers connected. The peer lost is [192.168.1.67]:18333
D/MainActivity: specified fragment name is: null
I/System.out: got here
D/OpenGLRenderer: Use EGL_SWAP_BEHAVIOR_PRESERVED: true
D/: HostConnection::get() New Host Connection established 0xaaac6600, tid 6796
D/: HostConnection::get() New Host Connection established 0xaaac68c0, tid 6845
I/OpenGLRenderer: Initialized EGL, version 1.4
W/OpenGLRenderer: Failed to choose config with EGL_SWAP_BEHAVIOR_PRESERVED, retrying without...
D/EGL_emulation: eglCreateContext: 0xae5548a0: maj 2 min 0 rcv 2
D/EGL_emulation: eglMakeCurrent: 0xae5548a0: ver 2 0 (tinfo 0xae552d70)
D/EGL_emulation: eglMakeCurrent: 0xae5548a0: ver 2 0 (tinfo 0xae552d70)
D/BitcoinExample: There are currently 0 peers connected. The peer lost is [192.168.1.67]:18333
D/BitcoinExample: There are currently 0 peers connected. The peer lost is [192.168.1.67]:18333
D/BitcoinExample: There are currently 0 peers connected. The peer lost is [192.168.1.67]:18333
D/BitcoinExample: There are currently 0 peers connected. The peer lost is [192.168.1.67]:18333
D/BitcoinExample: There are currently 0 peers connected. The peer lost is [192.168.1.67]:18333
D/BitcoinExample: There are currently 0 peers connected. The peer lost is [192.168.1.67]:18333
D/BitcoinExample: There are currently 0 peers connected. The peer lost is [192.168.1.67]:18333
D/BitcoinExample: There are currently 0 peers connected. The peer lost is [192.168.1.67]:18333
D/BitcoinExample: There are currently 0 peers connected. The peer lost is [192.168.1.67]:18333
D/BitcoinExample: There are currently 0 peers connected. The peer lost is [192.168.1.67]:18333
D/BitcoinExample: There are currently 0 peers connected. The peer lost is [192.168.1.67]:18333
D/BitcoinExample: There are currently 0 peers connected. The peer lost is [192.168.1.67]:18333
D/BitcoinExample: There are currently 0 peers connected. The peer lost is [192.168.1.67]:18333
D/BitcoinExample: There are currently 0 peers connected. The peer lost is [192.168.1.67]:18333
D/BitcoinExample: There are currently 0 peers connected. The peer lost is [192.168.1.67]:18333
