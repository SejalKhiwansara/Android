package com.example.madhura.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

public class RecyclerviewAdapter2 extends RecyclerView.Adapter<RecyclerviewAdapter2.MyHolder> {
    List<GetterSetter4> listdata;
    private Context context;

    public RecyclerviewAdapter2(ArrayList<GetterSetter4> listdata) {
        this.listdata = listdata;
    }

    View v;
    RecyclerviewAdapter2.MyHolder myHolder;

    @Override
    public RecyclerviewAdapter2.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myview2, parent, false);
        v = view;
        myHolder = new RecyclerviewAdapter2.MyHolder(view);
        context = view.getContext();
        //Toast.makeText(context, ""+TEST.st.getCmob(), Toast.LENGTH_SHORT).show();
        return myHolder;
    }


    public void onBindViewHolder(final RecyclerviewAdapter2.MyHolder holder, final int position) {
        final GetterSetter4 data = listdata.get(position);
        holder.cname.setText(data.getCname());
        holder.fmob.setText(data.getFmob());
        holder.quant1.setText(data.getQuantity());
        int t1, t2;
        t1 = Integer.parseInt(data.getQuantity().trim());
        t2 = Integer.parseInt(data.getCprice());
        int total = t1 * t2;
        String result = Integer.toString(total);
        holder.cprice.setText(result);
        holder.cimg.setImageBitmap(decode(data.getCimg()));
//        int i,tsum=0;
//        for(i=0;i<listdata.size();i++){
//            tsum+=total;
//        }
//        Toast.makeText(context, ""+tsum, Toast.LENGTH_SHORT).show();
//        Log.d("","sum"+tsum);
        gettotal();

        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Firebase ref = new Firebase("https://myprj-74da3.firebaseio.com/AddToCart/" + TEST.phone + "/" + data.getFmob() + "/");
                Firebase ref=new Firebase("https://farm-database-c29f5.firebaseio.com/AddToCart/"+TEST.phone+"/"+data.getFmob()+"/");
                String name = listdata.get(position).getCname();
                int x = 0;
                switch (name) {
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


                Firebase taskRef = ref.child("" + x);
                taskRef.removeValue();
                for (int i = 0; i < listdata.size(); i++) {

                    // Toast.makeText(context, "" + listdata.get(i).getCname(), Toast.LENGTH_SHORT).show();
                }
                listdata.remove(position);
                notifyItemRemoved(position);
            }
        });
        }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        TextView cname, cprice, fmob, quant1, sum;
        ImageView cimg;
        TextView b1;


        public MyHolder(View itemView) {
            super(itemView);
            quant1 = (TextView) itemView.findViewById(R.id.quant1);
            cname = (TextView) itemView.findViewById(R.id.cname);
            cprice = (TextView) itemView.findViewById(R.id.cpr);
            fmob = (TextView) itemView.findViewById(R.id.fmob);
            cimg = (ImageView) itemView.findViewById(R.id.profile_image);
            b1 = (TextView) itemView.findViewById(R.id.remove);
            sum = (TextView) itemView.findViewById(R.id.ts);
        }
    }

    public Bitmap decode(String imageString) {
        try {
            //decode base64 string to image
            byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            return decodedImage;
        } catch (Exception e) {
            //Toast.makeText(v.getContext(), "" + e, Toast.LENGTH_LONG).show();
        }
        return null;
    }

    double gettotal() {
        double t = 0;
        int i;
        for (i = 0; i < listdata.size(); i++) {

            int p = Integer.parseInt(listdata.get(i).getCprice().trim());
            int q = Integer.parseInt(listdata.get(i).getQuantity().trim());
            int z = p * q;
            t = t + z;


        }
        //Log.d("total", "" + t);

        cart.sum.setText("Your total = "+t);

        return t;
    }
}




