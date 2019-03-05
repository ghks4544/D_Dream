package com.example.user.opencv_app1;

import android.content.Context;
import android.util.Log;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

//import com.googlecode.tesseract.android.TessBaseAPI;


public class TesseractOCR {

    static final String TAG = "DBG_" + TesseractOCR.class.getName();

    private static final String tessdir = "tesseract";
    private static final String subdir = "tessdata";
    private static final String filename = "kor.traineddata";

    private static String trainedDataPath;

    private static String tesseractFolder;

    public static String getTesseractFolder() {
        return tesseractFolder;
    }
    public static String getTrainedDataPath(){
        return initiated ? trainedDataPath : null;
    }

    private static boolean initiated;

    protected void prepareTessdata(Context context) {
        if(initiated)
            return;

        File appFolder = context.getFilesDir();
        File folder = new File(appFolder, tessdir);
        if(!folder.exists())
            folder.mkdir();
        tesseractFolder = folder.getAbsolutePath();

        File subfolder = new File(folder, subdir);
        if(!subfolder.exists())
            subfolder.mkdir();
    }

    protected void excuteOCR() {

    }

    protected void putText() {

        //TessBaseAPI tessBaseAPI = new TessBaseAPI();

        //tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "@#$%^&*()_+=-[]}{;:'\"\\|~`/<>");
    }
}
