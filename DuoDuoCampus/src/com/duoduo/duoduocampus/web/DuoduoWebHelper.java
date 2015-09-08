package com.duoduo.duoduocampus.web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.util.ArrayList;

public class DuoduoWebHelper {

	/**
	 * 通过链接打开
	 * 
	 * @param context
	 * @param uri
	 */
	public static void openByUri(Context context, String uri) {
		openByUri(context, uri, null);
	}

	/**
	 * 通过连接打开web页面，并控制页面元素显隐
	 * 
	 * @param context
	 * @param uri
	 * @param controller
	 *            view控制器详见
	 *            {@link com.DuoduoWebViewController.app.web.MLSWebViewController}
	 */
	public static void openByUri(Context context, String uri,
			DuoduoWebViewController controller) {
		openByUri(context, uri, controller, false, -1);
	}

	/**
	 * forResult的方式打开web页面
	 * 
	 * @param context
	 * @param uri
	 * @param requestCode
	 */
	public static void openByUriForResult(Context context, String uri,
			int requestCode) {
		openByUri(context, uri, null, true, requestCode);
	}

	/**
	 * forResult的方式打开web页面,并控制页面元素显隐
	 * 
	 * @param context
	 * @param uri
	 * @param controller
	 *            view控制器详见
	 *            {@link com.DuoduoWebViewController.app.web.MLSWebViewController}
	 * @param requestCode
	 */
	public static void openByUriForResult(Context context, String uri,
			DuoduoWebViewController controller, int requestCode) {
		openByUri(context, uri, controller, true, requestCode);
	}

	private static void openByUri(Context context, String uri,
			DuoduoWebViewController controller, boolean isForResult,
			int requestCode) {
		if (TextUtils.isEmpty(uri)) {
			return;
		}
		Intent intent = new Intent();
		intent.setClass(context, DuoduoWebActivity.class);
		intent.putExtra(DuoduoWebModel.EXTRA_URI, uri);
		setViewControllerDate(controller, intent);

		if (isForResult) {
			((Activity) context).startActivityForResult(intent, requestCode);
		} else {
			context.startActivity(intent);
		}
	}

	/**
	 * 通过网页内容打开web页面
	 * 
	 * @param context
	 * @param uriContent
	 *            网页内容
	 */
	public static void openByContent(Context context, String uriContent) {
		openByContent(context, uriContent, null, false, -1);
	}

	private static void openByContent(Context context, String uriContent,
			DuoduoWebViewController controller, boolean isForResult,
			int requestCode) {
		if (TextUtils.isEmpty(uriContent)) {
			return;
		}
		Intent intent = new Intent();
		intent.setClass(context, DuoduoWebActivity.class);
		intent.putExtra(DuoduoWebModel.EXTRA_URI_CONTENT, uriContent);
		setViewControllerDate(controller, intent);
		if (isForResult) {
			((Activity) context).startActivityForResult(intent, requestCode);
		} else {
			context.startActivity(intent);
		}
	}

	/**
	 * 通过“类型”打开Web页面，将特殊需求的请求（如微博关注后续需要关注或者分享）通过“类型+参数”的形式进行覆盖
	 * 
	 * @param context
	 * @param uri
	 * @param type
	 *            定义的操作类型{@link DuoduoWebModel.OperType}
	 * @param params
	 */
	public static void openByType(Context context, String uri,
			DuoduoWebModel.OperType type, ArrayList<String> params) {
		openByType(context, uri, type, params, null);
	}

	/**
	 * 通过“类型”打开Web页面，将特殊需求的请求（如微博关注后续需要关注或者分享）通过“类型+参数”的形式进行覆盖,并控制页面元素显隐
	 * 
	 * @param context
	 * @param uri
	 * @param type
	 * @param params
	 * @param controller
	 *            view元素控制器详见
	 *            {@link com.DuoduoWebViewController.app.web.MLSWebViewController}
	 */
	public static void openByType(Context context, String uri,
			DuoduoWebModel.OperType type, ArrayList<String> params,
			DuoduoWebViewController controller) {
		openByType(context, uri, type, params, controller, false, -1);
	}

	/**
	 * 通过“类型”打开Web页面，将特殊需求的请求（如微博关注后续需要关注或者分享）通过“类型+参数”的形式进行覆盖,forResult的形式返回
	 * 
	 * @param context
	 * @param uri
	 * @param type
	 * @param params
	 * @param requestCode
	 *            返回请求码
	 */
	public static void openByTypeForResult(Context context, String uri,
			DuoduoWebModel.OperType type, ArrayList<String> params,
			int requestCode) {
		openByType(context, uri, type, params, null, true, requestCode);
	}

	public static void openByTypeForResult(Context context, String uri,
			DuoduoWebModel.OperType type, ArrayList<String> params,
			DuoduoWebViewController controller, int requestCode) {
		openByType(context, uri, type, params, controller, true, requestCode);
	}

	/**
	 * 通过类别打开
	 * 
	 * @param context
	 * @param type
	 * @param params
	 * @param controller
	 */
	private static void openByType(Context context, String uri,
			DuoduoWebModel.OperType type, ArrayList<String> params,
			DuoduoWebViewController controller, boolean isForResult,
			int requestCode) {
		Intent intent = new Intent();
		intent.setClass(context, DuoduoWebActivity.class);
		intent.putExtra(DuoduoWebModel.EXTRA_OPER_TYPE, type);
		if (params != null) {
			intent.putStringArrayListExtra(DuoduoWebModel.EXTRA_OPER_PARAMS,
					params);
		}
		intent.putExtra(DuoduoWebModel.EXTRA_URI, uri);
		setViewControllerDate(controller, intent);
		if (isForResult) {
			((Activity) context).startActivityForResult(intent, requestCode);
		} else {
			context.startActivity(intent);
		}
	}

	private static void setViewControllerDate(
			DuoduoWebViewController controller, Intent intent) {
		if (controller != null) {
			intent.putExtra(DuoduoWebModel.EXTRA_IS_SHOW_CART,
					controller.isShowCart());
			intent.putExtra(DuoduoWebModel.EXTRA_IS_SHOW_BACK,
					controller.isShowBack());
			intent.putExtra(DuoduoWebModel.EXTRA_IS_SHOW_CLOSE,
					controller.isShowClose());
			intent.putExtra(DuoduoWebModel.EXTRA_IS_CAN_BACK,
					controller.isCanBack());
		}
	}
}
