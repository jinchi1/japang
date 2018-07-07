package com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bongseongchan on 2017. 12. 14..
 */

public class MapBindingAPI {

    @SerializedName("success") public boolean success;
    @SerializedName("response") public List<MapBindingData> mapBindingDatas;
}
