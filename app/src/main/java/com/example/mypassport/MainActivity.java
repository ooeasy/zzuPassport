package com.example.mypassport;

import static com.example.mypassport.R.color.purple_700;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int REQUEST_IMAGE_PICK = 520;
    int num;
    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.edittext);
        button.setOnClickListener(this);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editText.setText("");
                } else {
                    //editText.setHint("这里输入数字");
                }
            }
        });

        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开图片选择器
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            // 获取选择的图片URI
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                Util.setParam(getApplicationContext(), "uri", imageUri.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                try {
                    String text = editText.getText().toString();
                    num = Integer.valueOf(text);
                    if (num < 0) {
                        Toast.makeText(this, "不是一个有效数字", Toast.LENGTH_LONG).show();
                        break;
                    }
                    if (num > 999) {
                        Toast.makeText(this, "不是一个有效数字", Toast.LENGTH_LONG).show();
                        break;
                    }
                    Intent intent = new Intent(this, passport.class);
                    intent.putExtra("num", text);
                    startActivity(intent);
                    break;
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "不是一个有效数字", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
        }
    }
}
