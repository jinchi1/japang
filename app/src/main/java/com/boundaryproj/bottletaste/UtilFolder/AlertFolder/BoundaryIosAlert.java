package com.boundaryproj.bottletaste.UtilFolder.AlertFolder;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;

import com.boundaryproj.bottletaste.R;


/**
 * Created by bongseongchan on 2017. 7. 2..
 */

public class BoundaryIosAlert {

    AlertDialog simpleDialog;

    public AlertDialog getSimpleDialog(){

        return this.simpleDialog;
    }

    public void setAlertUtil(AlertDialog target){
        simpleDialog = target;
    }


    public void PhotoOneAlert(Activity activity, View.OnClickListener clickListener){



        simpleDialog = new AlertDialog.Builder(activity, R.style.CustomDialog).
                setView((((BoundaryIOSDialog)activity.getLayoutInflater().inflate(R.layout.boundary_iosstyle_dialog, null)).PhotoOneItem(clickListener))).
                setCancelable(true).create();
        simpleDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        simpleDialog.show();
        setAlertUtil(simpleDialog);
    }

    public void PhotoTwoAlert(Activity activity, View.OnClickListener clickListener){



        simpleDialog = new AlertDialog.Builder(activity, R.style.CustomDialog).
                setView((((BoundaryIOSDialog)activity.getLayoutInflater().inflate(R.layout.boundary_iosstyle_dialog, null)).PhotoTwoItem(clickListener))).
                setCancelable(true).create();
        simpleDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        simpleDialog.show();
        setAlertUtil(simpleDialog);
    }


    public void InstagramAlert(Activity activity, String string, View.OnClickListener clickListener){



        simpleDialog = new AlertDialog.Builder(activity, R.style.CustomDialog).
                setView((((BoundaryIOSDialog)activity.getLayoutInflater().inflate(R.layout.boundary_iosstyle_dialog, null)).InstagramAlert(clickListener,string))).
                setCancelable(true).create();
        simpleDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        simpleDialog.show();
        setAlertUtil(simpleDialog);
    }






}
