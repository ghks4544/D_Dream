package com.example.user.opencv_app1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageResizeUtils {

    // 이미지의 너비를 변경한다
    // @param file          변형 시키고 싶은 파일
    // @param newFile       변형시킬 파일을 저장할 파일
    // @param newWidth      리사이징 할 크기
    // @param isCamera      카메라에서 온 이미지인 경우 회전각도를 반영
    public static void resizeFile(File file, File newFile, int newWidth, Boolean isCamera)
    {
        String TAG = "Dream";

        Bitmap originalBitmap = null;
        Bitmap resizedBitmap = null;

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPurgeable = true;
            options.inDither = true;

            originalBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

            if(isCamera) {
                // 카메라인 경우 이미지를 상황에 맞게 회전

                try {
                    ExifInterface exif = new ExifInterface(file.getAbsolutePath());
                    int exifOrientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    int exifDegree = exifOrientationToDegrees(exifOrientation);
                    Log.d(TAG, "exifDegree : "+ exifDegree);

                    originalBitmap = rotate(originalBitmap, exifDegree);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            if(originalBitmap == null) {
                Log.e(TAG, ("파일에러"));
                return;
            }

            int width = originalBitmap.getWidth();
            int height = originalBitmap.getHeight();

            float aspect, scaleWidth, scaleHeight;
            if(width > height){
                if(width <= newWidth) return;

                aspect = (float) width/height;

                scaleWidth = newWidth;
                scaleHeight = scaleWidth/aspect;

            } else {
                if(height <= newWidth) return;

                aspect = (float) height/width;

                scaleHeight = newWidth;
                scaleWidth = scaleHeight/aspect;
            }

            // create a matrix for the manipulation
            Matrix matrix = new Matrix();

            // resize the bitmap
            matrix.postScale(scaleWidth/width, scaleHeight/height);

            // recreate the new Bitmap
            resizedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, width, height, matrix, true);

            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, new FileOutputStream(newFile));
            } else {
                resizedBitmap.compress(Bitmap.CompressFormat.PNG, 80, new FileOutputStream(newFile));
            }

        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } finally {

            if(originalBitmap != null){
                originalBitmap.recycle();
            }

            if(resizedBitmap != null){
                resizedBitmap.recycle();
            }
        }
    }


    //EXIF 정보를 회전각도로 변호나하는 메서드
    //@param exifOrientation EXIF 회전각
    //@return 실제 각도

    public static int exifOrientationToDegrees(int exifOrientation)
    {
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
        {
            return 90;
        }
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
        {
            return 180;
        }
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
        {
            return 270;
        }

        return 0;
    }

    private static Bitmap rotate(Bitmap bitmap, int degrees) {
        if(degrees != 0 && bitmap !=null)
        {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float)bitmap.getWidth()/2,
                    (float)bitmap.getHeight()/2);

            try
            {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), m, true);
                if(bitmap != converted)
                {
                    bitmap.recycle();
                    bitmap = converted;
                }

            }
            catch(OutOfMemoryError ex)
            {
                // 원본 반환
            }
        }
        return bitmap;
    }

    // 출처 https://black-jin0427.tistory.com/122
}
