package com.duoduo.duoduocampus.web;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.tencent.smtt.sdk.WebView;


public class DuoduoWebHandler {

    /**
     * 自定义协议过滤函数
     * @param context
     * @param uri
     * @param webModel
     * @return
     */
    public static boolean protocolFilter(Activity context,String uri,DuoduoWebModel webModel){
//
//        if(TextUtils.isEmpty(uri)){
//            return false;
//        }
//
//        Iterator iterator = MLSWebConstants.mProtocolLists.entrySet().iterator();
//        while (iterator.hasNext()){
//            Map.Entry<String,String> entry = (Map.Entry) iterator.next();
//            String key = entry.getKey();
//            String value = entry.getValue();
//            if(uri.startsWith(key)){
//                try {
//                    Class filterClass = Class.forName(value);
//                    Constructor constructor = filterClass.getConstructor();
//                    MLSWebFilter webFilter = (MLSWebFilter) constructor.newInstance();
//                    webFilter.handleFilter(context, uri,webModel);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return true;
//            }
//        }
//
//        if (!(uri.startsWith("http:") || uri.startsWith("https:"))) {
//            MLSWebFilter webFilter = new MLSWebProtocolFilterHttp();
//            webFilter.handleFilter(context, uri,webModel);
//            return true;
//        }


        return false;
    }


    /**
     * 自定义结果过滤函数
     * @param context
     * @param uri
     * @param webModel
     * @return
     */
    public static boolean resultFilter(Activity context,String uri,DuoduoWebModel webModel){
        if(TextUtils.isEmpty(uri)){
            return false;
        }
//        Iterator iterator = MLSWebConstants.mResultLists.entrySet().iterator();
//        while (iterator.hasNext()){
//            Map.Entry<String,String> entry = (Map.Entry) iterator.next();
//            String key = entry.getKey();
//            String value = entry.getValue();
//            if(uri.contains(key)) {
//                try {
//                    Class filterClass = Class.forName(value);
//                    Constructor constructor = filterClass.getConstructor();
//                    MLSWebFilter webFilter = (MLSWebFilter) constructor.newInstance();
//                    webFilter.handleFilter(context, uri,webModel);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return true;
//            }
//
//        }

        return false;
    }


    /**
     * web页面js回调过滤
     * @param mWebView
     * @param type
     * @param data
     * @return
     */
    public static void javaScriptFilter(Activity context,WebView mWebView,String type,Intent data,DuoduoWebModel webModel){
        if(mWebView == null||TextUtils.isEmpty(type)){
            return;
        }

//        try {
//            String jsFilterClassName = MLSWebConstants.mJsFilterLists.get(type.toLowerCase());
//            if(!TextUtils.isEmpty(jsFilterClassName)){
//                Class jsFilterClass = Class.forName(jsFilterClassName);
//                Constructor constructor = jsFilterClass.getConstructor();
//                MLSWebJsFilter jsFilter = (MLSWebJsFilter) constructor.newInstance();
//                jsFilter.handleFilter(context,mWebView, data, webModel);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 工厂方法进行web页面事件过滤
     * @param context
     * @param webModel
     */

    public static void closeFilter(Activity context,DuoduoWebModel webModel){
//        MLSWebCloseFilter closeFilter;
//        switch (webModel.getOperType()){
//            case MLS_PAY:
//                closeFilter = new MLSWebCloseFilterPay();
//                break;
//            default:
//                closeFilter = new MLSWebCloseFilterDefault();
//                break;
//        }
//        closeFilter.close(context,webModel);
    	
    	Intent i = new Intent();
        i.putExtra("options", "canceled");
        context.setResult(Activity.RESULT_OK, i);
        context.finish();
    }
}
