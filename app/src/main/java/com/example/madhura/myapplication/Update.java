package com.example.madhura.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import static com.example.madhura.myapplication.TEST.phone;

public class Update extends Fragment implements View.OnClickListener {
    View my_v;
    EditText e1,e2,e3;
    Firebase ref;
    TextView tv;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.update, container, false);
        e1=(EditText)my_v.findViewById(R.id.cprice);
        e2=(EditText)my_v.findViewById(R.id.desc);
        e3=(EditText)my_v.findViewById(R.id.cname);
        tv=(TextView)my_v.findViewById(R.id.update);
        GetterSetter1 st=TEST.st;
        e1.setText(st.getCprice());
        e2.setText(st.getCdes());
        e3.setText(st.getCname());
        tv.setOnClickListener(this);
        Firebase.setAndroidContext(my_v.getContext());
        //ref=new Firebase("https://myprj-74da3.firebaseio.com/");
        ref=new Firebase("https://farm-database-c29f5.firebaseio.com/");
        return my_v;
    }

    @Override
    public void onClick(View v) {
        update();
    }

    void update(){
        String cprice=e1.getText().toString();
        String cdes=e2.getText().toString();
        String cname=e3.getText().toString();
        int x=0;
        switch (TEST.st.getCname())
        {
            case "Bajra":
                x = 1;
                break;
            case "Brinjal":
                x = 2;
                break;
            case "Coriander":
                x = 3;
                break;
            case "Cotton":
                x = 4;
                break;
            case "Green Chillies":
                x = 5;
                break;
            case "Green peas":
                x = 6;
                break;
            case "Groundnut":
                x = 7;
                break;
            case "Jowar":
                x = 8;
                break;
            case "Maize":
                x = 9;
                break;
            case "Mango":
                x = 10;
                break;
            case "Mustard":
                x = 11;
                break;
            case "Onion":
                x = 12;
                break;
            case "Potato":
                x = 13;
                break;
            case "Soyabean":
                x = 14;
                break;
            case "Tomato":
                x = 15;
                break;
            case "Toor dal":
                x = 16;
                break;
            case "Udad Dal":
                x = 17;
                break;
            case "Wheat":
                x = 18;
                break;

        }
        TEST.st.setCprice(cprice);
        TEST.st.setCdes(cdes);
        //   Random r=new Random(5);

        //ref.child(phone).child("CropInfo").child(""+x).setValue(ob);
        ref.child("Farmer").child(phone).child("Cropinfo").child(""+x).setValue(TEST.st);
        Toast.makeText(my_v.getContext(), "informatin updated !!", Toast.LENGTH_SHORT).show();
    }

}
