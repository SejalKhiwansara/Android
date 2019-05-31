package com.example.madhura.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class viewdetails extends Fragment implements View.OnClickListener, RatingBar.OnRatingBarChangeListener {

    View my_v;
    ImageView i1;
    TextView a;
    TextView b;
    TextView c;
    TextView d;
    TextView e;
    TextView f;
    TextView g;
    TextView h;
    Object i;
    TextView j;
    TextView k;
    TextView l;
    TextView cmnt;
    TextView rat1, rat2;
    TextView b1, b2;
    RatingBar r;
    Firebase ref;
    EditText e2, e3, e4;
    TextView e1;
    Query q;
    GetterSetter4 ob = new GetterSetter4();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.viewdetails, container, false);
        Firebase.setAndroidContext(my_v.getContext());
        cmnt = (TextView) my_v.findViewById(R.id.comment);
        rat1 = (TextView) my_v.findViewById(R.id.rating);
        rat2 = (TextView) my_v.findViewById(R.id.rate);
        e3 = (EditText) my_v.findViewById(R.id.cmnt);
        e4 = (EditText) my_v.findViewById(R.id.viewcmnt);
        b2 = (TextView) my_v.findViewById(R.id.post);
        r = (RatingBar) my_v.findViewById(R.id.ratingbar);
        i1 = (ImageView) my_v.findViewById(R.id.cimg);
        a = (TextView) my_v.findViewById(R.id.crname);
        b = (TextView) my_v.findViewById(R.id.cname);
        c = (TextView) my_v.findViewById(R.id.cpr);
        d = (TextView) my_v.findViewById(R.id.cprice);
        e = (TextView) my_v.findViewById(R.id.desc);
        f = (TextView) my_v.findViewById(R.id.des1);
        g = (TextView) my_v.findViewById(R.id.fname);
        h = (TextView) my_v.findViewById(R.id.frname);
        i = (TextView) my_v.findViewById(R.id.phn);
        j = (TextView) my_v.findViewById(R.id.phno);
        k = (TextView) my_v.findViewById(R.id.location);
        l = (TextView) my_v.findViewById(R.id.location1);
        e1 = (TextView) my_v.findViewById(R.id.quant1);
        b1 = (TextView) my_v.findViewById(R.id.addtocart);
        e2 = (EditText) my_v.findViewById(R.id.quant1);
        i1.setImageBitmap(decode(TEST.st.getCimg()));
        select(TEST.st.getCmob());
        ob.setCname(TEST.st.getCname());
        b.setText(TEST.st.getCname());
        ob.setCprice(TEST.st.getCprice());
        d.setText(TEST.st.getCprice());
        ob.setCimg(TEST.st.getCimg());
        f.setText(TEST.st.getCdes());
        ob.setCdes(TEST.st.getCdes());
        j.setText(TEST.st.getCmob());
        ob.setFmob(TEST.st.getCmob());
        String q1 = e2.getText().toString();
        b2.setOnClickListener(this);
        //ref = new Firebase("https://myprj-74da3.firebaseio.com");
        ref=new Firebase("https://farm-database-c29f5.firebaseio.com/");
        r.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //ref = new Firebase("https://myprj-74da3.firebaseio.com/");
                ref=new Firebase("https://farm-database-c29f5.firebaseio.com/");
                ref.child("Farmer").child(TEST.st.getCmob()).child("Ratings").child(TEST.phone).setValue((int) rating);
                Toast.makeText(my_v.getContext(), "Stars: " + (int) rating, Toast.LENGTH_SHORT).show();
            }
        });

        ob.setQuantity(q1);
        //Toast.makeText(my_v.getContext(), "quantity is added!", Toast.LENGTH_SHORT).show();
        b1.setOnClickListener(this);
        try {
            int x = 0;
            switch (TEST.st.getCname()) {
                case "Bajra":
                    x = 1;
                    break;
                case "Jowar":
                    x = 2;
                    break;
                case "Maze":
                    x = 3;
                    break;
                case "Rice":
                    x = 4;
                    break;
                case "Wheat":
                    x = 5;
                    break;
                case "Cotton":
                    x = 6;
                    break;
                case "Soyabean":
                    x = 7;
                    break;
                case "Sugarcane":
                    x = 8;
                    break;
                case "Groundnut":
                    x = 9;
                    break;
                case "Pulses":
                    x = 10;
                    break;
                case "Sunflower":
                    x = 11;
                    break;

            }
            //ref = new Firebase("https://myprj-74da3.firebaseio.com/Farmer/" + TEST.st.getCmob() + "/Cropinfo/" + x);
            ref=new Firebase("https://farm-database-c29f5.firebaseio.com/Farmer/"+TEST.st.getCmob()+"/Cropinfo/"+x);
            q = ref.orderByKey();
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Bitmap ob1;
                    GetterSetter1 p1 = dataSnapshot.getValue(GetterSetter1.class);
//                    String image=p1.getCimg();
  //                  ob.setCimg(image);
    //                ob1=decode(image);
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });

        } catch (Exception e) {
        }

        try {
            //ref = new Firebase("https://myprj-74da3.firebaseio.com/Farmer/" + TEST.st.getCmob() + "");
            ref=new Firebase("https://farm-database-c29f5.firebaseio.com/Farmer/"+TEST.st.getCmob()+"");
            q = ref.orderByKey();
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot sn : dataSnapshot.getChildren()) {
                        Log.i("Error", sn.getKey());
                        if (sn.getKey().equals("Comments")) {
                            Log.i("Error", "In if sta" + sn.getValue());
                            try {
                                   e4.setText(sn.getValue().toString());

                            } catch (Exception e) { }
                        }
                    }
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        } catch (Exception e) {
        }
        try {
            //ref = new Firebase("https://myprj-74da3.firebaseio.com/Farmer/" + TEST.st.getCmob() + "");
            ref=new Firebase("https://farm-database-c29f5.firebaseio.com/Farmer/"+TEST.st.getCmob()+"");
            q = ref.orderByKey();
            //  Log.i("Myeror", "https://myprj-74da3.firebaseio.com/Farmer/" + TEST.st.getCmob() + "/Ratings");
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    long totalSum = 0;
                    for (DataSnapshot sn : dataSnapshot.getChildren()) {
                        Log.i("Error", sn.getKey());
                        if (sn.getKey().equals("Ratings")) {
                            Log.i("Error", "In if sta" + sn.getValue());
                            try {
                                for (DataSnapshot sn1 : sn.getChildren()) {
                                    totalSum = totalSum + (long) sn1.getValue();
//                                    Toast.makeText(my_v.getContext(), "Totalsum=" + totalSum, Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(my_v.getContext(), "" + totalSum, Toast.LENGTH_SHORT).show();
                                // Toast.makeText(my_v.getContext(), "size="+(float) sn.getChildrenCount(), Toast.LENGTH_SHORT).show();
                                float avg = totalSum / (float) sn.getChildrenCount();
                                Toast.makeText(my_v.getContext(), "" + avg, Toast.LENGTH_SHORT).show();
                                rat2.setText("" + avg);

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
        }

        return my_v;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.addtocart:
                //ref = new Firebase("https://myprj-74da3.firebaseio.com/");
                ref=new Firebase("https://farm-database-c29f5.firebaseio.com/");
                int x = 0;
                //String rating=String.valueOf(r.getRating());
                //Toast.makeText(my_v.getContext(), ""+rating, Toast.LENGTH_SHORT).show();
                switch (TEST.st.getCname()) {
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

                if (e2.equals("")) {
                    e2.setError("Quantity is required");
                }
                ob.setQuantity(e2.getText().toString());
                TEST.list.add(ob);

                ref.child("AddToCart").child(TEST.phone).child(TEST.st.getCmob()).child(String.valueOf(x)).setValue(ob);

                //ref = new Firebase("https://myprj-74da3.firebaseio.com/");
                Toast.makeText(my_v.getContext(), "Product added!!", Toast.LENGTH_SHORT).show();
              FragmentManager fm2 = getActivity().getSupportFragmentManager();
              int commit2 = fm2.beginTransaction().replace(R.id.content_frame, new Buy()).addToBackStack("RecyclerviewAdapter1").commit();
                break;
            case R.id.post:
                String comments = ob.setComment(e3.getText().toString());
                //ref = new Firebase("https://myprj-74da3.firebaseio.com/");
                ref=new Firebase("https://farm-database-c29f5.firebaseio.com/");
                ref.child("Farmer").child(TEST.st.getCmob()).child("Comments").child(TEST.phone).setValue(comments);
                Toast.makeText(my_v.getContext(), "Your Comment has been Posted..!!", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//firebase code
        String s;
        GetterSetter2 ob = new GetterSetter2();
        s = "" + v;
        ref.child("Farmer").child("Rating").setValue(ob);
    }

    public Bitmap decode(String imageString) {
        try {
            //decode base64 string to image
            byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            return decodedImage;
        } catch (Exception e) {
            //Toast.makeText(my_v.getContext(), "" + e, Toast.LENGTH_LONG).show();
        }
        return null;
    }

    void select(String num) {
        try {
            //ref = new Firebase("https://myprj-74da3.firebaseio.com/Farmer/"+num+"/Personalinfo");
            ref=new Firebase("https://farm-database-c29f5.firebaseio.com/Farmer/"+num+"/Personalinfo");
            q = ref.orderByKey();
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //to fetch single record
                    GetterSetter ob=dataSnapshot.getValue(GetterSetter.class);
                    //     ob.setName(TEST.st.get);
                    ob.setName(ob.getName());
                    h.setText(ob.getName());
                    ob.setAddress(ob.getAddress());
                    l.setText(ob.getAddress());
                    // Toast.makeText(my_v.getContext(), ""+ob.getName(), Toast.LENGTH_SHORT).show();
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

