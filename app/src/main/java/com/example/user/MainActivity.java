package com.example.user.opencv_app1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this,"권한 허가", Toast.LENGTH_SHORT).show();
                Intent intent_to_mainservice = new Intent(MainActivity.this, MainService.class);
                startActivity(intent_to_mainservice);
                // 권한이 허가되면, 메인 메뉴로 넘어감.
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                // 권한이 거부되면, 어플리케이션 종료
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("들려Dream을 이용하기 위해서는 위치권한이 필요합니다.")
                .setDeniedMessage("권한을 거부하셨습니다. 어플리케이션을 종료합니다.")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                 .check();
    }


}
