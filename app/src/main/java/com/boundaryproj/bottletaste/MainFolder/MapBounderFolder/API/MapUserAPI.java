package com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API;

import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyProfieImageData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bongseongchan on 2017. 12. 14..
 */

public class MapUserAPI {


    @SerializedName("success") public boolean success;
    @SerializedName("user_id") public String user_id;
    @SerializedName("nickname") public String nickname;
    @SerializedName("gender") public String gender;
    @SerializedName("age") public String age;
    @SerializedName("job") public String job;
    @SerializedName("school") public String school;
    @SerializedName("info") public String info;
    @SerializedName("main_profile") public String main_profile;
    @SerializedName("facebook") public String facebook;
    @SerializedName("instagram") public String instagram;
    @SerializedName("friend_confirm") public String friend_confirm;
    @SerializedName("img_response") public List<MyProfieImageData> myProfieImageDatas;


}
