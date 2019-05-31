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

import java.util.ArrayList;
import java.util.List;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class ViewList extends Fragment  {
    View my_v;
    List<GetterSetter1> list;
    RecyclerView recyclerview;
    Firebase ref;
    Query q;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.viewlist, container, false);
        recyclerview = (RecyclerView) my_v.findViewById(R.id.rview);
        Firebase.setAndroidContext(my_v.getContext());
        try {
            //Toast.makeText(getContext(), "Email = " + email, Toast.LENGTH_SHORT).show();
            //ref = new Firebase("https://myprj-74da3.firebaseio.com/Farmer/"+TEST.phone+"/Cropinfo");
            ref=new Firebase("https://farm-database-c29f5.firebaseio.com/Farmer/"+TEST.phone+"/Cropinfo");
            q = ref.orderByKey();
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    list = new ArrayList<GetterSetter1>();
                    for (DataSnapshot sn : dataSnapshot.getChildren()) {
                        //to fetch single record
                            try {
                                GetterSetter1 p1 = sn.getValue(GetterSetter1.class);
                                //Listdata listdata = new Listdata();
                                String cimg=p1.getCimg();
                                String cname = p1.getCname();
                                String cprice =p1.getCprice();
                                String cdes =p1.getCdes() ;
                                p1.setCimg(cimg);
                                p1.setCname(cname);
                                p1.setCprice(cprice);
                                p1.setCdes(cdes);
                                list.add(p1);
                            } catch (Exception e) {
                                //Toast.makeText(my_v.getContext(), "" + e, Toast.LENGTH_SHORT).show();
                            }
                        RecyclerviewAdapter recycler = new RecyclerviewAdapter(list);
                        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(my_v.getContext());
                        recyclerview.setLayoutManager(layoutmanager);
                        recyclerview.setItemAnimator( new DefaultItemAnimator());
                        recyclerview.setAdapter(recycler);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        } catch (Exception e) {
            //Toast.makeText(this.getContext(), "" + e, Toast.LENGTH_SHORT).show();
        }
        return my_v;
    }
}
