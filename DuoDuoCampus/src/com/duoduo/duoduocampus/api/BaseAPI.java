package com.duoduo.duoduocampus.api;import java.io.ByteArrayInputStream;import java.io.File;import java.io.FileInputStream;import java.io.FilterInputStream;import java.io.IOException;import java.io.InputStream;import java.lang.reflect.ParameterizedType;import java.text.SimpleDateFormat;import java.util.ArrayList;import java.util.HashMap;import java.util.Hashtable;import java.util.List;import java.util.Locale;import java.util.Map;import java.util.concurrent.Future;import javax.net.ssl.SSLContext;import javax.net.ssl.TrustManager;import javax.net.ssl.X509TrustManager;import org.apache.http.NameValuePair;import org.apache.http.client.utils.URLEncodedUtils;import org.apache.http.conn.ssl.SSLSocketFactory;import org.apache.http.message.BasicNameValuePair;import org.apache.http.protocol.HTTP;import android.annotation.SuppressLint;import android.app.Activity;import android.app.Application.ActivityLifecycleCallbacks;import android.content.Context;import android.graphics.Bitmap;import android.os.Build;import android.os.Bundle;import android.text.TextUtils;import com.duoduo.duoduocampus.BaseApplication;import com.duoduo.duoduocampus.DuoDuoPrefences;import com.duoduo.duoduocampus.R;import com.duoduo.duoduocampus.utils.DToast;import com.duoduo.duoduocampus.utils.LogUtil;import com.duoduo.duoduocampus.utils.SDCardUtils;import com.google.gson.Gson;import com.google.gson.GsonBuilder;import com.google.gson.JsonElement;import com.google.gson.JsonParser;import com.koushikdutta.async.http.AsyncHttpClient;import com.koushikdutta.async.http.AsyncHttpClient.FileCallback;import com.koushikdutta.async.http.AsyncHttpClient.StringCallback;import com.koushikdutta.async.http.AsyncHttpGet;import com.koushikdutta.async.http.AsyncHttpPost;import com.koushikdutta.async.http.AsyncHttpRequest;import com.koushikdutta.async.http.AsyncHttpResponse;import com.koushikdutta.async.http.Multimap;import com.koushikdutta.async.http.body.FilePart;import com.koushikdutta.async.http.body.MultipartFormDataBody;import com.koushikdutta.async.http.body.UrlEncodedFormBody;@SuppressLint("NewApi")public class BaseAPI {	protected static final String TAG = "httpapi";		public static final int NETWORK_UNAVAILABLE = -1;	private Gson mGson = new GsonBuilder().setPrettyPrinting().create();	private JsonParser mJsonParser = new JsonParser();	private Hashtable<Long, Future<?>> mRequestQueue = new Hashtable<Long, Future<?>>();	private Hashtable<Activity, ArrayList<Long>> mActivityQueue = new Hashtable<Activity, ArrayList<Long>>();	private String mAccessToken = "";	private long mRequestId = Long.MIN_VALUE;	private List<AccessTokenListener> mAccessTokenListeners = new ArrayList<BaseAPI.AccessTokenListener>();	public static BaseAPI mBaseAPI;	/**	 * TODO: make this no-static {@link #blockRequest(long)} replace with	 * BaseApi.getInstance().blockRequest();	 */	private static String mCurrentUrl = "";	private static Map<Long, String> mBlockRequestList = new HashMap<Long, String>();	private static SimpleDateFormat Gmt = new SimpleDateFormat(			"EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);	protected BaseAPI() {	}	public static BaseAPI getInstance() {		if (mBaseAPI == null) {			mBaseAPI = new BaseAPI();		}		return mBaseAPI;	}	{		try {			TrustManager tm = new X509TrustManager() {				@Override				public java.security.cert.X509Certificate[] getAcceptedIssuers() {					return null;				}				@Override				public void checkClientTrusted(						java.security.cert.X509Certificate[] chain,						String authType)						throws java.security.cert.CertificateException {				}				@Override				public void checkServerTrusted(						java.security.cert.X509Certificate[] chain,						String authType)						throws java.security.cert.CertificateException {				}			};			SSLContext sslContext = SSLContext.getInstance("TLS");			sslContext.init(null, new TrustManager[] { tm }, null);			AsyncHttpClient.getDefaultInstance().getSSLSocketMiddleware()					.setSSLContext(sslContext);			AsyncHttpClient.getDefaultInstance().getSSLSocketMiddleware()					.setTrustManagers(new TrustManager[] { tm });			AsyncHttpClient					.getDefaultInstance()					.getSSLSocketMiddleware()					.setHostnameVerifier(							SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);		} catch (Exception e) {			e.printStackTrace();		}		if (Build.VERSION.SDK_INT >= 14) {			BaseApplication.getInstance().registerActivityLifecycleCallbacks(					new ActivityLifecycleCallbacks() {						@Override						public void onActivityStopped(Activity paramActivity) {						}						@Override						public void onActivityStarted(Activity paramActivity) {						}						@Override						public void onActivitySaveInstanceState(								Activity paramActivity, Bundle paramBundle) {						}						@Override						public void onActivityResumed(Activity paramActivity) {						}						@Override						public void onActivityPaused(Activity paramActivity) {						}						@Override						public void onActivityDestroyed(Activity paramActivity) {							if (mActivityQueue.containsKey(paramActivity)) {								LogUtil.d(TAG,										"onActivityDestroyed: cancel request");								ArrayList<Long> arrayList = mActivityQueue										.remove(paramActivity);								for (Long requestId : arrayList) {									abort(requestId);								}								LogUtil.d(TAG, "onActivityDestroyed:end");							}						}						@Override						public void onActivityCreated(Activity paramActivity,								Bundle paramBundle) {						}					});		}	}	// 回调接口	public static abstract class RequestListener<T> {		public abstract void onException(int status, T result, String error);		public abstract void onCompleted(T result);		public void onStart(long requestId) {		}		public void onDownload(float progress, float downloaded, float total) {		}		public void onUpload(float progress) {		}		public void onSyncServerTime(long serverTime) {		}	}	// 回调接口	public static interface AccessTokenListener {		public abstract void onGetAccessToken(String accessToken);	}	private AsyncHttpRequest createFileloadRequest(String relativeUrl,			List<NameValuePair> nameValuePairs, String accessToken,			final String path, final boolean image,			final RequestListener<?> requestListener) {		AsyncHttpRequest asyncHttpRequest = createHttpRequest(relativeUrl,				nameValuePairs, mAccessToken, false);		final File file = new File(path);		Multimap multimap = (Multimap) asyncHttpRequest.getBody().get();		MultipartFormDataBody multipartFormDataBody = new MultipartFormDataBody();		for (NameValuePair nameValuePair : multimap) {			if (nameValuePair != null					&& !TextUtils.isEmpty(nameValuePair.getName())					&& !TextUtils.isEmpty(nameValuePair.getValue())) {				multipartFormDataBody.addStringPart(nameValuePair.getName(),						nameValuePair.getValue());			}		}		multipartFormDataBody.addPart(new FilePart("attach[]", file) {			byte[] imageData;			@Override			public long length() {				if (image) {					if (imageData == null) {						Bitmap bitmap = SDCardUtils.compressRotateBitmap(path);						imageData = SDCardUtils.bitmapToBytes(bitmap);						if (!bitmap.isRecycled()) {							bitmap.recycle();						}					}					return imageData.length;				}				return super.length();			}			@Override			protected InputStream getInputStream() throws IOException {				if (image && imageData != null) {					return new CountingInputStream(new ByteArrayInputStream(							imageData), imageData.length, requestListener);				} else {					return new CountingInputStream(new FileInputStream(file),							file.length(), requestListener);				}			}		});		asyncHttpRequest.setBody(multipartFormDataBody);		return asyncHttpRequest;	}	private String buildUrl(String relativeUrl,			List<NameValuePair> nameValuePairs) {		String url = DomainUtil.getAbsoluteUrl(relativeUrl);		if (nameValuePairs != null) {			url = url + "?" + URLEncodedUtils.format(nameValuePairs, HTTP.UTF_8);		}		return url;	}	private AsyncHttpRequest createHttpRequest(String relativeUrl,			List<NameValuePair> nameValuePairs, String accessToken, boolean get) {		mCurrentUrl = buildUrl(relativeUrl, nameValuePairs);		String url = DomainUtil.getAbsoluteUrl(relativeUrl);		if (nameValuePairs == null) {			nameValuePairs = new ArrayList<NameValuePair>();		}		// IMEI		if (!TextUtils.isEmpty(BaseApplication.imei)) {			nameValuePairs.add(new BasicNameValuePair("imei", String					.valueOf(BaseApplication.imei)));		}		// Mac地址		if (!TextUtils.isEmpty(BaseApplication.mac)) {			nameValuePairs.add(new BasicNameValuePair("mac", String					.valueOf(BaseApplication.mac)));		}		// 渠道号		nameValuePairs.add(new BasicNameValuePair("qudaoid", String				.valueOf(BaseApplication.getChannelID())));		// 登录状态token		if (!TextUtils.isEmpty(accessToken)) {			nameValuePairs.add(new BasicNameValuePair("access_token",					accessToken));		}		LogUtil.d(TAG, (get ? "GET" : "POST") + ":" + url + "?"				+ URLEncodedUtils.format(nameValuePairs, HTTP.UTF_8));		AsyncHttpRequest asyncHttpRequest = null;		if (get) {			String query = URLEncodedUtils.format(nameValuePairs, HTTP.UTF_8);			asyncHttpRequest = new AsyncHttpGet(url + "?" + query);		} else {			asyncHttpRequest = new AsyncHttpPost(url);			UrlEncodedFormBody urlEncodedFormBody = new UrlEncodedFormBody(					nameValuePairs);			asyncHttpRequest.setBody(urlEncodedFormBody);		}		asyncHttpRequest				.setHeader("User-Agent", BaseApplication.getUserAgent());		return asyncHttpRequest;	}	/**	 * 上传文件	 */	public void fileUpload(final Activity activity, final String relativeUrl,			final List<NameValuePair> nameValuePairs,			final RequestListener<?> requestListener, final String path,			final boolean image) {		mAccessToken = DuoDuoPrefences.getAccesstoken(BaseApplication				.getInstance());		if (TextUtils.isEmpty(mAccessToken)) {			getAccessToken(new AccessTokenListener() {				@Override				public void onGetAccessToken(String accessToken) {					if (!TextUtils.isEmpty(accessToken)) {						AsyncHttpRequest asyncHttpRequest = createFileloadRequest(								relativeUrl, nameValuePairs, mAccessToken,								path, image, requestListener);						doRequest(activity, relativeUrl, asyncHttpRequest,								requestListener);					}				}			});		} else {			AsyncHttpRequest asyncHttpRequest = createFileloadRequest(					relativeUrl, nameValuePairs, mAccessToken, path, image,					requestListener);			doRequest(activity, relativeUrl, asyncHttpRequest, requestListener);		}	}	/**	 * 上传文件	 */	public void fileDown(final Activity activity, final String relativeUrl,			final List<NameValuePair> nameValuePairs,			final RequestListener<File> requestListener, final String path) {		mAccessToken = DuoDuoPrefences.getAccesstoken(BaseApplication				.getInstance());		if (TextUtils.isEmpty(mAccessToken)) {			getAccessToken(new AccessTokenListener() {				@Override				public void onGetAccessToken(String accessToken) {					if (!TextUtils.isEmpty(accessToken)) {						AsyncHttpRequest asyncHttpRequest = createHttpRequest(								relativeUrl, nameValuePairs, mAccessToken, true);						doFileDownRequest(activity, asyncHttpRequest,								requestListener, path);					}				}			});		} else {			AsyncHttpRequest asyncHttpRequest = createHttpRequest(relativeUrl,					nameValuePairs, mAccessToken, true);			doFileDownRequest(activity, asyncHttpRequest, requestListener, path);		}	}	public void get(final Activity activity, final String relativeUrl,			final List<NameValuePair> nameValuePairs,			final RequestListener<?> requestListener) {		String url = buildUrl(relativeUrl, nameValuePairs);		if (mBlockRequestList.containsValue(url)) {// 请求执行中			LogUtil.d(TAG, "request is running ,cancel");			return;		}		mAccessToken = DuoDuoPrefences.getAccesstoken(BaseApplication				.getInstance());		if (TextUtils.isEmpty(mAccessToken)) {			getAccessToken(new AccessTokenListener() {				@Override				public void onGetAccessToken(String accessToken) {					if (!TextUtils.isEmpty(accessToken)) {						AsyncHttpRequest httpGet = createHttpRequest(								relativeUrl, nameValuePairs, accessToken, true);						doRequest(activity, relativeUrl, httpGet,								requestListener);					}				}			});		} else {			AsyncHttpRequest httpGet = createHttpRequest(relativeUrl,					nameValuePairs, mAccessToken, true);			doRequest(activity, relativeUrl, httpGet, requestListener);		}	}	public void post(final Activity activity, final String relativeUrl,			final List<NameValuePair> nameValuePairs,			final RequestListener<?> requestListener) {		String url = buildUrl(relativeUrl, nameValuePairs);		if (mBlockRequestList.containsValue(url)) {// 请求执行中			LogUtil.d(TAG, "request is running ,cancel");			return;		}		mAccessToken = DuoDuoPrefences.getAccesstoken(BaseApplication				.getInstance());		if (TextUtils.isEmpty(mAccessToken)) {			getAccessToken(new AccessTokenListener() {				@Override				public void onGetAccessToken(String accessToken) {					if (!TextUtils.isEmpty(accessToken)) {						AsyncHttpRequest httpGet = createHttpRequest(								relativeUrl, nameValuePairs, accessToken, false);						doRequest(activity, relativeUrl, httpGet,								requestListener);					}				}			});		} else {			AsyncHttpRequest httpPost = createHttpRequest(relativeUrl,					nameValuePairs, mAccessToken, false);			doRequest(activity, relativeUrl, httpPost, requestListener);		}	}	/**	 * 请求之前必须获取accesstoken	 * 	 * @param accessTokenListener	 */	public void getAccessToken(final AccessTokenListener accessTokenListener) {		synchronized (mAccessTokenListeners) {			mAccessToken = "edb5df863d7c413420fd883d2246d595";			Context context = BaseApplication.getInstance();			DuoDuoPrefences.setAccesstoken(context, mAccessToken);			for (AccessTokenListener mAccessTokenListener : mAccessTokenListeners) {				mAccessTokenListener.onGetAccessToken(mAccessToken);			}			mAccessTokenListeners.clear();		}		// synchronized (mAccessTokenListeners) {		// if (TextUtils.isEmpty(mAccessToken)) {		// mAccessToken = DuoDuoPrefences.getAccesstoken(BaseApplication		// .getInstance());		// }		// if (!TextUtils.isEmpty(mAccessToken)) {		// accessTokenListener.onGetAccessToken(mAccessToken);		// return;		// } else {		// mAccessTokenListeners.add(accessTokenListener);		// if (mAccessTokenListeners.size() > 1) {		// return;		// }		// }		// }		// List<NameValuePair> params = new ArrayList<NameValuePair>();		// params.add(new BasicNameValuePair("response_type", "code"));		// params.add(new BasicNameValuePair("client_id", ""		// + BaseApplication.getChannelID()));		// params.add(new BasicNameValuePair("redirect_uri", ""));		// AsyncHttpRequest httpGet = createHttpRequest("oauth/authorize",		// params,		// null, true);		// doRequest(null, "oauth/authorize", httpGet,		// new RequestListener<String>() {		//		// @Override		// public void onException(int status, String result,		// String error) {		// synchronized (mAccessTokenListeners) {		// for (AccessTokenListener accessTokenListener : mAccessTokenListeners)		// {		// accessTokenListener.onGetAccessToken(null);		// }		// mAccessTokenListeners.clear();		// }		// }		//		// @Override		// public void onCompleted(String result) {		// JSONObject object = (JSONObject) JSONValue.parse(result);		// String code = JSonUtils.getString(object, "code");		// String accessToken = JSonUtils.getString(object, "access_token");		// String device_id = JSonUtils.getString(object, "device_id");		// if (TextUtils.isEmpty(accessToken)) {		// List<NameValuePair> params = new ArrayList<NameValuePair>();		// params.add(new BasicNameValuePair("grant_type",		// "authorization_code"));		// params.add(new BasicNameValuePair("client_id", ""		// + BaseApplication.getChannelID()));		// params.add(new BasicNameValuePair("code", code));		// params.add(new BasicNameValuePair("redirect_uri",		// ""));		// AsyncHttpRequest httpGet = createHttpRequest(		// "oauth/access_token", params, null, true);		// doRequest(null, "oauth/access_token", httpGet, this);		// } else {		// synchronized (mAccessTokenListeners) {		// mAccessToken = accessToken;		// Context context = BaseApplication.getInstance();		// DuoDuoPrefences.setAccesstoken(context,		// accessToken);		// for (AccessTokenListener accessTokenListener : mAccessTokenListeners)		// {		// accessTokenListener.onGetAccessToken(mAccessToken);		// }		// mAccessTokenListeners.clear();		// }		// }		// }		// });	}	@SuppressWarnings("rawtypes")	private void doRequest(final Activity activity, final String api,			AsyncHttpRequest httpRequest, final RequestListener requestListener) {		final long requestId = createRequestId();		StringCallback stringCallback = new StringCallback() {			private long transferred;			private long preTransferred;			@Override			public void onProgress(AsyncHttpResponse response,					final long downloaded, final long total) {				if (requestListener != null) {					transferred = downloaded;					if (transferred - preTransferred >= total / 100							|| transferred == total) {						preTransferred = transferred;						final float progress = 1.0f * downloaded / total;						if (requestListener != null && progress >= 0								&& progress <= 1) {							post(new Runnable() {								@Override								public void run() {									// 活动即将销毁 不关心返回数据了									if (activity != null											&& activity.isFinishing()) {										LogUtil.d(TAG,												"activity has finish,cancel the callback");										return;									}									if (requestListener != null) {										requestListener.onDownload(progress,												downloaded, total);									}								}							});						}					}				}			}			@Override			public void onCompleted(final Exception paramException,					final AsyncHttpResponse paramS, final String paramT) {				mRequestQueue.remove(requestId);				mBlockRequestList.remove(requestId);				if (LogUtil.isDebug()) {					if (!TextUtils.isEmpty(paramT)) {						try {							JsonElement jsonElement = mJsonParser.parse(paramT);							LogUtil.d(TAG + ": " + api,									"\r\n" + mGson.toJson(jsonElement));						} catch (Exception e) {							LogUtil.d(TAG + ": " + api, paramT);						}					}					if (paramException != null							&& !TextUtils.isEmpty(paramException.getMessage())) {						try {							JsonElement jsonElement = mJsonParser									.parse(paramException.getMessage());							LogUtil.d(TAG + ": " + api,									"\r\n" + mGson.toJson(jsonElement));						} catch (Exception e) {							LogUtil.d(TAG + ": " + api, paramT);						}					}				}				int status = -1;				if (!TextUtils.isEmpty(paramT)) {					status = 200;				}				if (paramS != null) {					status = paramS.code();				}				if (requestListener != null) {					LogUtil.d(TAG + api, "status:" + status);					Object object = paramT;					ParameterizedType pt = (ParameterizedType) requestListener							.getClass().getGenericSuperclass();					final Class<?> clazz = (Class<?>) pt							.getActualTypeArguments()[0];					if (!"java.lang.String".equals(clazz.getName())) {						object = mGson.fromJson(paramT, clazz);					}					final Object ob = object;					final int st = status;					post(new Runnable() {						@SuppressWarnings("unchecked")						@Override						public void run() {							// 活动即将销毁 不关心返回数据了							if (activity != null && activity.isFinishing()) {								LogUtil.d(TAG,										"activity has finish,cancel the callback");								return;							}							if (st == 200) {								if (requestListener != null) {									if (paramS != null											&& paramS.headers() != null) {										String date = paramS.headers().get(												"Date");										if (!TextUtils.isEmpty(date)) {											try {												final long serverTime = Gmt														.parse(date).getTime();												if (serverTime > 0) {													requestListener															.onSyncServerTime(serverTime);												}											} catch (Exception e) {											}										}									}									requestListener.onCompleted(ob);								}							} else {								if (requestListener != null) {									requestListener.onException(st, ob, paramT);								}							}						}					});				}			}		};		if (requestListener != null) {			requestListener.onStart(requestId);		}		Future<?> future = AsyncHttpClient.getDefaultInstance().executeString(				httpRequest, stringCallback);		mRequestQueue.put(requestId, future);		if (activity != null) {			if (mActivityQueue.containsKey(activity)) {				ArrayList<Long> arrayList = mActivityQueue.get(activity);				arrayList.add(requestId);			} else {				ArrayList<Long> arrayList = new ArrayList<Long>();				arrayList.add(requestId);				mActivityQueue.put(activity, arrayList);			}		}	}	@SuppressWarnings("rawtypes")	private void doFileDownRequest(final Activity activity,			final AsyncHttpRequest httpRequest,			final RequestListener requestListener, final String filePath) {		final long requestId = createRequestId();		Future<?> future = AsyncHttpClient.getDefaultInstance().executeFile(				httpRequest, filePath, new FileCallback() {					private long transferred;					private long preTransferred;					@Override					public void onProgress(AsyncHttpResponse response,							final long downloaded, final long total) {						if (requestListener != null) {							transferred = downloaded;							if (transferred - preTransferred >= total / 100									|| transferred == total) {								preTransferred = transferred;								final float progress = 1.0f * downloaded										/ total;								if (progress >= 0 && progress <= 1) {									post(new Runnable() {										@Override										public void run() {											// 活动即将销毁 不关心返回数据了											if (activity != null													&& activity.isFinishing()) {												LogUtil.d(TAG,														"activity has finish,cancel the callback");												return;											}											if (requestListener != null) {												requestListener.onDownload(1.0f														* downloaded / total,														downloaded, total);											}										}									});								}							}						}					}					@Override					public void onCompleted(final Exception paramException,							final AsyncHttpResponse paramS, final File paramT) {						int status = -1;						if (paramS != null) {							status = paramS.code();						}						if (requestListener != null) {							final int st = status;							LogUtil.d(TAG, "status:" + st);							post(new Runnable() {								@SuppressWarnings("unchecked")								@Override								public void run() {									// 活动即将销毁 不关心返回数据了									if (activity != null											&& activity.isFinishing()) {										LogUtil.d(TAG,												"activity has finish,cancel the callback");										return;									}									if (st == 200) {										if (requestListener != null) {											requestListener.onCompleted(paramT);										}									} else {										if (requestListener != null) {											requestListener.onException(st,													null, null);										}									}								}							});						}					}				});		mRequestQueue.put(requestId, future);		if (activity != null) {			if (mActivityQueue.containsKey(activity)) {				ArrayList<Long> arrayList = mActivityQueue.get(activity);				arrayList.add(requestId);			} else {				ArrayList<Long> arrayList = new ArrayList<Long>();				arrayList.add(requestId);				mActivityQueue.put(activity, arrayList);			}		}		if (requestListener != null) {			requestListener.onStart(requestId);		}	}	public static void blockRequest(long requestId) {		mBlockRequestList.put(requestId, mCurrentUrl);		LogUtil.d(TAG, "block:" + mCurrentUrl);	}	/**	 * 产生一个请求id	 * @return	 */	private long createRequestId() {		mRequestId++;		if (mRequestId == Long.MAX_VALUE) {			mRequestId = Long.MIN_VALUE;		}		return mRequestId;	}	/**	 * 终止请求	 * @param requesId	 */	public void abort(Long requesId) {		if (mRequestQueue.containsKey(requesId)) {			mBlockRequestList.remove(requesId);			LogUtil.d(TAG, "abort:" + requesId);			Future<?> future = mRequestQueue.remove(requesId);			if (future != null) {				future.cancel(true);			}		}	}	private class CountingInputStream extends FilterInputStream {		private final RequestListener<?> listener;		private long total;		private long transferred;		private long preTransferred;		public CountingInputStream(final InputStream in, long total,				final RequestListener<?> listener) {			super(in);			this.total = total;			this.listener = listener;			this.transferred = 0;			this.preTransferred = 0;		}		@Override		public int read(byte[] buffer, int byteOffset, int byteCount)				throws IOException {			int n = super.read(buffer, byteOffset, byteCount);			calProgress(n);			return n;		}		@Override		public int read(byte[] buffer) throws IOException {			int n = super.read(buffer);			calProgress(n);			return n;		}		@Override		public int read() throws IOException {			int n = super.read();			calProgress(n);			return n;		}		private void calProgress(int n) {			if (n != -1) {				this.transferred += n;				if (transferred - preTransferred >= total / 100						|| transferred == total) {					preTransferred = transferred;					final float progress = 1.0f * transferred / total;					if (progress >= 0 && progress <= 1) {						if (CountingInputStream.this.listener != null) {							post(new Runnable() {								@Override								public void run() {									CountingInputStream.this.listener											.onUpload(progress);								}							});						}					}				}			}		}	}	private void post(Runnable runnable) {		BaseApplication.getInstance().runOnUIThread(runnable);	}	private void post(Runnable runnable, long delayMillis) {		BaseApplication.getInstance().runOnUIThread(runnable, delayMillis);	}	public void showCommonToast(int status) {		Context context = BaseApplication.getInstance();		if (status == NETWORK_UNAVAILABLE) {			DToast.toastShort(context.getString(R.string.network_error_hint));		} else {			DToast.toastShort(context.getString(R.string.serverbusy));		}	}	/**	 * 上传音频文件	 */	public void fileUploadAudio(final Activity activity,			final String relativeUrl, final List<NameValuePair> nameValuePairs,			final RequestListener<?> requestListener, final String path) {		mAccessToken = DuoDuoPrefences.getAccesstoken(BaseApplication				.getInstance());		if (TextUtils.isEmpty(mAccessToken)) {			getAccessToken(new AccessTokenListener() {				@Override				public void onGetAccessToken(String accessToken) {					if (!TextUtils.isEmpty(accessToken)) {						AsyncHttpRequest asyncHttpRequest = createAudioFileloadRequest(								relativeUrl, nameValuePairs, mAccessToken,								path, requestListener);						doRequest(activity, relativeUrl, asyncHttpRequest,								requestListener);					}				}			});		} else {			AsyncHttpRequest asyncHttpRequest = createAudioFileloadRequest(					relativeUrl, nameValuePairs, mAccessToken, path,					requestListener);			doRequest(activity, relativeUrl, asyncHttpRequest, requestListener);		}	}	/**	 * 音频文件请求	 */	private AsyncHttpRequest createAudioFileloadRequest(String relativeUrl,			List<NameValuePair> nameValuePairs, String accessToken,			final String path, final RequestListener<?> requestListener) {		AsyncHttpRequest asyncHttpRequest = createHttpRequest(relativeUrl,				nameValuePairs, mAccessToken, false);		final File file = new File(path);		Multimap multimap = (Multimap) asyncHttpRequest.getBody().get();		MultipartFormDataBody multipartFormDataBody = new MultipartFormDataBody();		for (NameValuePair nameValuePair : multimap) {			if (nameValuePair != null					&& !TextUtils.isEmpty(nameValuePair.getName())					&& !TextUtils.isEmpty(nameValuePair.getValue())) {				multipartFormDataBody.addStringPart(nameValuePair.getName(),						nameValuePair.getValue());			}		}		multipartFormDataBody.addPart(new FilePart("audio", file) {			@Override			protected InputStream getInputStream() throws IOException {				return new CountingInputStream(new FileInputStream(file), file						.length(), requestListener);			}		});		asyncHttpRequest.setBody(multipartFormDataBody);		return asyncHttpRequest;	}		public static class RequestState {		public int type;		public RequestState(int type, String reqeustURL) {			super();			this.type = type;			this.reqeustURL = reqeustURL;		}		public RequestState(String reqeustURL) {			super();			this.reqeustURL = reqeustURL;		}		public RequestState() {			super();		}		public String tag_id;		public boolean isRequestServer;		public boolean isRefresh;		public boolean isBottom;		public int offset;		public int last_offset;		public int last_page;		public int page;		public int limit = 30;		public String reqeustURL;		public boolean isFistTime = true;		public String response;		public int count;		public boolean alive = true;		public String trace;	}}