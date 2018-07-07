package com.boundaryproj.bottletaste.UtilFolder.Recycler_util;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by bongseongchan on 2017. 9. 26..
 */


public class BindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private final T binding;

    public BindingViewHolder(View view) {
        super(view);
        this.binding = (T) DataBindingUtil.bind(view);
    }

    public T binding() {
        return binding;
    }
}