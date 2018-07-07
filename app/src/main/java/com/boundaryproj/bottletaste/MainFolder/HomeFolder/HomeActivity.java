package com.boundaryproj.bottletaste.MainFolder.HomeFolder;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.CountTotalAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Act.BoudndaryActivity;
import com.boundaryproj.bottletaste.MainFolder.LoginFolder.Act.LoginActivity;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.APIFolder.UserService;
import com.boundaryproj.bottletaste.UtilFolder.CenterToast;
import com.boundaryproj.bottletaste.UtilFolder.ControllerFolder.AppController;
import com.boundaryproj.bottletaste.UtilFolder.LocationUpdateUtil;
import com.boundaryproj.bottletaste.UtilFolder.ServiceFolder.RTGPSLocationService;
import com.boundaryproj.bottletaste.databinding.ActivityHomeBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    boolean time_bool = false;


    TransitionDrawable transitiondrawable;
    int cnt = 0;
    AppController appController;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);


        new LocationUpdateUtil().set(HomeActivity.this,getApplicationContext());

       // Log.d("object",String.valueOf(FirebaseInstanceId.getInstance().getToken().toString()));


        appController = AppController.create(getApplicationContext());
        userService = appController.getUserService();

        Glide.with(getApplicationContext()).load(AppController.getInstance().getPrefManager().getUser().getProfile_photo()).into(binding.borderview);
        Glide.with(getApplicationContext()).load(AppController.getInstance().getPrefManager().getUser().getProfile_photo()).into(binding.profileImageView);


        SimpleDateFormat sdfNow = new SimpleDateFormat("HH");
        String time = sdfNow.format(new Date(System.currentTimeMillis()));


        if(time.substring(0).toString().equals("0")  && !time.substring(1).toString().equals("0")){
            time = time.replace("0","");
        }

        Intent start_intent = new Intent(this, RTGPSLocationService.class);
        startService(start_intent);


        TimerTask adTast = new TimerTask() {

            public void run() {

                Message message = handler.obtainMessage();
                handler.sendMessage(message);


            }

        };
        binding.titleInfoLabel.setText("오늘의 Bounding 까지 남은 시간");

        Timer timer = new Timer();
        timer.schedule(adTast, 0, 1000); // 0초후 첫실행, 3초마다 계속실행

        if(Integer.valueOf(time)<Integer.valueOf("22") && Integer.valueOf(time)>=Integer.valueOf("10")){




            binding.alertLayer.setVisibility(View.GONE);
            binding.countTimeLabel.setVisibility(View.VISIBLE);
            time_bool = false;



        }else if(Integer.valueOf(time)<Integer.valueOf("10") || Integer.valueOf(time)>=Integer.valueOf("22") ){
            binding.alertLayer.setVisibility(View.GONE);
            binding.countTimeLabel.setVisibility(View.VISIBLE);
            binding.titleInfoLabel.setText("사진을 눌러 오늘의 바운더리를 확인하세요.");
            time_bool = true;

        }

        CountTotalAPI();
        binding.profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(time_bool){
                    Intent intent = new Intent(getApplicationContext(), BoudndaryActivity.class);
                    intent.putExtra("type","0");
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }else{
                    new CenterToast().show(getApplicationContext(),"Bounding Time은 오후 10시 부터입니다.");
                }
            }
        });
        binding.mypageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    Intent intent = new Intent(getApplicationContext(), BoudndaryActivity.class);
                    intent.putExtra("type","2");
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);

            }
        });

        binding.mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if(time_bool){
                    Intent intent = new Intent(getApplicationContext(), BoudndaryActivity.class);
                    intent.putExtra("type","0");
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);

                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),"Bounding Time은 오후 10시 부터입니다.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }


            }
        });

        binding.myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), BoudndaryActivity.class);
                intent.putExtra("type","1");
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);


            }
        });






       binding.borderview.setBorderColor(Color.parseColor("#ffffff"));


    }

    final Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {

            Date dates;
            dates = new Date();
            dates.setHours(22);
            dates.setMinutes(0);
            dates.setSeconds(0);
            long time1 = dates.getTime(); // 오늘 0시 0분 으로 설정

            dates.setDate(dates.getDate() + 1);
            long time2 = dates.getTime(); // 내일 0시 0분 으로 설정

            Date nows = new Date();
            long gap = Math.round((time2 - nows.getTime()) / 1000);

            double D = Math.floor(gap / 86400);
            double H = Math.floor((gap - D * 86400) / 3600 % 3600);
            double M = Math.floor((gap - H * 3600) / 60 % 60);
            double S = Math.floor((gap - M * 60) % 60);

            String hour_count = String.format("%02d",(int)(H));
            String minute_count = String.format("%02d",(int)(M));
            String second_count = String.format("%02d",(int)(S));

            if(hour_count.toString().equals("00") && minute_count.toString().equals("00") && second_count.toString().equals("00")){

                Toast toast = Toast.makeText(getApplicationContext(),"오후 10시가 되었습니다. \n" +
                        "Bounding 맵에서 오늘 지나친 사람들을 확인하고,\n" +
                        "친구가 되어 보세요.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), BoudndaryActivity.class);
                        intent.putExtra("type","0");
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                };

                mHandler = new Handler();
                mHandler.postDelayed(mRunnable, 3000);



            }else{
                binding.countTimeLabel.setText(hour_count + ":" +minute_count +":" +second_count);
            }


        }
    };

    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    protected void onDestroy() {
        if(mRunnable != null){
            mHandler.removeCallbacks(mRunnable);
        }

        super.onDestroy();
    }


    public void CountTotalAPI(){

        userService.COUNT_TOTAL_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id()).enqueue(new Callback<CountTotalAPI>() {
            @Override
            public void onResponse(Call<CountTotalAPI> call, Response<CountTotalAPI> response) {
                if(response.body().success){
                    binding.peopleTxtLabel.setText(response.body().bounding);
                }
            }

            @Override
            public void onFailure(Call<CountTotalAPI> call, Throwable t) {

            }
        });

    }

}
