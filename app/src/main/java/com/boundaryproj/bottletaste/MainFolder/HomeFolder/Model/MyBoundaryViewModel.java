package com.boundaryproj.bottletaste.MainFolder.HomeFolder.Model;

import android.databinding.ObservableArrayList;

import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.ChatRoomAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.ChatRoomData;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyBounderData;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyBoundingData;
import com.boundaryproj.bottletaste.UtilFolder.Models.BaseViewModel;

import java.util.List;

/**
 * Created by bongseongchan on 2018. 1. 2..
 */

public class MyBoundaryViewModel implements BaseViewModel {

    public final ObservableArrayList<ChatRoomData> chatRoomDatas = new ObservableArrayList<>();
    public final ObservableArrayList<MyBounderData> myBounderDatas = new ObservableArrayList<>();
    public final ObservableArrayList<MyBoundingData> myBoundingDatas = new ObservableArrayList<>();


    public void getChatRoom(List<ChatRoomData> data){
        chatRoomDatas.clear();
        for (int i = 0 ; i<data.size(); i++){
            chatRoomDatas.add(data.get(i));
        }
    }

    public void getMyBounder(List<MyBounderData> data){
        myBounderDatas.clear();
        for(int i = 0 ; i<data.size(); i++){
            myBounderDatas.add(data.get(i));
        }
    }

    public void getMyBounding(List<MyBoundingData> datas){
        myBoundingDatas.clear();
        for(int i = 0;i<datas.size();i++){
            myBoundingDatas.add(datas.get(i));
        }
    }
    @Override
    public void onCreate() {

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
