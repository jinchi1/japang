package com.boundaryproj.bottletaste.MainFolder.HomeFolder.Adpater;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.boundaryproj.bottletaste.MainFolder.FriemdFolder.API.CancelFriendAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyBounderData;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyBoundingData;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Fragment.MapBoundaryFragment;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Fragment.MyBoundaryFragment;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.APIFolder.UserService;
import com.boundaryproj.bottletaste.UtilFolder.AlertFolder.BoundaryAlert;
import com.boundaryproj.bottletaste.UtilFolder.ControllerFolder.AppController;
import com.boundaryproj.bottletaste.UtilFolder.Recycler_util.BindingViewHolder;
import com.boundaryproj.bottletaste.databinding.ListitemBounderBinding;
import com.boundaryproj.bottletaste.databinding.ListitemBoundingBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by bongseongchan on 2017. 9. 27..
 */

public class MyBoundingAdapter extends RecyclerView.Adapter<BindingViewHolder<ListitemBoundingBinding>> {
    private ArrayList<MyBoundingData> myBoundingDatas = new ArrayList<>();

    Context mContext;
    Activity mActivity;
    BoundaryAlert boundaryAlert = new BoundaryAlert();
    AppController appController;
    UserService userService;
    private Fragment fragment;


    public void add(ArrayList<MyBoundingData> datas, Context context, Activity activity, Fragment fragment) {
        mContext = context;
        mActivity= activity;
        this.myBoundingDatas = datas;
        appController = AppController.create(mContext);
        userService = appController.getUserService();
        this.fragment = fragment;

        notifyDataSetChanged();

    }

    @Override
    public BindingViewHolder<ListitemBoundingBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new BindingViewHolder<>(inflater.inflate(R.layout.listitem_bounding, parent, false));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<ListitemBoundingBinding> holder, final int position) {
        final MyBoundingData data = myBoundingDatas.get(position);
        ListitemBoundingBinding binding = holder.binding();
        Glide.with(mContext).load(data.profile_photo).into(binding.profileImageView);
        binding.profileImageView.setBorderColor(Color.parseColor("#D6D6D6"));
        binding.nicknameLabel.setText(data.nickname);
        holder.binding().boundingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boundaryAlert.WhiteAlert(mActivity, "정말 "+data.nickname+"님과의 Bounding을 취소 하시겠습니까?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(view.getId() == R.id.negative){
                            boundaryAlert.getSimpleDialog().dismiss();
                        }else if(view.getId() == R.id.positive){
                            boundaryAlert.getSimpleDialog().dismiss();
                            CancelFriendAPI(position,data.user_id);
                        }

                    }
                });
            }
        });

        binding.profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyBoundaryFragment)fragment).GoUserPage(data.user_id);
            }
        });

    }

    public void CancelFriendAPI(final int position, final String friend_id){
        userService.CANCEL_FRIEND_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id(),friend_id).enqueue(new Callback<CancelFriendAPI>() {
            @Override
            public void onResponse(Call<CancelFriendAPI> call, Response<CancelFriendAPI> response) {
                if(response.body().success){
                    myBoundingDatas.remove(position);
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(mContext,"데이터 연결을 확인해주세요.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CancelFriendAPI> call, Throwable t) {
                Toast.makeText(mContext,"데이터 연결을 확인해주세요.",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return myBoundingDatas.size();
    }


}