package com.boundaryproj.bottletaste.MainFolder.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boundaryproj.bottletaste.R;


public class InfoFiveFragment extends Fragment {


    public InfoFiveFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static InfoFiveFragment newInstance() {
        InfoFiveFragment fragment = new InfoFiveFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_five, container, false);
    }

}
