package com.boundaryproj.bottletaste.MainFolder.ChatFolder.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bongseongchan on 2018. 1. 3..
 */

public class ChatData {
    @SerializedName("email") public String email;
    @SerializedName("nickname") public String nickname;
    @SerializedName("profile_image") public String profile_image;
    @SerializedName("time") public String time;
    @SerializedName("txt") public String txt;
    @SerializedName("gender") public String gender;
}
