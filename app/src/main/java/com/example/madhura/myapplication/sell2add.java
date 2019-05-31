package com.example.madhura.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class sell2add extends Fragment implements View.OnClickListener {
    TextView b1,b2;
    View my_v;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.sell2add, container, false);
        b1=(TextView) my_v.findViewById(R.id.buy);
        b2=(TextView) my_v.findViewById(R.id.addp);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        return my_v;

    }


        @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buy:
                FragmentManager fm2 = getActivity().getSupportFragmentManager();
                int commit2 = fm2.beginTransaction().replace(R.id.content_frame, new ViewList()).addToBackStack("sell2add").commit();
                break;
            case R.id.addp:
                FragmentManager fm1 = getActivity().getSupportFragmentManager();
                int commit1 = fm1.beginTransaction().replace(R.id.content_frame, new AddProduct()).addToBackStack("sell2add").commit();
                break;
        }

    }









}
