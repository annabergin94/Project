package com.example.pracitcingrecievingbtc.View;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.pracitcingrecievingbtc.R;


public class SendBitcoinFragment extends Fragment {

    private static final String TAG = SendBitcoinFragment.class.getSimpleName();
    private ImageView ivCopy; // copy address emoji
    protected ClipboardManager clipboardManager;
    TextView tvMyAddress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragement_send_bitcoin, container, false);
        init(v);
        return v;
    }

    private void init(View view) {
        ivCopy = view.findViewById(R.id.copy);
    }

    private void copyWalletAddress() {
        ClipData clip = ClipData.newPlainText("My wallet address", tvMyAddress.getText().toString());
        clipboardManager.setPrimaryClip(clip);
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}