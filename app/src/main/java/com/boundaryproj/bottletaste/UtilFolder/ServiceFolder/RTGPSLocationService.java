package com.boundaryproj.bottletaste.UtilFolder.ServiceFolder;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.boundaryproj.bottletaste.MainActivity;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Act.BoudndaryActivity;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.APIFolder.UserService;
import com.boundaryproj.bottletaste.UtilFolder.ControllerFolder.AppController;
import com.boundaryproj.bottletaste.UtilFolder.UserFolder.UserLocation;
import com.boundaryproj.bottletaste.UtilFolder.UserFolder.UserLocationUpdate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bongseongchan on 2018. 1. 4..
 */

public class RTGPSLocationService extends Service {
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 20; // Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 2000; // in Milliseconds
    protected LocationManager locationManager;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    private final String TAG = "RTGPSLocationService";
    static Context mCon;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "GPS Service created ...");
        mCon = this;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
       // Log.d("RTGPSLocationService", " " + isGPSEnabled + " " + isNetworkEnabled);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
          //  Log.d(TAG, "Location Permission is declined");
        } else {
            if (isGPSEnabled) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        MINIMUM_TIME_BETWEEN_UPDATES,
                        MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, new MyLocationListener());
                Location location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                callLocationDetailAPI(location);
            } else if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        MINIMUM_TIME_BETWEEN_UPDATES,
                        MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, new MyLocationListener());
                Location location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                callLocationDetailAPI(location);


            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "GPS Service destroyed ...");
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
                Location before_location = new Location("before");
                before_location.setLatitude(Double.valueOf(AppController.getInstance().getPref_loc().getLocation().getLat()));
                before_location.setLongitude(Double.valueOf(AppController.getInstance().getPref_loc().getLocation().getLng()));
                float distanceTo = location.distanceTo(before_location);
                if(distanceTo>50){
                    UserLocation userLocation = new UserLocation(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
                    AppController.getInstance().getPref_loc().StoreLocation(userLocation);
                    if(AppController.getInstance().getPrefManager().getUser() != null){
                        if(!String.valueOf(location.getLatitude()).toString().equals("0")){
                            LocationUpdateAPI(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
                        }

                    }

                }
            }



        }else{
            if(!String.valueOf(location.getLatitude()).toString().equals("0")){
                UserLocation userLocation = new UserLocation(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
                AppController.getInstance().getPref_loc().StoreLocation(userLocation);
            }


        }

    }
    public void LocationUpdateAPI(String lat, String lng){

        if(AppController.getInstance().getPrefManager().getUser() != null){
            AppController appController = AppController.create(mCon);
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
