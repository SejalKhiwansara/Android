package com.example.madhura.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class PricePrediction extends Fragment implements View.OnClickListener , AdapterView.OnItemSelectedListener {
    View my_v;
    TextView b;
    Spinner sp;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.priceprediction, container, false);
        b=(TextView) my_v.findViewById(R.id.predict);
        sp=(Spinner)my_v.findViewById(R.id.spinner2);
        b.setOnClickListener(this);
        List<String> list1 = new ArrayList<>();
        list1.add("--Select crop--");
        list1.add("Bajra");
        list1.add("Brinjal");
        list1.add("Coriander");
        list1.add("Cotton");
        list1.add("Green chillies");
        list1.add("Green peas");
        list1.add("Groundnut");
        list1.add("Jowar");
        list1.add("Maize");
        list1.add("Mango");
        list1.add("Mustard");
        list1.add("Onion");
        list1.add("Potato");
        list1.add("Soyabean");
        list1.add("Tomato");
        list1.add("Toor dal");
        list1.add("Udad dal");
        list1.add("Wheat");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(my_v.getContext(),
                android.R.layout.simple_spinner_dropdown_item, list1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return my_v;
    }

    @Override
    public void onClick(View v) {
        String fn=sp.getSelectedItem().toString();
        fn=fn+".csv";
        Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse("http://192.168.43.40:5000/getprice?fname="+fn));
        startActivity(i);

//        String n="jowar";
//        Toast.makeText(my_v.getContext(), "1", Toast.LENGTH_SHORT).show();
//        try{
//            Toast.makeText(my_v.getContext(), "entry in try", Toast.LENGTH_SHORT).show();
//           URL url=new URL("http://192.168.1.103:5000/");           // URL url=new URL("http://localhost:5000/");
//            URLConnection con;
//            con=url.openConnection();
//            InputStream in=con.getInputStream();
//            int x;
//            StringBuffer sb=new StringBuffer();
//            Toast.makeText(my_v.getContext(), "2", Toast.LENGTH_SHORT).show();
//            do{
//                x=in.read();
//                sb.append((char)x);
//            }while (x!=-1);
//            in.close();
//            t.setText(sb.toString());
//            Toast.makeText(my_v.getContext(), "while completion", Toast.LENGTH_SHORT).show();
//        }catch (Exception e ){
//            Toast.makeText(my_v.getContext(), ""+e, Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
//    @Override
//    public void onPause() {
//        Intent it=new Intent(my_v.getContext(),MainActivity.class);
//        startActivity(it);
//        super.onPause();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//    }
}

