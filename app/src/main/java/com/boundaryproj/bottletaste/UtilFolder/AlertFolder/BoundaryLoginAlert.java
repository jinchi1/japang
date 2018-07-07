package com.boundaryproj.bottletaste.UtilFolder.AlertFolder;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;

import com.boundaryproj.bottletaste.R;


/**
 * Created by bongseongchan on 2017. 7. 2..
 */

public class BoundaryLoginAlert {

    AlertDialog simpleDialog;

    public AlertDialog getSimpleDialog(){

        return this.simpleDialog;
    }

    public void setAlertUtil(AlertDialog target){
        simpleDialog = target;
    }


    public void FacebookAlert(Activity activity, View.OnClickListener clickListener){

        simpleDialog = new AlertDialog.Builder(activity, R.style.CustomDialog).
                setView((((BoundaryLoginDialog)activity.getLayoutInflater().inflate(R.layout.boundary_login_dialog, null)).FacebookLogin(clickListener))).
                setCancelable(true).create();
        simpleDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        simpleDialog.show();
        setAlertUtil(simpleDialog);
    }


    public void LocationAlert(Activity activity, View.OnClickListener clickListener){

        simpleDialog = new AlertDialog.Builder(activity, R.style.CustomDialog).
                setView((((BoundaryLoginDialog)activity.getLayoutInflater().inflate(R.layout.boundary_login_dialog, null)).LocationAlert(clickListener))).
                setCancelable(true).create();
        simpleDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        simpleDialog.show();
        setAlertUtil(simpleDialog);
    }

    public void CustomAlert(Activity activity, String title, String subtitle, View.OnClickListener clickListener){
        simpleDialog = new AlertDialog.Builder(activity, R.style.CustomDialog).
                setView((((BoundaryLoginDialog)activity.getLayoutInflater().inflate(R.layout.boundary_login_dialog, null)).CustomAlert(title,subtitle,clickListener))).
                setCancelable(true).create();
        simpleDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        simpleDialog.show();
        setAlertUtil(simpleDialog);
    }







}
