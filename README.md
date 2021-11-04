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




11/04 00:54:48: Launching 'MainActivity' on Pixel 2 XL API 23.
Install successfully finished in 11 s 528 ms.
$ adb shell am start -n "com.example.practicingrecievingbtc/com.example.pracitcingrecievingbtc.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
Connected to process 5140 on device 'emulator-5554'.
Capturing and displaying logcat messages from application. This behavior can be disabled in the "Logcat output" section of the "Debugger" settings page.
W/System: ClassLoader referenced unknown path: /data/app/com.example.practicingrecievingbtc-2/lib/x86
D/MainActivity: creating btcService...
W/System.err: SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
    SLF4J: Defaulting to no-operation (NOP) logger implementation
    SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
I/System.out: setting up test network
    load wallet
D/BitcoinExample: existing wallet file name: TestNet3.wallet
D/BitcoinExample: existing wallet file size 693bytes
W/UnsafeUtil: platform method missing - proto runtime falling back to safer methods: java.lang.NoSuchMethodException: getByte [class java.lang.Object, long]
I/art: Background sticky concurrent mark sweep GC freed 24626(1286KB) AllocSpace objects, 0(0B) LOS objects, 34% free, 2MB/3MB, paused 6.416ms total 12.967ms
D/BitcoinExample: Loaded wallet file from disk
W/art: Suspending all threads took: 34.963ms
I/art: Background sticky concurrent mark sweep GC freed 71153(3MB) AllocSpace objects, 0(0B) LOS objects, 32% free, 2MB/3MB, paused 36.235ms total 241.186ms
I/System.out: Now we have loaded or created the wallet
    Next we save the file and state its name and what it includes
D/BitcoinExample: wallet file name: TestNet3.wallet
D/BitcoinExample: wallet contents: Wallet
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
    Earliest creation time: 2021-11-03T23:20:35Z
    Seed birthday:     1635981635  [2021-11-03T23:20:35Z]
    Ouput script type: P2PKH
    Key to watch:      tpubDA62op2RUmRCQ7AXMFEStxhm6wBcgAP3cjD5jsGasqQdXuP5qPHnyk4MBfXQYx8RgrxtjFUpn7xiMYjfF1bVseK8PBGmTvkXdHymawSA4dM
    Lookahead siz/thr: 100/33
      addr:n4AUvwZZLcFb1PzyBF6PEmWoNYgCszck7G  hash160:f86bbb416e738e192ef3ae09bd525199d990413d  (M, root)
      addr:miG2qrZZfWYv3FuprrFpU9SBJnCXMozMff  hash160:1e15ef4566dc661c80a5802701f5cf2e0729bdd8  (M/0H, account)
      addr:mriNC7BMJVwKpYME2JLZB3DGZSc3eq17s7  hash160:7ad1e865f04799fd04ab1617e71a3737c311bd57  (M/0H/0, external)
      addr:mnDkR3SupLN1MgR7EVPmkhnf9LBvCMAjJh  hash160:4987e4834dd2c8c5bd88fc4e3ec270b6cacafc56  (M/0H/1, internal)
I/System.out: Now we are going to create or load a wallet
D/BitcoinExample: No existing blockchain data found it may take a while to scan the blockchain ledger
D/BitcoinExample: Known blockchain height: 0
D/MainActivity: completed creating btcService
D/MainActivity: specified fragment name is: null
I/System.out: got here
D/BitcoinExample: 0 peers, lost peer: [192.168.1.67]:18333
D/OpenGLRenderer: Use EGL_SWAP_BEHAVIOR_PRESERVED: true
D/: HostConnection::get() New Host Connection established 0xaa1fe900, tid 5140
D/: HostConnection::get() New Host Connection established 0xaa1feb80, tid 5192
I/OpenGLRenderer: Initialized EGL, version 1.4
W/OpenGLRenderer: Failed to choose config with EGL_SWAP_BEHAVIOR_PRESERVED, retrying without...
D/EGL_emulation: eglCreateContext: 0xae5547e0: maj 2 min 0 rcv 2
D/EGL_emulation: eglMakeCurrent: 0xae5547e0: ver 2 0 (tinfo 0xae552ba0)
D/EGL_emulation: eglMakeCurrent: 0xae5547e0: ver 2 0 (tinfo 0xae552ba0)
D/EGL_emulation: eglMakeCurrent: 0xae5547e0: ver 2 0 (tinfo 0xae552ba0)
D/EGL_emulation: eglMakeCurrent: 0xae5547e0: ver 2 0 (tinfo 0xae552ba0)
D/EGL_emulation: eglMakeCurrent: 0xae5547e0: ver 2 0 (tinfo 0xae552ba0)
V/RenderScript: 0xae6bc000 Launching thread(s), CPUs 4
D/BitcoinExample: 0 peers, lost peer: [192.168.1.67]:18333
D/EGL_emulation: eglMakeCurrent: 0xae5547e0: ver 2 0 (tinfo 0xae552ba0)
E/Surface: getSlotFromBufferLocked: unknown buffer: 0xaab098c0
D/BitcoinExample: 0 peers, lost peer: [192.168.1.67]:18333
D/BitcoinExample: 0 peers, lost peer: [192.168.1.67]:18333
D/BitcoinExample: 0 peers, lost peer: [192.168.1.67]:18333
D/BitcoinExample: 0 peers, lost peer: [192.168.1.67]:18333
D/BitcoinExample: 0 peers, lost peer: [192.168.1.67]:18333
D/BitcoinExample: 0 peers, lost peer: [192.168.1.67]:18333
D/BitcoinExample: 0 peers, lost peer: [192.168.1.67]:18333
D/BitcoinExample: 0 peers, lost peer: [192.168.1.67]:18333
D/BitcoinExample: 0 peers, lost peer: [192.168.1.67]:18333
D/BitcoinExample: 0 peers, lost peer: [192.168.1.67]:18333
D/BitcoinExample: 0 peers, lost peer: [192.168.1.67]:18333
