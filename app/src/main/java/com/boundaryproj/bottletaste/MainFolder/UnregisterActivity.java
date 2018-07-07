package com.boundaryproj.bottletaste.MainFolder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.boundaryproj.bottletaste.MainFolder.Fragment.InfoFiveFragment;
import com.boundaryproj.bottletaste.MainFolder.Fragment.InfoFourFragment;
import com.boundaryproj.bottletaste.MainFolder.Fragment.InfoOneFragment;
import com.boundaryproj.bottletaste.MainFolder.Fragment.InfoSixFragment;
import com.boundaryproj.bottletaste.MainFolder.Fragment.InfoThreeFragment;
import com.boundaryproj.bottletaste.MainFolder.Fragment.InfoTwoFragment;
import com.boundaryproj.bottletaste.MainFolder.UserFolder.LoginAPI;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.APIFolder.UserService;
import com.boundaryproj.bottletaste.UtilFolder.ControllerFolder.AppController;
import com.boundaryproj.bottletaste.UtilFolder.UserFolder.User;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnregisterActivity extends AppCompatActivity {

    private ViewPager main_view_pager;
    FragmentStatePagerAdapter adapters = null;
    LoginButton loginButton;
    private AccessTokenTracker accessTokenTracker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unregister);
        main_view_pager = (ViewPager)findViewById(R.id.viewpager);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        adapters = new MyPagerAdapter(getSupportFragmentManager());
        main_view_pager.setPageTransformer(false, new FadePageTransformer());
        main_view_pager.setAdapter(adapters);
        indicator.setViewPager(main_view_pager);
        loginButton = (LoginButton)findViewById(R.id.login_button);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();


        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        accessTokenTracker.startTracking();

        loginButton.setReadPermissions("public_profile", "user_friends","email");
        loginButton.registerCallback(callbackManager, callbacks);




        ImageView facebookbtn = (ImageView)findViewById(R.id.facebookLoginBtn);
        facebookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.callOnClick();
            }
        });

    }

    public static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private static int NUM_ITEMS = 6;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }


        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return InfoOneFragment.newInstance();
                case 1:
                    return InfoTwoFragment.newInstance();
                case 2:
                    return InfoThreeFragment.newInstance();
                case 3:
                    return InfoFourFragment.newInstance();
                case 4 :
                    return InfoFiveFragment.newInstance();
                case 5 :
                    return InfoSixFragment.newInstance();

                default:
                    return null;
            }
        }
    }

    public class FadePageTransformer implements ViewPager.PageTransformer {
        public void transformPage(View view, float position) {
            view.setTranslationX(view.getWidth() * -position);

            if(position <= -1.0F || position >= 1.0F) {
                view.setAlpha(0.0F);
            } else if( position == 0.0F ) {
                view.setAlpha(1.0F);
            } else {
                // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                view.setAlpha(1.0F - Math.abs(position));
            }
        }
    }

    private CallbackManager callbackManager = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }



    private FacebookCallback<LoginResult> callbacks = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();


            final GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.v("LoginActivity", response.toString());

                            try {
                                Log.d("object",String.valueOf(object));

                                String emailz = "";

                                try {
                                    emailz = response.getJSONObject().get("email").toString();
                                } catch (JSONException e) {
                                    emailz = "";
                                    e.printStackTrace();
                                }
                                final String email = emailz;
                                final String fbemail = object.getString("id");
                                final String fname = object.getString("name");
                                final String proimage = "https://graph.facebook.com/" + fbemail +"/picture?type=large";
                                final String getage = "08/26/1990";

                                String age_array[] = getage.split("/");

                                AppController appController = AppController.create(getApplicationContext());
                                UserService userService = appController.getUserService();
                                final String ages = String.valueOf(getAge(Integer.valueOf(age_array[2]),Integer.valueOf(age_array[1]),Integer.valueOf(age_array[0])));
                                final String school = "인하대학교";
                                final String job = "사업가";
                                final String info = "봉성찬입니돠";

                                userService.LOGIN_API_CALL(fbemail,fname,proimage, FirebaseInstanceId.getInstance().getToken().toString(),"android",ages,job,school,info,"1").enqueue(new Callback<LoginAPI>() {
                                    @Override
                                    public void onResponse(Call<LoginAPI> call, Response<LoginAPI> response) {
                                        if(response.body().success){
                                            User user = new User(response.body().user_id,response.body().nickname,response.body().profile_photo,response.body().age,response.body().job,response.body().school,response.body().info,response.body().gender);
                                            AppController.getInstance().getPrefManager().storeUser(user);
                                            finish();
                                            Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<LoginAPI> call, Throwable t) {

                                    }
                                });



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday");
            request.setParameters(parameters);
            request.executeAsync();


        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "User sign in canceled!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    public int getAge(int birthYear, int birthMonth, int birthDay)
    {
        Calendar current = Calendar.getInstance();
        int currentYear  = current.get(Calendar.YEAR);
        int currentMonth = current.get(Calendar.MONTH) + 1;
        int currentDay   = current.get(Calendar.DAY_OF_MONTH);

        int age = currentYear - birthYear + 1;
        Log.d("object", String.valueOf(age));
        // 생일 안 지난 경우 -1

        return age;
    }
}
