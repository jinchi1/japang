package com.boundaryproj.bottletaste.MainFolder.HomeFolder.Adpater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.boundaryproj.bottletaste.MainFolder.ChatFolder.Act.ChatMainActivity;
import com.boundaryproj.bottletaste.MainFolder.FriemdFolder.API.CancelFriendAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.ChatRoomData;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Fragment.MapBoundaryFragment;
import com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API.MapBindingData;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.APIFolder.UserService;
import com.boundaryproj.bottletaste.UtilFolder.AlertFolder.BoundaryAlert;
import com.boundaryproj.bottletaste.UtilFolder.ControllerFolder.AppController;
import com.boundaryproj.bottletaste.UtilFolder.Recycler_util.BindingViewHolder;
import com.boundaryproj.bottletaste.databinding.ListitemChatroomBinding;
import com.boundaryproj.bottletaste.databinding.ListitemMapboundingBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by bongseongchan on 2017. 9. 27..
 */

public class MapBindingAdapter extends RecyclerView.Adapter<BindingViewHolder<ListitemMapboundingBinding>> {
    private ArrayList<MapBindingData> mapBindingDatas = new ArrayList<>();

    Context mContext;
    Activity mActivity;
    BoundaryAlert boundaryAlert = new BoundaryAlert();
    AppController appController;
    UserService userService;

    private Fragment fragment;

    public void add(ArrayList<MapBindingData> datas, Context context, Activity activity, Fragment fragment) {
        mContext = context;
        mActivity= activity;
        this.mapBindingDatas = datas;
        appController = AppController.create(mContext);
        this.fragment = fragment;
        notifyDataSetChanged();

    }

    @Override
    public BindingViewHolder<ListitemMapboundingBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new BindingViewHolder<>(inflater.inflate(R.layout.listitem_mapbounding, parent, false));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<ListitemMapboundingBinding> holder, final int position) {

        SharedPreferences prefs = mContext.getSharedPreferences("adapter", MODE_PRIVATE);
        String save_position = prefs.getString("position", "");

        if(String.valueOf(position).toString().equals(save_position)){
            holder.binding().profileImageView.setBorderColor(Color.parseColor("#609ED5"));
        }else {
            holder.binding().profileImageView.setBorderColor(Color.parseColor("#989898"));
        }

        final MapBindingData data = mapBindingDatas.get(position);
        Glide.with(mContext).load(mapBindingDatas.get(position).profile_photo).into(holder.binding().profileImageView);
        holder.binding().nicknameLabel.setText((mapBindingDatas.get(position).nickname));
        holder.binding().profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences prefs = mContext.getSharedPreferences("adapter", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("position", String.valueOf(position));
                editor.commit();
                holder.binding().profileImageView.setBorderColor(Color.parseColor("#609ED5"));
                ((MapBoundaryFragment)fragment).ShowUserProfile(data.user_id,data.rapport_cnt);
                notifyDataSetChanged();
            }
        });

    }



    @Override
    public int getItemCount() {
        return mapBindingDatas.size();
    }



}

