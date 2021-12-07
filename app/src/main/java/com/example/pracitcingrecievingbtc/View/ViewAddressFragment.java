package com.example.pracitcingrecievingbtc.View;

import android.content.ClipData;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pracitcingrecievingbtc.R;
import android.content.ClipboardManager;


// a subclass of Fragment to view address in a Fragment for the MainActivity to display
public class ViewAddressFragment extends Fragment {

    ImageButton btnCopy;
    TextView tvMyAddress;
    ClipboardManager clipboard;
    ClipData cd;
    private static final String TAG = ViewAddressFragment.class.getSimpleName();

    Button btnPaste;
    EditText etPasteAddress;

    // major diff with activities need to create a view object and return the view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        View view = inflater.inflate(R.layout.fragment_view_address, container, false);

        btnCopy = (ImageButton) view.findViewById(R.id.btnCopy);
        tvMyAddress = (TextView) view.findViewById(R.id.tvMyAddress);
        tvMyAddress.setText(((MainActivity)getActivity()).getBitcoinWalletPresenter().printMyWalletAddress());

        btnCopy.setOnClickListener(v -> copy()); // copy address

        // for user testing
//        btnPaste = (Button) view.findViewById(R.id.btnPaste);
//        etPasteAddress = (EditText) view.findViewById(R.id.etPasteAddress);
//        btnPaste.setOnClickListener(v -> paste()); // paste for testing since prototype

        return view;
    }

    public void copy(){
        clipboard = (android.content.ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // storing address as a string
        String text = tvMyAddress.getText().toString();
        // story copy of the address on the clipboard
        cd = ClipData.newPlainText("Address", text);
        // setting the address on the clipboard
        clipboard.setPrimaryClip(cd);
        Toast.makeText(getActivity(), "Address copied!", Toast.LENGTH_SHORT).show();
    }

    // for user testing
//    public void paste(){
//        // get clipboard data from clipboard manager
//        ClipData cd2 = clipboard.getPrimaryClip();
//        ClipData.Item item=cd2.getItemAt(0);
//        String copied =item.getText().toString();
//        etPasteAddress.setText(copied);
//        Toast.makeText(getActivity(), "Pasting address!", Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
