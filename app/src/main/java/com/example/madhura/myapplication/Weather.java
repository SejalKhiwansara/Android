package com.example.madhura.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Weather extends Fragment {
    View my_v;
   TextView t1, t2, t3, t4, t5;
    String description;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.weather, container, false);
        t1 = (TextView) my_v.findViewById(R.id.textView40);
        t2 = (TextView) my_v.findViewById(R.id.textView41);
        t3 = (TextView) my_v.findViewById(R.id.textView42);
        t4 = (TextView) my_v.findViewById(R.id.textView45);
        t5 = (TextView) my_v.findViewById(R.id.textView46);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String api_key = "58c9cb5846ec0e1f1cc809b123d632d7";
        String base_url = "http://api.openweathermap.org/data/2.5/weather?";
        String city_name = "aurangabad";
        String complete_url = base_url + "appid=" + api_key + "&q=" + city_name;
        try {
            URL url = new URL(complete_url);
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String str = "";
            int x;
            do {
                x = in.read();
                if (x != -1) {
                    str += (char) x;
                }
            } while (x != -1);

            in.close();
            JSONObject reader = new JSONObject(str);
            JSONObject main = reader.getJSONObject("main");

            String temp = main.getString("temp");
            String temp_min = main.getString("temp_min");
            String temp_max = main.getString("temp_max");
            String pressure = main.getString("pressure");
            String humidity = main.getString("humidity");
            t1.setText(temp+ "K");
            t2.setText(pressure+ "Pa");
            t3.setText(humidity);
            t4.setText(temp_min+ "K");
            t5.setText(temp_max+ "K");

        } catch (Exception e) {
            //Toast.makeText(my_v.getContext(), "" + e, Toast.LENGTH_SHORT).show();
        }
        return my_v;
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