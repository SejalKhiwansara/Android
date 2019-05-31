package com.example.madhura.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AddProduct extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    Firebase ref;
    ImageView cimg;
    EditText e2,e3;
    TextView t1,t2;
    //Button b;
    Spinner s;
    View my_v;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.addproduct, container, false);
        e2=(EditText)my_v.findViewById(R.id.cprice);
        e3=(EditText)my_v.findViewById(R.id.desc);
        t1=(TextView)my_v.findViewById(R.id.addp);
        s=(Spinner)my_v.findViewById(R.id.spinner);
        t2=(TextView)my_v.findViewById(R.id.addproduct);
        t1.setOnClickListener(this);
        t2.setOnClickListener(this);
        cimg=(ImageView)my_v.findViewById(R.id.cimg);
        List<String> list = new ArrayList<>();
        list.add("--Select crop--");
        list.add("Bajra");
        list.add("Brinjal");
        list.add("Coriander");
        list.add("Cotton");
        list.add("Green chillies");
        list.add("Green peas");
        list.add("Groundnut");
        list.add("Jowar");
        list.add("Maize");
        list.add("Mango");
        list.add("Mustard");
        list.add("Onion");
        list.add("Potato");
        list.add("Soyabean");
        list.add("Tomato");
        list.add("Toor dal");
        list.add("Udad dal");
        list.add("Wheat");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(my_v.getContext(),
                android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(dataAdapter);

        Firebase.setAndroidContext(my_v.getContext());
        //ref = new Firebase("https://myprj-74da3.firebaseio.com");
        ref=new Firebase("https://farm-database-c29f5.firebaseio.com");
        return my_v;
    }

    String cname,cprice,img,cdes,phone;
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.addp:

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
                break;

            case R.id.addproduct:
                cprice=e2.getText().toString();
                cdes=e3.getText().toString();
                int x=s.getSelectedItemPosition();
                String y=s.getSelectedItem().toString();
                GetterSetter1 ob=new GetterSetter1();
                ob.setCimg(img);
                ob.setCname(y);
                ob.setCmob(TEST.phone);
                ob.setCprice(cprice);
                ob.setCdes(cdes);
                ref.child("Farmer").child(TEST.phone).child("Cropinfo").child(""+x).setValue(ob);
                Toast.makeText(my_v.getContext(), "Product Added", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 1 && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                // Get the cursor
                Cursor cursor = my_v.getContext().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
                Toast.makeText( my_v.getContext(), "", Toast.LENGTH_SHORT).show();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imageView = (ImageView) my_v.findViewById(R.id.cimg);
                imageView.setDrawingCacheEnabled(true);
                // Set the Image in ImageView after decoding the String
                imageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                Bitmap bmp = BitmapFactory.decodeFile(imgDecodableString);
                img = BitMapToString(bmp);
                //write code for firebase
                Toast.makeText(my_v.getContext(), "Image inserted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText( my_v.getContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText( my_v.getContext(), ""+e, Toast.LENGTH_LONG)
                    .show();
        }
    }


    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      String item=parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), ""+item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
