package com.boundaryproj.bottletaste.MainFolder.HomeFolder.Fragment;


import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.ChatRoomAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.ChatRoomData;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyBounderAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyBounderData;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyBoundingAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyBoundingData;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyProfieImageData;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Act.BoudndaryActivity;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Adpater.ChatRoomAdapter;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Adpater.MyBounderAdapter;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Adpater.MyBoundingAdapter;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Model.MyBoundaryViewModel;
import com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API.MapBoundingService;
import com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API.MapUserAPI;
import com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.Fragment.FriendInformationFragment;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.APIFolder.UserService;
import com.boundaryproj.bottletaste.UtilFolder.ControllerFolder.AppController;
import com.boundaryproj.bottletaste.databinding.FragmentMyBoundaryBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyBoundaryFragment extends Fragment {

    MyBoundaryViewModel model;
    FragmentMyBoundaryBinding binding;
    static Context mCon;
    static Activity mAct;
    AppController appController;
    UserService userService;
    static Fragment fragment;
    String flag ="0";

    public MyBoundaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return  DataBindingUtil.inflate(inflater, R.layout.fragment_my_boundary, container, false).getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        appController = AppController.create(getContext());
        userService = appController.getUserService();
        binding = DataBindingUtil.getBinding(getView());
        mCon = getContext();
        mAct = getActivity();
        model = new MyBoundaryViewModel();
        binding.setModel(model);

        fragment = MyBoundaryFragment.this;

        Button confirm_Btn = (Button)getActivity().findViewById(R.id.profileChange_btn);
        confirm_Btn.setVisibility(View.GONE);
        ImageView logo = (ImageView)getActivity().findViewById(R.id.logoImageView);
        logo.setVisibility(View.VISIBLE);
        TextView logo_title = (TextView)getActivity().findViewById(R.id.profileLogotitleLabel);
        logo_title.setVisibility(View.GONE);
        ImageView block_icon = (ImageView)getActivity().findViewById(R.id.block_icon);
        block_icon.setVisibility(View.GONE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 5);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        binding.bounderRecycler.setHasFixedSize(true);
        binding.bounderRecycler.setLayoutManager(gridLayoutManager);

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 5);
        gridLayoutManager2.setOrientation(GridLayoutManager.VERTICAL);
        binding.boundingRecycler.setHasFixedSize(true);
        binding.boundingRecycler.setLayoutManager(gridLayoutManager2);


        binding.chatRecycler.setLayoutManager(layoutManager);
        getChatRoomDataAPI();
      //  getMyBounderAPI();
       // getMyBoundingAPI();

        binding.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.d("object","hi");
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        binding.chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HideView();
                flag = "0";
                binding.chatBtn.setBackgroundResource(R.drawable.dark_gray_background);
                binding.chatBtn.setTextColor(getResources().getColor(R.color.white));
                binding.bounderBtn.setBackgroundResource(R.drawable.light_gray_background);
                binding.bounderBtn.setTextColor(getResources().getColor(R.color.txt_color));
                binding.boundingBtn.setBackgroundResource(R.drawable.light_gray_background);
                binding.boundingBtn.setTextColor(getResources().getColor(R.color.txt_color));

                if(binding.editText.getText().toString().equals("")){
                    getChatRoomDataAPI();
                }else{
                    SearchChatRoomDataAPI();
                }

            }
        });

        binding.bounderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = "1";
                HideView();
                binding.bounderBtn.setBackgroundResource(R.drawable.dark_gray_background);
                binding.bounderBtn.setTextColor(getResources().getColor(R.color.white));
                binding.chatBtn.setBackgroundResource(R.drawable.light_gray_background);
                binding.chatBtn.setTextColor(getResources().getColor(R.color.txt_color));
                binding.boundingBtn.setBackgroundResource(R.drawable.light_gray_background);
                binding.boundingBtn.setTextColor(getResources().getColor(R.color.txt_color));

                if(binding.editText.getText().toString().equals("")){
                    getMyBounderAPI();
                }else{
                    SearchMyBounderAPI();
                }

            }
        });

        binding.boundingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = "2";
                HideView();
                binding.boundingBtn.setBackgroundResource(R.drawable.dark_gray_background);
                binding.boundingBtn.setTextColor(getResources().getColor(R.color.white));
                binding.bounderBtn.setBackgroundResource(R.drawable.light_gray_background);
                binding.bounderBtn.setTextColor(getResources().getColor(R.color.txt_color));
                binding.chatBtn.setBackgroundResource(R.drawable.light_gray_background);
                binding.chatBtn.setTextColor(getResources().getColor(R.color.txt_color));

                if(binding.editText.getText().toString().equals("")){
                    getMyBoundingAPI();
                }else{
                    SearchMyBoundingAPI();
                }

            }
        });
    }

    public void HideView(){
        binding.chatRecycler.setVisibility(View.GONE);
        binding.bounderRecycler.setVisibility(View.GONE);
        binding.boundingRecycler.setVisibility(View.GONE);
    }

    public void getChatRoomDataAPI(){

        userService.CHAT_ROOM_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id()).enqueue(new Callback<ChatRoomAPI>() {
            @Override
            public void onResponse(Call<ChatRoomAPI> call, Response<ChatRoomAPI> response) {
                if(response.body().success){
                    binding.chatRecycler.setVisibility(View.VISIBLE);
                    model.getChatRoom(response.body().chatRoomDataList);
                }else{
                    binding.chatRecycler.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ChatRoomAPI> call, Throwable t) {

            }
        });

    }

    public void SearchChatRoomDataAPI(){

        userService.SEARCH_CHAT_ROOM_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id(),binding.editText.getText().toString()).enqueue(new Callback<ChatRoomAPI>() {
            @Override
            public void onResponse(Call<ChatRoomAPI> call, Response<ChatRoomAPI> response) {
                if(response.body().success){
                    binding.chatRecycler.setVisibility(View.VISIBLE);
                    model.getChatRoom(response.body().chatRoomDataList);
                }else{
                    binding.chatRecycler.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ChatRoomAPI> call, Throwable t) {

            }
        });

    }

    public void getMyBounderAPI(){

        userService.MY_BOUNDER_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id()).enqueue(new Callback<MyBounderAPI>() {
            @Override
            public void onResponse(Call<MyBounderAPI> call, Response<MyBounderAPI> response) {
                if(response.body().success){
                    binding.bounderRecycler.setVisibility(View.VISIBLE);
                    model.getMyBounder(response.body().myBounderDatas);
                }else{
                    binding.bounderRecycler.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MyBounderAPI> call, Throwable t) {

            }
        });

    }

    public void SearchMyBounderAPI(){

        userService.SEARCH_MY_BOUNDER_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id(),binding.editText.getText().toString()).enqueue(new Callback<MyBounderAPI>() {
            @Override
            public void onResponse(Call<MyBounderAPI> call, Response<MyBounderAPI> response) {
                if(response.body().success){
                    binding.bounderRecycler.setVisibility(View.VISIBLE);
                    model.getMyBounder(response.body().myBounderDatas);
                }else{
                    binding.bounderRecycler.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MyBounderAPI> call, Throwable t) {

            }
        });

    }

    public void getMyBoundingAPI(){
        userService.MY_BOUNDING_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id()).enqueue(new Callback<MyBoundingAPI>() {
            @Override
            public void onResponse(Call<MyBoundingAPI> call, Response<MyBoundingAPI> response) {
                if(response.body().success){
                    binding.boundingRecycler.setVisibility(View.VISIBLE);
                    model.getMyBounding(response.body().myBoundingDatas);
                }else{
                    binding.boundingRecycler.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MyBoundingAPI> call, Throwable t) {

            }
        });
    }

    public void SearchMyBoundingAPI(){
        userService.SEARCH_MY_BOUNDING_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id(),binding.editText.getText().toString()).enqueue(new Callback<MyBoundingAPI>() {
            @Override
            public void onResponse(Call<MyBoundingAPI> call, Response<MyBoundingAPI> response) {
                if(response.body().success){
                    binding.boundingRecycler.setVisibility(View.VISIBLE);
                    model.getMyBounding(response.body().myBoundingDatas);
                }else{
                    binding.boundingRecycler.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MyBoundingAPI> call, Throwable t) {

            }
        });
    }

    @BindingAdapter("app:chatroomadapter")
    public static void setReview(RecyclerView recyclerView, ArrayList<ChatRoomData> users) {

        ChatRoomAdapter adapter;

        if (recyclerView.getAdapter() == null) {
            adapter = new ChatRoomAdapter();
            recyclerView.setAdapter(adapter);
        } else {
            adapter = (ChatRoomAdapter) recyclerView.getAdapter();
        }
        adapter.add(users, mCon,mAct,fragment);


    }

    @BindingAdapter("app:mybounderadapter")
    public static void setBounder(RecyclerView recyclerView, ArrayList<MyBounderData> users) {

        MyBounderAdapter adapter;

        if (recyclerView.getAdapter() == null) {
            adapter = new MyBounderAdapter();
            recyclerView.setAdapter(adapter);
        } else {
            adapter = (MyBounderAdapter) recyclerView.getAdapter();
        }
        adapter.add(users, mCon,mAct,fragment);


    }

    public void GoUserPage(final String email){
        ((BoudndaryActivity)getActivity()).replaceFragment(new FriendInformationFragment().newInstance(email),"FriendInformationFragment");
    }


    @BindingAdapter("app:myboundingadapter")
    public static void setBounding(RecyclerView recyclerView, ArrayList<MyBoundingData> users) {

        MyBoundingAdapter adapter;

        if (recyclerView.getAdapter() == null) {
            adapter = new MyBoundingAdapter();
            recyclerView.setAdapter(adapter);
        } else {
            adapter = (MyBoundingAdapter) recyclerView.getAdapter();
        }
        adapter.add(users, mCon,mAct,fragment);


    }

    public void performSearch(){
        if(flag.toString().equals("0")){
            SearchChatRoomDataAPI();
        }else if(flag.toString().equals("1")){
            SearchMyBounderAPI();
        }else if(flag.toString().equals("2")){
            SearchMyBoundingAPI();
        }

    }
}
