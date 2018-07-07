package com.boundaryproj.bottletaste.MainFolder.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boundaryproj.bottletaste.R;


public class InfoSixFragment extends Fragment {


    public InfoSixFragment() {
        // Required empty public constructor
    }


    public static InfoSixFragment newInstance() {
        InfoSixFragment fragment = new InfoSixFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_six, container, false);
    }


}
