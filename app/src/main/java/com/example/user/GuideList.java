package com.example.user.opencv_app1;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class GuideList extends AppCompatActivity {


    private static final String TAG = "opencv";
    static final int PERMISSION_REQUEST_CODE = 1;
    String[] PERMISSIONS  = {"android.permission.WRITE_EXTERNAL_STORAGE"};

    //TTS객체와 안내 음성을 출력할 문자열 선언
    DreamTTS dreamTTS;
    String guideTXT;

    //메뉴 버튼과 버튼 아이디 설정
    Button gdlistBtn[] = new Button[3];
    Integer[] gdlistBtnIds = {R.id.btn_calluserguidelist, R.id.btn_startgeneralcapture, R.id.btn_startprivatlibrary};

    //UI 버튼과 버튼 아이디 설정(디버깅용)
    Button uiBtn[] = new Button[3];
    Integer[] uiBtnIds = {R.id.btn_uileft, R.id.btn_uiok, R.id.btn_uiright};

    // 메인 메뉴 안내문 불러오기
    String[] guidelist;
    Integer guidelistIndex=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelist);

        dreamTTS = new DreamTTS(GuideList.this);
        guidelist = getResources().getStringArray(R.array.guidelist);

        for(int i = 0; i<gdlistBtnIds.length; i++){
            gdlistBtn[i] = (Button)findViewById(gdlistBtnIds[i]);
        }

        for(int i = 0; i<uiBtnIds.length; i++){
            uiBtn[i] = (Button)findViewById(uiBtnIds[i]);
        }

    }

    public void mOnclick(View v) {
        switch(v.getId()) {
            case R.id.btn_uileft :
                switch(guidelistIndex){
                    case 0:
                        guidelistIndex=1;
                        dreamTTS.ttsSpeech(guidelist[guidelistIndex-1]);
                        break;
                    case 1:
                        guidelistIndex=1;
                        dreamTTS.ttsSpeech(guidelist[guidelistIndex-1]);
                        break;
                    case 2:
                        guidelistIndex--;
                        dreamTTS.ttsSpeech(guidelist[guidelistIndex-1]);
                        break;
                    case 3:
                        guidelistIndex--;
                        dreamTTS.ttsSpeech(guidelist[guidelistIndex-1]);
                        break;
                }
                break;
            case R.id.btn_uiok :
                switch(guidelistIndex){
                    case 0:
                        guidelistIndex=1;
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
                break;
            case R.id.btn_uiright :
                switch(guidelistIndex){
                    case 0:
                        guidelistIndex=1;
                        dreamTTS.ttsSpeech(guidelist[guidelistIndex-1]);
                        break;
                    case 1:
                        guidelistIndex++;
                        dreamTTS.ttsSpeech(guidelist[guidelistIndex-1]);
                        break;
                    case 2:
                        guidelistIndex++;
                        dreamTTS.ttsSpeech(guidelist[guidelistIndex-1]);
                        break;
                    case 3:
                        guidelistIndex=3;
                        dreamTTS.ttsSpeech(guidelist[guidelistIndex-1]);
                        break;
                }
                break;
        }
    }
}
