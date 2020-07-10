package com.example.project1.ui.phonebook;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.project1.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PhoneBookFragment extends Fragment {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
   // private PhoneBookViewModel phoneBookViewModel;
    private Adapter adapter;
    private ArrayList<JsonData> contactList;
    private EditText searchbutton;
    private ArrayList<JsonData> list_copy;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //phoneBookViewModel = ViewModelProviders.of(this).get(PhoneBookViewModel.class);
        View root = inflater.inflate(R.layout.fragment_phonebook, container, false);

        contactList = new ArrayList<JsonData>();
        adapter = new Adapter(this.contactList, this.getContext());
        requestContactList();
        ListView listview = root.findViewById(R.id.listView);
        listview.setAdapter(adapter);
        
        return root;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    phoneBookViewModel.initializeContacts();
        }
    }

    private void requestContactList() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            ArrayList<JsonData> data = phoneBookViewModel.getContacts().getValue();
            if (data == null)
                phoneBookViewModel.initializeContacts();
            else
                adapter.updateItems(phoneBookViewModel.getContacts().getValue());
        }
        else
            requestPermissions(new String[]{ Manifest.permission.READ_CONTACTS }, PERMISSIONS_REQUEST_READ_CONTACTS);
    }
}

