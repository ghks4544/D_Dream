package com.example.user.opencv_app1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class Result4GC1_Stub extends AppCompatActivity{

    private static final String TAG = "Dream";

    //stub image
    Bitmap image;

    //stub button
    Button resultcarry_btn;

    //Test Text
    TextView testtext;

    // Result STring
    String retStr = "";
    String tempStr = "";

    //Not used;
    private File tempFile = null;
    private Boolean isCamera = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re4gc1_stub);

        ArrayList<String> result = (ArrayList<String>) getIntent().getSerializableExtra("resultString");
        // 출처 https://roemilk.tistory.com/4

        int totalString = result.size();
        for(int index = 0; index < totalString; index++) {
            tempStr = result.get(index);
            retStr = retStr.concat(tempStr);
        }

        testtext = (TextView) findViewById(R.id.test_text);
        testtext.setText(retStr);

        // 촬영 메뉴로 가기
        resultcarry_btn = (Button)findViewById(R.id.result_carrybtn);

    }


    public void mOnClick(View v){
        switch(v.getId()){
            case R.id.result_carrybtn:
                break;
            default:
                break;
        }
    }

}
