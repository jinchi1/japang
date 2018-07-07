package com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API;

import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.*;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by bongseongchan on 2017. 12. 3..
 */

public interface MapBoundingService {

    @FormUrlEncoded
    @POST("budr_map/map_userlist")
    //@POST("budr_map/t_map_userlist")
    Call<MapBindingAPI> MAP_BINDING_API_CALL(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("budr_friend/friend_data")
    Call<MapUserAPI> MAP_USER_API_CALL(@Field("user_id") String user_id, @Field("friend_id") String friend_id);

    @FormUrlEncoded
    @POST("budr_friend/friend_request")
    Call<TrueFalseAPI> TRUE_FALSE_API_CALL(@Field("user_id") String user_id, @Field("friend_id") String friend_id);

    @FormUrlEncoded
    @POST("budr_filter/filter_userlist")
    Call<MapBindingAPI> FILTER_MAP_BINDING_API_CALL(@Field("user_id") String user_id, @Field("gender") String gender, @Field("min_cnt") String min_cnt, @Field("max_cnt") String max_cnt, @Field("min_age") String min_age, @Field("max_age") String max_age);

}
