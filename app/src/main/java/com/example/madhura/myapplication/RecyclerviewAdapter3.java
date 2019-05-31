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

import java.util.ArrayList;
import java.util.List;

public class RecyclerviewAdapter3 extends RecyclerView.Adapter<RecyclerviewAdapter3.MyHolder> {
    List<GetterSetter4> listdata;
    private Context context;
    public RecyclerviewAdapter3(ArrayList<GetterSetter4> listdata) {
        this.listdata = listdata;
    }

    View v;
    RecyclerviewAdapter3.MyHolder myHolder;


    @Override
    public RecyclerviewAdapter3.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myview3, parent, false);
        v = view;
        myHolder = new RecyclerviewAdapter3.MyHolder(view);
        context = view.getContext();
        return myHolder;
    }

    public void onBindViewHolder(final RecyclerviewAdapter3.MyHolder holder, final int position) {
        GetterSetter4 data = listdata.get(position);
        holder.cname.setText(data.getCname());
        holder.cprice.setText(data.getCprice());
        holder.cmob.setText(data.getFmob());
        holder.quant1.setText(data.getQuantity());

        // Log.i("Data ",data.getCimg());
        holder.cimg.setImageBitmap(decode(data.getCimg()));


    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        TextView cname, cprice, cmob,quant1;
        ImageView cimg;
        TextView b1;


        public MyHolder(View itemView) {
            super(itemView);
            quant1=(TextView)itemView.findViewById(R.id.quant1);
            cname = (TextView) itemView.findViewById(R.id.cname);
            cprice = (TextView) itemView.findViewById(R.id.cpr);
            cmob = (TextView) itemView.findViewById(R.id.cmob);
            cimg = (ImageView) itemView.findViewById(R.id.profile_image);
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




