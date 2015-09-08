package com.duoduo.duoduocampus.web;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ProgressBar;

import com.duoduo.duoduocampus.BaseApplication;
import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.activity.BaseActivity;
import com.duoduo.duoduocampus.profile.views.MessageBoxDialog;
import com.duoduo.duoduocampus.utils.CookieUtil;
import com.duoduo.duoduocampus.utils.LogUtil;
import com.duoduo.duoduocampus.utils.MLSUriUtils;
import com.duoduo.duoduocampus.views.RefreshView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class DuoduoWebActivity extends BaseActivity implements
		RefreshView.OnRefreshListener, OnClickListener, OnTouchListener {
	private static final String TAG = "MLSWebActivity";
	private static final int BACK_COUNT_NUM = 1;

	private WebView mWebView;
	private View mBtnBack;
	private View mBtnClose;
	private View mViewReload;
	private View mBtnShare;
	private RefreshView mViewRefresh;
	private AdjustableTextView mTextTitle;

	private String errorUrl = "";
	private String mOriginalUserAgent;

	private boolean mBackPressed = false;

	private int mCounterBack = BACK_COUNT_NUM;

	private Map<String, String> shareMap = new HashMap<String, String>();

	private String shareSchemeUrl;

	public ProgressBar mProgressBar;

	private DuoduoWebModel mWebDataModel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getIntentExtra(getIntent());
		registerReceiver();
		initViewHandle();
		initViewListener();
		initViewData();
		settingWebView();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		callHiddenWebViewMethod("onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		callHiddenWebViewMethod("onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		unregisterReceiver(refreshOpenIdReceiver);
//		unregisterReceiver(mReceiver);

		if (mWebView != null) {
			mWebView.loadUrl("about:blank");
		}
	}

	private BroadcastReceiver refreshOpenIdReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			if (mWebView != null && mWebDataModel.isUri()) {
				CookieUtil.setCookie(mWebDataModel.getUri(),
						DuoduoWebActivity.this);
			}
		}
	};

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent intent) {
			// MLSWebHandler.javaScriptFilter(MLSWebActivity.this, mWebView,
			// intent.getAction(), intent, mWebDataModel);
		}
	};

	public void setTitle(String title) {
		mTextTitle.setSelfMaxWidth(mWebDataModel.isShowClose());
		mTextTitle.setResizeText(title);
	}

	/**
	 * 获取各个控件的引用
	 */
	private void initViewHandle() {
		setContentView(R.layout.web_view);
		mViewRefresh = (RefreshView) this.findViewById(R.id.refresh_view);
		mViewReload = findViewById(R.id.center_refresh);
		mProgressBar = (ProgressBar) findViewById(R.id.web_progress);
		mBtnBack = findViewById(R.id.btn_back);
		mBtnClose = findViewById(R.id.btn_close);
		mWebView = (WebView) findViewById(R.id.web);
		mBtnShare = findViewById(R.id.iv_share);
		mTextTitle = (AdjustableTextView) findViewById(R.id.title);
	}

	/**
	 * 添加各种监听
	 */
	private void initViewListener() {
		mViewRefresh.setOnRefreshListener(this);
		mBtnBack.setOnClickListener(this);
		mBtnClose.setOnClickListener(this);
		mViewReload.setOnClickListener(this);
		mBtnShare.setOnClickListener(this);
	}

	/**
	 * 注册各种Receiver
	 */
	private void registerReceiver() {
//		 registerReceiver(refreshOpenIdReceiver, new IntentFilter(
//		 AppConstants.NOTIFICATION_GET_OPEN_ID));
		//
		// IntentFilter intentFilter = new IntentFilter();
		// intentFilter.addAction(AppConstants.USER_LOGIN);
		// intentFilter.addAction(AppConstants.USER_LOGOUT);
		// intentFilter.addAction(AppConstants.ACTION_SCANNER_RESULT);
		// intentFilter.addAction(AppConstants.WEB_ACTION_TYPE_PWD_DIALOG);
		// intentFilter.addAction(AppConstants.WEB_ACTION_TYPE_HIGO);
		// intentFilter.addAction(AppConstants.UPLOAD_SUCCESS);
		// registerReceiver(mReceiver, intentFilter);
	}

	/**
	 * 更新界面数据
	 */
	private void initViewData() {
		if (!mWebDataModel.isShowBack()) {
			mBtnBack.setVisibility(View.GONE);
		} else {
			mBtnBack.setVisibility(View.VISIBLE);
		}

		if (!mWebDataModel.isShowClose()) {
			mBtnClose.setVisibility(View.GONE);
		} else {
			mBtnClose.setVisibility(View.VISIBLE);
		}

		mTextTitle.setSelfMaxWidth(mWebDataModel.isShowClose());
	}

	/**
	 * 获取Intent中的参数
	 * 
	 * @param intent
	 */
	private void getIntentExtra(Intent intent) {
		mWebDataModel = DuoduoWebModel.getWebModel(intent);
		if (mWebDataModel == null) {// 页面intent数据合法性检查
			finish();
		}
	}

	/**
	 * 设置WebView
	 */
	private void settingWebView() {
		for (int i = 0; i < mWebView.getChildCount(); i++) {
			View child = mWebView.getChildAt(i);
			String tencentWebkit = child.getClass().getPackage().toString();
			if (tencentWebkit.contains("com.tencent.smtt")) {
				mViewRefresh.setSlidablyView(child);
			}
		}

		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setBuiltInZoomControls(true); // 设置支持缩放
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setUseWideViewPort(true);
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ECLAIR_MR1) {
			mWebView.getSettings().setLoadWithOverviewMode(true);
		}

		mWebView.getSettings().setDomStorageEnabled(true);
		mWebView.getSettings().setDatabaseEnabled(true);
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
			mWebView.getSettings().setDatabasePath(
					"/data/data/" + mWebView.getContext().getPackageName()
							+ "/databases/");
		}
		mWebView.getSettings().setSupportMultipleWindows(true);

		mOriginalUserAgent = mWebView.getSettings().getUserAgentString();

		mWebView.setWebChromeClient(new MLSWebChromeClient());
		mWebView.setDownloadListener(new DuoduoWebDownloadListener(
				DuoduoWebActivity.this));
		mWebView.setWebViewClient(new MLSWebViewClient());
		mWebView.getView().setOnTouchListener(this);
		if (mWebDataModel.isUri()) {
			if (mWebDataModel.isUriContains("connect/client_callback")
					|| mWebDataModel.isUriContains("connect/auth")) {
				// connect/client_callback修改https方式
				setWebUserAgent();
				mWebDataModel.setCanBack(false);
				mWebDataModel.setUri(MLSUriUtils.addAccessToken(
						DuoduoWebActivity.this, mWebDataModel.getUri()));
			}

			CookieUtil.setCookie(mWebDataModel.getUri(), this);
			detectTouchDisAllow(mWebDataModel.getUri());
			mWebView.loadUrl(mWebDataModel.getUri());
		} else {
			mWebView.loadDataWithBaseURL(null, mWebDataModel.getUriContent(),
					"text/html", "utf-8", null);
		}

	}

	private void setWebUserAgent() {
		String userAgent = BaseApplication.getUserAgent();
		mWebView.getSettings().setUserAgentString(userAgent);
	}

	private void detectTouchDisAllow(String url) {
		boolean disAllowTouchEvent = false;
		String query = url.substring(url.indexOf('?') + 1);

		String[] paramStr = query.split("&");
		Map<String, String> paramMap = new HashMap<String, String>();
		for (String pairStr : paramStr) {
			String[] pair = pairStr.split("=");
			if (pair.length == 2) {
				try {
					paramMap.put(pair[0], URLDecoder.decode(pair[1], "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}

		for (Map.Entry<String, String> e : paramMap.entrySet()) {
			if (e.getKey().equals("activity_has_gesture")) {
				if (e.getValue().equals("1")) {
					disAllowTouchEvent = true;
				}
				break;
			}
		}

		if (disAllowTouchEvent) {
			mViewRefresh.disAllowTouchEvent(true);
		}
	}

	private void callHiddenWebViewMethod(String name) {
		if (mWebView != null) {
			try {
				Method method = WebView.class.getMethod(name);
				method.invoke(mWebView);
			} catch (Exception e) {
				LogUtil.e(TAG + " : ", e.toString());
			}
		}
	}

	private void backCount() {
		mCounterBack--;
		if (mCounterBack <= 0) {
			mWebDataModel.setShowClose(true);
			mBtnClose.setVisibility(View.VISIBLE);
			mTextTitle.setSelfMaxWidth(mWebDataModel.isShowClose());
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}

	class MLSWebViewClient extends WebViewClient {

		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler,
				SslError error) {
			handler.proceed();// 接受证书
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			LogUtil.e(TAG, "onPageFinished : " + url);
			if (url.contains("qq.com")) {
				DuoduoWebHandler.javaScriptFilter(DuoduoWebActivity.this,
						mWebView, DuoduoWebConstants.WEB_JS_CALLBACK_QQ_RESULT,
						null, mWebDataModel);
			}

			if (mWebDataModel.isUriContains("connect/client_callback")
					|| mWebDataModel.isUriContains("connect/auth")) {// 前面修改过
																		// 这里需要恢复下
				mWebView.getSettings().setUserAgentString(mOriginalUserAgent);
			}

			if (mViewReload.getVisibility() == View.VISIBLE) {
				mWebView.clearHistory();
			}

			if (mViewRefresh != null) {
				mViewRefresh.onRefreshComplete();
			}

			DuoduoWebHandler.resultFilter(DuoduoWebActivity.this, url,
					mWebDataModel);
			super.onPageFinished(view, url);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			LogUtil.e(TAG, "shouldOverrideUrlLoading : " + url);
			LogUtil.e("mls", "<---webview---shouldOverrideUrlLoading--->");
			if (!TextUtils.isEmpty(url)
					&& (url.contains("connect/client_callback") || url
							.contains("connect/auth"))) {// connect/client_callback修改https方式
				String userAgent = BaseApplication.getUserAgent();
				view.getSettings().setUserAgentString(userAgent);
			}
			shareSchemeUrl = url; // 仅在分享Scheme调用js回调的时候用
			if (DuoduoWebHandler.protocolFilter(DuoduoWebActivity.this, url,
					mWebDataModel)) {
				return true;
			} else {
				detectTouchDisAllow(url);
				// 隐藏分享按钮
				mBtnShare.setVisibility(View.GONE);
				return super.shouldOverrideUrlLoading(view, url);
			}
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			LogUtil.e(TAG, "onPageStarted : " + url);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// -2 :找不到该网址。[网络不好，或者需要刷新]
			if (errorCode == -2) {
				// mWebView.loadUrl(errorTipUrl);
				LogUtil.e("mls", "<----onReceivedError---->");
				errorUrl = failingUrl;
				mViewReload.setVisibility(View.VISIBLE);
			}
			super.onReceivedError(view, errorCode, description, failingUrl);
			LogUtil.i(TAG, String.format("onReceivedError:", failingUrl));
		}
	}

	final class MLSWebChromeClient extends WebChromeClient {

		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			setTitle(title);
		}

		@Override
		public void onProgressChanged(WebView webView, int i) {
			super.onProgressChanged(webView, i);
			if (i == 100) {
				mProgressBar.setVisibility(View.INVISIBLE);
			} else {
				if (mProgressBar.getVisibility() == View.INVISIBLE) {
					mProgressBar.setVisibility(View.VISIBLE);
				}
				mProgressBar.setProgress(i);
			}
		}

		/**
		 * 处理alert弹出框
		 */
		public boolean onJsAlert(WebView view, String url, String message,
				final JsResult result) {
			if (!isFinishing()) {
				MessageBoxDialog.Builder builder = new MessageBoxDialog.Builder(
						DuoduoWebActivity.this, 1);

				final MessageBoxDialog dialog = builder
						.setMessageAndStyle(message,
								R.style.MessageBoxMessageStyle)
						.setCancelButton("知道了", R.style.CancelButtonStyle, 0,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
										result.confirm();
									}
								}).create();
				dialog.setCancelable(false);
				dialog.show();
			}
			return true;
		}

		/**
		 * 处理confirm弹出框
		 */

		public boolean onJsConfirm(WebView view, String url, String message,
				JsResult result) {
			return super.onJsConfirm(view, url, message, result);
		}

		/**
		 * 处理prompt弹出框
		 */
		public boolean onJsPrompt(WebView view, String url, String message,
				String defaultValue, JsPromptResult result) {
			return super.onJsPrompt(view, url, message, message, result);
		}

	}

	/**
	 * 返回文件选择
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		// if (requestCode == AppConstants.SCHEME_REQUEST_PICK_ADDRESS
		// && resultCode == 200) {
		// MLSWebHandler.javaScriptFilter(MLSWebActivity.this, mWebView,
		// MLSWebConstants.WEB_JS_CALLBACK_PICK_ADDRESS, intent,
		// mWebDataModel);
		// } else if (requestCode == AppConstants.SCHEME_REQUEST_ADD_ADDRESS
		// && resultCode == 200) {
		// MLSWebHandler.javaScriptFilter(MLSWebActivity.this, mWebView,
		// MLSWebConstants.WEB_JS_CALLBACK_ADD_ADDRESS, intent,
		// mWebDataModel);
		// } else if (requestCode == AppConstants.SCHEME_REQUEST_UPDATE_ADDRESS
		// && resultCode == 200) {
		// MLSWebHandler.javaScriptFilter(MLSWebActivity.this, mWebView,
		// MLSWebConstants.WEB_JS_CALLBACK_UPDATE_ADDRESS, intent,
		// mWebDataModel);
		// }

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			onBackPressed();
			break;
		case R.id.btn_close:
			closePage();
			break;
		case R.id.center_refresh:
			mViewReload.setVisibility(View.GONE);
			refreshData();
			break;
		case R.id.iv_share:
			// ShareWebDialog dialog = new ShareWebDialog(this,
			// R.style.Theme_at_her);
			// dialog.setShareItems(shareMap, MLSWebActivity.this);
			// dialog.show();
			break;
		default:
			break;
		}

	}

	private void refreshData() {
		if (errorUrl != null) {
			mWebView.loadUrl(errorUrl);
		}
	}

	private void closePage() {
		DuoduoWebHandler.closeFilter(DuoduoWebActivity.this, mWebDataModel);
	}

	// 签到调用的回调
	public void signInCallBack(String miyao) {
		Intent intent = new Intent();
		intent.putExtra("secret_key", miyao);
		DuoduoWebHandler.javaScriptFilter(DuoduoWebActivity.this, mWebView,
				DuoduoWebConstants.WEB_JS_CALLBACK_SIGN_IN, intent,
				mWebDataModel);
	}

	@Override
	public void onBackPressed() {
		mBackPressed = true;
		if (mWebDataModel.isCanGoBack() && mWebView.canGoBack()) {
			mWebView.goBack();
			backCount();
		} else {
			closePage();
		}
	}

	public void loadJavaScript(String script) {
		if (mWebView != null) {
			mWebView.loadUrl("javascript:" + script);
		}
	}

	public void addCookie(String name, String value) {
		CookieSyncManager.createInstance(this);
		CookieManager cookieManager = CookieManager.getInstance();
		CookieUtil.addCookie(cookieManager, mWebDataModel.getUri(), name,
				value, new String[] { ".meilishuo.com" });
	}

	/**
	 * web页scheme分享的信息
	 */
	public void addShareItems(String weibo, String qzone, String qq,
			String weixin, String weixin_timeline) {
		shareMap.clear();
		shareMap.put("weibo", weibo);
		shareMap.put("qzone", qzone);
		shareMap.put("qq", qq);
		shareMap.put("weixin", weixin);
		shareMap.put("weixin_timeline", weixin_timeline);
		mBtnShare.setVisibility(View.VISIBLE);
	}

	@Override
	public void onRefresh(PullToRefreshBase refreshView) {
		if (mViewReload.getVisibility() == View.VISIBLE) {
			mViewReload.setVisibility(View.GONE);
		}
		mWebView.reload();
	}
}
