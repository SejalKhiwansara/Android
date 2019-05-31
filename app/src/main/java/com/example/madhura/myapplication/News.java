package com.example.madhura.myapplication;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class News extends Fragment{
    View my_v;
    ListView lvRss;
    ArrayList<String> titles;
    ArrayList<String> links;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.news, container, false);
        lvRss = (ListView) my_v.findViewById(R.id.lvRss);
        titles = new ArrayList<String>();
        links = new ArrayList<String>();
        lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = Uri.parse(links.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        new ProcessInBackground().execute();
        return my_v;

    }
    public  InputStream getInputStream(URL url)
    {
        try
        {  return url.openConnection().getInputStream(); }
        catch (IOException e)
        { return null; }
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Uri uri = Uri.parse(links.get(position));
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);
//
//    }
//

    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception>
    {
        ProgressDialog progressDialog = new ProgressDialog(my_v.getContext());
        Exception exception = null;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog.setMessage("Busy loading rss feed...please wait...");
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... params) {
            try
            {
                URL url = new URL("http://indiatogether.org/rss/category/agriculture");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                int eventType = xpp.getEventType(); //loop control variable
                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        if (xpp.getName().equalsIgnoreCase("item"))
                        { insideItem = true; }
                        else if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            if (insideItem)
                            { titles.add(xpp.nextText()); }
                        }
                        else if (xpp.getName().equalsIgnoreCase("link"))
                        {
                            if (insideItem)
                            { links.add(xpp.nextText()); }
                        }
                    }
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    { insideItem = false; }
                    eventType = xpp.next(); //move to next element
                }


            }
            catch (MalformedURLException e)
            { exception = e; }
            catch (XmlPullParserException e)
            { exception = e; }
            catch (IOException e)
            { exception = e; }
            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);
           // Toast.makeText(my_v.getContext(), "before adapter", Toast.LENGTH_SHORT).show();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(my_v.getContext(), android.R.layout.simple_list_item_1, titles);
            //Toast.makeText(my_v.getContext(), ""+adapter, Toast.LENGTH_SHORT).show();
            lvRss.setAdapter(adapter);
            progressDialog.dismiss();
        }

    }

}

