package com.shie1d.inkbelly.net.zhihudaily;


import com.shie1d.inkbelly.net.zhihudaily.bean.StoriesCollection;
import com.shie1d.inkbelly.net.zhihudaily.bean.StoryDetail;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 知乎日报Api
 */

public interface ZhihuDailyService {
    @GET("news/latest")
    Observable<StoriesCollection> getLatestStoriesCollection();

    @GET("news/before/{date}")
    Observable<StoriesCollection> getPastStoriesCollection(@Path("date") String date);

    @GET("news/{id}")
    Observable<StoryDetail> getStoryDetailCollection(@Path("id") long id);
}
