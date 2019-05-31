package com.example.madhura.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import static com.example.madhura.myapplication.TEST.phone;

public class Delete extends Fragment  {

    View my_v;
    Firebase ref;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.sell2add, container, false);
        GetterSetter1 st=TEST.st;
        Firebase.setAndroidContext(my_v.getContext());
        //ref=new Firebase("https://myprj-74da3.firebaseio.com/");
        ref=new Firebase("https://farm-database-c29f5.firebaseio.com/");
        int x=0;
        switch (TEST.st.getCname())
        {
            case "Bajra":
                x=1;
                break;
            case "Jowar":
                x=2;
                break;
            case "Maze":
                x=3;
                break;
            case "Rice":
                x=4;
                break;
            case "Wheat":
                x=5;
                break;
            case "Cotton":
                x=6;
                break;
            case "Soyabean":
                x=7;
                break;
            case "Sugarcane":
                x=8;
                break;
            case "Groundnut":
                x=9;
                break;
            case "Pulses":
                x=10;
                break;
            case "Sunflower":
                x=11;
                break;

        }

        ref.child("Farmer").child(phone).child("Cropinfo").child(""+x).removeValue();
        //Firebase objRef = ref.child("stud");
        //Firebase taskRef = objRef.child();
        //taskRef.removeValue();
        Toast.makeText(my_v.getContext(), "deleted", Toast.LENGTH_SHORT).show();

        return my_v;
    }
}
