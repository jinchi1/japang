package com.boundaryproj.bottletaste.UtilFolder.InstagramFolder;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.boundaryproj.bottletaste.R;
import com.instagram.instagramapi.widgets.InstagramWebViewClient;

/**
 * Display 37Signals authentication dialog.
 * 
 * @author Thiago Locatelli <thiago.locatelli@gmail.com>
 * @author Lorensius W. L T <lorenz@londatiga.net>
 * 
 */
public class InstagramDialog extends Dialog {

	static final float[] DIMENSIONS_LANDSCAPE = { 460, 260 };
	static final float[] DIMENSIONS_PORTRAIT = { 280, 420 };
	static final FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(
			ViewGroup.LayoutParams.FILL_PARENT,
			ViewGroup.LayoutParams.FILL_PARENT);
	static final int MARGIN = 4;
	static final int PADDING = 2;

	private String mUrl;
	private OAuthDialogListener mListener;
	private ProgressDialog mSpinner;
	private WebView mWebView;
	private LinearLayout mContent;
	private TextView mTitle;
	private ProgressBar mPBLoading;

	private static final String TAG = "Instagram-WebView";

	public InstagramDialog(Context context, String url,
			OAuthDialogListener listener) {
		super(context);

		mUrl = url;
		mListener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setupDialog();
		setupWebView();

		mPBLoading = (ProgressBar) findViewById(R.id.pb_loading);
	}

	private void setUpTitle() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mTitle = new TextView(getContext());
		mTitle.setText("Instagram");
		mTitle.setTextColor(Color.WHITE);
		mTitle.setTypeface(Typeface.DEFAULT_BOLD);
		mTitle.setBackgroundColor(Color.BLACK);
		mTitle.setPadding(MARGIN + PADDING, MARGIN, MARGIN, MARGIN);
		mContent.addView(mTitle);
	}

	private void setUpWebView() {
		mWebView = new WebView(getContext());
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.setWebViewClient(new OAuthWebViewClient());
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl(mUrl);
		mWebView.setLayoutParams(FILL);
		mContent.addView(mWebView);
	}

	private void progressBarVisible(boolean visible) {
		if (visible) {
			mWebView.setVisibility(View.INVISIBLE);
			mPBLoading.setVisibility(View.VISIBLE);
		} else {
			mWebView.setVisibility(View.VISIBLE);
			mPBLoading.setVisibility(View.INVISIBLE);
		}
	}

	private void setupDialog() {
		View view = (View) getLayoutInflater().inflate(R.layout.dialog_instagram, null);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
	}

	private void setupWebView() {
		mWebView = (WebView) findViewById(R.id.webview);
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.setWebViewClient(new OAuthWebViewClient());
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setSaveFormData(false);
		mWebView.loadUrl(mUrl);
	}

	private class OAuthWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.d(TAG, "Redirecting URL " + url);

			if (url.startsWith(InstagramApp.mCallbackUrl)) {
				String urls[] = url.split("=");
				mListener.onComplete(urls[1]);
				InstagramDialog.this.dismiss();
				return true;
			}
			return false;
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			Log.d(TAG, "Page error: " + description);

			super.onReceivedError(view, errorCode, description, failingUrl);
			mListener.onError(description);
			InstagramDialog.this.dismiss();
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			Log.d(TAG, "Loading URL: " + url);


			super.onPageStarted(view, url, favicon);
			progressBarVisible(true);

		//	mSpinner.show();
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			progressBarVisible(false);
			String title = mWebView.getTitle();
			if (title != null && title.length() > 0) {
				//mTitle.setText(title);
			}
			Log.d(TAG, "onPageFinished URL: " + url);
		//	mSpinner.dismiss();
		}

	}

	public interface OAuthDialogListener {
		public abstract void onComplete(String accessToken);
		public abstract void onError(String error);
	}

}