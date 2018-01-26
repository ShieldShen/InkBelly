package com.shie1d.inkbelly.net.zhihudaily;

import com.shie1d.inkbelly.BuildConfig;
import com.shie1d.inkbelly.net.BaseServiceProxy;
import com.shie1d.inkbelly.net.zhihudaily.bean.LatestStoriesCollection;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;

/**
 * 知乎服务器接口实现
 */

public class ZhihuDailyHttp extends BaseServiceProxy<ZhihuDailyService> {
    private final ZhihuDailyService mService;

    private ZhihuDailyHttp(String url) {
        OkHttpClient.Builder builder = getDefaultOkHttpBuilder();
        mService = buildService(builder.build(), url, ZhihuDailyService.class);
    }

    private interface Holder {
        ZhihuDailyHttp mHttp = new ZhihuDailyHttp(BuildConfig.API_HOST);
    }

    public static ZhihuDailyHttp use() {
        return Holder.mHttp;
    }

    public Observable<LatestStoriesCollection> getLatestStoriesCollection() {
        return mService.getLatestStoriesCollection();
    }
}
