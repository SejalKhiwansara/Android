package com.example.madhura.myapplication;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecyclerviewAdapter1 extends RecyclerView.Adapter<RecyclerviewAdapter1.MyHolder> {
    List<GetterSetter1> listdata;
    private Context context;

    public RecyclerviewAdapter1(List<GetterSetter1> listdata) {
        this.listdata = listdata;
    }

    View v;
    MyHolder myHolder;

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myview1, parent, false);
        v = view;
        myHolder = new MyHolder(view);
        context = view.getContext();
        return myHolder;
    }


    public void onBindViewHolder(MyHolder holder, final int position) {
        GetterSetter1 data = listdata.get(position);
        holder.cname.setText(data.getCname());
        holder.cprice.setText(data.getCprice());
        holder.cmob.setText(data.getCmob());
        holder.cimg.setImageBitmap(decode(data.getCimg()));

        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetterSetter1 st = listdata.get(position);
                TEST.st=st;
                FragmentManager fm2 =MainActivity.ob.getSupportFragmentManager();
                int commit2 = fm2.beginTransaction().replace(R.id.content_frame, new viewdetails()).addToBackStack("RecyclerviewAdapter1").commit();
                Toast.makeText(v.getContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        TextView cname, cprice, cmob;
        ImageView cimg;
        TextView b1;


        public MyHolder(View itemView) {
            super(itemView);
            cname = (TextView) itemView.findViewById(R.id.cname);
            cprice = (TextView) itemView.findViewById(R.id.cpr);
            cmob = (TextView) itemView.findViewById(R.id.cmob);
            cimg = (ImageView) itemView.findViewById(R.id.profile_image);
            b1 = (TextView) itemView.findViewById(R.id.buy);
            //   b2 = (TextView) itemView.findViewById(R.id.addtocart);
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
}



