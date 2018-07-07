package com.boundaryproj.bottletaste.MainFolder.HomeFolder.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bongseongchan on 2017. 12. 8..
 */

public class MyInformationAPI {

    @SerializedName("success") public boolean success;
    @SerializedName("user_id") public String user_id;
    @SerializedName("nickname") public String nickname;
    @SerializedName("age") public String age;
    @SerializedName("job") public String job;
    @SerializedName("school") public String school;
    @SerializedName("info") public String info;
    @SerializedName("main_profile") public String main_profile;
    @SerializedName("facebook") public String facebook;
    @SerializedName("instagram") public String instagram;
    @SerializedName("gender") public String gender;


    @SerializedName("img_response")
    List<MyProfieImageData> myProfieImageDatas;

    public List<MyProfieImageData> getMyProfieImageDatas() {
        return myProfieImageDatas;
    }

    public void setMyProfieImageDatas(List<MyProfieImageData> myProfieImageDatas) {
        this.myProfieImageDatas = myProfieImageDatas;
    }
}
