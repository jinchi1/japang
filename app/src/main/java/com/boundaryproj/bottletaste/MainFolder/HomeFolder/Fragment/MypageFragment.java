package com.boundaryproj.bottletaste.MainFolder.HomeFolder.Fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.CountPeopleAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyInformationAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyProfieImageData;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.TrueFalseAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Act.BoudndaryActivity;
import com.boundaryproj.bottletaste.MainFolder.MypageFolder.Fragment.MypageSettingFragment;
import com.boundaryproj.bottletaste.MainFolder.SettingFolder.Act.SettingMainActivity;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.APIFolder.UserService;
import com.boundaryproj.bottletaste.UtilFolder.AlertFolder.BoundaryAlert;
import com.boundaryproj.bottletaste.UtilFolder.CenterToast;
import com.boundaryproj.bottletaste.UtilFolder.ControllerFolder.AppController;
import com.boundaryproj.bottletaste.UtilFolder.ImageFolder.GlideImageLoader;
import com.boundaryproj.bottletaste.UtilFolder.UserFolder.User;
import com.boundaryproj.bottletaste.databinding.FragmentMypageBinding;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MypageFragment extends Fragment {


    FragmentMypageBinding binding;

    BoundaryAlert alert = new BoundaryAlert();
    boolean insta_controller = false;

    public MypageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return DataBindingUtil.inflate(inflater, R.layout.fragment_mypage, container, false).getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = DataBindingUtil.getBinding(getView());
        binding.modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BoudndaryActivity)getActivity()).replaceFragment(new MypageSettingFragment(),"MypageSetting");
            }
        });

        binding.settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SettingMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        ImageView logo = (ImageView)getActivity().findViewById(R.id.logoImageView);
        logo.setVisibility(View.VISIBLE);
        TextView logo_title = (TextView)getActivity().findViewById(R.id.profileLogotitleLabel);
        logo_title.setVisibility(View.GONE);
        ImageView block_icon = (ImageView)getActivity().findViewById(R.id.block_icon);
        block_icon.setVisibility(View.GONE);
        Button confirm_Btn = (Button)getActivity().findViewById(R.id.profileChange_btn);
        confirm_Btn.setVisibility(View.GONE);

        AppController appController = AppController.create(getContext());
        UserService userService = appController.getUserService();
        userService.MY_INFORMATION_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id()).enqueue(new Callback<MyInformationAPI>() {
            @Override
            public void onResponse(Call<MyInformationAPI> call, Response<MyInformationAPI> response) {
                if(response.body().success){
                    User user = new User(response.body().user_id,response.body().nickname,response.body().main_profile,response.body().age,response.body().job,response.body().school,response.body().info,response.body().gender);
                    AppController.getInstance().getPrefManager().storeUser(user);

                    List<MyProfieImageData> contentsDatas = response.body().getMyProfieImageDatas();
                    List<String> imgurl_multi = new ArrayList<>();
                    for (int i = 0; i <= contentsDatas.size(); i++) {
                        if (i == contentsDatas.size()) {
                            binding.multiImageView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                            binding.multiImageView.setImageLoader(new GlideImageLoader());
                            binding.multiImageView.setImages(imgurl_multi);
                            binding.multiImageView.setBannerAnimation(Transformer.Default);
                            binding.multiImageView.isAutoPlay(false);
                            binding.multiImageView.setDelayTime(2500);
                            binding.multiImageView.setIndicatorGravity(BannerConfig.CENTER);

                            binding.multiImageView.start();
                        } else {
                            imgurl_multi.add(contentsDatas.get(i).image);
                        }

                    }



                    binding.nickLabel.setText(response.body().nickname + ", " +response.body().age +"세");
                    binding.infoLabel.setText(response.body().info);
                    binding.schoolLabel.setText(response.body().school);
                    binding.jobLabel.setText(response.body().job);


                }
            }

            @Override
            public void onFailure(Call<MyInformationAPI> call, Throwable t) {

            }
        });

        userService.MY_COUNT_BOUND_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id()).enqueue(new Callback<CountPeopleAPI>() {
            @Override
            public void onResponse(Call<CountPeopleAPI> call, Response<CountPeopleAPI> response) {
                if(response.body().success){
                    binding.bndCntLabel.setText(response.body().bounding);
                }
            }

            @Override
            public void onFailure(Call<CountPeopleAPI> call, Throwable t) {

            }
        });

        binding.myBoundingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new CenterToast().show(getContext(),"오늘 나를 바운딩 한 사람 숫자 입니다.");

            }
        });

    }


}
