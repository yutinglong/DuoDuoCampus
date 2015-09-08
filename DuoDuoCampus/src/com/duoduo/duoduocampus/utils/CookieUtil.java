package com.duoduo.duoduocampus.utils;

import android.content.Context;

import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;


public class CookieUtil {

	private static String[] main_domains = new String[] { ".meilishuo.com" };

	private static String[] midian_domains = new String[] { ".midianapp.com",
			".mi-dian.com" };

	public static void addCookie(CookieManager cookieManager, String url,
			String name, String value, String[] domains) {
		for (String domain : domains) {
			String cookieStr = name + "=" + value + "; domain=" + domain + "; path=/";
//			Debug.trace("CookieUtil", "cookie : " + cookieStr);
			cookieManager.setCookie(url, cookieStr);
		}
	}

	public static void setCookie(String url, Context context) {

		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();

		cookieManager.setAcceptCookie(true);
		addCookie(cookieManager, url, "app_name", "Meilishuo", main_domains);
//		addCookie(cookieManager, url, "app_version",
//				MLSAppInfo.getVersionName(), main_domains);
//		if (!TextUtils.isEmpty(MeilishuoPrefences.getAccesstoken(context))) {
//			addCookie(cookieManager, url, "app_access_token",
//					MeilishuoPrefences.getAccesstoken(context), main_domains);
//		}
//		if (!TextUtils.isEmpty(MeilishuoPrefences.getDeviceID(context))) {
//			addCookie(cookieManager, url, "app_device_id",
//					MeilishuoPrefences.getDeviceID(context), main_domains);
//		}
//		addCookie(cookieManager, url, "app_client_id",
//				MLSAppInfo.getClientCode(), main_domains);
//		addCookie(cookieManager, url, "mlsApps", "android_"
//				+ MLSAppInfo.getVersionName(), main_domains);
//		if (MeilishuoPrefences.isLogin(context)
//				&& !TextUtils.isEmpty(MeilishuoPrefences.getUserID(context))) {
//			long user_id = Long.valueOf(MeilishuoPrefences.getUserID(context));
//			String app_meilishuo_rz = String.valueOf(user_id * 3 + 7);
//			addCookie(cookieManager, url, "app_meilishuo_rz ",
//					app_meilishuo_rz, main_domains);
//		}
//		addCookie(cookieManager, url, "app_imei", MLSDeviceInfo.imei,
//				main_domains);
//		addCookie(cookieManager, url, "app_mac", MLSDeviceInfo.mac,
//				main_domains);
//
//		addCookie(cookieManager, url, "app_version",
//				MLSAppInfo.getVersionName(), midian_domains);
//		addCookie(cookieManager, url, "mls_logged_in",
//				MeilishuoPrefences.isLogin(context) ? "1" : "0", midian_domains);
//		addCookie(cookieManager, url, "mls_in_app", "1", midian_domains);
//		addCookie(cookieManager, url, "mls_open_id",
//                AccountOpenIdService.getInstance().midian_openid,
//				midian_domains);
//		if(MeilishuoPrefences.getContactsAuthority(context)==ContactsUtil.CONTACTS_AUTHORITY_TRUE){
//			addCookie(cookieManager, url, "app_contacts_authorization_status", "authorized",
//					main_domains);
//		}else if(MeilishuoPrefences.getContactsAuthority(context)==ContactsUtil.CONTACTS_AUTHORITY_FALSE){
//			addCookie(cookieManager, url, "app_contacts_authorization_status", "denied",
//					main_domains);
//		}

		CookieSyncManager.getInstance().sync();

	}

	public static void cleanAllCookies(Context context) {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
		CookieSyncManager.getInstance().sync();
	}
}
