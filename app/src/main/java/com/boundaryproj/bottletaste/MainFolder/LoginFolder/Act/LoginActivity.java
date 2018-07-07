package com.boundaryproj.bottletaste.MainFolder.LoginFolder.Act;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.boundaryproj.bottletaste.MainFolder.RegisterActivity;
import com.boundaryproj.bottletaste.MainFolder.SettingFolder.Act.WebviewActivity;
import com.boundaryproj.bottletaste.MainFolder.UserFolder.LoginAPI;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.APIFolder.UserService;
import com.boundaryproj.bottletaste.UtilFolder.AlertFolder.BoundaryLoginAlert;
import com.boundaryproj.bottletaste.UtilFolder.ControllerFolder.AppController;
import com.boundaryproj.bottletaste.UtilFolder.LocationUpdateUtil;
import com.boundaryproj.bottletaste.UtilFolder.UserFolder.User;
import com.boundaryproj.bottletaste.databinding.ActivityLoginBinding;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {



    ActivityLoginBinding binding;
    BoundaryLoginAlert alert = new BoundaryLoginAlert();
    private AccessTokenTracker accessTokenTracker = null;
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.facebookLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.FacebookAlert(LoginActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(view.getId() == R.id.positive){
                            alert.getSimpleDialog().dismiss();
                            loginButton.callOnClick();
                        }else{
                            alert.getSimpleDialog().dismiss();
                        }

                    }
                });
            }
        });

        binding.webBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),WebviewActivity.class);
                intent.putExtra("type","0");
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        binding.webBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),WebviewActivity.class);
                intent.putExtra("type","1");
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        loginButton = binding.loginButton;


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();


        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        accessTokenTracker.startTracking();

        loginButton.setReadPermissions("public_profile","email","user_birthday","user_work_history","user_education_history","user_about_me");
        loginButton.registerCallback(callbackManager, callbacks);

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
            final AccessToken accessToken = loginResult.getAccessToken();


            final GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                           // Log.v("LoginActivity", response.toString());

                            try {

                                String emailz = "";

                                try {
                                    emailz = response.getJSONObject().get("email").toString();
                                } catch (JSONException e) {
                                    emailz = "";
                                    e.printStackTrace();
                                }
                                final String fbemail = object.getString("id");
                                final String fname = object.getString("name");
                                final String proimage = "https://graph.facebook.com/" + fbemail +"/picture?type=large";
                                final String getage = object.getString("birthday");
                                String jobs = "";
                                String schools = "";
                                String gender = "";
                                if(object.getString("gender").toString().equals("male")){
                                    gender = "1";
                                }else{
                                    gender = "0";
                                }
                                String age_array[] = getage.split("/");
                                if(!object.isNull("work")){
                                    if(object.getJSONArray("work") != null){
                                        JSONArray jobarray = object.getJSONArray("work");
                                        JSONObject jsonObject = jobarray.getJSONObject(0);
                                        JSONObject jsonObject1 = jsonObject.getJSONObject("employer");
                                        jobs = jsonObject1.getString("name");
                                        Log.d("object",jobs);

                                    }else{
                                        jobs = "";
                                    }
                                }else{
                                    jobs = "";
                                }

                                if(!object.isNull("education")){
                                    JSONArray schoolarray = object.getJSONArray("education");
                                    schools = schoolarray.getJSONObject(schoolarray.length()-1).getJSONObject("school").getString("name");
                                }else{
                                    schools = "";
                                }

                                final String ages = String.valueOf(getAge(Integer.valueOf(age_array[2]),Integer.valueOf(age_array[1]),Integer.valueOf(age_array[0])));

                                String info = "";
                                if(!object.isNull("about")){
                                    info = object.getString("about");
                                }else{
                                    info = "";
                                }

                                AppController appController = AppController.create(getApplicationContext());
                                UserService userService = appController.getUserService();

                                Log.d("object",String.valueOf(FirebaseInstanceId.getInstance().getToken().toString()));


                                userService.LOGIN_API_CALL(fbemail,fname,proimage, FirebaseInstanceId.getInstance().getToken().toString(),"android",ages,jobs,schools,info,gender).enqueue(new Callback<LoginAPI>() {
                                    @Override
                                    public void onResponse(Call<LoginAPI> call, Response<LoginAPI> response) {
                                        if(response.body().success){

                                            User user = new User(response.body().user_id,response.body().nickname,response.body().profile_photo,response.body().age,response.body().job,response.body().school,response.body().info,response.body().gender);
                                            AppController.getInstance().getPrefManager().storeUser(user);
                                           // new LocationUpdateUtil().set(LoginActivity.this,getApplicationContext());
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
            parameters.putString("fields", "id,name,email,gender,birthday,work,education,about");
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
        int age = currentYear - birthYear + 1;
        return age;
    }
}
