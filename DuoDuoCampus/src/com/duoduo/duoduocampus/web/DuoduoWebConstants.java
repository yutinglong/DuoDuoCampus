package com.duoduo.duoduocampus.web;

import java.util.HashMap;

public class DuoduoWebConstants {
    public static HashMap<String,String> mProtocolLists = new HashMap<String, String>();
    public static HashMap<String,String> mResultLists = new HashMap<String, String>();//web结果链接处理

    public static HashMap<String,String> mJsFilterLists = new HashMap<String, String>();//js回调处理器


    public static String WEB_JS_CALLBACK_QQ_RESULT = "qq_result";
    public static String WEB_JS_CALLBACK_WECHAT_SHARE_SUCCESS = "weixin_share_success";
    public static String WEB_JS_CALLBACK_SINA_SHARE_SUCCESS = "sinaweibo_share_success";
    public static String WEB_JS_CALLBACK_QQ_SHARE_SUCCESS = "qq_share_success";
    public static String WEB_JS_CALLBACK_QZONE_SHARE_SUCCESS = "qqzone_share_success";
    public static String WEB_JS_CALLBACK_TENCENT_SHARE_SUCCESS = "txweibo_share_success";

    public static String WEB_JS_CALLBACK_QQ_SHARE_FAIL = "qq_share_fail";
    public static String WEB_JS_CALLBACK_QZONE_SHARE_FAIL = "qqzone_share_fail";
    public static String WEB_JS_CALLBACK_WECHAT_SHARE_FAIL = "weixin_share_fail";
    public static String WEB_JS_CALLBACK_SINA_SHARE_FAIL = "sinaweibo_share_fail";
    public static String WEB_JS_CALLBACK_TENCENT_SHARE_FAIL = "txweibo_share_fail";

    public static String WEB_JS_CALLBACK_WECHAT_SHARE_CANCEL = "weixin_share_cancel";
    public static String WEB_JS_CALLBACK_QQ_SHARE_CANCEL = "qq_share_cancel";
    public static String WEB_JS_CALLBACK_QZONE_SHARE_CANCEL = "qqzone_share_cancel";


    public static String WEB_JS_CALLBACK_SIGN_IN = "sign_in";
    public static String WEB_JS_CALLBACK_PICK_ADDRESS = "pick_address";
    public static String WEB_JS_CALLBACK_ADD_ADDRESS = "add_address";
    public static String WEB_JS_CALLBACK_UPDATE_ADDRESS = "update_address";



    static {
//        mProtocolLists.put("sms:",MLSWebProtocolFilterSMS.class.getName());
    }
}
