package com.boundaryproj.bottletaste;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.boundaryproj.bottletaste.MainFolder.HomeFolder.HomeActivity;
import com.boundaryproj.bottletaste.MainFolder.LoginFolder.Act.LoginActivity;
import com.boundaryproj.bottletaste.MainFolder.RegisterActivity;
import com.boundaryproj.bottletaste.MainFolder.TestFolder.TestActivity;
import com.boundaryproj.bottletaste.MainFolder.UnregisterActivity;
import com.boundaryproj.bottletaste.UtilFolder.ControllerFolder.AppController;
import com.boundaryproj.bottletaste.UtilFolder.LocationUpdateUtil;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    Handler hd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int permissionCheck1 = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck3 = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck1 == PackageManager.PERMISSION_DENIED && permissionCheck2 == PackageManager.PERMISSION_DENIED && permissionCheck3 == PackageManager.PERMISSION_DENIED) {
            PermissionCheck();
        } else {
            hd = new Handler();
            hd.postDelayed(new SplashHandler(), 1500);
        }


    }

    private void PermissionCheck() {

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                final String appDirectoryName = "boundary";
                final File imageRoot = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), appDirectoryName);
                imageRoot.mkdirs();
                String finalPath = "/sdcard/boundary";
                File file = new File(finalPath);
                if (!file.isDirectory()) file.mkdirs();
                hd = new Handler();
                hd.postDelayed(new SplashHandler(), 1500);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(getApplicationContext(), "필수 앱 권한을 설정해주세요.", Toast.LENGTH_SHORT).show();
            }


        };


        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("권한 설정을 사용하지 않으면 Boundary를 사용할 수 없습니다.")
                .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_FINE_LOCATION)
                .check();


    }

    private class SplashHandler implements Runnable {
        public void run() {
            if(AppController.getInstance().getPrefManager().getUser() != null){
            //    new LocationUpdateUtil().set(SplashActivity.this,getApplicationContext());
                Intent intent = new Intent(getApplication(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                finish();
                startActivity(intent);
            }else{
                Intent intent = new Intent(getApplication(),LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                finish();
                startActivity(intent);
            }






        }
    }
}
