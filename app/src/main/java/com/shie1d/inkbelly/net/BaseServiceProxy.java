package com.shie1d.inkbelly.net;

import com.shie1d.inkbelly.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseServiceProxy<T> {
    interface Config {
        boolean DEBUG = BuildConfig.DEBUG;
        long CONNECT_TIMEOUT = 30;
        long READ_TIMEOUT = 30;
    }

    @android.support.annotation.NonNull
    protected OkHttpClient.Builder getDefaultOkHttpBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(Config.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Config.READ_TIMEOUT, TimeUnit.SECONDS);
        if (Config.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging); //可以查看log
        }
        return builder;
    }

    protected T buildService(OkHttpClient okHttp, String baseUrl, Class<T> service) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //retrofit原生只支持RxJava1 使用RxJava2需要这个
                .client(okHttp)
                .build()
                .create(service);
    }
}
