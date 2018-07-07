package com.boundaryproj.bottletaste.MainFolder.ChatFolder.Act;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.boundaryproj.bottletaste.MainFolder.ChatFolder.API.ChatData;
import com.boundaryproj.bottletaste.MainFolder.ChatFolder.API.ChatInfoAPI;
import com.boundaryproj.bottletaste.MainFolder.ChatFolder.Adapter.ChatAdapter;
import com.boundaryproj.bottletaste.MainFolder.ChatFolder.Model.ChatMainViewModel;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyBoundingData;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.TrueFalseAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Adpater.MyBoundingAdapter;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.APIFolder.UserService;
import com.boundaryproj.bottletaste.UtilFolder.ControllerFolder.AppController;
import com.boundaryproj.bottletaste.databinding.ActivityChatMainBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatMainActivity extends AppCompatActivity {

    String friend_id,chat_code;
    ActivityChatMainBinding binding;
    AppController appController;
    UserService userService;
    private DatabaseReference root;
    static Context mCon;
    static Activity mAct;
    ChatMainViewModel model;
    ArrayList<ChatData> chatDatas = new ArrayList<>();
    InputMethodManager imm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCon = getApplicationContext();
        mAct = this;
        model = new ChatMainViewModel();
        appController = AppController.create(getApplicationContext());
        userService = appController.getUserService();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_chat_main);
        binding.setModel(model);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        //manager.setStackFromEnd(true);
        binding.chatRecycler.setLayoutManager(manager);
        friend_id = getIntent().getStringExtra("friend_id");
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        userService.CHAT_INFO_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id(),friend_id).enqueue(new Callback<ChatInfoAPI>() {
            @Override
            public void onResponse(Call<ChatInfoAPI> call, Response<ChatInfoAPI> response) {
                if(response.body().success){

                    Glide.with(getApplicationContext()).load(response.body().profile_photo).into(binding.profileImageView);
                    binding.nicknameLabel.setText(response.body().nickname +"님과의 대화");
                    if(response.body().gender.toString().equals("0")){
                        binding.profileImageView.setBorderColor(getResources().getColor(R.color.boundary_red));
                    }else{
                        binding.profileImageView.setBorderColor(getResources().getColor(R.color.boundary_blue));
                    }
                    chat_code = response.body().chat_code;
                    FireBaseSetting();

                }
            }

            @Override
            public void onFailure(Call<ChatInfoAPI> call, Throwable t) {

            }
        });

        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map_s = new HashMap<String, Object>();
                String temp_key = root.push().getKey();
                root.updateChildren(map_s);
                DatabaseReference message_root = root.child(temp_key);
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("email", AppController.getInstance().getPrefManager().getUser().getUser_id());
                map2.put("nickname", AppController.getInstance().getPrefManager().getUser().getNickname());
                map2.put("txt",binding.chatEditText.getText().toString());
                map2.put("profile_image",AppController.getInstance().getPrefManager().getUser().getProfile_photo());
                map2.put("time",time);
                map2.put("gender",AppController.getInstance().getPrefManager().getUser().getSex());
                message_root.updateChildren(map2);
                userService.LAST_MESSAGE_SAVE_CALL(chat_code,binding.chatEditText.getText().toString()).enqueue(new Callback<TrueFalseAPI>() {
                    @Override
                    public void onResponse(Call<TrueFalseAPI> call, Response<TrueFalseAPI> response) {
                        if(response.body().success){
                            binding.chatEditText.setText("");
                            imm.hideSoftInputFromWindow(binding.chatEditText.getWindowToken(), 0);
                            SendPush();
                        }
                    }

                    @Override
                    public void onFailure(Call<TrueFalseAPI> call, Throwable t) {

                    }
                });


            }
        });

    }

    public void FireBaseSetting(){
        root = FirebaseDatabase.getInstance().getReference().getRoot();
        root = FirebaseDatabase.getInstance().getReference().child(chat_code);
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                chatDatas.clear();

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                  //  Log.d("object",String.valueOf(dataSnapshot.child("txt").getValue().toString()));
                    ChatData data = new ChatData();
                    data.email = String.valueOf(childDataSnapshot.child("email").getValue().toString());
                    data.nickname = String.valueOf(childDataSnapshot.child("nickname").getValue().toString());
                    data.profile_image = String.valueOf(childDataSnapshot.child("profile_image").getValue().toString());
                    data.time = String.valueOf(childDataSnapshot.child("time").getValue().toString());
                    data.txt = String.valueOf(childDataSnapshot.child("txt").getValue().toString());
                    data.gender = String.valueOf(childDataSnapshot.child("gender").getValue().toString());
                    chatDatas.add(data);
                }
                //Log.d("object",String.valueOf(chatDatas.size()));
                model.addChat(chatDatas);
                if(chatDatas.size()>0){
                   binding.chatRecycler.smoothScrollToPosition(chatDatas.size()-1);
                }
//                binding.chatRecycler.smoothScrollToPosition(chatDatas.size()-1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @BindingAdapter("app:chatadapter")
    public static void setChat(RecyclerView recyclerView, ArrayList<ChatData> users) {

        ChatAdapter adapter;

        if (recyclerView.getAdapter() == null) {
            adapter = new ChatAdapter();
            recyclerView.setAdapter(adapter);
        } else {
            adapter = (ChatAdapter) recyclerView.getAdapter();
        }
        adapter.add(users, mCon,mAct);


    }

    @Override
    protected void onResume() {

        RoomChecked("1");
        super.onResume();

    }

    @Override
    protected void onPause() {
        RoomChecked("0");
        super.onPause();

    }

    public void SendPush(){


        userService.PUSH_SEND_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id(),chat_code).enqueue(new Callback<TrueFalseAPI>() {
            @Override
            public void onResponse(Call<TrueFalseAPI> call, Response<TrueFalseAPI> response) {

            }

            @Override
            public void onFailure(Call<TrueFalseAPI> call, Throwable t) {

            }
        });

    }

    public void RoomChecked(String checked){
        AppController appController = AppController.create(getApplicationContext());
        UserService userService = appController.getUserService();
        userService.CHECK_ROOM_IN_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id(),getIntent().getStringExtra("chat_code"),checked).enqueue(new Callback<TrueFalseAPI>() {
            @Override
            public void onResponse(Call<TrueFalseAPI> call, Response<TrueFalseAPI> response) {

            }

            @Override
            public void onFailure(Call<TrueFalseAPI> call, Throwable t) {

            }
        });
    }
}
