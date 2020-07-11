package com.example.project1.ui.Dial;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.project1.R;

public class Dial extends Fragment implements  View.OnClickListener{
    private Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,btext,bcall;
    private TextView text;
    String count ="";
    private static final int PERMISSIONS_REQUEST_SEND_SMS = 1;
    private static final int PERMISSIONS_CALL_PHONE = 2;
    private static final int PERMISSIONS_REQUEST_ALL = 3;
    private static String[] requiredPermissions = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.CALL_PHONE
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dial, container, false);
        requestRequiredPermissions();
        b1=(Button)view.findViewById(R.id.b1);
        b2=(Button)view.findViewById(R.id.b2);
        b3=(Button)view.findViewById(R.id.b3);
        b4=(Button)view.findViewById(R.id.b4);
        b5=(Button)view.findViewById(R.id.b5);
        b6=(Button)view.findViewById(R.id.b6);
        b7=(Button)view.findViewById(R.id.b7);
        b8=(Button)view.findViewById(R.id.b8);
        b9=(Button)view.findViewById(R.id.b9);
        b0=(Button)view.findViewById(R.id.b0);
        btext=(Button)view.findViewById(R.id.btext);
        bcall=(Button)view.findViewById(R.id.bcall);
        text=(TextView)view.findViewById(R.id.text);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        b0.setOnClickListener(this);
        btext.setOnClickListener(this);
        bcall.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.b1:
                count+="1";
                break;
            case R.id.b2:
                count+="2";
                break;
            case R.id.b3:
                count+="3";
                break;
            case R.id.b4:
                count+="4";
                break;
            case R.id.b5:
                count+="5";
                break;
            case R.id.b6:
                count+="6";
                break;
            case R.id.b7:
                count+="7";
                break;
            case R.id.b8:
                count+="8";
                break;
            case R.id.b9:
                count+="9";
                break;
            case R.id.b0:
                count+="0";
                break;

            case R.id.btext:
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission.SEND_SMS }, PERMISSIONS_REQUEST_SEND_SMS);
                else {
                    Intent text = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+count));
                    startActivity(text);
                }
                break;
            case R.id.bcall:
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission.CALL_PHONE }, PERMISSIONS_CALL_PHONE);
                else {
                    Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+count));
                    startActivity(call);
                }
                break;
        }
        text.setText(count);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            /* 요청한 권한을 사용자가 "허용"했다면 인텐트를 띄워라
                내가 요청한 게 하나밖에 없기 때문에. 원래 같으면 for문을 돈다.*/
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-2222"));
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                }
            }
            else {
                Toast.makeText(getActivity(), "권한 요청을 거부했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void requestRequiredPermissions() {
        boolean allGranted = true;
        for (String permission : Dial.requiredPermissions) {
            boolean granted = ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
            allGranted = allGranted && granted;
        }
        if (!allGranted)
            requestPermissions(requiredPermissions, PERMISSIONS_REQUEST_ALL);
    }
}
