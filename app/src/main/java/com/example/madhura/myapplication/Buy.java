package com.example.madhura.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Buy extends Fragment implements AdapterView.OnItemSelectedListener {

    View my_v;
    SearchView t;
    List<GetterSetter1> list,list2,l1;
    List<GetterSetter> list1;
    RecyclerView recyclerview;
    Firebase ref;
    Query q;
    Spinner s1;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.buy, container, false);
        recyclerview= (RecyclerView) my_v.findViewById(R.id.rview1);
        t=(SearchView) my_v.findViewById(R.id.search);
        s1 = (Spinner) my_v.findViewById(R.id.filter);
        List<String> l = new ArrayList<>();
        l.add("Filter");
        l.add("Low To High");
        l.add("High to Low");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(my_v.getContext(),
                android.R.layout.simple_spinner_dropdown_item, l);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(dataAdapter);
        s1.setOnItemSelectedListener(this);
        t.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showFilteredItems(query);
                // Toast.makeText(my_v.getContext(), ""+query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String userInput=newText.toLowerCase();
                return false;
            }
        });
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        Firebase.setAndroidContext(my_v.getContext());
        try {
            //ref = new Firebase("https://myprj-74da3.firebaseio.com/Farmer");
            ref=new Firebase("https://farm-database-c29f5.firebaseio.com/Farmer");
            q = ref.orderByKey();
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    list = new ArrayList<GetterSetter1>();
                    list1 = new ArrayList<GetterSetter>();
                    for (DataSnapshot sn : dataSnapshot.getChildren()) {
                        //to fetch single record
                        for (DataSnapshot sn1 : sn.getChildren()) {
                            //      for (DataSnapshot sn2 : sn1.getChildren()) {
                            String name = sn1.getKey();
                            //  Toast.makeText(my_v.getContext(), ""+name, Toast.LENGTH_SHORT).show();
                            if (name.equals("Cropinfo")) {
                                try {
                                    for (DataSnapshot sn2 : sn1.getChildren()) {
                                        GetterSetter1 p1 = sn2.getValue(GetterSetter1.class);
                                        //Listdata listdata = new Listdata();
                                        String cimg = p1.getCimg();
                                        String cname = p1.getCname();
                                        String cprice = p1.getCprice();
                                        String cdes = p1.getCdes();
                                        p1.setCimg(cimg);
                                        p1.setCname(cname);
                                        p1.setCprice(cprice);
                                        p1.setCdes(cdes);
                                        list.add(p1);
                                        //Toast.makeText(my_v.getContext(), "" + cname, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {

                                }
                            }

                        }
                        RecyclerviewAdapter1 recycler = new RecyclerviewAdapter1(list);
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
    void showFilteredItems(String query){
        list2 = new ArrayList<GetterSetter1>();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getCname().toLowerCase().equals(query.toLowerCase())){
                list2.add(list.get(i));
            }
        }
        RecyclerviewAdapter1 recycler = new RecyclerviewAdapter1(list2);
        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(my_v.getContext());
        recyclerview.setLayoutManager(layoutmanager);
        recyclerview.setItemAnimator( new DefaultItemAnimator());
        recyclerview.setAdapter(recycler);    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==1){
            try {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "" + item, Toast.LENGTH_SHORT).show();
                l1 = new ArrayList<GetterSetter1>();
                l1 = list2;
                Collections.sort(l1, new Comparator<GetterSetter1>() {
                    @Override
                    public int compare(GetterSetter1 t1, GetterSetter1 t2) {
                        Integer a = Integer.parseInt(t1.getCprice().trim());
                        Integer b = Integer.parseInt(t2.getCprice().trim());
                        Log.d("My Data", "" + a.compareTo(b));
                        return a.compareTo(b);
                    }


                });

                Log.d("Sorted List is :", "" + l1);
                RecyclerviewAdapter1 recycler = new RecyclerviewAdapter1(l1);
                RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(my_v.getContext());
                recyclerview.setLayoutManager(layoutmanager);
                recyclerview.setItemAnimator(new DefaultItemAnimator());
                recyclerview.setAdapter(recycler);
                // }

            } catch (Exception e) {

            }

        }
        if(position==2){
            try {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "" + item, Toast.LENGTH_SHORT).show();
                l1 = new ArrayList<GetterSetter1>();
                l1 = list2;
                Collections.sort(l1, new Comparator<GetterSetter1>() {
                    @Override
                    public int compare(GetterSetter1 t1, GetterSetter1 t2) {
                        Integer a = Integer.parseInt(t1.getCprice().trim());
                        Integer b = Integer.parseInt(t2.getCprice().trim());
                        Log.d("My Data", "" + a.compareTo(b));
                        return a.compareTo(b);
                    }


                });
                Collections.reverse(l1);
                Log.d("Sorted List is :", "" + l1);
                RecyclerviewAdapter1 recycler = new RecyclerviewAdapter1(l1);
                RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(my_v.getContext());
                recyclerview.setLayoutManager(layoutmanager);
                recyclerview.setItemAnimator(new DefaultItemAnimator());
                recyclerview.setAdapter(recycler);


            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
