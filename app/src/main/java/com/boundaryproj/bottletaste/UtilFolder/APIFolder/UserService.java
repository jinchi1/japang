package com.boundaryproj.bottletaste.UtilFolder.APIFolder;

import com.boundaryproj.bottletaste.MainFolder.ChatFolder.API.ChatInfoAPI;
import com.boundaryproj.bottletaste.MainFolder.FriemdFolder.API.CancelFriendAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.ChatRoomAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.CountPeopleAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.CountTotalAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyBounderAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyBoundingAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyInformationAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.TrueFalseAPI;
import com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API.RequestBounderAPI;
import com.boundaryproj.bottletaste.MainFolder.MypageFolder.API.ProfileImageChangeAPI;
import com.boundaryproj.bottletaste.MainFolder.MypageFolder.API.ProfileImageDeleteAPI;
import com.boundaryproj.bottletaste.MainFolder.SettingFolder.API.InstaCheckAPI;
import com.boundaryproj.bottletaste.MainFolder.SettingFolder.API.PushControlAPI;
import com.boundaryproj.bottletaste.MainFolder.SettingFolder.API.PushStateAPI;
import com.boundaryproj.bottletaste.MainFolder.UserFolder.LoginAPI;
import com.boundaryproj.bottletaste.UtilFolder.UserFolder.UserLocationUpdate;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by bongseongchan on 2017. 12. 3..
 */

public interface UserService {

