package com.boundaryproj.bottletaste.MainFolder.SettingFolder.Act;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;

import com.boundaryproj.bottletaste.R;
import com.boundaryproj.bottletaste.databinding.ActivityWebviewBinding;

public class WebviewActivity extends AppCompatActivity {

    ActivityWebviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_webview);
        binding.webView.setWebViewClient(new WebViewClient());
        if(getIntent().getStringExtra("type").toString().equals("0")){
            binding.title.setText("이용약관");
            binding.webView.loadUrl("http://13.125.72.245/terms/terms_3.php");

        }else{
            binding.title.setText("개인정보 취급방침");
            binding.webView.loadUrl("http://13.125.72.245/terms/terms_2.php");
        }
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
