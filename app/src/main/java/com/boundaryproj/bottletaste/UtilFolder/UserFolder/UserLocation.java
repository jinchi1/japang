package com.boundaryproj.bottletaste.UtilFolder.UserFolder;

import java.io.Serializable;

/**
 * Created by Lincoln on 07/01/16.
 */
public class UserLocation implements Serializable {
    /*
    user_id,nickname,profile_photo,phone,address,sub_address,
last_address,point,rank,type,created_at,recomendcode

{"success":true,"user_id":"",
"nickname":"","ptofile_photo":"","age":"","job":"",
"school":"","info":"",
"token":"","device":""}
     */
    String lat, lng;

    public UserLocation() {
    }

    public UserLocation(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;

    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }
}
