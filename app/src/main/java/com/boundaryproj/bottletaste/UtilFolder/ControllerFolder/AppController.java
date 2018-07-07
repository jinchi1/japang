package com.boundaryproj.bottletaste.UtilFolder.ControllerFolder;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API.MapBoundingFactory;
import com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API.MapBoundingService;
import com.boundaryproj.bottletaste.SplashActivity;
import com.boundaryproj.bottletaste.UtilFolder.APIFolder.UserAPIFactory;
import com.boundaryproj.bottletaste.UtilFolder.APIFolder.UserService;
import com.boundaryproj.bottletaste.UtilFolder.UserFolder.UserLocationPreferenceManager;
import com.boundaryproj.bottletaste.UtilFolder.UserFolder.UserPreferenceManager;
import com.facebook.login.LoginManager;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bongseongchan on 2017. 12. 3..
 */

public class AppController  extends Application {

    private UserService userService;
    private Scheduler scheduler;
    private UserPreferenceManager pref;
    private UserLocationPreferenceManager pref_loc;
    private MapBoundingService mapBoundingService;

    private static AppController get(Context context) {
        return (AppController) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    private static AppController mInstance;

    public MapBoundingService getMapBoundingService() {
        if(mapBoundingService == null){
            mapBoundingService = MapBoundingFactory.create();
        }
        return mapBoundingService;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static AppController create(Context context) {
        return AppController.get(context);
    }

    public UserService getUserService() {
        if (userService == null) {
            userService = UserAPIFactory.create();
        }

        return userService;
    }



    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    public UserLocationPreferenceManager getPref_loc(){
        if(pref_loc == null){
            pref_loc = new UserLocationPreferenceManager(this);
        }
        return pref_loc;
    }

    public UserPreferenceManager getPrefManager() {
        if (pref == null) {
            pref = new UserPreferenceManager(this);
        }

        return pref;
    }

    public void logout() {
        pref.clear();
//        pref_loc.clear();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}
