package com.boundaryproj.bottletaste.UtilFolder.Models;

/**
 * Created by bongseongchan on 2017. 9. 26..
 */


public interface BaseViewModel {
    void onCreate();

    void onResume();

    void onPause();

    void onDestroy();
}