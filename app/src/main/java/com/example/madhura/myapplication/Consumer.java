package com.example.madhura.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class Consumer extends Fragment implements View.OnClickListener {
    View my_v;
    ImageView i1,i2,i3;
    Firebase ref;
    Query q;
    String email;
    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.consumer, container, false);
        i1=(ImageView)my_v.findViewById(R.id.buy);
        i2=(ImageView)my_v.findViewById(R.id.mycart);
        i3=(ImageView)my_v.findViewById(R.id.recommend);
        i1.setOnClickListener(this);
        i2.setOnClickListener(this);
        i3.setOnClickListener(this);
        pd=new ProgressDialog(my_v.getContext());
        pd.setMessage("please wait...");
        Firebase.setAndroidContext(my_v.getContext());
        select();
        return my_v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buy:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                int commit = fm.beginTransaction().replace(R.id.content_frame, new Buy()).addToBackStack("Consumer").commit();
                break;
            case R.id.mycart:
                FragmentManager fm1 = getActivity().getSupportFragmentManager();
                int commit1 = fm1.beginTransaction().replace(R.id.content_frame, new cart()).addToBackStack("Consumer").commit();
                break;
            case  R.id.recommend:
                FragmentManager fm2 = getActivity().getSupportFragmentManager();
                int commit2= fm2.beginTransaction().replace(R.id.content_frame, new Recommend()).addToBackStack("Consumer").commit();
                break;

        }
    }
    void select() {

        try {
            email = TEST.email;
            pd.show();
        //    Toast.makeText(getContext(), "Email = " + email, Toast.LENGTH_SHORT).show();
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
          //                              Toast.makeText(getContext(), "Phone = " + p1.getPhone(), Toast.LENGTH_SHORT).show();
                                        pd.dismiss();
                                        TEST.phone = p1.getPhone();
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
            //Toast.makeText(this.getContext(), "" + e, Toast.LENGTH_SHORT).show();
        }

    }
}


