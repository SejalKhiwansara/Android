package com.example.madhura.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class Recommend extends Fragment {
    View my_v;
    Firebase ref;
    Query q;
    ListView lv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.recommend, container, false);
        lv = (ListView) my_v.findViewById(R.id.lv);
       // lv.setBackgroundResource(R.drawable.list_color);
        //Toast.makeText(my_v.getContext(), "in recommendation", Toast.LENGTH_SHORT).show();
        select();
        return my_v;
    }
    String fmobnumber = "";
    TreeMap<String,Double> tmap;
    void select() {
        try {
            //ref = new Firebase("https://myprj-74da3.firebaseio.com/Farmer/");
            ref=new Firebase("https://farm-database-c29f5.firebaseio.com/Farmer/");
            tmap=new TreeMap<>();
            q = ref.orderByKey();
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    long totalSum = 0;
                    for (DataSnapshot sn : dataSnapshot.getChildren()) {
                        double avg=0,total=0,i=0;
                        fmobnumber = sn.getKey();
                        for (DataSnapshot sn1 : sn.getChildren()) {
                            try{
                                if (sn1.getKey().equals("Ratings")) {
                                    for (DataSnapshot sn2 : sn1.getChildren()) {
                                        total+=Double.parseDouble(sn2.getValue().toString().trim());
                                        i++;
                                    }
                                    avg=total/i;
                                    tmap.put(fmobnumber,avg);
                                }
                            }catch (Exception e){}
                            SortedSet<Map.Entry<String,Double>> sorted=entriesSortedByValues(tmap);
                            String arr[]=new String[sorted.size()];
                            int j=0;
                            for (Map.Entry<String, Double> entry : sorted)
                            {
                                String key = entry.getKey();
                                Double value = entry.getValue();
                                arr[j]="         "+key+"         "+":"+"             "+value;
                                j++;
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(my_v.getContext(), android.R.layout.simple_list_item_1, arr);
                            lv.setAdapter(adapter);
                           // Log.d("Mydata",""+sorted);
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) { }
            });


        } catch (Exception e) { }
    }
    static <K,V extends Comparable<? super V>>
    SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
                new Comparator<Map.Entry<K,V>>() {
                    @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                        int res = e2.getValue().compareTo(e1.getValue());
                        return res != 0 ? res : 1;
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
}

