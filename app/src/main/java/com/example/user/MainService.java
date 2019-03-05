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


public class MainService extends AppCompatActivity {


    private static final String TAG = "opencv";
    static final int PERMISSION_REQUEST_CODE = 1;
    String[] PERMISSIONS  = {"android.permission.WRITE_EXTERNAL_STORAGE"};

    //TTS객체와 안내 음성을 출력할 문자열 선언
    DreamTTS dreamTTS;
    String guideTXT;

    //메뉴 버튼과 버튼 아이디 설정
    Button menuBtn[] = new Button[3];
    Integer[] menuBtnIds = {R.id.btn_calluserguidelist, R.id.btn_startgeneralcapture, R.id.btn_startprivatlibrary};

    //UI 버튼과 버튼 아이디 설정(디버깅용)
    Button uiBtn[] = new Button[3];
    Integer[] uiBtnIds = {R.id.btn_uileft, R.id.btn_uiok, R.id.btn_uiright};

    // 메인 메뉴 안내문 불러오기
    String[] mainmenu;
    Integer mainmenuIndex=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dreamTTS = new DreamTTS(MainService.this);
        mainmenu = getResources().getStringArray(R.array.mainmenu);

        for(int i = 0; i<menuBtnIds.length; i++){
            menuBtn[i] = (Button)findViewById(menuBtnIds[i]);
        }

        for(int i = 0; i<menuBtnIds.length; i++){
            uiBtn[i] = (Button)findViewById(uiBtnIds[i]);
        }

    }

    public void mOnclick(View v) {
        switch(v.getId()) {
            case R.id.btn_uileft :
                switch(mainmenuIndex){
                    case 0:
                        mainmenuIndex=1;
                        dreamTTS.ttsSpeech(mainmenu[mainmenuIndex-1]);
                        break;
                    case 1:
                        mainmenuIndex=1;
                        dreamTTS.ttsSpeech(mainmenu[mainmenuIndex-1]);
                        break;
                    case 2:
                        mainmenuIndex--;
                        dreamTTS.ttsSpeech(mainmenu[mainmenuIndex-1]);
                        break;
                    case 3:
                        mainmenuIndex--;
                        dreamTTS.ttsSpeech(mainmenu[mainmenuIndex-1]);
                        break;
                }
                break;
            case R.id.btn_uiok :
                switch(mainmenuIndex){
                    case 0:
                        mainmenuIndex=1;
                        break;
                    case 1:
                        Intent intent_to_guidelist = new Intent(MainService.this, GuideList.class);
                        startActivity(intent_to_guidelist);
                        break;
                    case 2:
                        Intent intent_to_waiting4gc = new Intent(MainService.this, WaitingPicture4GC_Stub.class);
                        startActivity(intent_to_waiting4gc);
                        break;
                    case 3:
                        //테스트 액티비티 작동 - 블루투스 테스트 페이지
                        Intent intent_to_privatelibrary = new Intent(MainService.this, BluetoothTestDriver.class);
                        startActivity(intent_to_privatelibrary);
                        break;
                }
                break;
            case R.id.btn_uiright :
                switch(mainmenuIndex){
                    case 0:
                        mainmenuIndex=1;
                        dreamTTS.ttsSpeech(mainmenu[mainmenuIndex-1]);
                        break;
                    case 1:
                        mainmenuIndex++;
                        dreamTTS.ttsSpeech(mainmenu[mainmenuIndex-1]);
                        break;
                    case 2:
                        mainmenuIndex++;
                        dreamTTS.ttsSpeech(mainmenu[mainmenuIndex-1]);
                        break;
                    case 3:
                        mainmenuIndex=3;
                        dreamTTS.ttsSpeech(mainmenu[mainmenuIndex-1]);
                        break;
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){
        switch(permsRequestCode){

            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean writeAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if (!writeAccepted )
                        {
                            showDialogforPermission("앱을 실행하려면 퍼미션을 허가하셔야합니다.");
                            return;
                        }
                    }
                }
                break;
        }
    }

    private void showDialogforPermission(String msg) {

        final AlertDialog.Builder myDialog = new AlertDialog.Builder(  MainService.this);
        myDialog.setTitle("알림");
        myDialog.setMessage(msg);
        myDialog.setCancelable(false);
        myDialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(PERMISSIONS, PERMISSION_REQUEST_CODE);
                }

            }
        });
        myDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        myDialog.show();
    }
}
