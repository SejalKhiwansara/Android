package com.example.madhura.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PriceAlert extends Fragment {
    View my_v;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.pricealert, container, false);

        return my_v;
    }
    @Override
    public void onPause() {
        Intent it=new Intent(my_v.getContext(),MainActivity.class);
        startActivity(it);
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
