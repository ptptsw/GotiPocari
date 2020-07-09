package com.example.project1.ui.phonebook;

import android.content.Context;
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

    private ArrayList<SampleData> listViewItemList = new ArrayList<SampleData>();



    public void addItem(String name, String number, int photo)
    {
        SampleData item = new SampleData();

        item.setName(name);
        item.setNumber(number);
        item.setPhoto(photo);

        listViewItemList.add(item);
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
    public SampleData getItem(int position) {
        return listViewItemList.get(position);
    }

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
        photo = (ImageView) convertView.findViewById(R.id.photo);

        SampleData listViewItem = listViewItemList.get(position);

        name.setText(listViewItem.getName());
        number.setText(listViewItem.getNumber());
        photo.setImageResource(listViewItem.getPhoto());

        return convertView;
    }
}