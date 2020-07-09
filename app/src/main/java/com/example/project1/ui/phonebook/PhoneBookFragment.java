package com.example.project1.ui.phonebook;

import android.content.Context;
import android.database.Cursor;
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

import java.util.ArrayList;

public class PhoneBookFragment extends Fragment {

    private PhoneBookViewModel phoneBookViewModel;
    private Adapter adapter;
    private SampleData listview;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        phoneBookViewModel = ViewModelProviders.of(this).get(PhoneBookViewModel.class);
        View root = inflater.inflate(R.layout.fragment_phonebook, container, false);

        adapter = new Adapter();
        ListView listview = (ListView)root.findViewById(R.id.listView);
        listview.setAdapter(adapter);

        adapter.addItem("서건식","010-5424-8706",R.drawable.ic_phonebook);
        adapter.addItem("서건식","010-5424-8706",R.drawable.ic_phonebook);
        adapter.addItem("서건식","010-5424-8706",R.drawable.ic_phonebook);
        adapter.addItem("서건식","010-5424-8706",R.drawable.ic_phonebook);
        adapter.addItem("서건식","010-5424-8706",R.drawable.ic_phonebook);
        adapter.addItem("서건식","010-5424-8706",R.drawable.ic_phonebook);
        adapter.addItem("서건식","010-5424-8706",R.drawable.ic_phonebook);
        adapter.addItem("서건식","010-5424-8706",R.drawable.ic_phonebook);
        adapter.addItem("서건식","010-5424-8706",R.drawable.ic_phonebook);
        adapter.addItem("서건식","010-5424-8706",R.drawable.ic_phonebook);

        adapter.notifyDataSetChanged();




        return root;
    }
}




