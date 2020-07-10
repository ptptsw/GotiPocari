package com.example.project1.ui.phonebook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project1.R;

import java.util.ArrayList;

public class PhoneBookFragment extends Fragment {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private PhoneBookViewModel phoneBookViewModel;
    private Adapter adapter;
    private ArrayList<JsonData> contactList;
    private EditText searchbutton;
    private ArrayList<JsonData> list_copy;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PhoneBookViewModelFactory factory = new PhoneBookViewModelFactory(this.getContext());
        phoneBookViewModel = ViewModelProviders.of(getActivity(), factory).get(PhoneBookViewModel.class);
        View root = inflater.inflate(R.layout.fragment_phonebook, container, false);
        adapter = new Adapter(new ArrayList<JsonData>(), this.getContext());
        final Observer<ArrayList<JsonData>> contactObserver = new Observer<ArrayList<JsonData>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<JsonData> newContacts) {
                adapter.updateItems(newContacts);
            }
        };
        ListView listview = root.findViewById(R.id.listView);
        listview.setAdapter(adapter);
        requestContactList();
        phoneBookViewModel.getContacts().observe(getViewLifecycleOwner(), contactObserver);

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

