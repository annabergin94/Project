package com.example.pracitcingrecievingbtc.View;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pracitcingrecievingbtc.R;
import org.bitcoinj.core.Address;

// a subclass of Fragment to view address in a Fragment for the MainActivity to display
public class ViewAddressFragment extends Fragment {

    private static final String TAG = ViewAddressFragment.class.getSimpleName();

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
            View view = inflater.inflate(R.layout.fragment_view_address, container, false);
            return view;
        }

    @Override
        public void onResume() {
            super.onResume();
//            Log.d(TAG, "registering view addresses fragment on the event bus");
//            OttoEventBus.getInstance().register(this);
        }

        @Override
        public void onPause() {
            super.onPause();
//            Log.d(TAG, "unregistering view addresses fragment on the event bus");
//            OttoEventBus.getInstance().unregister(this);
        }

}
