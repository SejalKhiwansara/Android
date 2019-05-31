package com.example.madhura.myapplication;

import android.content.Context;
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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.List;


public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyHolder> {

    List<GetterSetter1> listdata;
    private Context context;

    public RecyclerviewAdapter(List<GetterSetter1> listdata) {
        this.listdata = listdata;
    }

    View v;
    MyHolder myHolder;

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myview, parent, false);
        v = view;
        myHolder = new MyHolder(view);
        context = view.getContext();
        return myHolder;
    }


    public void onBindViewHolder(final MyHolder holder, final int position) {
        GetterSetter1 data = listdata.get(position);
        holder.cname.setText(data.getCname());
        holder.cprice.setText(data.getCprice());
        holder.cdes.setText(data.getCdes());
        // Log.i("Data ",data.getCimg());
        holder.cimg.setImageBitmap(decode(data.getCimg()));

        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetterSetter1 st = listdata.get(position);
                TEST.st = st;
                FragmentManager fm2 = MainActivity.ob.getSupportFragmentManager();
                int commit2 = fm2.beginTransaction().replace(R.id.content_frame, new Update()).addToBackStack("RecyclerviewAdapter").commit();
                Toast.makeText(v.getContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Firebase ref = new Firebase("https://myprj-74da3.firebaseio.com/Farmer/"+TEST.phone+"/Cropinfo/");
                Firebase ref=new Firebase("https://farm-database-c29f5.firebaseio.com/Farmer/"+TEST.phone+"/Cropinfo/");
                //Toast.makeText(context, "" + ref.child(String.valueOf(position)), Toast.LENGTH_SHORT).show();
                String name=listdata.get(position).getCname();
//                int id=0;
//                //Log.d("My data",name);
//                if(name.equals("Cotton")){
//                id=4;
//                    Log.d("My data",""+id);
//                }


                int x=0;
                switch (name)
                {
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


                Firebase taskRef = ref.child(""+x);
                taskRef.removeValue();
//                for (int i = 0; i < listdata.size(); i++) {
//
//                    Toast.makeText(context, "" + listdata.get(i).getCname(), Toast.LENGTH_SHORT).show();
//                }
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
        TextView cname, cprice, cdes;
        ImageView cimg;
        TextView b1, b2;


        public MyHolder(View itemView) {
            super(itemView);
            cname = (TextView) itemView.findViewById(R.id.cname);
            cprice = (TextView) itemView.findViewById(R.id.cpr);
            cdes = (TextView) itemView.findViewById(R.id.cdes);
            cimg = (ImageView) itemView.findViewById(R.id.profile_image);
            b1 = (TextView) itemView.findViewById(R.id.update);
            b2 = (TextView) itemView.findViewById(R.id.delete);
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



