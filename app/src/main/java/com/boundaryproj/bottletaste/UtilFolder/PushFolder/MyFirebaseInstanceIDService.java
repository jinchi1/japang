package com.boundaryproj.bottletaste.UtilFolder.PushFolder;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by sungchanbong on 2016. 9. 5..
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";


    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
    }


}
