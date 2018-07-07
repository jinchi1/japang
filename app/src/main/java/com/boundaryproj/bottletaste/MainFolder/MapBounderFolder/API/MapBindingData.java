package com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bongseongchan on 2017. 12. 14..
 */

public class MapBindingData {

    @SerializedName("user_id") public String user_id;
    @SerializedName("nickname") public String nickname;
    @SerializedName("profile_photo") public String profile_photo;
    @SerializedName("friend_cnt") public String friend_cnt;
    @SerializedName("rapport_cnt") public String rapport_cnt;
}
