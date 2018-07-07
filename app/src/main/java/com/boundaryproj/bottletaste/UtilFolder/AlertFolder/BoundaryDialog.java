package com.boundaryproj.bottletaste.UtilFolder.AlertFolder;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boundaryproj.bottletaste.R;


/**
 * Created by Administrator on 2017-04-09.
 */

public class BoundaryDialog extends LinearLayout {
    public BoundaryDialog(Context context) {
        super(context);
    }

    public BoundaryDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BoundaryDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public BoundaryDialog InstagramAlert(OnClickListener listener, String txt){

        ((RelativeLayout)findViewById(R.id.mainLayer)).setBackgroundResource(R.drawable.alert_layer);
        ((RelativeLayout)findViewById(R.id.customLayer)).setVisibility(VISIBLE);
        ((TextView)findViewById(R.id.alertTitle)).setText(txt);
        ((TextView)findViewById(R.id.alertTitle)).setTextColor(getResources().getColor(R.color.white));
        ((TextView)findViewById(R.id.positive)).setText("확인");
        ((TextView)findViewById(R.id.positive)).setTextColor(getResources().getColor(R.color.txtbold_color));
        ((TextView)findViewById(R.id.positive)).setBackgroundResource(R.drawable.white_round_layer);
        ((TextView)findViewById(R.id.negative)).setText("취소");
        ((TextView)findViewById(R.id.negative)).setTextColor(getResources().getColor(R.color.white));
        ((TextView)findViewById(R.id.negative)).setBackgroundResource(R.drawable.white_border_round_layer);
        findViewById(R.id.positive).setOnClickListener(listener);
        findViewById(R.id.negative).setOnClickListener(listener);
        return this;
    }

    public BoundaryDialog WhiteAlert(OnClickListener listener, String txt){

        ((RelativeLayout)findViewById(R.id.mainLayer)).setBackgroundResource(R.drawable.white_round_layer);
        ((RelativeLayout)findViewById(R.id.customLayer)).setVisibility(VISIBLE);
        ((TextView)findViewById(R.id.alertTitle)).setText(txt);
        ((TextView)findViewById(R.id.alertTitle)).setTextColor(getResources().getColor(R.color.boundary_red));
        ((TextView)findViewById(R.id.positive)).setText("확인");
        ((TextView)findViewById(R.id.positive)).setTextColor(getResources().getColor(R.color.txtbold_color));
        ((TextView)findViewById(R.id.positive)).setBackgroundResource(R.drawable.gray_round_layer);
        ((TextView)findViewById(R.id.negative)).setText("취소");
        ((TextView)findViewById(R.id.negative)).setTextColor(getResources().getColor(R.color.txt_color));
        ((TextView)findViewById(R.id.negative)).setBackgroundResource(R.drawable.gray_border_round_layer);
        findViewById(R.id.positive).setOnClickListener(listener);
        findViewById(R.id.negative).setOnClickListener(listener);
        return this;
    }

    public BoundaryDialog WhiteAlert_blue(OnClickListener listener, String txt){

        ((RelativeLayout)findViewById(R.id.mainLayer)).setBackgroundResource(R.drawable.white_round_layer);
        ((RelativeLayout)findViewById(R.id.customLayer)).setVisibility(VISIBLE);
        ((TextView)findViewById(R.id.alertTitle)).setText(txt);
        ((TextView)findViewById(R.id.alertTitle)).setTextColor(getResources().getColor(R.color.boundary_blue));
        ((TextView)findViewById(R.id.positive)).setText("확인");
        ((TextView)findViewById(R.id.positive)).setTextColor(getResources().getColor(R.color.txtbold_color));
        ((TextView)findViewById(R.id.positive)).setBackgroundResource(R.drawable.gray_round_layer);
        ((TextView)findViewById(R.id.negative)).setText("취소");
        ((TextView)findViewById(R.id.negative)).setTextColor(getResources().getColor(R.color.txt_color));
        ((TextView)findViewById(R.id.negative)).setBackgroundResource(R.drawable.gray_border_round_layer);
        findViewById(R.id.positive).setOnClickListener(listener);
        findViewById(R.id.negative).setOnClickListener(listener);
        return this;
    }






    public static class  Builder{
        private String header;
        private String text;
        private String itemText;
        private View v;

        private AlertDialog dialog;
        public Builder(View v) {
            this.v = v;
        }

        public Builder setHeader(String header) {
            ((TextView)v.findViewById(R.id.alertTitle)).setText(header);
            return this;
        }

        public Builder setText(String text) {
            v.findViewById(R.id.text).setVisibility(VISIBLE);
            ((TextView)v.findViewById(R.id.text)).setText(text);
            return this;
        }

        public Builder setOnClickALL(OnClickListener listener){
            v.findViewById(R.id.positive).setOnClickListener(listener);
            v.findViewById(R.id.negative).setOnClickListener(listener);
            return this;
        }
        public Builder setOnClickPositive(OnClickListener listener){
            v.findViewById(R.id.positive).setOnClickListener(listener);

            v.findViewById(R.id.negative).setVisibility(GONE);
            return this;
        }
        public Builder setOnClickNegative(OnClickListener listener){
            v.findViewById(R.id.negative).setOnClickListener(listener);

            v.findViewById(R.id.positive).setVisibility(GONE);
            return this;
        }
        public Builder setNoticeClick(final AlertDialog dialog){

            v.findViewById(R.id.negative).setVisibility(GONE);
            v.findViewById(R.id.positive).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            ((TextView)v.findViewById(R.id.positive)).setText("확 인");
            return this;
        }

        public void showList(){ //TODO setSingleChoiceView 메서드 쓰려면 이거 불러내야됨.
            dialog = new AlertDialog.Builder(v.getContext(), R.style.CustomDialog).
                    setView(v).setCancelable(true).create();
            dialog.show();
        }

        public View Build(){
            if(header == null)
                ((TextView)v.findViewById(R.id.alertTitle)).setText("알 림");
            return v;
        }
        public void Build(Activity act){
            if(header == null)
                ((TextView)v.findViewById(R.id.alertTitle)).setText("알 림");
            dialog = new AlertDialog.Builder(act, R.style.CustomDialog).
                    setView(v).setCancelable(true).create();
            dialog.show();
            setNoticeClick(dialog);
        }
    }

    private static class CheckedItemAdapter extends ArrayAdapter<CharSequence> {
        public CheckedItemAdapter(Context context, int resource, int textViewResourceId,
                                  CharSequence[] objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

    public interface ClickCallback{
        void do_Click();
    }
}
