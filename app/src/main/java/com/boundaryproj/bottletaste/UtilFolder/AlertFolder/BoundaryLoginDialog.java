package com.boundaryproj.bottletaste.UtilFolder.AlertFolder;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boundaryproj.bottletaste.R;


/**
 * Created by Administrator on 2017-04-09.
 */

public class BoundaryLoginDialog extends LinearLayout {
    public BoundaryLoginDialog(Context context) {
        super(context);
    }

    public BoundaryLoginDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BoundaryLoginDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public BoundaryLoginDialog FacebookLogin(OnClickListener listener){

        ((TextView)findViewById(R.id.title)).setText("‘Boundary’이(가)\n" +
                "‘facebook.com’을(를) 사용하여\n" +
                "로그인하려고 합니다.");
        ((TextView)findViewById(R.id.subtitle)).setText("사용자에 관한 정보를 앱 및 웹 사이트가\n" +
                "공유하게 됩니다.");
        findViewById(R.id.positive).setOnClickListener(listener);
        findViewById(R.id.negative).setOnClickListener(listener);
        return this;
    }


    public BoundaryLoginDialog LocationAlert(OnClickListener listener){
        ((TextView)findViewById(R.id.title)).setText("‘Boundary’는 위치 정보를 필수로 받아와야 합니다.");
        ((TextView)findViewById(R.id.subtitle)).setText("위치 설정을 진행하시겠습니까?.");
        findViewById(R.id.positive).setOnClickListener(listener);
        findViewById(R.id.negative).setOnClickListener(listener);
        return this;
    }

    public BoundaryLoginDialog CustomAlert(String title, String subtitle, OnClickListener listener){
        ((TextView)findViewById(R.id.title)).setText(title);
        ((TextView)findViewById(R.id.subtitle)).setText(subtitle);
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
            return this;
        }

        public Builder setText(String text) {

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
            return this;
        }

        public void showList(){ //TODO setSingleChoiceView 메서드 쓰려면 이거 불러내야됨.
            dialog = new AlertDialog.Builder(v.getContext(), R.style.CustomDialog).
                    setView(v).setCancelable(true).create();
            dialog.show();
        }

        public View Build(){

            return v;
        }
        public void Build(Activity act){
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
