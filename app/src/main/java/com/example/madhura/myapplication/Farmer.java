package com.example.madhura.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class Farmer extends Fragment implements View.OnClickListener {
    View my_v;
    Firebase ref;
    Query q;
    String email;
    ImageView i1,i2,i3,i4,i5;
    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.farmer, container, false);
        i1=(ImageView)my_v.findViewById(R.id.sell);
        i1.setOnClickListener(this);
        i2 = (ImageView) my_v.findViewById(R.id.myshop);
        i2.setOnClickListener(this);
        i3 = (ImageView) my_v.findViewById(R.id.pricealert);
        i3.setOnClickListener(this);
        i4 = (ImageView) my_v.findViewById(R.id.weather);
        i4.setOnClickListener(this);
        i5 = (ImageView) my_v.findViewById(R.id.pricepred);
        i5.setOnClickListener(this);
        pd=new ProgressDialog(my_v.getContext());
        pd.setMessage("please wait...");
        Firebase.setAndroidContext(my_v.getContext());
        select();
        return my_v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sell:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                int commit = fm.beginTransaction().replace(R.id.content_frame, new sell2add()).addToBackStack("Farmer").commit();
                break;
            case R.id.weather:
                FragmentManager fm1 = getActivity().getSupportFragmentManager();
                int commit1 = fm1.beginTransaction().replace(R.id.content_frame, new Weather()).addToBackStack("Farmer").commit();
                break;
            case R.id.myshop:
                FragmentManager fm2 = getActivity().getSupportFragmentManager();
                int commit2 = fm2.beginTransaction().replace(R.id.content_frame, new MyDemands()).addToBackStack("Farmer").commit();
                break;
            case R.id.pricealert:
                FragmentManager fm3 = getActivity().getSupportFragmentManager();
                int commit3 = fm3.beginTransaction().replace(R.id.content_frame, new News()).addToBackStack("Farmer").commit();
                break;
            case R.id.pricepred:
//                Intent it=new Intent(Intent.ACTION_VIEW,Uri.parse("http://192.168.43.40:5000"));
//                startActivity(it);
                FragmentManager fm4 = getActivity().getSupportFragmentManager();
                int commit4 = fm4.beginTransaction().replace(R.id.content_frame, new PricePrediction()).addToBackStack("Farmer").commit();
                break;
        }
    }


    void select() {

        try {
            email = TEST.email;
            pd.show();
            //Toast.makeText(getContext(), "Email = " + email, Toast.LENGTH_SHORT).show();
            //ref = new Firebase("https://myprj-74da3.firebaseio.com/Farmer");
            ref=new Firebase("https://farm-database-c29f5.firebaseio.com/Farmer");
            q = ref.orderByKey();
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot sn : dataSnapshot.getChildren()) {
                        //to fetch single record

                        for (DataSnapshot sn1 : sn.getChildren()) {
                            String s = sn1.getKey();
                            try {
                                if (s.equals("Personalinfo")) {
                                    GetterSetter p1 = sn1.getValue(GetterSetter.class);
                                    if (p1.getEmail().equals(email)) {
                                        //Toast.makeText(getContext(), "Phone = " + p1.getPhone(), Toast.LENGTH_SHORT).show();
                                        TEST.phone = p1.getPhone();
                                        pd.dismiss();

                                    }

                                }
                            } catch (Exception e) {
                                //Toast.makeText(my_v.getContext(), "" + e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(this.getContext(), "" + e, Toast.LENGTH_SHORT).show();
        }

    }
}


