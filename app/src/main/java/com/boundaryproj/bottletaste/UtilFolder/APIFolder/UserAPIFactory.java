package com.boundaryproj.bottletaste.UtilFolder.APIFolder;



import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.boundaryproj.bottletaste.UtilFolder.APIFolder.Constant.BASE_URL;


/**
 * Created by Ahmad Shubita on 8/1/17.
 */

public class UserAPIFactory {

    public static UserService create() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(UserService.class);
    }



}
