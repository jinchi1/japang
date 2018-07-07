package com.boundaryproj.bottletaste.MainFolder;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.boundaryproj.bottletaste.MainFolder.HomeFolder.HomeActivity;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.ControllerFolder.AppController;
import com.boundaryproj.bottletaste.UtilFolder.UserFolder.User;
import com.boundaryproj.bottletaste.databinding.ActivityRegisterBinding;
import com.bumptech.glide.Glide;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = AppController.getInstance().getPrefManager().getUser();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        Glide.with(getApplicationContext()).load(user.getProfile_photo()).into(binding.profileImageView);
        binding.nicknameLabel.setText(user.getNickname()+"님\n Boundary에 오신것을 환영합니다" );

        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

    }
}
