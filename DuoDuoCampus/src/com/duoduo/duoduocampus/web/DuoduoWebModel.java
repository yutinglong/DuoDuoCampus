package com.duoduo.duoduocampus.web;

import android.content.Intent;
import android.text.TextUtils;

import java.util.ArrayList;

public class DuoduoWebModel {
	public static final String EXTRA_URI = "uri";
	public static final String EXTRA_URI_CONTENT = "uri_content";
	public static final String EXTRA_IS_SHOW_CART = "is_show_cart";
	public static final String EXTRA_IS_SHOW_BACK = "is_show_back";
	public static final String EXTRA_IS_SHOW_CLOSE = "is_show_close";
	public static final String EXTRA_OPER_TYPE = "oper_type";
	public static final String EXTRA_OPER_PARAMS = "oper_params";
	public static final String EXTRA_IS_CAN_BACK = "is_can_back";

	/**
	 * 操作类型
	 */
	public enum OperType {
		NORMAL, // 默认类型
		RISK_CONTROL, // 风控
		BIND, // 绑定账号
		WEIBO_SHARE, // 微博分享
		WEIBO_FOLLOW, // 微博关注
		WECHAT_FAST_LOGIN, // 微信一键登录
		MLS_PAY, // 美丽说支付
	}

	private DuoduoWebViewController mWebViewController;

	private String mUri;// uri链接
	private String mUriContent;// 直接给出页面代码

	private OperType mOperType = OperType.NORMAL;// 区分操作类型

	private ArrayList<String> mParams;// 备注参数

	public long mLastFilterTime = -1;// 上一次过滤时间戳

	private DuoduoWebModel() {
	}

	public static DuoduoWebModel getWebModel(Intent intent) {
		if (intent == null) {
			return null;
		}
		DuoduoWebModel resultModel = new DuoduoWebModel();
		if (resultModel.mWebViewController == null) {
			resultModel.mWebViewController = new DuoduoWebViewController();
		}
		resultModel.mWebViewController.setShowCart(intent.getBooleanExtra(
				EXTRA_IS_SHOW_CART, true));
		resultModel.mWebViewController.setShowBack(intent.getBooleanExtra(
				EXTRA_IS_SHOW_BACK, true));
		resultModel.mWebViewController.setShowClose(intent.getBooleanExtra(
				EXTRA_IS_SHOW_CLOSE, false));
		resultModel.mWebViewController.setCanBack(intent.getBooleanExtra(
				EXTRA_IS_CAN_BACK, true));

		resultModel.mUri = intent.getStringExtra(EXTRA_URI);
		resultModel.mUriContent = intent.getStringExtra(EXTRA_URI_CONTENT);

		OperType operType = (OperType) intent
				.getSerializableExtra(EXTRA_OPER_TYPE);
		if (operType != null) {
			resultModel.mOperType = operType;
		}
		resultModel.mParams = intent.getStringArrayListExtra(EXTRA_OPER_PARAMS);
		return resultModel;
	}

	/**
	 * 是否展示购物车按钮
	 * 
	 * @return
	 */
	public boolean isShowCart() {
		if (mWebViewController != null) {
			return mWebViewController.isShowCart();
		}
		return true;
	}

	/**
	 * 是否展示回退按钮
	 * 
	 * @return
	 */
	public boolean isShowBack() {
		if (mWebViewController != null) {
			return mWebViewController.isShowBack();
		}
		return true;
	}

	/**
	 * 是否展示关闭按钮
	 * 
	 * @return
	 */
	public boolean isShowClose() {
		if (mWebViewController != null) {
			return mWebViewController.isShowClose();
		}
		return false;
	}

	public void setShowClose(boolean isShowClose) {
		if (mWebViewController != null) {
			mWebViewController.setShowClose(isShowClose);
		}
	}

	public boolean isCanGoBack() {
		if (mWebViewController != null) {
			return mWebViewController.isCanBack();
		}
		return true;
	}

	public void setCanBack(boolean isCanBack) {
		if (mWebViewController != null) {
			mWebViewController.setCanBack(isCanBack);
		}
	}

	public boolean isUri() {
		if (TextUtils.isEmpty(mUri)) {
			return false;
		}

		return true;
	}

	public String getUri() {
		return mUri;
	}

	public void setUri(String mUri) {
		this.mUri = mUri;
	}

	public String getUriContent() {
		return mUriContent;
	}

	public boolean isUriContains(String kword) {

		if (!isUri()) {
			return false;
		}

		return mUri.contains(kword);
	}

	public OperType getOperType() {
		return mOperType;
	}

	public ArrayList<String> getParams() {
		return mParams;
	}

	/**
	 * 参数合法性检查,至少含有一个
	 * 
	 * @return
	 */
	public boolean isParamsAvailable() {
		if (mParams != null && mParams.size() > 0) {
			return true;
		}
		return false;
	}

}
