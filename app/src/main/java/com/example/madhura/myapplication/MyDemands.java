package com.example.madhura.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyDemands extends Fragment {
    View my_v;
    List<GetterSetter4>list;
    RecyclerView recyclerview;
    Firebase ref;
    Query q;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.mydemands, container, false);
        recyclerview = (RecyclerView) my_v.findViewById(R.id.rview4);
        Firebase.setAndroidContext(my_v.getContext());
        try {

            //Toast.makeText(getContext(), "Email = " + email, Toast.LENGTH_SHORT).show();

            //ref = new Firebase("https://myprj-74da3.firebaseio.com/Orders/"+TEST.phone+"/");
            ref=new Firebase("https://farm-database-c29f5.firebaseio.com/Orders/"+TEST.phone+"/");
            //Toast.makeText(my_v.getContext(), "https://myprj-74da3.firebaseio.com/Orders/"+TEST.phone, Toast.LENGTH_SHORT).show();
            //  Toast.makeText(my_v.getContext(), "", Toast.LENGTH_SHORT).show();
            q = ref.orderByKey();
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    list = new ArrayList<GetterSetter4>();
                    for (DataSnapshot sn : dataSnapshot.getChildren()) {
                        //to fetch single record
                        Toast.makeText(my_v.getContext(), ""+sn.getKey(), Toast.LENGTH_SHORT).show();

                        for (DataSnapshot sn1 : sn.getChildren()) {

                            for (DataSnapshot sn2 : sn1.getChildren()) {


                                try {
//                                ref = new Firebase("https://myprj-74da3.firebaseio.com/");

                                    GetterSetter4 p1 = sn2.getValue(GetterSetter4.class);
                                    //Listdata listdata = new Listdata();
                                    String cimg = p1.getCimg();
                                    String cname = p1.getCname();
                                    String cprice = p1.getCprice();
                                    //  String cmob=p1.get ;
                                    p1.setCimg(cimg);
                                    p1.setCname(cname);
                                    p1.setCprice(cprice);
                                    p1.setFmob(sn.getKey());
                                    Toast.makeText(my_v.getContext(), "" + sn2.getValue(), Toast.LENGTH_SHORT).show();
                                    //p1.setFmob(fmob);
                                    list.add(p1);
                                } catch (Exception e) {
                                    //Toast.makeText(my_v.getContext(), "" + e, Toast.LENGTH_SHORT).show();
                                }



                            }
                        }
                        RecyclerviewAdapter3 recycler = new RecyclerviewAdapter3((ArrayList<GetterSetter4>) list);
                        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(my_v.getContext());
                        recyclerview.setLayoutManager(layoutmanager);
                        recyclerview.setItemAnimator(new DefaultItemAnimator());
                        recyclerview.setAdapter(recycler);
                    }}

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        } catch (Exception e) {
            //Toast.makeText(this.getContext(), "" + e, Toast.LENGTH_SHORT).show();
        }

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
