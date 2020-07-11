package com.example.project1.ui.phonebook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    protected static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    protected static final int PERMISSIONS_REQUEST_SEND_SMS = 2;
    protected static final int PERMISSIONS_CALL_PHONE = 3;
    protected static final int PERMISSIONS_REQUEST_ALL = 4;
    private static String[] requiredPermissions = new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.CALL_PHONE
    };
    private PhoneBookViewModel phoneBookViewModel;
    private Adapter adapter;

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
        initializeContacts();
        requestRequiredPermissions();
        phoneBookViewModel.getContacts().observe(getViewLifecycleOwner(), contactObserver);

        return root;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS:
            case PERMISSIONS_REQUEST_ALL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    phoneBookViewModel.initializeContacts();
        }
    }

    private void initializeContacts() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            ArrayList<JsonData> data = phoneBookViewModel.getContacts().getValue();
            if (data == null)
                phoneBookViewModel.initializeContacts();
            else
                adapter.updateItems(phoneBookViewModel.getContacts().getValue());
        }
    }

    private void requestRequiredPermissions() {
        boolean allGranted = true;
        for (String permission : PhoneBookFragment.requiredPermissions) {
            boolean granted = ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
            allGranted = allGranted && granted;
        }

        if (!allGranted)
            requestPermissions(requiredPermissions, PERMISSIONS_REQUEST_ALL);
    }
}

