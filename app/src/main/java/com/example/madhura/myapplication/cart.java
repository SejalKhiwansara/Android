package com.example.madhura.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class cart extends Fragment implements View.OnClickListener {
    View my_v;
    TEST st;
    TextView b;
    Firebase ref;
    Query q;
    public static TextView sum;
    RecyclerView recyclerview;
    List<GetterSetter4> list;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.mycart, container, false);
        Firebase.setAndroidContext(my_v.getContext());
        //Toast.makeText(my_v.getContext(), "in a cart", Toast.LENGTH_SHORT).show();
        int i;
        recyclerview= (RecyclerView) my_v.findViewById(R.id.rview3);
        b=(TextView) my_v.findViewById(R.id.buy);
        sum=(TextView)my_v.findViewById(R.id.ts);
        try {
            //ref = new Firebase("https://myprj-74da3.firebaseio.com/AddToCart/"+TEST.phone);
            ref=new Firebase("https://farm-database-c29f5.firebaseio.com/AddToCart/"+TEST.phone);
            q = ref.orderByKey();
            //Toast.makeText(my_v.getContext(), "https://myprj-74da3.firebaseio.com/AddToCart/"+TEST.phone, Toast.LENGTH_SHORT).show();
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    list = new ArrayList<GetterSetter4>();
                    for (DataSnapshot sn : dataSnapshot.getChildren()) {
                        //to fetch single record
                        for (DataSnapshot sn1:sn.getChildren()) {
                                String name = sn1.getKey();
                                Toast.makeText(my_v.getContext(), "" + name, Toast.LENGTH_SHORT).show();
                                try {
                                    GetterSetter4 p1 = sn1.getValue(GetterSetter4.class);
                                    //Listdata listdata = new Listdata();
                                    String cimg = p1.getCimg();
                                    String cname = p1.getCname();
                                    String cprice = p1.getCprice();
                                    String quant1 = p1.getQuantity();
                                    String fmob=p1.getFmob();
                                    p1.setCimg(cimg);
                                    p1.setCname(cname);
                                    p1.setCprice(cprice);
                                    p1.setQuantity(quant1);
                                    p1.setFmob(fmob);
                                    list.add(p1);
                                   // Toast.makeText(my_v.getContext(), "" + sn1.getValue(), Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    //Toast.makeText(my_v.getContext(), "" + e, Toast.LENGTH_SHORT).show();
                                }
                        }

                        RecyclerviewAdapter2 recycler = new RecyclerviewAdapter2((ArrayList<GetterSetter4>) list);
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
        b.setOnClickListener(this);
        return my_v;
    }

    String cimg,cname,total,fmob,cquant;
    @Override
    public void onClick(View view) {
       Toast.makeText(my_v.getContext(), "Order placed..!!", Toast.LENGTH_SHORT).show();
       // ref = new Firebase("https://myprj-74da3.firebaseio.com/");
        ref=new Firebase("https://farm-database-c29f5.firebaseio.com/");
        Calendar c=Calendar.getInstance();
        int d=c.get(Calendar.DAY_OF_MONTH);
        int m=c.get(Calendar.MONTH);
        int y=c.get(Calendar.YEAR);
        SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
        m++;
        String str =y+"-"+m+"-"+d;
        GetterSetter4 ob;
        int i;
        //Toast.makeText(my_v.getContext(), ""+list.size(), Toast.LENGTH_SHORT).show();
        for(i=0;i<list.size();i++){
          ref.child("Orders").child(list.get(i).getFmob()).child(TEST.phone).child(str).child(String.valueOf(i)).setValue(list.get(i));
        }
        TEST.list.clear();

        RecyclerviewAdapter2 recycler = new RecyclerviewAdapter2((ArrayList<GetterSetter4>) TEST.list);
        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(my_v.getContext());
        recyclerview.setLayoutManager(layoutmanager);
        recyclerview.setItemAnimator( new DefaultItemAnimator());
        recyclerview.setAdapter(recycler);

    }

}
