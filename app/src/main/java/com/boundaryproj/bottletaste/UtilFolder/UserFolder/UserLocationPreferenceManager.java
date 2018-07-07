package com.boundaryproj.bottletaste.UtilFolder.UserFolder;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Lincoln on 07/01/16.
 */
public class UserLocationPreferenceManager {



    private String TAG = UserLocationPreferenceManager.class.getSimpleName();

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "boundary_location";

    // All Shared Preferences Keys
    /*
     String user_id, nickname, profile_photo,age,job,school,info;
     */
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";

    // Constructor
    public UserLocationPreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void StoreLocation(UserLocation user) {
        editor.putString(KEY_LAT,user.getLat());
        editor.putString(KEY_LNG,user.getLng());
        editor.commit();

    }




    public UserLocation getLocation() {
        if (pref.getString(KEY_LAT, null) != null) {
            String lat, lng;
            lat = pref.getString(KEY_LAT,null);
            lng = pref.getString(KEY_LNG,null);
            UserLocation user = new UserLocation(lat,lng);
            return user;
        }
        return null;
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }


}
