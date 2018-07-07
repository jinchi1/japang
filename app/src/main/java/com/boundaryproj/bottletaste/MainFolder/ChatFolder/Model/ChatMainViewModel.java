package com.boundaryproj.bottletaste.MainFolder.ChatFolder.Model;

import android.databinding.ObservableArrayList;
import android.util.Log;

import com.boundaryproj.bottletaste.MainFolder.ChatFolder.API.ChatData;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.ChatRoomData;
import com.boundaryproj.bottletaste.UtilFolder.Models.BaseViewModel;

import java.util.List;

/**
 * Created by bongseongchan on 2018. 1. 2..
 */

public class ChatMainViewModel implements BaseViewModel {

    public final ObservableArrayList<ChatData> chatDatas = new ObservableArrayList<>();

    public void addChat(List<ChatData> datas){
       // chatDatas.clear();
        chatDatas.clear();
        //Log.d("object",String.valueOf(chatDatas.size()));
        for(int i = 0 ; i<datas.size();i++){
            chatDatas.add(datas.get(i));
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