    @FormUrlEncoded
    @POST("budr_user/get_user")
    Call<MyInformationAPI> MY_INFORMATION_API_CALL(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("budr_user/budr_user_register_login")
    Call<LoginAPI> LOGIN_API_CALL(@Field("user_id") String user_id, @Field("nickname") String nickname, @Field("profile_photo") String profile_photo, @Field("token") String token, @Field("device") String device, @Field("age") String age, @Field("job") String job, @Field("school") String school, @Field("info") String info, @Field("gender") String gender);

    @FormUrlEncoded
    @POST("budr_user/instagram_action")
    Call<TrueFalseAPI> INSTAGRAM_CONTROL_API(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("budr_user/user_info_update")
    Call<TrueFalseAPI> PROFILE_CHANGE_API(@Field("user_id") String user_id, @Field("nickname") String nickname, @Field("age") String age, @Field("job") String job, @Field("school") String school, @Field("info") String info);

    @Multipart
    @POST("budr_user/photo_update")
    Call<ProfileImageChangeAPI> PROFILE_IMAGE_CHANGE_API_CALL(@PartMap() Map<String,RequestBody> mapFileAndName);

    @FormUrlEncoded
    @POST("budr_user/photo_remove")
    Call<ProfileImageDeleteAPI> PROFILE_IMAGE_DELETE_API_CALL(@Field("user_id") String user_id, @Field("no") String no);


    // TODO: 2018. 1. 4. LocationUpdate;
    @FormUrlEncoded
    @POST("budr_bounding/location_bounding")
    Call<UserLocationUpdate> USER_LOCATION_UPDATE_CALL(@Field("user_id") String user_id, @Field("lat") String lat, @Field("lng") String lng);


    // TODO: 2018. 1. 4. Bounding Count;

    @FormUrlEncoded
    @POST("budr_special/dailey_bounding")
    Call<CountTotalAPI> COUNT_TOTAL_API_CALL(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("budr_special/friend_bounding_cnt")
    Call<CountPeopleAPI> COUNT_PEOPLE_API_CALL(@Field("user_id") String user_id, @Field("friend_id") String friend_id);

    @FormUrlEncoded
    @POST("budr_special/following_bounding_cnt")
    Call<CountPeopleAPI> MY_COUNT_BOUND_API_CALL(@Field("user_id") String user_id);

    // TODO: 2018. 1. 2. MY Bounder;;

    //채팅방리스트
    @FormUrlEncoded
    @POST("budr_search/chatlist_data")
    Call<ChatRoomAPI> CHAT_ROOM_API_CALL(@Field("user_id") String user_id);

    //바운더리스트
    @FormUrlEncoded
    @POST("budr_search/bounderlist_data")
    Call<MyBounderAPI> MY_BOUNDER_API_CALL(@Field("user_id") String user_id);

    //바운딩리스트
    @FormUrlEncoded
    @POST("budr_search/boundinglist_data")
    Call<MyBoundingAPI> MY_BOUNDING_API_CALL(@Field("user_id") String user_id);

    // TODO: 2018. 1. 31. 검색

    @FormUrlEncoded
    @POST("budr_search/search_chatlist_data")
    Call<ChatRoomAPI> SEARCH_CHAT_ROOM_API_CALL(@Field("user_id") String user_id, @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST("budr_search/search_bounderlist_data")
    Call<MyBounderAPI> SEARCH_MY_BOUNDER_API_CALL(@Field("user_id") String user_id, @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST("budr_search/search_boundinglist_data")
    Call<MyBoundingAPI> SEARCH_MY_BOUNDING_API_CALL(@Field("user_id") String user_id, @Field("keyword") String keyword);

    // TODO: 2018. 1. 2. 친구관리

    //친구끊기
    @FormUrlEncoded
    @POST("budr_friend/friend_cut")
    Call<CancelFriendAPI> CANCEL_FRIEND_API_CALL(@Field("user_id") String user_id, @Field("friend_id") String friend_id);

    //바운딩 신청
    @FormUrlEncoded
    @POST("budr_friend/friend_request")
    Call<RequestBounderAPI> REQUEST_BOUNDER_API_CALL(@Field("user_id") String user_id, @Field("friend_id") String friend_id);

    @FormUrlEncoded
    @POST("budr_special/budr_block")
    Call<TrueFalseAPI> USER_BLOCK_API_CALL(@Field("user_id") String user_id, @Field("friend_id") String friend_id);

    // TODO: 2018. 1. 3. 채팅방

    //채팅방 정보
    @FormUrlEncoded
    @POST("budr_chat/chatroom_detail")
    Call<ChatInfoAPI> CHAT_INFO_API_CALL(@Field("user_id") String user_id, @Field("friend_id") String friend_id);

    @FormUrlEncoded
    @POST("budr_chat/chat_last_message")
    Call<TrueFalseAPI> LAST_MESSAGE_SAVE_CALL(@Field("chat_code") String chat_code, @Field("message") String message);

    @FormUrlEncoded
    @POST("budr_chat/chat_in_out")
    Call<TrueFalseAPI> CHECK_ROOM_IN_CALL(@Field("user_id") String user_id, @Field("chat_code") String chat_code, @Field("confirm") String confirm);

    @FormUrlEncoded
    @POST("budr_push/chat_push")
    Call<TrueFalseAPI> PUSH_SEND_CALL(@Field("user_id") String user_id, @Field("chat_code") String chat_code);

    // TODO: 2018. 1. 5. 설정

    @FormUrlEncoded
    @POST("budr_user/check_setting")
    Call<PushStateAPI> PUSH_STATE_API_CALL(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("budr_user/setting_control")
    Call<PushControlAPI> PUSH_CONTROL_API_CALL(@Field("user_id") String user_id, @Field("type") String type, @Field("s_type") String s_type);


    @FormUrlEncoded
    @POST("budr_user/member_leave")
    Call<TrueFalseAPI> REMOVE_USER_ID_CALL(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("budr_user/instagram_join")
    Call<TrueFalseAPI> INSTA_ID_UP_CALL(@Field("user_id") String user_id, @Field("insta_id") String insta_id);

    @FormUrlEncoded
    @POST("budr_user/instagram_join_check")
    Call<InstaCheckAPI> INSTA_CONNECT_CALL(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("budr_special/location_update")
    Call<TrueFalseAPI> LOCATION_UPDATE(@Field("user_id") String user_id, @Field("lat") String lat, @Field("lng") String lng);
}
