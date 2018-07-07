package com.boundaryproj.bottletaste.MainFolder.ChatFolder.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.boundaryproj.bottletaste.MainFolder.ChatFolder.API.ChatData;
import com.boundaryproj.bottletaste.MainFolder.ChatFolder.Act.ChatMainActivity;
import com.boundaryproj.bottletaste.MainFolder.FriemdFolder.API.CancelFriendAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.ChatRoomData;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.APIFolder.UserService;
import com.boundaryproj.bottletaste.UtilFolder.AlertFolder.BoundaryAlert;
import com.boundaryproj.bottletaste.UtilFolder.ControllerFolder.AppController;
import com.boundaryproj.bottletaste.UtilFolder.Recycler_util.BindingViewHolder;
import com.boundaryproj.bottletaste.databinding.ListitemChatBubbleBinding;
import com.boundaryproj.bottletaste.databinding.ListitemChatroomBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by bongseongchan on 2017. 9. 27..
 */

public class ChatAdapter extends RecyclerView.Adapter<BindingViewHolder<ListitemChatBubbleBinding>> {
    private ArrayList<ChatData> chatRoomDatas = new ArrayList<>();

    Context mContext;
    Activity mActivity;
    BoundaryAlert boundaryAlert = new BoundaryAlert();


    public void add(ArrayList<ChatData> datas, Context context, Activity activity) {
        mContext = context;
        mActivity= activity;

        this.chatRoomDatas = datas;
        notifyDataSetChanged();




    }

    @Override
    public BindingViewHolder<ListitemChatBubbleBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new BindingViewHolder<>(inflater.inflate(R.layout.listitem_chat_bubble, parent, false));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<ListitemChatBubbleBinding> holder, final int position) {
        ChatData data = chatRoomDatas.get(position);
        ListitemChatBubbleBinding binding = holder.binding();
        if(data.email.toString().equals(AppController.getInstance().getPrefManager().getUser().getUser_id())){
            binding.myLayer.setVisibility(View.VISIBLE);
            binding.myTxtLabel.setText(data.txt);
            binding.myTimeLabel.setText(data.time);
            binding.userLayer.setVisibility(View.GONE);

        }else{
            binding.userLayer.setVisibility(View.VISIBLE);
            binding.myLayer.setVisibility(View.GONE);
            binding.userTimeLabel.setText(data.time);
            binding.userTxtLabel.setText(data.txt);
            Glide.with(mContext).load(data.profile_image).into(binding.userProfileImageView);
            if(data.gender.toString().equals("0")){
                binding.userProfileImageView.setBorderColor(mContext.getResources().getColor(R.color.boundary_red));
            }else{
                binding.userProfileImageView.setBorderColor(mContext.getResources().getColor(R.color.boundary_blue));
            }
        }




    }



    @Override
    public int getItemCount() {
        return chatRoomDatas.size();
    }


}