package com.example.pracitcingrecievingbtc.View;

import android.os.Bundle;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pracitcingrecievingbtc.R;

public class ReceiveBitcoinFragment extends Fragment {

    private static final String TAG = ReceiveBitcoinFragment.class.getSimpleName();
    EditText etReceiveAmount;
    String freshAddressString = "";


    public ReceiveBitcoinFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receive_bitcoin, container, false);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }



//
//    public static ReceiveBitcoinFragment newInstance(String param1, String param2) {
//        ReceiveBitcoinFragment fragment = new ReceiveBitcoinFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }


}