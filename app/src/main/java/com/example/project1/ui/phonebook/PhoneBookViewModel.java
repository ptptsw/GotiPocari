package com.example.project1.ui.phonebook;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.project1.R;


import java.util.ArrayList;

public class PhoneBookViewModel extends ViewModel {
    private Adapter adapter;
    private JsonData listview;

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    public JsonData getListview() {
        return listview;
    }

    public void setListview(JsonData listview) {
        this.listview = listview;
    }




}