package com.example.project1.ui.phonebook;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.project1.R;

import java.util.ArrayList;

public class Adapter extends BaseAdapter implements Filterable {
    private ImageView photo;
    private TextView name;
    private TextView number;
    private TextView email;

    private ArrayList<JsonData> listViewItemList;
    //원본 데이터 리스트
    private ArrayList<JsonData> filteredItemList = new ArrayList<>() ;
    //필터링된 결과 데이터 리스트. Adapter 생성자에서 전체리스트를 보유하도록 한다.
    private Context context;
    private LayoutInflater layoutInflater;
    private Filter listFilter;


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
    public ArrayList<JsonData> getFilteredItemList() {
        return filteredItemList;
    }


    public void updateItems(ArrayList<JsonData> items) {
        listViewItemList.clear();
        if (items != null)
            listViewItemList.addAll(items);
            filteredItemList.addAll(items);
        notifyDataSetChanged();
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

        name = convertView.findViewById(R.id.name);
        number = convertView.findViewById(R.id.number);
        email = convertView.findViewById(R.id.email);
        photo = convertView.findViewById(R.id.photo);

        JsonData listViewItem = listViewItemList.get(position);


        name.setText(listViewItem.getName());
        number.setText(listViewItem.getNumber());
        email.setText(listViewItem.getEmail());
        photo.setImageURI(listViewItem.getPhoto());
        if (photo.getDrawable() == null)
            photo.setImageResource(R.drawable.ic_profile_placeholder);

        ImageButton callButton= (ImageButton)convertView.findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Adapter.this.context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions((Activity)Adapter.this.context, new String[]{ Manifest.permission.CALL_PHONE }, PhoneBookFragment.PERMISSIONS_CALL_PHONE);
                else {
                    JsonData data = listViewItemList.get(pos);
                    Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + data.getNumber()));
                    context.startActivity(call);
                }
            }
        });

        ImageButton smsButton = (ImageButton)convertView.findViewById(R.id.smsButton);
        smsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Adapter.this.context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions((Activity)Adapter.this.context, new String[]{ Manifest.permission.SEND_SMS }, PhoneBookFragment.PERMISSIONS_REQUEST_SEND_SMS);
                else {
                    JsonData data = listViewItemList.get(pos);
                    Intent send = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + data.getNumber()));
                    context.startActivity(send);
                }
            }
        });
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (listFilter == null) {
            listFilter = new ListFilter() ;
        }

        return listFilter ;
    }

    public void fillter(String searchText, ArrayList<JsonData> backupList){

        listViewItemList.clear();
        if(searchText.length() == 0)
        {
            listViewItemList.addAll(backupList);
        }
        else
        {
            for( JsonData item : backupList)
            {
                if(item.getName().contains(searchText))
                {
                    listViewItemList.add(item);
                }
            }
        }
        notifyDataSetChanged();

    }
    private class ListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults() ;

            if (constraint == null || constraint.length() == 0) {
                results.values = listViewItemList ;
                results.count = listViewItemList.size() ;
            } else {
                ArrayList<JsonData> itemList = new ArrayList<JsonData>() ;

                for (JsonData item : listViewItemList) {
                    if (item.getName().contains(constraint.toString()))
                    {
                        itemList.add(item) ;

                    }
                }

                results.values = itemList ;
                results.count = itemList.size() ;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            // update listview by filtered data list.
            filteredItemList = (ArrayList<JsonData>) results.values ;

            // notify
            if (results.count > 0) {
                notifyDataSetChanged() ;
            } else {
                notifyDataSetInvalidated() ;
            }
        }
    }
}
