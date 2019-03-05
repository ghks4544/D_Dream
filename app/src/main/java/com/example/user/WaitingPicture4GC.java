package com.example.user.opencv_app1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class WaitingPicture4GC extends AppCompatActivity{
    private SurfaceView surfaceView;
    private CameraPreview mCameraPreview;
    private View mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 상단바를 없애서 전체화면으로 전환
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 항상 켜진상태 유지하기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_wait4gc);

        surfaceView = (SurfaceView)findViewById(R.id.cameraView);

        mCameraPreview = new CameraPreview(this);
    }
}
