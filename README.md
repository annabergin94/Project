# Project

Wallet-App Kit is highly recommended for anyone who does not have much programming experience on BitcoinJ.

I tried working with some old code from 2015 and trying to create a wallet from it just changing out some depreciated methods etc. It seems to have actually done something compared to all the examples and my code with the wallet app kit. 

New Logcat:


11-04 23:23:33.316 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Save completed in 12.30 ms
11-04 23:23:33.512 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Background saving wallet; last seen block is height 2101543, date 2021-10-30T07:56:42Z, hash 000000000000003b8c916cd5809c206c8ad0f697721a2c1c4ed213746345fc59
11-04 23:23:33.521 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Save completed in 7.970 ms
11-04 23:23:33.752 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Background saving wallet; last seen block is height 2101618, date 2021-10-30T17:21:21Z, hash 000000000000000d08bf5ec8c4091f4305f76cfdc890bfead373424dba0a265a
11-04 23:23:33.761 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Save completed in 8.792 ms
11-04 23:23:34.016 12860-12965/com.example.practicingrecievingbtc W/System.err: [PeerGroup Thread] INFO org.bitcoinj.core.PeerGroup$ChainDownloadSpeedCalculator - 628 blocks/sec, 0 tx/sec, 18864 pre-filtered tx/sec, avg/last 64.09/49.06 kilobytes per sec, chain/common height 2101757/2102642, not stalled (threshold <0.78 KB/sec for 10 seconds)
11-04 23:23:34.058 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Background saving wallet; last seen block is height 2101787, date 2021-10-31T15:07:13Z, hash 00000000000000271fd7da35821e2b51aee96c351392f2617f50388e8044e665
11-04 23:23:34.068 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Save completed in 9.534 ms
11-04 23:23:34.292 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Background saving wallet; last seen block is height 2102022, date 2021-11-01T23:36:26Z, hash 000000000000af8a09974db4d426e8387446d08ef2896928879f9615311e47bc
11-04 23:23:34.301 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Save completed in 7.825 ms
11-04 23:23:34.541 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Background saving wallet; last seen block is height 2102118, date 2021-11-02T08:32:00Z, hash 000000000000001bf2a91c041fac897f4a960c1f337fe0299a566149f99aecb7
11-04 23:23:34.548 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Save completed in 6.649 ms
11-04 23:23:34.878 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Background saving wallet; last seen block is height 2102288, date 2021-11-03T03:37:57Z, hash 000000000000002c18a4ff34503f717a976a75c81a8cc92bdcf333a1939c7a18
11-04 23:23:34.886 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Save completed in 8.056 ms
11-04 23:23:35.017 12860-12965/com.example.practicingrecievingbtc W/System.err: [PeerGroup Thread] INFO org.bitcoinj.core.PeerGroup$ChainDownloadSpeedCalculator - 657 blocks/sec, 1 tx/sec, 18870 pre-filtered tx/sec, avg/last 60.32/51.55 kilobytes per sec, chain/common height 2102413/2102642, not stalled (threshold <0.78 KB/sec for 10 seconds)
11-04 23:23:35.115 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Background saving wallet; last seen block is height 2102525, date 2021-11-04T08:56:22Z, hash 0000000000000003f5a9e449032e8a22b5eb59baf8d2c0e9499c528c15715610
11-04 23:23:35.126 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Save completed in 10.32 ms
11-04 23:23:35.362 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Background saving wallet; last seen block is height 2102618, date 2021-11-04T20:22:29Z, hash 00000000000000069facb65324281cec31aaae14fb35d31c3b7b0a8d0d302905
11-04 23:23:35.368 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Save completed in 6.227 ms
11-04 23:23:35.465 12860-12967/com.example.practicingrecievingbtc W/System.err: [NioClientManager] INFO org.bitcoinj.core.AbstractBlockChain - Connected orphan 00000000000000237757918b4591dd37ae89af21d192f5c985f3b0e1eca1c0a7
11-04 23:23:35.465 12860-12967/com.example.practicingrecievingbtc W/System.err: [NioClientManager] INFO org.bitcoinj.core.AbstractBlockChain - Connected orphan 00000000000000379d90561068defcca01c461fa4600aebbc3d028f656bf727e
11-04 23:23:35.465 12860-12967/com.example.practicingrecievingbtc W/System.err: [NioClientManager] INFO org.bitcoinj.core.AbstractBlockChain - Connected orphan 000000000000002b85c7053d27717e66077dd2143453c3364e686232a9f5b28c
11-04 23:23:35.466 12860-12967/com.example.practicingrecievingbtc W/System.err: [NioClientManager] INFO org.bitcoinj.core.AbstractBlockChain - Connected 3 orphan blocks.
11-04 23:23:35.466 12860-12967/com.example.practicingrecievingbtc W/System.err: [NioClientManager] INFO org.bitcoinj.core.listeners.DownloadProgressTracker - Chain download 100% done with 0 blocks to go, block date 2021-11-04T23:10:47Z
11-04 23:23:35.602 12860-13121/com.example.practicingrecievingbtc W/OpenGLRenderer: Failed to choose config with EGL_SWAP_BEHAVIOR_PRESERVED, retrying without...
11-04 23:23:35.603 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Background saving wallet; last seen block is height 2102642, date 2021-11-04T23:16:29Z, hash 000000000000002b85c7053d27717e66077dd2143453c3364e686232a9f5b28c
11-04 23:23:35.609 12860-13109/com.example.practicingrecievingbtc W/System.err: [Wallet autosave thread] INFO org.bitcoinj.wallet.WalletFiles - Save completed in 5.478 ms
11-04 23:23:36.016 12860-12965/com.example.practicingrecievingbtc W/System.err: [PeerGroup Thread] INFO org.bitcoinj.core.PeerGroup$ChainDownloadSpeedCalculator - End of sync detected at height 2102642.





Old error message:

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
