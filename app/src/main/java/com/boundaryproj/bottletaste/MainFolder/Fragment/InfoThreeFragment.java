package com.boundaryproj.bottletaste.MainFolder.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boundaryproj.bottletaste.R;


public class InfoThreeFragment extends Fragment {


    public InfoThreeFragment() {
        // Required empty public constructor
    }


    public static InfoThreeFragment newInstance() {
        InfoThreeFragment fragment = new InfoThreeFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_three, container, false);
    }

}
