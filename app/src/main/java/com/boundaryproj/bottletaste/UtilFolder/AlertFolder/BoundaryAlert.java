package com.boundaryproj.bottletaste.UtilFolder.AlertFolder;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;

import com.boundaryproj.bottletaste.R;


/**
 * Created by bongseongchan on 2017. 7. 2..
 */

public class BoundaryAlert {

    AlertDialog simpleDialog;

    public AlertDialog getSimpleDialog(){

        return this.simpleDialog;
    }

    public void setAlertUtil(AlertDialog target){
        simpleDialog = target;
    }


    public void InstagramAlert(Activity activity, String string, View.OnClickListener clickListener){

        simpleDialog = new AlertDialog.Builder(activity, R.style.CustomDialog).
                setView((((BoundaryDialog)activity.getLayoutInflater().inflate(R.layout.boundary_dialog, null)).InstagramAlert(clickListener,string))).
                setCancelable(true).create();


        simpleDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        simpleDialog.show();
        setAlertUtil(simpleDialog);
    }

    public void WhiteAlert(Activity activity, String string, View.OnClickListener clickListener){

        simpleDialog = new AlertDialog.Builder(activity, R.style.CustomDialog).
                setView((((BoundaryDialog)activity.getLayoutInflater().inflate(R.layout.boundary_dialog, null)).WhiteAlert(clickListener,string))).
                setCancelable(true).create();
        simpleDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        simpleDialog.show();
        setAlertUtil(simpleDialog);
    }

    public void WhiteAlert_Blue(Activity activity, String string, View.OnClickListener clickListener){

        simpleDialog = new AlertDialog.Builder(activity, R.style.CustomDialog).
                setView((((BoundaryDialog)activity.getLayoutInflater().inflate(R.layout.boundary_dialog, null)).WhiteAlert_blue(clickListener,string))).
                setCancelable(true).create();
        simpleDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        simpleDialog.show();
        setAlertUtil(simpleDialog);
    }








}
