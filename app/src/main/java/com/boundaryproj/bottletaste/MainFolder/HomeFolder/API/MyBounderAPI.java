package com.boundaryproj.bottletaste.MainFolder.HomeFolder.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bongseongchan on 2018. 1. 2..
 */

public class MyBounderAPI {

    @SerializedName("success") public boolean success;
    @SerializedName("response") public List<MyBounderData> myBounderDatas;
}
