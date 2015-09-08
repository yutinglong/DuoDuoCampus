package com.duoduo.duoduocampus.utils;

import java.net.URI;
import java.net.URISyntaxException;

import android.content.Context;
import android.text.TextUtils;

import com.duoduo.duoduocampus.DuoDuoPrefences;
public class MLSUriUtils {
    /**
     * 为url添加access_token
     * @param url
     * @return
     */
    public static String addAccessToken(Context context,String url) {
        if (!TextUtils.isEmpty(url)) {
            try {
                URI uri = new URI(url);
                String scheme = uri.getScheme();
                String userInfo = uri.getUserInfo();
                String host = uri.getHost();
                int port = uri.getPort();
                String path = uri.getPath();
                String query = uri.getQuery();
                String fragment = uri.getFragment();

                if (host != null && host.endsWith(".meilishuo.com")) {
//                    if (!TextUtils.isEmpty(DuoDuoPrefences
//                            .getAccesstoken(context))) {
//                        if (TextUtils.isEmpty(query)) {
//                            query = "access_token="
//                                    + DuoDuoPrefences.getAccesstoken(context);
//                        } else if (!query.contains("access_token=")) {
//                            query = query + "&access_token="
//                                    + DuoDuoPrefences.getAccesstoken(context);
//                        }
//                    }
                    url = new URI(scheme, userInfo, host, port, path, query,
                            fragment).toString();
                }

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return url;
    }


    /**
     * 为链接添加R参数
     * @param sourceString
     * @return
     */
    public static String addR(String sourceString,String r) {
        StringBuffer handleStringBuffer = new StringBuffer();
        if (sourceString.indexOf("?") > 0 && sourceString.indexOf("r=") == -1) {
            addRAnchor(sourceString, handleStringBuffer, "&r=",r);
        } else if (sourceString.indexOf("?") == -1 && sourceString.indexOf("r=") == -1) {
            addRAnchor(sourceString, handleStringBuffer, "?r=",r);
        } else {
            handleStringBuffer.append(sourceString);
        }
        return handleStringBuffer.toString();
    }

    private static void addRAnchor(String sourceString, StringBuffer handleStringBuffer, String tempParams,String r) {
        if (sourceString.indexOf("#") > 0) {
            String[] anchorIndex = sourceString.split("#");
            if (anchorIndex.length > 0) {
                anchorIndex[0] += tempParams + r;
            }
            for (int i = 0; i < anchorIndex.length; i++) {
                if (i < anchorIndex.length - 1) {
                    handleStringBuffer.append(anchorIndex[i]);
                    handleStringBuffer.append("#");
                } else {
                    handleStringBuffer.append(anchorIndex[i]);
                }
            }
        } else {
            handleStringBuffer.append(sourceString);
            handleStringBuffer.append(tempParams);
            handleStringBuffer.append(r);
        }
    }

}
