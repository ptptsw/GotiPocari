package com.example.project1.ui.phonebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project1.R;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    private ImageView photo;
    private TextView name;
    private TextView number;
    private TextView email;

    public ArrayList<JsonData> getListViewItemList() {
        return listViewItemList;
    }

    private ArrayList<JsonData> listViewItemList = new ArrayList<JsonData>();



    public void addItem(String name, String number, String email, int photo)
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
        final int pos = position;
        final Context context = parent.getContext();


        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_phonebook_listview,parent,false);
        }

        name = (TextView) convertView.findViewById(R.id.name);
        number  = (TextView) convertView.findViewById(R.id.number);
        email  = (TextView) convertView.findViewById(R.id.email);
        photo = (ImageView) convertView.findViewById(R.id.photo);

        JsonData listViewItem = listViewItemList.get(position);

        name.setText(listViewItem.getName());
        number.setText(listViewItem.getNumber());
        email.setText(listViewItem.getEmail());
        photo.setImageResource(listViewItem.getPhoto());

        return convertView;
    }
}