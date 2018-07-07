package com.boundaryproj.bottletaste.UtilFolder;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by bongseongchan on 2018. 2. 8..
 */

public class CenterToast {
    public void show(Context context, String txt){
        Toast toast = Toast.makeText(context, txt, Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
