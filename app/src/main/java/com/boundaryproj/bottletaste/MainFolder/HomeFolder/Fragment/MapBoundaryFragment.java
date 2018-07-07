package com.boundaryproj.bottletaste.MainFolder.HomeFolder.Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.FloatMath;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boundaryproj.bottletaste.MainFolder.ChatFolder.Act.ChatMainActivity;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.ChatRoomData;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyProfieImageData;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Adpater.ChatRoomAdapter;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Adpater.MapBindingAdapter;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Model.MapBIndingViewModel;
import com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API.MapBindingAPI;
import com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API.MapBindingData;
import com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API.MapBoundingService;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Act.BoudndaryActivity;
import com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API.MapUserAPI;
import com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API.RequestBounderAPI;
import com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.Fragment.FriendInformationFragment;
import com.boundaryproj.bottletaste.MainFolder.MypageFolder.Fragment.MypageSettingFragment;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.APIFolder.UserService;
import com.boundaryproj.bottletaste.UtilFolder.AlertFolder.BoundaryAlert;
import com.boundaryproj.bottletaste.UtilFolder.CenterToast;
import com.boundaryproj.bottletaste.UtilFolder.ControllerFolder.AppController;
import com.boundaryproj.bottletaste.UtilFolder.ImageFolder.GlideImageLoader;
import com.boundaryproj.bottletaste.UtilFolder.SizeUtil;
import com.boundaryproj.bottletaste.databinding.FragmentMapBoundaryBinding;
import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.apptik.widget.MultiSlider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapBoundaryFragment extends Fragment {


    public MapBoundaryFragment() {
        // Required empty public constructor
    }


    FragmentMapBoundaryBinding binding;
    int maximum =100;
    int minimum = 0;
    int age_maximum = 100;
    int age_minimum = 19;
    String gender = "2";
    MapBIndingViewModel model;
    static Context mCon;
    static Fragment fragment;
    static Activity mAct;
    BoundaryAlert alert = new BoundaryAlert();
    AppController appController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return DataBindingUtil.inflate(inflater, R.layout.fragment_map_boundary, container, false).getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        appController = AppController.create(getContext());

        Button confirm_Btn = (Button)getActivity().findViewById(R.id.profileChange_btn);
        confirm_Btn.setVisibility(View.GONE);
        ImageView logo = (ImageView)getActivity().findViewById(R.id.logoImageView);
        logo.setVisibility(View.VISIBLE);
        TextView logo_title = (TextView)getActivity().findViewById(R.id.profileLogotitleLabel);
        logo_title.setVisibility(View.GONE);
        ImageView block_icon = (ImageView)getActivity().findViewById(R.id.block_icon);
        block_icon.setVisibility(View.GONE);

        mCon = getContext();
        mAct = getActivity();
        fragment = MapBoundaryFragment.this;

        binding = DataBindingUtil.getBinding(getView());
        model = new MapBIndingViewModel();
        binding.setModel(model);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setLayoutManager(layoutManager);


        binding.rangeSlider.setOnThumbValueChangeListener(new MultiSlider.SimpleChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int
                    thumbIndex, int value) {
                if (thumbIndex == 0) {
                    minimum = value;
                    binding.counterLabel.setText(String.valueOf(minimum) + "번 ~ "  + String.valueOf(maximum) +"번");
                } else {
                    maximum = value;
                    binding.counterLabel.setText(String.valueOf(minimum) + "번 ~ "  + String.valueOf(maximum) +"번");
                }
            }
        });

        binding.ageMultiSlider.setOnThumbValueChangeListener(new MultiSlider.SimpleChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int
                    thumbIndex, int value) {
                if (thumbIndex == 0) {
                    age_minimum = value;
                    binding.ageCountLabel.setText(String.valueOf(age_minimum) + "세 ~ "  + String.valueOf(age_maximum) +"세");
                } else {
                    age_maximum = value;
                    binding.ageCountLabel.setText(String.valueOf(age_minimum) + "세 ~ "  + String.valueOf(age_maximum) +"세");
                }
            }
        });

        binding.bothBtn.setSelected(true);
        binding.femaleBtn.setSelected(false);
        binding.maleBtn.setSelected(false);
        binding.bothBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.bothBtn.isSelected() ){
                    gender = "2";
                    binding.bothBtn.setSelected(false);
                    binding.bothBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                    binding.bothBtn.setTextColor(Color.parseColor("#666666"));

                }else{
                    gender = "2";
                    if(binding.maleBtn.isSelected() || binding.femaleBtn.isSelected()){
                        binding.maleBtn.setSelected(false);
                        binding.maleBtn.setTextColor(Color.parseColor("#666666"));
                        binding.maleBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                        binding.bothBtn.setSelected(true);
                        binding.bothBtn.setBackgroundResource(R.drawable.red_rounded_btn);
                        binding.bothBtn.setTextColor(Color.parseColor("#ffffff"));
                        binding.femaleBtn.setSelected(false);
                        binding.femaleBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                        binding.femaleBtn.setTextColor(Color.parseColor("#666666"));
                    }else {
                        binding.bothBtn.setSelected(true);
                        binding.bothBtn.setBackgroundResource(R.drawable.red_rounded_btn);
                        binding.bothBtn.setTextColor(Color.parseColor("#ffffff"));
                    }
                }

            }
        });
        binding.femaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.femaleBtn.isSelected() ){
                    binding.femaleBtn.setSelected(false);
                    binding.femaleBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                    binding.femaleBtn.setTextColor(Color.parseColor("#666666"));

                    gender = "2";
                    binding.bothBtn.setSelected(true);
                    binding.bothBtn.setBackgroundResource(R.drawable.red_rounded_btn);
                    binding.bothBtn.setTextColor(Color.parseColor("#ffffff"));

                }else{
                    gender = "0";
                    if(binding.maleBtn.isSelected() || binding.bothBtn.isSelected()){
                        binding.maleBtn.setSelected(false);
                        binding.maleBtn.setTextColor(Color.parseColor("#666666"));
                        binding.maleBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                        binding.femaleBtn.setSelected(true);
                        binding.femaleBtn.setBackgroundResource(R.drawable.red_rounded_btn);
                        binding.femaleBtn.setTextColor(Color.parseColor("#ffffff"));

                        binding.bothBtn.setSelected(false);
                        binding.bothBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                        binding.bothBtn.setTextColor(Color.parseColor("#666666"));
                    }else {
                        binding.femaleBtn.setSelected(true);
                        binding.femaleBtn.setBackgroundResource(R.drawable.red_rounded_btn);
                        binding.femaleBtn.setTextColor(Color.parseColor("#ffffff"));
                    }
                }
            }
        });

        binding.maleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.maleBtn.isSelected()){

                    gender = "2";
                    binding.bothBtn.setSelected(true);
                    binding.bothBtn.setBackgroundResource(R.drawable.red_rounded_btn);
                    binding.bothBtn.setTextColor(Color.parseColor("#ffffff"));


                    binding.maleBtn.setSelected(false);
                    binding.maleBtn.setTextColor(Color.parseColor("#666666"));
                    binding.maleBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                }else{
                    gender = "1";
                    if(binding.femaleBtn.isSelected() || binding.bothBtn.isSelected()){
                        binding.femaleBtn.setSelected(false);
                        binding.femaleBtn.setTextColor(Color.parseColor("#666666"));
                        binding.femaleBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                        binding.maleBtn.setSelected(true);
                        binding.maleBtn.setTextColor(Color.parseColor("#ffffff"));
                        binding.maleBtn.setBackgroundResource(R.drawable.red_rounded_btn);

                        binding.bothBtn.setSelected(false);
                        binding.bothBtn.setBackgroundResource(R.drawable.white_rounded_btn);
                        binding.bothBtn.setTextColor(Color.parseColor("#666666"));
                    }else {
                        binding.maleBtn.setSelected(true);
                        binding.maleBtn.setTextColor(Color.parseColor("#ffffff"));
                        binding.maleBtn.setBackgroundResource(R.drawable.red_rounded_btn);
                    }
                }
            }
        });

        binding.settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.settingLayer.getVisibility() == View.VISIBLE){
                    binding.settingLayer.setVisibility(View.GONE);
                }else{
                    binding.settingLayer.setVisibility(View.VISIBLE);
                }
            }
        });

        Glide.with(getContext()).load(AppController.getInstance().getPrefManager().getUser().getProfile_photo()).into(binding.myprofileView);
        final MapBoundingService mapBoundingService = appController.getMapBoundingService();
        mapBoundingService.MAP_BINDING_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id()).enqueue(new Callback<MapBindingAPI>() {
            @Override
            public void onResponse(Call<MapBindingAPI> call, final Response<MapBindingAPI> response) {
                if(response.body().success){
                    SharedPreferences prefs = getActivity().getSharedPreferences("adapter", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("position", "0");
                    editor.commit();
                    binding.contentLayers.setVisibility(View.VISIBLE);
                    binding.todaycountLabel.setText(String.valueOf(response.body().mapBindingDatas.size()) + "명");
                    model.getMapBinding(response.body().mapBindingDatas);
                    ShowUserProfile(response.body().mapBindingDatas.get(0).user_id,response.body().mapBindingDatas.get(0).rapport_cnt);

                }else{
                    binding.contentLayers.setVisibility(View.GONE);
                    binding.todaycountLabel.setText("0명");
                }
            }

            @Override
            public void onFailure(Call<MapBindingAPI> call, Throwable t) {

            }
        });


        binding.filterConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.settingLayer.setVisibility(View.GONE);
                FilterAction();
            }
        });

        binding.myprofileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BoudndaryActivity)getActivity()).replaceFragment(new FriendInformationFragment().newInstance(AppController.getInstance().getPrefManager().getUser().getUser_id()),"FriendInformationFragment");
            }
        });

    }

    public void FilterAction(){



        final MapBoundingService mapBoundingService = appController.getMapBoundingService();
        mapBoundingService.FILTER_MAP_BINDING_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id(),String.valueOf(gender),String.valueOf(minimum),String.valueOf(maximum),String.valueOf(age_minimum),String.valueOf(age_maximum)).enqueue(new Callback<MapBindingAPI>() {
            @Override
            public void onResponse(Call<MapBindingAPI> call, Response<MapBindingAPI> response) {

                if(response.body().success){
                    binding.contentLayers.setVisibility(View.VISIBLE);
                    binding.todaycountLabel.setText(String.valueOf(response.body().mapBindingDatas.size()) + "명");
                    model.getMapBinding(response.body().mapBindingDatas);
                    ShowUserProfile(response.body().mapBindingDatas.get(0).user_id,response.body().mapBindingDatas.get(0).rapport_cnt);

                }else{
                    binding.contentLayers.setVisibility(View.GONE);
                    binding.todaycountLabel.setText("0명");
                }

            }

            @Override
            public void onFailure(Call<MapBindingAPI> call, Throwable t) {

            }
        });
    }

    public String friend_state;
    public String user_nickname;
    public void ShowUserProfile(final String email, final String counter){
        final MapBoundingService mapBoundingService = appController.getMapBoundingService();
        mapBoundingService.MAP_USER_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id(),email).enqueue(new Callback<MapUserAPI>() {
            @Override
            public void onResponse(Call<MapUserAPI> call, Response<MapUserAPI> response) {
                if(response.body().success){

                    binding.userInfoLabel.setText(response.body().nickname+", "+response.body().age + "세");

                    if(response.body().friend_confirm.toString().equals("0")){
                        binding.boundingBtn.setImageResource(R.drawable.add_friend_btn_binding);

                    }else if(response.body().friend_confirm.toString().equals("1")){
                        binding.boundingBtn.setImageResource(R.drawable.friend_bounding_activation_btn);

                    }else{
                        binding.boundingBtn.setImageResource(R.drawable.friend_active_bounding);

                    }

                    user_nickname = response.body().nickname;
                    friend_state = response.body().friend_confirm.toString();

                    binding.counterLabels.setText(counter);

                    binding.myboundingsBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new CenterToast().show(getContext(),"그동안 "+user_nickname+"님과 지나친 누적 횟수 입니다.");
                        }
                    });

                    List<MyProfieImageData> contentsDatas = response.body().myProfieImageDatas;
                    Glide.with(getActivity()).load(contentsDatas.get(0).image).into(binding.userProfileImage);

                        binding.userProfileImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(binding.settingLayer.getVisibility() == View.VISIBLE){

                                }else{
                                    ((BoudndaryActivity)getActivity()).replaceFragment(new FriendInformationFragment().newInstance(email),"FriendInformationFragment");

                                }
                            }
                        });

                        binding.boundingBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(binding.settingLayer.getVisibility() == View.VISIBLE){

                                }else{
                                    String message = "";
                                    if(friend_state.toString().equals("2") || friend_state.toString().equals("1")){
                                        message = "정말 "+user_nickname +" 님과의 Bounding을 취소하시겠습니까?";
                                    }else{
                                        message = user_nickname +" 님을 Bounding 하시겠습니까?";
                                    }
                                    alert.WhiteAlert(getActivity(), message, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(view.getId() == R.id.positive){
                                                alert.getSimpleDialog().dismiss();
                                                RequestBounder(email);
                                            }else{
                                                alert.getSimpleDialog().dismiss();
                                            }
                                        }
                                    });
                                }


                            }
                        });


                }
            }

            @Override
            public void onFailure(Call<MapUserAPI> call, Throwable t) {

            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @BindingAdapter("app:mapboundingadapter")
    public static void setBinding(RecyclerView recyclerView, ArrayList<MapBindingData> users) {

        MapBindingAdapter adapter;

        if (recyclerView.getAdapter() == null) {
            adapter = new MapBindingAdapter();
            recyclerView.setAdapter(adapter);
        } else {
            adapter = (MapBindingAdapter) recyclerView.getAdapter();
        }
        adapter.add(users, mCon,mAct,fragment);


    }


    @Override
    public void onResume() {
        super.onResume();


    }

    public void RequestBounder(String friend_id){
        UserService userService = appController.getUserService();
        userService.REQUEST_BOUNDER_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id(),friend_id).enqueue(new Callback<RequestBounderAPI>() {
            @Override
            public void onResponse(Call<RequestBounderAPI> call, Response<RequestBounderAPI> response) {
                if(response.body().success){
                    if(response.body().budr_check.toString().equals("1")){
                        friend_state = "1";
                        binding.boundingBtn.setImageResource(R.drawable.friend_bounding_activation_btn);

                    }else if(response.body().budr_check.toString().equals("2")){
                        friend_state = "2";
                        binding.boundingBtn.setImageResource(R.drawable.friend_active_bounding);

                    }else if(response.body().budr_check.toString().equals("3")){
                        friend_state = "0";
                        binding.boundingBtn.setImageResource(R.drawable.add_friend_btn_binding);
                    }
                }
            }

            @Override
            public void onFailure(Call<RequestBounderAPI> call, Throwable t) {

            }
        });
    }


}
