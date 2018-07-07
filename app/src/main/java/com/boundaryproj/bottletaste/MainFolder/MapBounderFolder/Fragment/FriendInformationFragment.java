package com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.Fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.boundaryproj.bottletaste.MainFolder.ChatFolder.Act.ChatMainActivity;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.CountPeopleAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyProfieImageData;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.TrueFalseAPI;
import com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API.MapBoundingService;
import com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API.MapUserAPI;
import com.boundaryproj.bottletaste.MainFolder.MapBounderFolder.API.RequestBounderAPI;
import com.boundaryproj.bottletaste.MainFolder.SettingFolder.API.InstaCheckAPI;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.APIFolder.UserService;
import com.boundaryproj.bottletaste.UtilFolder.AlertFolder.BoundaryAlert;
import com.boundaryproj.bottletaste.UtilFolder.ControllerFolder.AppController;
import com.boundaryproj.bottletaste.UtilFolder.ImageFolder.GlideImageLoader;
import com.boundaryproj.bottletaste.databinding.FragmentFriendInformationBinding;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FriendInformationFragment extends Fragment {

    public FriendInformationFragment() {
        // Required empty public constructor
    }


    private static final String FRIEND_ID = "fiend_id";
    private String friend_id;
    FragmentFriendInformationBinding binding;
    public String friend_state;
    public String user_nickname;
    BoundaryAlert alert = new BoundaryAlert();
    boolean myInsta = false;


    // TODO: Rename and change types and number of parameters
    public static FriendInformationFragment newInstance(String param1) {
        FriendInformationFragment fragment = new FriendInformationFragment();
        Bundle args = new Bundle();
        args.putString(FRIEND_ID, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            friend_id = getArguments().getString(FRIEND_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return DataBindingUtil.inflate(inflater, R.layout.fragment_friend_information, container, false).getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ImageView block_icon = (ImageView)getActivity().findViewById(R.id.block_icon);
        block_icon.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        ImageView block_icon = (ImageView)getActivity().findViewById(R.id.block_icon);
        block_icon.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = DataBindingUtil.getBinding(getView());

        final AppController appController = AppController.create(getContext());
        final MapBoundingService mapBoundingService = appController.getMapBoundingService();
        mapBoundingService.MAP_USER_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id(),friend_id).enqueue(new Callback<MapUserAPI>() {
            @Override
            public void onResponse(Call<MapUserAPI> call, Response<MapUserAPI> response) {
                if(response.body().success){
                    user_nickname = response.body().nickname;
                    binding.nicknameLabel.setText(response.body().nickname+", "+response.body().age + "세");
                    binding.jobLabel.setText(response.body().job);
                    binding.schoolLabel.setText(response.body().school);
                    friend_state = response.body().friend_confirm.toString();
                    binding.FaceBookLabel.setText(response.body().nickname + "님의 Facebook");
                    binding.InstaLabel.setText(response.body().nickname+"님의 Instagram");
                    if(response.body().friend_confirm.toString().equals("0")){
                        binding.friendStateBtn.setImageResource(R.drawable.add_friend_btn_binding);
                        binding.schoolLayer.setVisibility(View.INVISIBLE);
                        binding.jobLayer.setVisibility(View.INVISIBLE);
                        binding.boundingLayer.setVisibility(View.GONE);
                        binding.chatImageBtn.setVisibility(View.INVISIBLE);
                    }else if(response.body().friend_confirm.toString().equals("1")){
                        binding.friendStateBtn.setImageResource(R.drawable.friend_bounding_activation_btn);
                        binding.schoolLayer.setVisibility(View.INVISIBLE);
                        binding.jobLayer.setVisibility(View.INVISIBLE);
                        binding.boundingLayer.setVisibility(View.GONE);
                        binding.chatImageBtn.setVisibility(View.INVISIBLE);
                    }else{
                        binding.friendStateBtn.setImageResource(R.drawable.friend_active_bounding);
                        binding.schoolLayer.setVisibility(View.VISIBLE);
                        binding.jobLayer.setVisibility(View.VISIBLE);
                        binding.boundingLayer.setVisibility(View.VISIBLE);
                        binding.chatImageBtn.setVisibility(View.VISIBLE);
                        binding.chatImageBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getContext(), ChatMainActivity.class);
                                intent.putExtra("friend_id",friend_id);
                                startActivity(intent);
                            }
                        });
                    }

                    List<MyProfieImageData> contentsDatas = response.body().myProfieImageDatas;
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
                }
            }

            @Override
            public void onFailure(Call<MapUserAPI> call, Throwable t) {

            }
        });

        UserService userService = appController.getUserService();

        userService.INSTA_CONNECT_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id()).enqueue(new Callback<InstaCheckAPI>() {
            @Override
            public void onResponse(Call<InstaCheckAPI> call, Response<InstaCheckAPI> response) {
                if(response.body().success){
                    myInsta = true;
                }else{
                    myInsta = false;
                }
            }

            @Override
            public void onFailure(Call<InstaCheckAPI> call, Throwable t) {

            }
        });

        userService.INSTA_CONNECT_CALL(friend_id).enqueue(new Callback<InstaCheckAPI>() {
            @Override
            public void onResponse(Call<InstaCheckAPI> call, final Response<InstaCheckAPI> response) {
                if(response.body().success){
                    binding.instaBtns.setImageResource(R.drawable.instagram_link_actived);
                    final Uri uri = Uri.parse("http://instagram.com/_u/"+response.body().insta_id);
                    binding.instaBtns.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            InstagramOpen(uri,response.body().insta_id);
                        }
                    });


                }else{
                    binding.instaBtns.setImageResource(R.drawable.instagram_link_not_activated);
                    binding.instaBtns.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getContext(),"인스타 계정이 연동되지 않은 회원입니다.",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<InstaCheckAPI> call, Throwable t) {

            }
        });

        binding.friendStateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                            RequestBounder();
                        }else{
                            alert.getSimpleDialog().dismiss();
                        }
                    }
                });

            }
        });


        ImageView block_icon = (ImageView)getActivity().findViewById(R.id.block_icon);
        block_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "";
                message = user_nickname + "님을 정말\n" +
                        "차단 하시겠습니까?\n" +
                        "\n" +
                        "차단하면 더이상 서로의 계정에\n" +
                        "나타나지 않습니다.";
                alert.WhiteAlert(getActivity(), message, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(view.getId() == R.id.positive){
                            alert.getSimpleDialog().dismiss();
                            UserService userService = appController.getUserService();
                            userService.USER_BLOCK_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id(),friend_id).enqueue(new Callback<TrueFalseAPI>() {
                                @Override
                                public void onResponse(Call<TrueFalseAPI> call, Response<TrueFalseAPI> response) {
                                    if(response.body().success){
                                        getActivity().getSupportFragmentManager().beginTransaction().remove(FriendInformationFragment.this).commit();
                                        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                                    }
                                }

                                @Override
                                public void onFailure(Call<TrueFalseAPI> call, Throwable t) {

                                }
                            });
                        }else{
                            alert.getSimpleDialog().dismiss();
                        }
                    }
                });

            }
        });

        binding.FaceBookLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String facebookUrl = "https://www.facebook.com/"+friend_id;
                try {
                    int versionCode = getContext().getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                    if (versionCode >= 3002850) {
                        Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));;
                    } else {
                        // open the Facebook app using the old method (fb://profile/id or fb://page/id)
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/"+friend_id)));
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    // Facebook is not installed. Open the browser
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
                }
            }
        });

        CounterPeopleAPI();
    }



    public void CounterPeopleAPI(){
        AppController appController = AppController.create(getContext());
        UserService userService = appController.getUserService();
        userService.COUNT_PEOPLE_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id(),friend_id).enqueue(new Callback<CountPeopleAPI>() {
            @Override
            public void onResponse(Call<CountPeopleAPI> call, Response<CountPeopleAPI> response) {
                if(response.body().success){
                    if(Integer.valueOf(response.body().bounding)>100){
                        binding.counterLabel.setText("+99");
                    }else{
                        binding.counterLabel.setText(response.body().bounding);
                    }

                }
            }

            @Override
            public void onFailure(Call<CountPeopleAPI> call, Throwable t) {

            }
        });

    }

    public void RequestBounder(){
        AppController appController = AppController.create(getContext());
        UserService userService = appController.getUserService();
        userService.REQUEST_BOUNDER_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id(),friend_id).enqueue(new Callback<RequestBounderAPI>() {
            @Override
            public void onResponse(Call<RequestBounderAPI> call, Response<RequestBounderAPI> response) {
                if(response.body().success){
                    if(response.body().budr_check.toString().equals("1")){
                        friend_state = "1";
                        binding.friendStateBtn.setImageResource(R.drawable.friend_bounding_activation_btn);
                        binding.schoolLayer.setVisibility(View.INVISIBLE);
                        binding.jobLayer.setVisibility(View.INVISIBLE);
                        binding.chatImageBtn.setVisibility(View.INVISIBLE);

                        binding.boundingLayer.setVisibility(View.GONE);
                    }else if(response.body().budr_check.toString().equals("2")){
                        friend_state = "2";
                        binding.friendStateBtn.setImageResource(R.drawable.friend_active_bounding);
                        binding.schoolLayer.setVisibility(View.VISIBLE);
                        binding.jobLayer.setVisibility(View.VISIBLE);
                        binding.boundingLayer.setVisibility(View.VISIBLE);
                        binding.chatImageBtn.setVisibility(View.VISIBLE);
                        binding.chatImageBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getContext(), ChatMainActivity.class);
                                intent.putExtra("friend_id",friend_id);
                                startActivity(intent);
                            }
                        });
                    }else if(response.body().budr_check.toString().equals("3")){
                        friend_state = "0";
                        binding.friendStateBtn.setImageResource(R.drawable.add_friend_btn_binding);
                        binding.schoolLayer.setVisibility(View.INVISIBLE);
                        binding.jobLayer.setVisibility(View.INVISIBLE);
                        binding.boundingLayer.setVisibility(View.GONE);
                        binding.chatImageBtn.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RequestBounderAPI> call, Throwable t) {

            }
        });
    }

    private boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager packageManager = ctx.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public void InstagramOpen(Uri uri, String insta_id){
        if(myInsta){
            Intent insta = new Intent(Intent.ACTION_VIEW, uri);
            insta.setPackage("com.instagram.android");

            if (isIntentAvailable(getContext(), insta)){
                startActivity(insta);
            } else{
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/"+insta_id)));
            }
        }else{
            Toast.makeText(getContext(),"본인 Instagram 계정을 연동해야 볼 수 있습니다.",Toast.LENGTH_LONG).show();
        }


    }
}
