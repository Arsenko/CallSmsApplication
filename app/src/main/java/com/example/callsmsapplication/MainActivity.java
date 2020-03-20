package com.example.callsmsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        Button btnCall=findViewById(R.id.btnCall);
        Button btnSend=findViewById(R.id.btnSend);

        final TextView phoneNumber=findViewById(R.id.phoneNumber);
        final TextView smsText=findViewById(R.id.smsText);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
                    if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(phoneNumber.getText().toString()));
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this,getString(R.string.permissionDeniedCall),Toast.LENGTH_LONG).show();
                    };
                }else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+phoneNumber.getText().toString()));
                    startActivity(intent);
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},1);
                    if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED){
                        SmsManager smsmnrg=SmsManager.getDefault();
                        smsmnrg.sendTextMessage(phoneNumber.getText().toString(),null,smsText.getText().toString(),null,null);
                    }else{
                        Toast.makeText(MainActivity.this,getString(R.string.permissionDeniedSend),Toast.LENGTH_LONG).show();
                    };
                }else {
                    SmsManager smsmnrg=SmsManager.getDefault();
                    smsmnrg.sendTextMessage(phoneNumber.getText().toString(),null,smsText.getText().toString(),null,null);
                }
            }
        });
    }
}
