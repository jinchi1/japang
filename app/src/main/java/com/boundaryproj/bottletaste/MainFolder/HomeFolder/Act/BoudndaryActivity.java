package com.boundaryproj.bottletaste.MainFolder.HomeFolder.Act;

import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Fragment.MapBoundaryFragment;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Fragment.MyBoundaryFragment;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Fragment.MypageFragment;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.CenterToast;
import com.boundaryproj.bottletaste.UtilFolder.NotificationFolder.NotificationUtils;
import com.boundaryproj.bottletaste.databinding.ActivityBoudndaryBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BoudndaryActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ActivityBoudndaryBinding binding;

    private NotificationUtils mNotificationUtils;
    boolean time_bool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_boudndary);



        SimpleDateFormat sdfNow = new SimpleDateFormat("HH");
        String time = sdfNow.format(new Date(System.currentTimeMillis()));
        if(time.substring(0).toString().equals("0")  && !time.substring(1).toString().equals("0")){
            time = time.replace("0","");
        }
        if(Integer.valueOf(time)<Integer.valueOf("22") && Integer.valueOf(time)>=Integer.valueOf("10")){
            time_bool = false;
        }else if(Integer.valueOf(time)<Integer.valueOf("10") || Integer.valueOf(time)>=Integer.valueOf("22") ){
            time_bool = true;
        }

        if(getIntent().getStringExtra("type").toString().equals("0")){
            if(time_bool){
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frameLayout, new MapBoundaryFragment());
                fragmentTransaction.commit();
                binding.boundaryMapBtn.setImageResource(R.drawable.bounder_map_activated);
                binding.boundaryMybounderBtn.setImageResource(R.drawable.my_bounders_not_activated);
                binding.boundaryMypageBtn.setImageResource(R.drawable.mypage_not_activated);
            }else{
                new CenterToast().show(getApplicationContext(),"Bounding Time은 오후 10시 부터입니다.");
            }

        }else if(getIntent().getStringExtra("type").toString().equals("1")){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frameLayout, new MyBoundaryFragment());
            fragmentTransaction.commit();
            binding.boundaryMapBtn.setImageResource(R.drawable.boundary_map_not_activated);
            binding.boundaryMybounderBtn.setImageResource(R.drawable.my_bounder_state_active);
            binding.boundaryMypageBtn.setImageResource(R.drawable.mypage_not_activated);
        }else if(getIntent().getStringExtra("type").toString().equals("2")){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frameLayout, new MypageFragment());
            fragmentTransaction.commit();

            binding.boundaryMapBtn.setImageResource(R.drawable.boundary_map_not_activated);
            binding.boundaryMybounderBtn.setImageResource(R.drawable.my_bounders_not_activated);
            binding.boundaryMypageBtn.setImageResource(R.drawable.my_page_activated);

        }

        binding.boundaryMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(time_bool){
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new MapBoundaryFragment());

                    fragmentTransaction.commit();
                    binding.boundaryMapBtn.setImageResource(R.drawable.bounder_map_activated);
                    binding.boundaryMybounderBtn.setImageResource(R.drawable.my_bounders_not_activated);
                    binding.boundaryMypageBtn.setImageResource(R.drawable.mypage_not_activated);
                }else{
                    new CenterToast().show(getApplicationContext(),"Bounding Time은 오후 10시 부터입니다.");
                }

            }
        });
        binding.boundaryMypageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new MypageFragment());

                fragmentTransaction.commit();

                binding.boundaryMapBtn.setImageResource(R.drawable.boundary_map_not_activated);
                binding.boundaryMybounderBtn.setImageResource(R.drawable.my_bounders_not_activated);
                binding.boundaryMypageBtn.setImageResource(R.drawable.my_page_activated);
            }
        });

        binding.boundaryMybounderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new MyBoundaryFragment());

                fragmentTransaction.commit();
                binding.boundaryMapBtn.setImageResource(R.drawable.boundary_map_not_activated);
                binding.boundaryMybounderBtn.setImageResource(R.drawable.my_bounder_state_active);
                binding.boundaryMypageBtn.setImageResource(R.drawable.mypage_not_activated);
            }
        });


        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backAction();
            }
        });

        binding.logoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private final static String TAG = "BoundaryActivity";

    public void replaceFragment(android.support.v4.app.Fragment fragment, String TAG) {

        try {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment, TAG);
            fragmentTransaction.addToBackStack(TAG);
            fragmentTransaction.commitAllowingStateLoss();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @Override
    public void onBackPressed(){

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            super.onBackPressed();
        }
    }

    public void backAction(){
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            super.onBackPressed();
        }
    }



}
