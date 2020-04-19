package com.mrrun.lib.mvpbase.network.retrofit;

import android.content.Context;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.Converter;

/**
 * <br>类描述:Retrofit生成工具类
 * <br>功能详细描述:
 *
 */
public class RetrofitUtil {

    public static <T> T createApi(Context context, Class<T> clazz, String URL, Converter converter) {

        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(URL);
        builder.setConverter(converter);
        builder.setClient(new OkClient(OkHttpUtils.getInstance(context)));
        builder.setLogLevel(RestAdapter.LogLevel.NONE);
        return builder.build().create(clazz);
    }
}
