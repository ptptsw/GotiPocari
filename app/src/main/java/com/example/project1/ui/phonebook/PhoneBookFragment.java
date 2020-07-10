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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.project1.R;
import com.example.project1.ui.search.SearchAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PhoneBookFragment extends Fragment {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private PhoneBookViewModel phoneBookViewModel;
    private Adapter adapter;
    private ArrayList<JsonData> contactList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        phoneBookViewModel = ViewModelProviders.of(this).get(PhoneBookViewModel.class);
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
                    getContactList();
        }
    }

    private void requestContactList() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
            getContactList();
        else
            requestPermissions(new String[]{  Manifest.permission.READ_CONTACTS }, PERMISSIONS_REQUEST_READ_CONTACTS);
    }

    private String fetchPhoneNumber(ContentResolver cr, String id) {
        Cursor phoneCursor = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                null,
                null
        );
        String number = "";

        if (phoneCursor.moveToFirst())
            number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

        phoneCursor.close();
        return number;
    }

    private String fetchEmail(ContentResolver cr, String id) {
        Cursor emailCursor = cr.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + id,
                null,
                null
        );

        String email = "";
        if (emailCursor.moveToFirst())
            email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

        emailCursor.close();
        return email;
    }

    private Uri fetchPhotoUri(ContentResolver cr, String id) {
        try {
            Cursor cursor = cr.query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    ContactsContract.Data.CONTACT_ID + " = " + id + " AND " + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'",
                    null,
                    null
            );
            if (cursor == null || !cursor.moveToFirst())
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(id));
        return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }

    private void getContactList() {
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cur == null || cur.getCount() == 0)
            return;

        while (cur != null && cur.moveToNext()) {
            String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String number = fetchPhoneNumber(cr, id);
            String email = fetchEmail(cr, id);
            Uri photo = fetchPhotoUri(cr, id);

            contactList.add(new JsonData(name, number, email, photo));
        }

        if (cur != null)
            cur.close();

        adapter.notifyDataSetChanged();
    }
}

