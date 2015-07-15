package com.duoduo.duoduocampus.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @description: 字符串处理工具类
 * @company: 多多校园
 * @author yutinglong
 * @version 1.0
 * @created 2015-7-15 下午11:23:32
 * @changeRecord
 */
public class StringUtil {
    /**
     * 编码转换
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String urlEncode(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, Constants.UTF8).replaceAll("\\+", "%20").replaceAll("%7E", "~").replaceAll("\\*", "%2A");
    }
    
    /**
     * 解码转换
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String urlDecode(String str) throws UnsupportedEncodingException {
        return URLDecoder.decode(str, Constants.UTF8);
    }
    
	public static boolean isNullOrEmpty(String s) {
		return s == null || s.equals("");
	}
}
