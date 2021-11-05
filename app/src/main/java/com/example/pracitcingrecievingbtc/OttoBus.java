package com.example.pracitcingrecievingbtc;

import android.util.Log;
import com.squareup.otto.Bus;

public class OttoBus {

    public static final class OttoEventBus {

        private static String TAG = OttoEventBus.class.getSimpleName();
        private static Bus BUS = new Bus();

        private OttoEventBus() {

        }

        public static Bus getInstance() {
            Log.d(TAG, "providing Otto event bus instance");
            return BUS;
        }
    }
}
