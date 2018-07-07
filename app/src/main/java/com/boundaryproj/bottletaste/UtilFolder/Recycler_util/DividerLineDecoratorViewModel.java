package com.boundaryproj.bottletaste.UtilFolder.Recycler_util;

import android.content.Context;
import android.databinding.ObservableField;
import android.support.v7.widget.DividerItemDecoration;

import com.boundaryproj.bottletaste.UtilFolder.Models.BaseViewModel;


/**
 * Created by bongseongchan on 2017. 9. 26..
 */

public class DividerLineDecoratorViewModel implements BaseViewModel {
    public final ObservableField<DividerItemDecoration> decorator = new ObservableField<>();
    public Context context;

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {}

    @Override
    public void onPause() {}

    @Override
    public void onDestroy() {}
}