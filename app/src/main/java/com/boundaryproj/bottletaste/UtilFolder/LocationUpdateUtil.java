package com.boundaryproj.bottletaste.UtilFolder;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.TrueFalseAPI;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.APIFolder.UserService;
import com.boundaryproj.bottletaste.UtilFolder.AlertFolder.BoundaryLoginAlert;
import com.boundaryproj.bottletaste.UtilFolder.ControllerFolder.AppController;
import com.boundaryproj.bottletaste.UtilFolder.ServiceFolder.RTGPSLocationService;
import com.boundaryproj.bottletaste.UtilFolder.UserFolder.UserLocation;
import com.boundaryproj.bottletaste.UtilFolder.UserFolder.UserLocationUpdate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bongseongchan on 2018. 1. 17..
 */

public class LocationUpdateUtil {

    BoundaryLoginAlert alert = new BoundaryLoginAlert();
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 50; // Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 30000; // in Milliseconds
    protected LocationManager locationManager;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    Context con;

    public void set(Activity activity, final Context context){
        con = context;

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        // Log.d("RTGPSLocationService", " " + isGPSEnabled + " " + isNetworkEnabled);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            //  Log.d(TAG, "Location Permission is declined");
        } else {
            if (isGPSEnabled) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        MINIMUM_TIME_BETWEEN_UPDATES,
                        MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, new LocationUpdateUtil.MyLocationListener());
                Location location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                callLocationDetailAPI(location);
            } else if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        MINIMUM_TIME_BETWEEN_UPDATES,
                        MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, new LocationUpdateUtil.MyLocationListener());
                Location location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                callLocationDetailAPI(location);

            }else{

            }
        }

    }

    private class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {


            callLocationDetailAPI(location);
        }

        public void onStatusChanged(String s, int i, Bundle b) {
        }

        public void onProviderDisabled(String s) {
        }

        public void onProviderEnabled(String s) {
        }
    }

    public void callLocationDetailAPI(Location location){
        if(AppController.getInstance().getPref_loc().getLocation() != null){
            if(location != null){
                if(!String.valueOf(location.getLatitude()).toString().equals("0")){
                    UserLocation userLocation = new UserLocation(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
                    AppController.getInstance().getPref_loc().StoreLocation(userLocation);
                    LocationUpdate(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
                }
            }
        }else{
            if(!String.valueOf(location.getLatitude()).toString().equals("0")){
                UserLocation userLocation = new UserLocation(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
                AppController.getInstance().getPref_loc().StoreLocation(userLocation);
                PushLocationUpdate(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
            }
        }

    }

    public void quit() {
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        System.exit(0);
    }

    public void LocationUpdate(String lat, String lng){
        if(AppController.getInstance().getPrefManager().getUser() != null){
            AppController appController = AppController.create(con);
            UserService userService = appController.getUserService();
            userService.LOCATION_UPDATE(AppController.getInstance().getPrefManager().getUser().getUser_id(),lat,lng).enqueue(new Callback<TrueFalseAPI>() {
                @Override
                public void onResponse(Call<TrueFalseAPI> call, Response<TrueFalseAPI> response) {

                }

                @Override
                public void onFailure(Call<TrueFalseAPI> call, Throwable t) {

                }
            });
        }

    }

    public void PushLocationUpdate(String lat, String lng){

        if(AppController.getInstance().getPrefManager().getUser() != null){
            AppController appController = AppController.create(con);
            UserService userService = appController.getUserService();
            userService.USER_LOCATION_UPDATE_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id(),lat,lng).enqueue(new Callback<UserLocationUpdate>() {
                @Override
                public void onResponse(Call<UserLocationUpdate> call, Response<UserLocationUpdate> response) {

                }

                @Override
                public void onFailure(Call<UserLocationUpdate> call, Throwable t) {

                }
            });
        }
    }
}
