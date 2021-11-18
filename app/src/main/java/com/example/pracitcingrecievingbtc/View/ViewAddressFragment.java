package com.example.pracitcingrecievingbtc.View;

import android.content.ClipData;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pracitcingrecievingbtc.Presenter.BitcoinWalletPresenter;
import com.example.pracitcingrecievingbtc.R;
import org.bitcoinj.core.Address;
import org.bitcoinj.wallet.Wallet;
import org.jetbrains.annotations.NotNull;

// a subclass of Fragment to view address in a Fragment for the MainActivity to display
public class ViewAddressFragment extends Fragment {


    Button copy;
    EditText etMyAddress;
    TextView addressHeader;

    private static final String TAG = ViewAddressFragment.class.getSimpleName();

    // major diff with activities need to create a view object and return the view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_view_address, container, false);
        // need to use view because frag
        copy = (Button) view.findViewById(R.id.copy);
        etMyAddress = (EditText) view.findViewById(R.id.etMyAddress);
        addressHeader = (TextView) view.findViewById(R.id.tvTitle);

        etMyAddress.setText(((MainActivity)getActivity()).getBtcService().printMyWalletAddress());

        copy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                ClipData clip = ClipData.newPlainText("My wallet address", etMyAddress.getText().toString());
////            clipboardManager.setPrimaryClip(clip);
                Toast.makeText(getActivity(), "Copying your address", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).getBtcService().printMyWalletAddress();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

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
