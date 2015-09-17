package com.duoduo.duoduocampus.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.text.TextUtils;

import com.duoduo.duoduocampus.BaseApplication;

/**
 * @author tinglongyu 本地测试数据工具类 本地接口文件存放位置：assets 本地接口文件命名规则url.replace("/", "_")
 */
public class LocalApiUtil {

	private final static String TAG = "LocalApiUtil";
	private final static String DIR_NAME = "api";

	private static String[] localAPI = null;

	public static void initLocalApi() {
		try {
			localAPI = BaseApplication.getInstance().getAssets().list(DIR_NAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean hasLocalApi(String api) {
		if (localAPI != null && !TextUtils.isEmpty(api)) {
			for (String local : localAPI) {
				if (api.equals(local.replaceFirst("_", "/"))) {
					return true;
				}
			}
		}
		return false;
	}

	public static String getLocalResponse(String api) {
		String result = null;
		if (hasLocalApi(api)) {
			result = readLocalFile(DIR_NAME + "/" + api.replace("/", "_"));
		}
		return result;
	}

	private static String readLocalFile(String path) {
		InputStream inputStream = null;
		InputStreamReader inputReader = null;
		BufferedReader bufferReader = null;
		StringBuilder sb = new StringBuilder();

		try {
			inputStream = BaseApplication.getInstance().getAssets()
					.open(path);
			inputReader = new InputStreamReader(inputStream);
			bufferReader = new BufferedReader(inputReader);
			String line = null;
			while ((line = bufferReader.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputReader != null) {
				try {
					inputReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bufferReader != null) {
				try {
					bufferReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}
}
