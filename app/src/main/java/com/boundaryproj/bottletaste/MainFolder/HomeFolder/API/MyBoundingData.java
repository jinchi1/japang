package com.boundaryproj.bottletaste.MainFolder.HomeFolder.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bongseongchan on 2018. 1. 2..
 */

public class MyBoundingData {

    @SerializedName("user_id") public  String user_id;
    @SerializedName("gender") public String gender;
    @SerializedName("nickname") public String nickname;
    @SerializedName("profile_photo") public String profile_photo;

}
