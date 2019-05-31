package com.example.madhura.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    ProgressDialog pd;
   public static MainActivity ob;
    ImageView iv;
    String phone,img;
    Firebase ref,ref1;
    Integer REQUEST_CAMERA=2;


    Query q;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        navigationView1.setNavigationItemSelectedListener(this);
        View headerView = navigationView1.getHeaderView(0);
        iv=(ImageView)headerView.findViewById(R.id.imageView);
        iv.setOnClickListener(this);
        TextView navemail = (TextView) headerView.findViewById(R.id.femail);
        navemail.setText(TEST.email);
        Firebase.setAndroidContext(this);


        //ref1=new Firebase("https://myprj-74da3.firebaseio.com/");
        ref1=new Firebase("https://farm-database-c29f5.firebaseio.com/");
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        select();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager fm = getSupportFragmentManager();
        ob=this;
        if (TEST.type.equals("farmer")) {
            int commit = fm.beginTransaction().replace(R.id.content_frame, new Farmer()).commit();
        } else {
            int commit = fm.beginTransaction().replace(R.id.content_frame, new Consumer()).commit();
        }
    }

    public void select() {
        phone = TEST.phone;
        //ref = new Firebase("https://myprj-74da3.firebaseio.com/ProfileImage/");
        ref = new Firebase("https://farm-database-c29f5.firebaseio.com/ProfileImage");
        q = ref.orderByKey();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot sn : dataSnapshot.getChildren()) {
                    try {
                        GetterSetter p1 = sn.getValue(GetterSetter.class);
                        if (p1.getPhone().trim().equals(phone.trim())) {
                            TEST.pimg = p1.getPrimage();
                            iv.setImageBitmap(decode(TEST.pimg));

                        } else {
                            //Toast.makeText(MainActivity.this, "default image", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    public Bitmap decode(String imageString) {

        try {
            //decode base64 string to image
            byte[] imageBytes =Base64.decode(imageString,Base64.DEFAULT);
                    //Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
                   // BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            // iv.setImageBitmap(decodedImage);
            return decodedImage;
        } catch (Exception e) {
            //  Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();}
        }
        return null;
    }

    private void selectImage(){
        final CharSequence[] item={"CAMERA","GALLERY","CANCEL"};
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("ADD IMAGE");
        builder.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(item[i].equals("CAMERA")){
//                    Intent it =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(it,REQUEST_CAMERA);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
                else if(item[i].equals("GALLERY")){
                    Intent it=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //it.setType("image/*");
                    startActivityForResult(it, 1);
                    // startActivityForResult(it.createChooser(it,"Select File"),SELECT_FILE);
                }
                else if(item[i].equals("CANCEL")){
                    dialogInterface.dismiss();;
                }
            }

        });
        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAMERA) {
//                Bundle bundle =data.getExtras();
//                final Bitmap bmp=(Bitmap)bundle.get("data");
//                iv.setImageBitmap(bmp);
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    iv.setImageBitmap(bitmap);
                    img=BitMapToString(bitmap);
                    add();
                    String path = android.os.Environment.getExternalStorageDirectory() + File.separator + "Phoenix" + File.separator + "default";
                    f.delete();
//                    OutputStream outFile = null;
//                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
//                    try {
//                        outFile = new FileOutputStream(file);
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
//                        outFile.flush();
//                        outFile.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //write code for firebase
                Toast.makeText(this, "image inserted", Toast.LENGTH_SHORT).show();



            }//else if(resultCode==SELECT_FILE) {
//                Uri uri=data.getData();
//                iv.setImageURI(uri);


            // When an Image is picked
            else if (requestCode == 1 && resultCode ==Activity.RESULT_OK
                    && null != data) {
                //Toast.makeText(this, "in 2nd block", Toast.LENGTH_SHORT).show();
                // Get the Image from data
                Uri selectedImage = data.getData();
                //iv.setImageURI(Uri.parse(img));
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                // Get the cursor
                Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
                Toast.makeText( this, "in gallery", Toast.LENGTH_SHORT).show();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                //iv = (ImageView)findViewById(R.id.imageView);
                iv.setDrawingCacheEnabled(true);
                // Set the Image in ImageView after decoding the String
                iv.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                Bitmap bmp = BitmapFactory.decodeFile(imgDecodableString);
                img = BitMapToString(bmp);
                add();
                //write code for firebase
                Toast.makeText(this, "image inserted", Toast.LENGTH_SHORT).show();





            } else {
                Toast.makeText( this, "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
            // }
        } catch (Exception e) {
            Toast.makeText( this, ""+e, Toast.LENGTH_LONG)
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm = getSupportFragmentManager();
        if (id == R.id.nav_first_layout) {
            int commit = fm.beginTransaction().replace(R.id.content_frame, new Home()).commit();
            // Handle the camera action
        } else if (id == R.id.nav_second_layout) {
            fm.beginTransaction().replace(R.id.content_frame, new Godown()).addToBackStack("MainActivity").commit();
        } else if (id == R.id.nav_third_layout) {
            fm.beginTransaction().replace(R.id.content_frame, new Dealers()).addToBackStack("MainActivity").commit();
        } else if (id == R.id.nav_fourth_layout) {
            fm.beginTransaction().replace(R.id.content_frame, new Cropdiseases()).addToBackStack("MainActivity").commit();
        } else if (id == R.id.nav_fifth_layout) {
            fm.beginTransaction().replace(R.id.content_frame, new Pesticides()).addToBackStack("MainActivity").commit();
        } else if (id == R.id.nav_share) {

            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage= "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }
        }  else if (id == R.id.nav_contactus) {
            fm.beginTransaction().replace(R.id.content_frame, new ContactUs()).addToBackStack("MainActivity").commit();


        } else if (id == R.id.nav_help) {
            //Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
        else if(id==R.id.nav_lang)
        {
            showLanguageChage();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void showLanguageChage() {
        final String[] listitems={"English","मराठी"};
        android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose Language");
        builder.setSingleChoiceItems(listitems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(i==0)
                {
                    setLocale("En");
                    recreate();
                }
                if(i==1)
                {
                    setLocale("Mr");
                    recreate();
                }
                dialog.dismiss();
            }
        });
        android.support.v7.app.AlertDialog at=builder.create();
        at.show();
    }

    private void setLocale(String lang) {
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor=getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }
    public void loadLocale()
    {
        SharedPreferences pref=getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language=pref.getString("My_Lang"," ");
        setLocale(language);
    }

    void add(){
        GetterSetter ob =new GetterSetter();
        ob.setPrimage(img);
        ob.setPhone(TEST.phone);
        ref1.child("ProfileImage").child(TEST.phone).setValue(ob);
    }

    @Override
    public void onClick(View v) {
        selectImage();

    }
}
