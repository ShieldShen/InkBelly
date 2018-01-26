package com.shie1d.inkbelly.net.zhihudaily;


import com.shie1d.inkbelly.net.zhihudaily.bean.LatestStoriesCollection;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 知乎日报Api
 */

public interface ZhihuDailyService {
    @GET("news/latest")
    Observable<LatestStoriesCollection> getLatestStoriesCollection();
}
