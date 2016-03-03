package com.licheng.github.cashregister;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;


/**
 * Created by licheng on 3/3/16.
 */
public class MyDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog, container);
        TextView text = (TextView) view.findViewById(R.id.textView);
        String pricelist = getArguments().getString("pricelist");
        text.setText(pricelist);
        return view;
    }
}
