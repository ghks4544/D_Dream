package com.example.user.opencv_app1;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

// Call Tesseract
import com.googlecode.tesseract.android.TessBaseAPI;

import java.util.Arrays;
import java.util.List;

public class TessEngine {

    private static  final String TAG = "Dream" + TessEngine.class;

    private Context mCtx;

    public TessEngine(Context context) {
        this.mCtx = context;
    } 

    public List<String> detectText(Bitmap bitmap) {
        Log.d(TAG, "Initialization of TessBaseApi");
        TessDataMgr.initTessTrainedData(mCtx);      //DataManager클래스의 initTessTrainedData 메소드를 통해 트레이닝 데이터를 읽는다.
        TessBaseAPI tessBaseAPI = new TessBaseAPI();
        String path = TessDataMgr.getTesseractFolder();     //path에는 tesseract라는 이름의 디렉토리의 경로가 저장된다.
        Log.d(TAG, "Tess folder: " + path);
        tessBaseAPI.setDebug(true);
        tessBaseAPI.init(path, "kor");
        // 추천 글자들
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ을를이가했하였다지고");
        // 비추천 글자들
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-[]}{;:'\"\\|~`,./<>?");
        tessBaseAPI.setPageSegMode(TessBaseAPI.OEM_TESSERACT_LSTM_COMBINED);
        Log.d(TAG, "Ended initialization of TessEngine");
        Log.d(TAG, "Running inspection on bitmap");
        tessBaseAPI.setImage(bitmap);
        String inspection = tessBaseAPI.getUTF8Text();
        Log.d(TAG, "=====Got data=====" + inspection);
        List<String> recogText = Arrays.asList(inspection.split("\n|\\u0020"));    //반환된 문자열은 공백 아니면 엔터로 구분되므로 split을 통해 배열에 넣어 반환한다.
        tessBaseAPI.end();
        return recogText;              // 글자를 반환한다.
    }

    // 출처 https://jinseongsoft.tistory.com/42

}
