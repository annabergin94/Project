package com.example.pracitcingrecievingbtc.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.pracitcingrecievingbtc.OttoBus;
import com.example.pracitcingrecievingbtc.R;

public class AddressFragment extends Fragment {

    private static final String TAG = AddressFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.activity_address_fragement , container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "registering view addresses fragment on the event bus");
        OttoBus.OttoEventBus.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "unregistering view addresses fragment on the event bus");
        OttoBus.OttoEventBus.getInstance().unregister(this);
    }

}