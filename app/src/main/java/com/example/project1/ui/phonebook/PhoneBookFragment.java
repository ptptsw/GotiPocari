package com.example.project1.ui.phonebook;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project1.R;
import com.example.project1.ui.notifications.NotificationsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PhoneBookFragment extends Fragment {

    private PhoneBookViewModel phoneBookViewModel;
    private Adapter adapter;
    //private ArrayList<JsonData> contactlist;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        phoneBookViewModel = ViewModelProviders.of(this).get(PhoneBookViewModel.class);
        View root = inflater.inflate(R.layout.fragment_phonebook, container, false);





        adapter = new Adapter();
        ListView listview = (ListView)root.findViewById(R.id.listView);
        listview.setAdapter(adapter);

        //

        String json = this.getJsonString();

        try{
            JSONObject jsonObject = new JSONObject(json);

            JSONArray Array = jsonObject.getJSONArray("Contacts");

            for(int i=0; i< Array.length(); i++)
            {
                JSONObject Object = Array.getJSONObject(i);


                adapter.addItem(Object.getString("name"), Object.getString("number"),Object.getString("email"), R.drawable.ic__);
            }
        }catch (JSONException e) {
           // System.out.println("fqwefffffffffffffffffffffffffffffffffffffffffffffffffff");
            e.printStackTrace();
        }


       /* jsonParsing(json); //contact list에 추가 완료*/




        adapter.notifyDataSetChanged();




        return root;
    }

    private String getJsonString()
    {
        String json = "";

        try {
            InputStream is = this.getContext().getAssets().open("contact_list.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return json;
    }



    private void jsonParsing(String json)
    {
        try{
            JSONObject jsonObject = new JSONObject(json);

            JSONArray Array = jsonObject.getJSONArray("Contacts");

            for(int i=0; i< Array.length(); i++)
            {
                JSONObject Object = Array.getJSONObject(i);

                JsonData data = new JsonData();

                data.setName(Object.getString("name"));
                data.setNumber(Object.getString("number"));
                data.setPhoto(Object.getInt("photo"));


            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}




