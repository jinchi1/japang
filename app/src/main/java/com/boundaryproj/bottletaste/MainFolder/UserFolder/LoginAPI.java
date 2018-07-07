package com.boundaryproj.bottletaste.MainFolder.UserFolder;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bongseongchan on 2017. 12. 4..
 */

public class LoginAPI {

    /*
    "success":true,"user_id":"",
"nickname":"","ptofile_photo":"",
"token":"","device":""}
     */


    @SerializedName("success") public boolean success;
    @SerializedName("user_id") public String user_id;
    @SerializedName("nickname") public String nickname;
    @SerializedName("profile_photo") public String profile_photo;
    @SerializedName("token") public String token;
    @SerializedName("device") public String device;
    @SerializedName("age") public String age;
    @SerializedName("job") public String job;
    @SerializedName("school") public String school;
    @SerializedName("info") public String info;
    @SerializedName("gender") public String gender;
}
