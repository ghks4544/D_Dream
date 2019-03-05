package com.example.user.opencv_app1;

import android.content.Context;
import android.hardware.Camera;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;

    CameraPreview(Context context){
        super(context);

        try{
            releaseCameraAndPreView();
            mCamera = Camera.open(1);
            mCamera.setDisplayOrientation(90);
        } catch (Exception e){

        }

        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private void releaseCameraAndPreView() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        // Surface가 생성되었다면, 카메라의 인스턴스를 받아온 후
        // 카메라의 Preview를 표시할 위치를 설정
        // 추후 임베디드 카메라의 영상을 받아올 메소드
        try{
            if(mCamera == null){
                mCamera.setPreviewDisplay(holder);

            }
        } catch (IOException exception) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // 다른 화면으로 돌아가면, surface가 소멸됨,
        // 따라서 카메라의 Preview도 중지해야하며, 카메라는 공유할 수 있는 자원이 아니므로,
        // 사용하지 않을 경우 자원 반환
        if(mCamera !=null){
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 표시할 영역의 크기를 알았으므로 해당 크기로 Preview를 시작
        if(mHolder.getSurface() == null) {
            return;
        }

        // 작업을 위해 잠시 멈춤
        try {
            mCamera.startPreview();
        } catch (Exception e){

        }

        Camera.Parameters parameters = mCamera.getParameters();
        List<String> focusModes = parameters.getSupportedFocusModes();
        if(focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)){
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        mCamera.setParameters(parameters);

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch(Exception e){

        }
    }

}
