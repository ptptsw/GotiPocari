package com.example.project1.ui.phonebook;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.project1.MainActivity;
import com.example.project1.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class Adapter extends BaseAdapter {

    private ImageView photo;
    private TextView name;
    private TextView number;
    private TextView email;
    private ArrayList<JsonData> listViewItemList;
    private Context context;
    private LayoutInflater layoutInflater;


    public Adapter() {
        listViewItemList = new ArrayList<JsonData>();
    }

    public Adapter(ArrayList<JsonData> itemList, Context context) {
        super();
        this.listViewItemList = itemList == null ? new ArrayList<JsonData>() : itemList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public ArrayList<JsonData> getListViewItemList() {
        return listViewItemList;
    }

    public void addItem(String name, String number, String email, Uri photo)
    {
        JsonData data = new JsonData();

        data.setName(name);
        data.setNumber(number);
        data.setEmail(email);
        data.setPhoto(photo);

        listViewItemList.add(data);
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public JsonData getItem(int position) {
        return listViewItemList.get(position);
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        final int pos = position;
        convertView = layoutInflater.inflate(R.layout.fragment_phonebook_listview, parent, false);
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_phonebook_listview,parent,false);
        }

        name = convertView.findViewById(R.id.name);
        number = convertView.findViewById(R.id.number);
        email = convertView.findViewById(R.id.email);
        photo = convertView.findViewById(R.id.photo);

        JsonData listViewItem = listViewItemList.get(position);

        name.setText(listViewItem.getName());
        number.setText(listViewItem.getNumber());
        email.setText(listViewItem.getEmail());
        photo.setImageURI(listViewItem.getPhoto());

        ImageButton callButton= (ImageButton)convertView.findViewById(R.id.callButton);
        //ConstraintLayout cmdArea= (ConstraintLayout)convertView.findViewById(R.id.listview_layout);
        callButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                JsonData data = listViewItemList.get(pos);






                Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+data.getNumber()));
                context.startActivity(call);
            }
        });


        return convertView;
    }




}