package com.boundaryproj.bottletaste.MainFolder.HomeFolder.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bongseongchan on 2018. 1. 2..
 */

public class ChatRoomData {
    /*
    success false 데이터없음
success true
response 배열
chat_code = 채팅코드, message = 마지막메시지, user_id = 사용자 아이디
gender = 성별, nickname = 닉네임, profile_photo = 프로필 사진
     */

    @SerializedName("chat_code") public String chat_code;
    @SerializedName("message") public String message;
    @SerializedName("user_id") public String user_id;
    @SerializedName("gender") public String gender;
    @SerializedName("nickname") public String nickname;
    @SerializedName("profile_photo") public String profile_photo;
}
