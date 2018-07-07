package com.boundaryproj.bottletaste.MainFolder.TestFolder;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.boundaryproj.bottletaste.MainActivity;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.InstagramFolder.ApplicationData;
import com.boundaryproj.bottletaste.UtilFolder.InstagramFolder.InstagramApp;


public class TestActivity extends AppCompatActivity  {

    private InstagramApp mApp;

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            Log.d("object",String.valueOf(mApp.getId()));
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mApp = new InstagramApp(this, ApplicationData.CLIENT_ID,
                ApplicationData.CLIENT_SECRET, ApplicationData.CALLBACK_URL);
        mApp.setListener(new InstagramApp.OAuthAuthenticationListener() {
            @Override
            public void onSuccess() {
                mApp.fetchUserName(handler);
            }

            @Override
            public void onFail(String error) {
                Log.d("object",String.valueOf(error));

            }
        });

        if (mApp.hasAccessToken()) {
            // tvSummary.setText("Connected as " + mApp.getUserName());

            mApp.fetchUserName(handler);


        }

        connectOrDisconnectUser();


    }

    private void connectOrDisconnectUser() {
        if (mApp.hasAccessToken()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(
                    TestActivity.this);
            builder.setMessage("Disconnect from Instagram?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    mApp.resetAccessToken();
                                    // btnConnect.setVisibility(View.VISIBLE);

                                    // tvSummary.setText("Not connected");
                                }
                            })
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.cancel();
                                }
                            });
            final AlertDialog alert = builder.create();
            alert.show();
        } else {
            mApp.authorize();
        }
    }



}
