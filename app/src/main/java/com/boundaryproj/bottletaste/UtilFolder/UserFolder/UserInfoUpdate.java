package com.boundaryproj.bottletaste.UtilFolder.UserFolder;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyInformationAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyProfieImageData;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.APIFolder.UserService;
import com.boundaryproj.bottletaste.UtilFolder.ControllerFolder.AppController;
import com.boundaryproj.bottletaste.UtilFolder.ImageFolder.GlideImageLoader;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bongseongchan on 2017. 12. 8..
 */

public class UserInfoUpdate {

    public void Update(Context context){
        AppController appController = AppController.create(context);
        UserService userService = appController.getUserService();
        userService.MY_INFORMATION_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id()).enqueue(new Callback<MyInformationAPI>() {
            @Override
            public void onResponse(Call<MyInformationAPI> call, Response<MyInformationAPI> response) {
                if(response.body().success){
                    User user = new User(response.body().user_id,response.body().nickname,response.body().main_profile,response.body().age,response.body().job,response.body().school,response.body().info,response.body().gender);
                    AppController.getInstance().getPrefManager().storeUser(user);
                }
            }

            @Override
            public void onFailure(Call<MyInformationAPI> call, Throwable t) {

            }
        });

    }
}
