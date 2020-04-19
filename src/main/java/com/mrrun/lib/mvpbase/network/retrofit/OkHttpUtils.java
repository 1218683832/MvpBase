package com.mrrun.lib.mvpbase.network.retrofit;

import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * OkHttpClient自定义工具类
 */
public class OkHttpUtils {

	private static OkHttpClient singleton;

	public static final int CONNECT_TIMEOUT_MILLIS = 15 * 1000; // 15s

	public static final int READ_TIMEOUT_MILLIS = 20 * 1000; // 20s

	public static OkHttpClient getInstance(Context context) {
		if (singleton == null) {
			synchronized (OkHttpUtils.class) {
				if (singleton == null) {
					singleton = new OkHttpClient();
					if (context != null) {
						try {
							File cacheDir = new File(context.getCacheDir(), UUID.randomUUID().toString());
							Cache cache = new Cache(cacheDir, 1024);
							singleton.setCache(cache);
						} catch (Exception e) {
						}
					}
					singleton.setConnectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
					singleton.setReadTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
				}
			}
		}
		return singleton;
	}
}
