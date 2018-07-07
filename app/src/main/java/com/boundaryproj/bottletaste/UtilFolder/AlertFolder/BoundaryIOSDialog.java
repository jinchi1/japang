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

public class BoundaryIOSDialog extends LinearLayout {
    public BoundaryIOSDialog(Context context) {
        super(context);
    }

    public BoundaryIOSDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BoundaryIOSDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BoundaryIOSDialog PhotoTwoItem(OnClickListener listener){

        ((RelativeLayout)findViewById(R.id.twoLayer)).setVisibility(VISIBLE);
        ((RelativeLayout)findViewById(R.id.oneLayer)).setVisibility(VISIBLE);
        ((TextView)findViewById(R.id.txt1label)).setText("사진 앨범");
        ((TextView)findViewById(R.id.txt2label)).setText("삭제");
        ((TextView)findViewById(R.id.cancleBtn)).setText("취소");
        findViewById(R.id.txt1label).setOnClickListener(listener);
        findViewById(R.id.txt2label).setOnClickListener(listener);
        findViewById(R.id.cancleBtn).setOnClickListener(listener);
        return this;
    }

    public BoundaryIOSDialog PhotoOneItem(OnClickListener listener){

        ((RelativeLayout)findViewById(R.id.twoLayer)).setVisibility(GONE);
        ((RelativeLayout)findViewById(R.id.oneLayer)).setVisibility(VISIBLE);
        ((TextView)findViewById(R.id.txt1label)).setText("사진 앨범");
        ((TextView)findViewById(R.id.txt2label)).setText("삭제");
        ((TextView)findViewById(R.id.cancleBtn)).setText("취소");
        findViewById(R.id.txt1label).setOnClickListener(listener);
        findViewById(R.id.txt2label).setOnClickListener(listener);
        findViewById(R.id.cancleBtn).setOnClickListener(listener);
        return this;
    }


    public BoundaryIOSDialog InstagramAlert(OnClickListener listener, String txt){

        /*
        ((RelativeLayout)findViewById(R.id.customLayer)).setVisibility(VISIBLE);
        ((TextView)findViewById(R.id.alertTitle)).setText(txt);
        ((TextView)findViewById(R.id.positive)).setText("확인");
        ((TextView)findViewById(R.id.negative)).setText("취소");
        findViewById(R.id.positive).setOnClickListener(listener);
        findViewById(R.id.negative).setOnClickListener(listener);
        */
        return this;
    }

    public BoundaryIOSDialog PointSendAlert(OnClickListener listener){

        /*
        ((RelativeLayout)findViewById(R.id.customLayer)).setVisibility(GONE);
        ((TextView)findViewById(R.id.positive)).setText("보내기");
        ((TextView)findViewById(R.id.negative)).setText("취소");
        findViewById(R.id.positive).setOnClickListener(listener);
        findViewById(R.id.negative).setOnClickListener(listener);
        */
        return this;
    }

    public BoundaryIOSDialog show_custom(String title, String text, String positive, String negative, OnClickListener listener){

        /*
        findViewById(R.id.text).setVisibility(View.VISIBLE);

        ((TextView) findViewById(R.id.text)).setText(text);
        ((TextView)findViewById(R.id.positive)).setText(positive);
        ((TextView)findViewById(R.id.negative)).setText(negative);
        findViewById(R.id.positive).setOnClickListener(listener);
        findViewById(R.id.negative).setOnClickListener(listener);
        */
        return this;

    }



    public BoundaryIOSDialog OneItem(String title, String text, String positive, String negative, OnClickListener listener){

        /*
        findViewById(R.id.text).setVisibility(View.VISIBLE);

        ((TextView) findViewById(R.id.text)).setText(text);
        ((TextView)findViewById(R.id.positive)).setText(positive);
        ((TextView) findViewById(R.id.positive)).setVisibility(GONE);
        ((TextView)findViewById(R.id.negative)).setText(negative);
        findViewById(R.id.positive).setOnClickListener(listener);
        findViewById(R.id.negative).setOnClickListener(listener);
        */
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


        public View Build(){
            return v;
        }
        public void Build(Activity act){
            dialog = new AlertDialog.Builder(act, R.style.CustomDialog).
                    setView(v).setCancelable(true).create();
            dialog.show();
           // setNoticeClick(dialog);
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
