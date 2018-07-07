package com.boundaryproj.bottletaste.MainFolder.HomeFolder.Model;

import android.databinding.ObservableArrayList;
import android.util.Log;

import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.ChatRoomData;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyBoundingData;
import com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API.MapBindingData;
import com.boundaryproj.bottletaste.UtilFolder.Models.BaseViewModel;

import java.util.List;

/**
 * Created by bongseongchan on 2018. 1. 24..
 */

public class MapBIndingViewModel implements BaseViewModel {

    public final ObservableArrayList<MapBindingData> mapBindingDatas = new ObservableArrayList<>();

    @Override
    public void onCreate() {

    }

    public void getMapBinding(List<MapBindingData> datas ){



        mapBindingDatas.clear();
        for(int i = 0 ; i<datas.size();i++){
            mapBindingDatas.add(datas.get(i));
        }
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }
}
