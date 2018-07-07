package com.boundaryproj.bottletaste.MainFolder.ChatFolder.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bongseongchan on 2018. 1. 3..
 */

public class ChatInfoAPI {
    /*
    {"success":true,"chat_code":"","user_id":"","gender":"",
"nickname":"","profile_photo":""}
     */

    @SerializedName("success") public boolean success;
    @SerializedName("chat_code") public String chat_code;
    @SerializedName("user_id") public String user_id;
    @SerializedName("gender") public String gender;
    @SerializedName("nickname") public String nickname;
    @SerializedName("profile_photo") public String profile_photo;

}
