package com.example.user.opencv_app1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.icu.text.SimpleDateFormat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class WaitingPicture4GC_Stub extends AppCompatActivity{

    //stub tag string
    private static final String TAG = "Dream";

    // original imageview
    ImageView imageView;

    //stub image uri
    private File tempFile;

    //stub button
    Button imageopen_btn;
    Button imagetake_btn;
    Button imagecarry_btn;

    //private stub request code
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;
    private Boolean isPermission = true;
    private Boolean isCamera = true;

    //intent uri
    Uri photoUri = null;


    // Origin from
    Bitmap originalBitmap;

    //TestDRIVER LIST
    TessEngine tessCore;

    //Result LIST
    List<String> receive_result1;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wp4gc_stub);


        /*********************************
        * 이미지 불러오기 Stub
        *********************************/
        imageView = (ImageView) findViewById(R.id.image);

        // 갤러리 불로오기 버튼에 대한 리스너 작성
        imageopen_btn = (Button)findViewById(R.id.picture_openbtn);
        imagetake_btn= (Button)findViewById(R.id.picture_takebtn);
        imagecarry_btn = (Button)findViewById(R.id.picture_carrybtn);


        /*********************************
         * Tesseract Driver
         *********************************/
        tessCore = new TessEngine(WaitingPicture4GC_Stub.this);

    }


    /*********************************
     * 이미지 불러오기 Stub
     *********************************/
    // 갤러리로 부터 결과를 받음
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            
            if(tempFile != null) {
                if(tempFile.exists()) {
                    if(tempFile.delete()){
                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }

            return;
        }
        if(requestCode == PICK_FROM_ALBUM) {

            photoUri = data.getData();

            Cursor cursor = null;

            try{
                //Uri 스키마를 content:///에서 file:///로 변경한다.
                String[] proj = {MediaStore.Images.Media.DATA};

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));

            } finally {
                if(cursor != null) {
                    cursor.close();
                }
            }

            setImage();
        } else if (requestCode == PICK_FROM_CAMERA) {
            setImage();
        }
    }



    private void goToAlbum() {
        // 카메라에서 불러온것이 아니면 False상태로 변환
        isCamera=false;

        Intent intent_openpicture = new Intent(Intent.ACTION_PICK);
        intent_openpicture.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent_openpicture, PICK_FROM_ALBUM);

    }

    private void setImage() {
        ImageView imageView = findViewById(R.id.image);

        ImageResizeUtils.resizeFile(tempFile, tempFile, 1280, isCamera);

        BitmapFactory.Options options = new BitmapFactory.Options();
        originalBitmap = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        Log.d(TAG, "setImage : "+tempFile.getAbsolutePath());

        imageView.setImageBitmap(originalBitmap);

        // 템프파일 사용후 null처리르 하는 이유
        // resultCode != RESULT_OK) 일 때 tempFile을 삭제하기 때문에 기존의 데이터를 원치 않게 삭제하게 됨
        // tempFile = null;
    }

    private void takePhoto(){
        isCamera = true;

        Intent intent_takepicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try{
            tempFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if(tempFile != null) {

            if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.N) {
                Uri photoUri = FileProvider.getUriForFile(this,"com.example.user.opencv_app1.provider", tempFile);
                intent_takepicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent_takepicture, PICK_FROM_CAMERA);
            } else {
                Uri photoUri = Uri.fromFile(tempFile);
                intent_takepicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent_takepicture, PICK_FROM_CAMERA);

            }
        }

    }


    private File createImageFile() throws IOException {
        // 이미지 파일 이름 ( tesseract_used_{time}_ )
        String timeStamp = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        }
        String imageFileName = "tesseract_used_"+timeStamp+"_";

        // 이미지가 저장될 폴더 이름 ( tesseract_used )
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/tesseract_used/");
        if(!storageDir.exists()) storageDir.mkdirs();

        // 빈파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        Log.d(TAG, "createImageFile :" + image.getAbsolutePath());

        return image;
    }

    // 출처 https://black-jin0427.tistory.com/121

    /*********************************
     * Tesseract Driver
     *********************************/


    public void mOnClick(View v){
        switch(v.getId()){
            case R.id.picture_openbtn:
                if(isPermission) goToAlbum();
                else Toast.makeText(v.getContext(), getResources().getString(R.string.permission_list), Toast.LENGTH_SHORT).show();
                break;
            // 권한 허용에 동의하지 않았을 경우 토스트를 띄움

            case R.id.picture_takebtn:
                // 권한 허용에 동의하지 않았을 경우 토스트를 띄움
                if(isPermission) takePhoto();
                else Toast.makeText(v.getContext(), getResources().getString(R.string.permission_list), Toast.LENGTH_SHORT).show();
                break;

            case R.id.picture_carrybtn:
                Intent intent_carrypicture = new Intent(getApplicationContext(), Result4GC1_Stub.class);

                ArrayList<String> resultStrings = new ArrayList<>(tessCore.detectText(originalBitmap));
                intent_carrypicture.putExtra("resultString", resultStrings);
                startActivity(intent_carrypicture);
                break;

            default:
                break;
        }
    }
}
