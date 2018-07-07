package com.boundaryproj.bottletaste.MainFolder.MypageFolder.Fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyInformationAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.MyProfieImageData;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.API.TrueFalseAPI;
import com.boundaryproj.bottletaste.MainFolder.HomeFolder.Act.BoudndaryActivity;
import com.boundaryproj.bottletaste.MainFolder.MypageFolder.API.ProfileImageChangeAPI;
import com.boundaryproj.bottletaste.MainFolder.MypageFolder.API.ProfileImageDeleteAPI;
import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.UtilFolder.APIFolder.UserService;
import com.boundaryproj.bottletaste.UtilFolder.AlertFolder.BoundaryAlert;
import com.boundaryproj.bottletaste.UtilFolder.AlertFolder.BoundaryIosAlert;
import com.boundaryproj.bottletaste.UtilFolder.AlertFolder.BoundaryLoginAlert;
import com.boundaryproj.bottletaste.UtilFolder.ControllerFolder.AppController;
import com.boundaryproj.bottletaste.UtilFolder.ProgressFolder.CustomProgressDialog;
import com.boundaryproj.bottletaste.UtilFolder.SoftKeyboard;
import com.boundaryproj.bottletaste.UtilFolder.UserFolder.UserInfoUpdate;
import com.boundaryproj.bottletaste.databinding.FragmentMypageSettingBinding;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MypageSettingFragment extends Fragment {

    public MypageSettingFragment() {
        // Required empty public constructor
    }

    FragmentMypageSettingBinding binding;
    ImageView profileImageView[];
    ImageView modifyBtn[];
    RelativeLayout choiceBtn[];
    BoundaryIosAlert iosAlert = new BoundaryIosAlert();
    int imgView_position = 0;
    public CustomProgressDialog customProgressDialog;;

    InputMethodManager imm;
    BoundaryAlert alert = new BoundaryAlert();


    public static MypageSettingFragment newInstance() {
        MypageSettingFragment fragment = new MypageSettingFragment();

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return DataBindingUtil.inflate(inflater, R.layout.fragment_mypage_setting, container, false).getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ImageView logo = (ImageView)getActivity().findViewById(R.id.logoImageView);
        logo.setVisibility(View.GONE);
        TextView logo_title = (TextView)getActivity().findViewById(R.id.profileLogotitleLabel);
        logo_title.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        ImageView logo = (ImageView)getActivity().findViewById(R.id.logoImageView);
        logo.setVisibility(View.VISIBLE);
        TextView logo_title = (TextView)getActivity().findViewById(R.id.profileLogotitleLabel);
        logo_title.setVisibility(View.GONE);

        imm.hideSoftInputFromWindow(binding.nickEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(binding.jobEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(binding.schoolEditTExt.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(binding.infoEditText.getWindowToken(), 0);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        customProgressDialog = new CustomProgressDialog(getActivity());
        customProgressDialog .getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        customProgressDialog.setCancelable(false);
        customProgressDialog.setCanceledOnTouchOutside(false);


        binding = DataBindingUtil.getBinding(getView());

        imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);



        Button confirm_Btn = (Button)getActivity().findViewById(R.id.profileChange_btn);
        confirm_Btn.setVisibility(View.VISIBLE);
        profileImageView = new ImageView[]{binding.profileImage1,binding.profileImage2,binding.profileImage3,binding.profileImage4,binding.profileImage5,binding.profileImage6};
        modifyBtn = new ImageView[]{binding.modify1,binding.modify2,binding.modify3,binding.modify4,binding.modify5,binding.modify6};
        choiceBtn = new RelativeLayout[]{binding.choiceBtn1,binding.choiceBtn2,binding.choiceBtn3,binding.choiceBtn4,binding.choiceBtn5,binding.choiceBtn6};



        for(int i = 0; i<choiceBtn.length;i++){
            final int finalI = i;
            choiceBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(finalI == 0){
                        iosAlert.PhotoOneAlert(getActivity(), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(view.getId() == R.id.cancleBtn){
                                    iosAlert.getSimpleDialog().dismiss();
                                }else if(view.getId() == R.id.txt1label){
                                    iosAlert.getSimpleDialog().dismiss();
                                    ChoiceAlbum(finalI);
                                }

                            }
                        });
                    }else{
                        if(modifyBtn[finalI].getVisibility() == View.VISIBLE){
                            iosAlert.PhotoTwoAlert(getActivity(), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(view.getId() == R.id.cancleBtn){
                                        iosAlert.getSimpleDialog().dismiss();
                                    }else if(view.getId() == R.id.txt1label){
                                        iosAlert.getSimpleDialog().dismiss();
                                        ChoiceAlbum(finalI);
                                    }else if(view.getId() == R.id.txt2label){
                                        iosAlert.getSimpleDialog().dismiss();
                                        DeleteProfile(finalI+1);
                                    }

                                }
                            });
                        }else{
                            iosAlert.PhotoOneAlert(getActivity(), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(view.getId() == R.id.cancleBtn){

                                        iosAlert.getSimpleDialog().dismiss();
                                    }else if(view.getId() == R.id.txt1label){
                                        iosAlert.getSimpleDialog().dismiss();
                                        ChoiceAlbum(finalI);
                                    }

                                }
                            });
                        }
                    }


                }
            });

        }

        SetProfiles();



        confirm_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alert.WhiteAlert(getActivity(),"개인정보를 변경 하시겠습니까?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(view.getId() == R.id.positive){
                            alert.getSimpleDialog().dismiss();
                            AppController appController = AppController.create(getContext());
                            UserService userService = appController.getUserService();
                            userService.PROFILE_CHANGE_API(appController.getPrefManager().getUser().getUser_id(),binding.nickEditText.getText().toString(),binding.ageEditText.getText().toString(),binding.jobEditText.getText().toString(),binding.schoolEditTExt.getText().toString(),binding.infoEditText.getText().toString()).enqueue(new Callback<TrueFalseAPI>() {
                                @Override
                                public void onResponse(Call<TrueFalseAPI> call, Response<TrueFalseAPI> response) {
                                    if(response.body().success){
                                        new UserInfoUpdate().Update(getContext());

                                        imm.hideSoftInputFromWindow(binding.nickEditText.getWindowToken(), 0);
                                        imm.hideSoftInputFromWindow(binding.jobEditText.getWindowToken(), 0);
                                        imm.hideSoftInputFromWindow(binding.schoolEditTExt.getWindowToken(), 0);
                                        imm.hideSoftInputFromWindow(binding.infoEditText.getWindowToken(), 0);

                                        ((BoudndaryActivity)getActivity()).backAction();
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
    }



    public void DeleteProfile(int position){
        AppController appController = AppController.create(getContext());
        UserService userService = appController.getUserService();
        userService.PROFILE_IMAGE_DELETE_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id(),String.valueOf(position)).enqueue(new Callback<ProfileImageDeleteAPI>() {
            @Override
            public void onResponse(Call<ProfileImageDeleteAPI> call, Response<ProfileImageDeleteAPI> response) {
                if(response.body().success){
                    SetProfiles();
                }else{
                    Toast.makeText(getContext(),"데이터 연결을 확인해주세요.",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ProfileImageDeleteAPI> call, Throwable t) {
                Toast.makeText(getContext(),"데이터 연결을 확인해주세요.",Toast.LENGTH_LONG).show();

            }
        });
    }

    public void SetProfiles(){
        AppController appController = AppController.create(getContext());
        UserService userService = appController.getUserService();
        userService.MY_INFORMATION_API_CALL(AppController.getInstance().getPrefManager().getUser().getUser_id()).enqueue(new Callback<MyInformationAPI>() {
            @Override
            public void onResponse(Call<MyInformationAPI> call, Response<MyInformationAPI> response) {
                if(response.body().success) {
                    List<MyProfieImageData> contentsDatas = response.body().getMyProfieImageDatas();
                    for(int i = 0 ; i< 6 ; i++){
                        profileImageView[i].setVisibility(View.GONE);
                        modifyBtn[i].setVisibility(View.GONE);
                    }
                    for (int i = 0; i < contentsDatas.size(); i++) {
                        profileImageView[i].setVisibility(View.VISIBLE);
                        modifyBtn[i].setVisibility(View.VISIBLE);
                        Glide.with(getContext()).load(contentsDatas.get(i).image).into(profileImageView[i]);
                    }

                    binding.nickEditText.setText(response.body().nickname);
                    binding.ageEditText.setText(response.body().age);
                    binding.jobEditText.setText(response.body().job);
                    binding.schoolEditTExt.setText(response.body().school);
                    binding.infoEditText.setText(response.body().info);


                }

            }

            @Override
            public void onFailure(Call<MyInformationAPI> call, Throwable t) {

            }
        });
    }

    public void ChoiceAlbum(int position){
        imgView_position = position;
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 2);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 2 && resultCode == getActivity().RESULT_OK) {

            UploadProfile(getPath(data.getData()),imgView_position+1);
        }


    }

    public void UploadProfile(final String path, int position){
        customProgressDialog.show();

        HashMap<String, RequestBody> profile_change = new HashMap<String, RequestBody>();
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), AppController.getInstance().getPrefManager().getUser().getUser_id());
        RequestBody no = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(position));
        profile_change.put("user_id",user_id);
        profile_change.put("no",no);
        File file = new File(path);
        RequestBody imageRequest = RequestBody.create( MediaType.parse("multipart/form-data"), file);
        profile_change.put("image"+"\"; filename=\""+"image"+".png\"",imageRequest);
        AppController appController = AppController.create(getContext());
        UserService userService = appController.getUserService();
        userService.PROFILE_IMAGE_CHANGE_API_CALL(profile_change).enqueue(new Callback<ProfileImageChangeAPI>() {
            @Override
            public void onResponse(Call<ProfileImageChangeAPI> call, Response<ProfileImageChangeAPI> response) {

                if(response.body().success){
                    customProgressDialog.dismiss();
                    new UserInfoUpdate().Update(getContext());
                    Toast.makeText(getContext(),"프로필 사진이 변경되었습니다.",Toast.LENGTH_LONG).show();
                    SetProfiles();
                }else{
                    customProgressDialog.dismiss();
                    Toast.makeText(getContext(),"데이터 연결을 확인해주세요.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileImageChangeAPI> call, Throwable t) {

                customProgressDialog.dismiss();
                Toast.makeText(getContext(),"데이터 연결을 확인해주세요.",Toast.LENGTH_LONG).show();

            }
        });


    }

    public String getPath(Uri uri) {
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null );
        cursor.moveToNext();
        String path = cursor.getString( cursor.getColumnIndex( "_data" ) );
        cursor.close();
        return path;
    }


}
