package com.boundaryproj.bottletaste.UtilFolder.UserFolder;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Lincoln on 07/01/16.
 */
public class UserPreferenceManager {



    private String TAG = UserPreferenceManager.class.getSimpleName();

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "boundary";

    // All Shared Preferences Keys
    /*
     String user_id, nickname, profile_photo,age,job,school,info;
     */
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_NICKNAME = "nickname";
    private static final String KEY_PROFILE_PHOTO = "profile_photo";
    private static final String KEY_PROFILE_AGE = "age";
    private static final String KEY_PROFILE_JOB = "job";
    private static final String KEY_PROFILE_SCHOOL = "school";
    private static final String KEY_PROFILE_INFO = "info";
    private static final String KEY_SEX = "sex";


    // Constructor
    public UserPreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void storeUser(User user) {
        editor.putString(KEY_USER_ID,user.getUser_id());
        editor.putString(KEY_NICKNAME,user.getNickname());
        editor.putString(KEY_PROFILE_PHOTO,user.getProfile_photo());
        editor.putString(KEY_PROFILE_AGE,user.getAge());
        editor.putString(KEY_PROFILE_JOB,user.getJob());
        editor.putString(KEY_PROFILE_SCHOOL,user.getSchool());
        editor.putString(KEY_PROFILE_INFO,user.getInfo());
        editor.putString(KEY_SEX,user.getSex());

        editor.commit();

    }




    public User getUser() {
        if (pref.getString(KEY_USER_ID, null) != null) {
            String user_id, nickname, profile_photo,age,job,school,info,sex;
            user_id = pref.getString(KEY_USER_ID,null);
            nickname = pref.getString(KEY_NICKNAME,null);
            profile_photo = pref.getString(KEY_PROFILE_PHOTO,null);
            age = pref.getString(KEY_PROFILE_AGE,null);
            job = pref.getString(KEY_PROFILE_JOB,null);
            school = pref.getString(KEY_PROFILE_SCHOOL,null);
            info = pref.getString(KEY_PROFILE_INFO,null);
            sex = pref.getString(KEY_SEX,null);
            User user = new User(user_id,nickname,profile_photo,age,job,school,info,sex);
            return user;
        }
        return null;
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }


}
