package com.boundaryproj.bottletaste.MainFolder.SettingFolder.Act;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.TrueFalseAPI;
import com.boundaryproj.bottletaste.MainFolder.SettingFolder.API.PushControlAPI;
import com.boundaryproj.bottletaste.MainFolder.SettingFolder.API.PushStateAPI;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.APIFolder.UserService;
import com.boundaryproj.bottletaste.UtilFolder.AlertFolder.BoundaryAlert;
import com.boundaryproj.bottletaste.UtilFolder.ControllerFolder.AppController;
import com.boundaryproj.bottletaste.UtilFolder.InstagramFolder.ApplicationData;
import com.boundaryproj.bottletaste.UtilFolder.InstagramFolder.InstagramApp;
import com.boundaryproj.bottletaste.UtilFolder.ServiceFolder.RTGPSLocationService;
import com.boundaryproj.bottletaste.databinding.ActivitySettingMainBinding;
import com.suke.widget.SwitchButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingMainActivity extends AppCompatActivity {

    ActivitySettingMainBinding binding;
    AppController appController;
    UserService userService;
    BoundaryAlert alert = new BoundaryAlert();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appController = AppController.create(getApplicationContext());
        userService = appController.getUserService();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_setting_main);



        userService.PUSH_STATE_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id()).enqueue(new Callback<PushStateAPI>() {
            @Override
            public void onResponse(Call<PushStateAPI> call, Response<PushStateAPI> response) {
                if(response.body().success){
                    if(response.body().instagram.toString().equals("0")){
                        binding.instaSwitch.setChecked(false);
                    }else{
                        binding.instaSwitch.setChecked(true);

                    }

                    if(response.body().fresh.toString().equals("0")){
                        binding.freshSwitch.setChecked(false);

                    }else{
                        binding.freshSwitch.setChecked(true);

                    }

                    if(response.body().message.toString().equals("0")){
                        binding.messageSwitch.setChecked(false);
                    }else{
                        binding.messageSwitch.setChecked(true);
                    }

                    if(response.body().bounding.toString().equals("0")){
                        binding.boundingSwitch.setChecked(false);
                    }else{
                        binding.boundingSwitch.setChecked(true);
                    }

                    if(response.body().pass.toString().equals("0")){
                        binding.passSwtich.setChecked(false);
                    }else{
                        binding.passSwtich.setChecked(true);
                    }

                    if(response.body().appearance.toString().equals("0")){
                        binding.appearSwitch.setChecked(false);
                    }else{
                        binding.appearSwitch.setChecked(true);
                    }
                    Log.d("object",response.body().expose);

                    if(response.body().expose.toString().equals("0")){

                        binding.maleBtn.setSelected(false);
                        binding.maleBtn.setTextColor(Color.parseColor("#666666"));
                        binding.maleBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                        binding.bothBtn.setSelected(true);
                        binding.bothBtn.setBackgroundResource(R.drawable.red_rounded_btn);
                        binding.bothBtn.setTextColor(Color.parseColor("#ffffff"));
                        binding.femaleBtn.setSelected(false);
                        binding.femaleBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                        binding.femaleBtn.setTextColor(Color.parseColor("#666666"));

                    }else if(response.body().expose.toString().equals("1")){

                        binding.femaleBtn.setSelected(false);
                        binding.femaleBtn.setTextColor(Color.parseColor("#666666"));
                        binding.femaleBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                        binding.maleBtn.setSelected(true);
                        binding.maleBtn.setTextColor(Color.parseColor("#ffffff"));
                        binding.maleBtn.setBackgroundResource(R.drawable.red_rounded_btn);

                        binding.bothBtn.setSelected(false);
                        binding.bothBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                        binding.bothBtn.setTextColor(Color.parseColor("#666666"));



                    }else if(response.body().expose.toString().equals("2")){
                        binding.maleBtn.setSelected(false);
                        binding.maleBtn.setTextColor(Color.parseColor("#666666"));
                        binding.maleBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                        binding.femaleBtn.setSelected(true);
                        binding.femaleBtn.setBackgroundResource(R.drawable.red_rounded_btn);
                        binding.femaleBtn.setTextColor(Color.parseColor("#ffffff"));

                        binding.bothBtn.setSelected(false);
                        binding.bothBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                        binding.bothBtn.setTextColor(Color.parseColor("#666666"));
                    }
                }
            }

            @Override
            public void onFailure(Call<PushStateAPI> call, Throwable t) {

            }
        });





        binding.instaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.instaSwitch.isChecked()){
                    alert.WhiteAlert_Blue(SettingMainActivity.this, "정말 Instagram 계정 연동을\n취소 하시겠습니까?", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(view.getId() == R.id.positive){
                                alert.getSimpleDialog().dismiss();
                                PushControll("7","7",binding.instaSwitch);

                            }else{
                                alert.getSimpleDialog().dismiss();
                            }
                        }
                    });
                }else{
                    PushControll("7","7",binding.instaSwitch);
                }

            }
        });

        binding.webBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),WebviewActivity.class);
                intent.putExtra("type","0");
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        binding.webBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),WebviewActivity.class);
                intent.putExtra("type","1");
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.freshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.freshSwitch.isChecked()){
                    alert.WhiteAlert_Blue(SettingMainActivity.this, "새 매칭 알림을 받지 않겠습니까?", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(view.getId() == R.id.positive){
                                alert.getSimpleDialog().dismiss();
                                PushControll("2","7",binding.freshSwitch);

                            }else{
                                alert.getSimpleDialog().dismiss();
                            }
                        }
                    });
                }else{
                    PushControll("2","7",binding.freshSwitch);
                }
            }
        });

        binding.freshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.freshSwitch.isChecked()){
                    alert.WhiteAlert_Blue(SettingMainActivity.this, "새 매칭 알림을 받지 않겠습니까?", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(view.getId() == R.id.positive){
                                alert.getSimpleDialog().dismiss();
                                PushControll("2","7",binding.freshSwitch);

                            }else{
                                alert.getSimpleDialog().dismiss();
                            }
                        }
                    });
                }else{
                    PushControll("2","7",binding.freshSwitch);
                }
            }
        });

        binding.messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.messageSwitch.isChecked()){
                    alert.WhiteAlert_Blue(SettingMainActivity.this, "메시지 알림을 받지 않겠습니까?", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(view.getId() == R.id.positive){
                                alert.getSimpleDialog().dismiss();
                                PushControll("3","7",binding.messageSwitch);

                            }else{
                                alert.getSimpleDialog().dismiss();
                            }
                        }
                    });
                }else{
                    PushControll("3","7",binding.messageSwitch);
                }
            }
        });

        binding.boundingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.boundingSwitch.isChecked()){
                    alert.WhiteAlert_Blue(SettingMainActivity.this, "바운딩 알림을 받지 않겠습니까?", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(view.getId() == R.id.positive){
                                alert.getSimpleDialog().dismiss();
                                PushControll("4","7",binding. boundingSwitch);

                            }else{
                                alert.getSimpleDialog().dismiss();
                            }
                        }
                    });
                }else{
                    PushControll("4","7",binding.boundingSwitch);
                }
            }
        });

        binding.passBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.passSwtich.isChecked()){
                    alert.WhiteAlert_Blue(SettingMainActivity.this, "지나친 횟수 알림을 받지 않겠습니까?", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(view.getId() == R.id.positive){
                                alert.getSimpleDialog().dismiss();
                                PushControll("5","7",binding.passSwtich);

                            }else{
                                alert.getSimpleDialog().dismiss();
                            }
                        }
                    });
                }else{
                    PushControll("5","7",binding.passSwtich);
                }
            }
        });

        binding.appearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.appearSwitch.isChecked()){
                    alert.WhiteAlert_Blue(SettingMainActivity.this, "정말 Boundary에 등장하지\n" +
                            "않으시겠습니까?", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(view.getId() == R.id.positive){
                                alert.getSimpleDialog().dismiss();
                                PushControll("6","7",binding.appearSwitch);

                            }else{
                                alert.getSimpleDialog().dismiss();
                            }
                        }
                    });
                }else{
                    PushControll("6","7",binding.appearSwitch);
                }
            }
        });

        binding.bothBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PushControll("1","0",view);
            }
        });

        binding.femaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PushControll("1","2",view);
            }
        });

        binding.maleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PushControll("1","1",view);
            }
        });

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.WhiteAlert_Blue(SettingMainActivity.this, "정말 로그아웃 하시겠습니까?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(view.getId() == R.id.positive){
                            alert.getSimpleDialog().dismiss();
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), RTGPSLocationService.class);
                            stopService(intent);
                            AppController.getInstance().logout();
                        }else{
                            alert.getSimpleDialog().dismiss();
                        }
                    }
                });
            }
        });

        binding.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.WhiteAlert_Blue(SettingMainActivity.this, "정말 Boundary 계정을\n" +
                        "삭제 하시겠습니까?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(view.getId() == R.id.positive){
                            alert.getSimpleDialog().dismiss();
                            //AppController.getInstance().logout();
                            userService.REMOVE_USER_ID_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id()).enqueue(new Callback<TrueFalseAPI>() {
                                @Override
                                public void onResponse(Call<TrueFalseAPI> call, Response<TrueFalseAPI> response) {
                                    if(response.body().success){
                                        AppController.getInstance().logout();
                                    }
                                }

                                @Override
                                public void onFailure(Call<TrueFalseAPI> call, Throwable t) {

                                }
                            });
                        }else{
                            alert.getSimpleDialog().dismiss();
                        }
                    }
                });
            }
        });

    }

    public void PushControll(final String type, final String s_type, final View view){

        userService.PUSH_CONTROL_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id(),type,s_type).enqueue(new Callback<PushControlAPI>() {
            @Override
            public void onResponse(Call<PushControlAPI> call, Response<PushControlAPI> response) {
                if(type.toString().equals("1")){
                    if(s_type.toString().equals("0")){
                        if(binding.bothBtn.isSelected() ){
                            binding.bothBtn.setSelected(false);
                            binding.bothBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                            binding.bothBtn.setTextColor(Color.parseColor("#666666"));

                        }else{
                            if(binding.maleBtn.isSelected() || binding.femaleBtn.isSelected()){
                                binding.maleBtn.setSelected(false);
                                binding.maleBtn.setTextColor(Color.parseColor("#666666"));
                                binding.maleBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                                binding.bothBtn.setSelected(true);
                                binding.bothBtn.setBackgroundResource(R.drawable.red_rounded_btn);
                                binding.bothBtn.setTextColor(Color.parseColor("#ffffff"));
                                binding.femaleBtn.setSelected(false);
                                binding.femaleBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                                binding.femaleBtn.setTextColor(Color.parseColor("#666666"));
                            }else {
                                binding.bothBtn.setSelected(true);
                                binding.bothBtn.setBackgroundResource(R.drawable.red_rounded_btn);
                                binding.bothBtn.setTextColor(Color.parseColor("#ffffff"));
                            }
                        }
                    }else if(s_type.toString().equals("1")){

                        if(binding.maleBtn.isSelected()){
                            binding.maleBtn.setSelected(false);
                            binding.maleBtn.setTextColor(Color.parseColor("#666666"));
                            binding.maleBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                        }else{
                            if(binding.femaleBtn.isSelected() || binding.bothBtn.isSelected()){
                                binding.femaleBtn.setSelected(false);
                                binding.femaleBtn.setTextColor(Color.parseColor("#666666"));
                                binding.femaleBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                                binding.maleBtn.setSelected(true);
                                binding.maleBtn.setTextColor(Color.parseColor("#ffffff"));
                                binding.maleBtn.setBackgroundResource(R.drawable.red_rounded_btn);

                                binding.bothBtn.setSelected(false);
                                binding.bothBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                                binding.bothBtn.setTextColor(Color.parseColor("#666666"));
                            }else {
                                binding.maleBtn.setSelected(true);
                                binding.maleBtn.setTextColor(Color.parseColor("#ffffff"));
                                binding.maleBtn.setBackgroundResource(R.drawable.red_rounded_btn);
                            }
                        }
                    }else if(s_type.toString().equals("2")){
                        if(binding.femaleBtn.isSelected() ){
                            binding.femaleBtn.setSelected(false);
                            binding.femaleBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                            binding.femaleBtn.setTextColor(Color.parseColor("#666666"));

                        }else{
                            if(binding.maleBtn.isSelected() || binding.bothBtn.isSelected()){
                                binding.maleBtn.setSelected(false);
                                binding.maleBtn.setTextColor(Color.parseColor("#666666"));
                                binding.maleBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                                binding.femaleBtn.setSelected(true);
                                binding.femaleBtn.setBackgroundResource(R.drawable.red_rounded_btn);
                                binding.femaleBtn.setTextColor(Color.parseColor("#ffffff"));

                                binding.bothBtn.setSelected(false);
                                binding.bothBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                                binding.bothBtn.setTextColor(Color.parseColor("#666666"));
                            }else {
                                binding.femaleBtn.setSelected(true);
                                binding.femaleBtn.setBackgroundResource(R.drawable.red_rounded_btn);
                                binding.femaleBtn.setTextColor(Color.parseColor("#ffffff"));
                            }
                        }
                    }
                }else  if(type.toString().equals("7")){
                    if(response.body().success){
                        Update_Insta(view);
                    }

                }else{
                    if(response.body().success){
                        SwitchButton switchButton =(SwitchButton)view;
                        switchButton.setChecked(!switchButton.isChecked());
                    }
                }

            }

            @Override
            public void onFailure(Call<PushControlAPI> call, Throwable t) {

            }
        });

    }

    private InstagramApp mApp;

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            userService.INSTA_ID_UP_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id(),mApp.getUserName()).enqueue(new Callback<TrueFalseAPI>() {
                @Override
                public void onResponse(Call<TrueFalseAPI> call, Response<TrueFalseAPI> response) {
                    if(response.body().success){
                       binding.instaSwitch.setChecked(true);

                    }
                }

                @Override
                public void onFailure(Call<TrueFalseAPI> call, Throwable t) {

                }
            });

            return false;
        }
    });
    View instaView;

    public void Update_Insta(View view){

        SwitchButton switchButton = (SwitchButton)view;
        instaView = switchButton;
        if(switchButton.isChecked()){
            mApp = new InstagramApp(this, ApplicationData.CLIENT_ID,
                    ApplicationData.CLIENT_SECRET, ApplicationData.CALLBACK_URL);
            mApp.setListener(new InstagramApp.OAuthAuthenticationListener() {
                @Override
                public void onSuccess() {
                    mApp.fetchUserName(handler);
                }

                @Override
                public void onFail(String error) {

                }
            });

            if (mApp.hasAccessToken()) {
                // tvSummary.setText("Connected as " + mApp.getUserName());

                mApp.fetchUserName(handler);


            }
            mApp.resetAccessToken();
            switchButton.setChecked(false);
        }else{
            mApp = new InstagramApp(this, ApplicationData.CLIENT_ID,
                    ApplicationData.CLIENT_SECRET, ApplicationData.CALLBACK_URL);
            mApp.setListener(new InstagramApp.OAuthAuthenticationListener() {
                @Override
                public void onSuccess() {
                    mApp.fetchUserName(handler);
                }

                @Override
                public void onFail(String error) {
                }
            });

            if (mApp.hasAccessToken()) {
                // tvSummary.setText("Connected as " + mApp.getUserName());

                mApp.fetchUserName(handler);


            }
            mApp.authorize();
        }
    }
}
