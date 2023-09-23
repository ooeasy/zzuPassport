package com.example.mypassport;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class passport extends Activity {
    String num;
    TextView textView;
    LinearLayout linearLayout;
    boolean flag = true;
    ImageView handsomeme;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                flash();
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passport);
        Intent intent = getIntent();
        num = intent.getStringExtra("num");
        textView = (TextView) findViewById(R.id.passportid);
        textView.setText(num);
        linearLayout = (LinearLayout) findViewById(R.id.flash);
        handsomeme = (ImageView) findViewById(R.id.handsomeme);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0x123);
            }
        }, 0, 723);
        String savedUriString = (String) Util.getParam(this, "uri", "false");
        if (!savedUriString.equals("false")) {
            Uri savedUri = Uri.parse(savedUriString);
            Toast.makeText(this, savedUri.toString(), Toast.LENGTH_SHORT).show();
            Log.e("----------------------------------------------------", savedUri.toString());
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), savedUri);
                    @SuppressLint("WrongThread") Bitmap bitmap = ImageDecoder.decodeBitmap(source);
                    handsomeme.setImageBitmap(bitmap);
                } else {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), savedUri);
                    handsomeme.setImageBitmap(bitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void flash() {
        if (flag) {
            linearLayout.setBackgroundResource(R.drawable.frame2);
            flag = false;
        } else {
            linearLayout.setBackgroundResource(R.drawable.frame);
            flag = true;

        }

    }
}