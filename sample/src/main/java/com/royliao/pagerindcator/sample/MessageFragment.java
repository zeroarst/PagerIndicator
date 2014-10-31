package com.royliao.pagerindcator.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MessageFragment extends Fragment {

    static final String ARGS_MESSAGE = "ARGS_MESSAGE";

    public static MessageFragment create(String message) {
        MessageFragment fg = new MessageFragment();
        Bundle args = new Bundle();
        fg.setArguments(args);
        args.putString(ARGS_MESSAGE, message);
        return fg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        TextView tv = new TextView(getActivity());
        tv.setText(getArguments().getString(ARGS_MESSAGE));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        tv.setLayoutParams(lp);

        RelativeLayout layout = new RelativeLayout(getActivity());
        layout.addView(tv);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
