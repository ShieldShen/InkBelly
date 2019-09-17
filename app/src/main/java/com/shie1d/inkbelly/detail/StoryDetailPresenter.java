package com.shie1d.inkbelly.detail;

import android.os.Bundle;

import com.shie1d.inkbelly.net.zhihudaily.ZhihuDailyHttp;
import com.shie1d.inkbelly.net.zhihudaily.bean.StoryBrief;
import com.shie1d.inkbelly.net.zhihudaily.bean.StoryDetail;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 详情页逻辑
 */

public class StoryDetailPresenter implements StoryDetailContract.IStoryDetailPresenter {
    private StoryDetailContract.IStoryDetailView mView;
    private StoryDetailModel mModel;

    @Override
    public void init(StoryDetailContract.IStoryDetailView view) {
        mView = view;
    }

    @Override
    public void release() {
        mView = null;
    }

    @Override
    public void pullCurrentStoryDetail() {
        ZhihuDailyHttp.use().getStoryDetailCollection(mModel.getStoryBrief().id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//                        if (mView != null) mView.loading(true);
//                        mLoadingFlag.put(loadingDate, true);
//                    }
//                })
//                .doOnNext(new Consumer<StoriesCollection>() {
//                    @Override
//                    public void accept(StoriesCollection storiesCollection) throws Exception {
//                        Moneta.use("load_tag").e("Data retrieved : " + storiesCollection.date);
//                        mModel.appendLatestStories(storiesCollection);
//                    }
//                })
                .subscribe(new io.reactivex.functions.Consumer<StoryDetail>() {
                    @Override
                    public void accept(StoryDetail storyDetail) throws Exception {
                        mModel.setStoryDetail(storyDetail);
                        mView.showContent(mModel);
                    }
                });
    }

    @Override
    public void setBrief(Bundle arguments) {
        if (arguments == null) {
            if (mModel == null) {
                mView.showErrorPage();
            } else {
                mView.showContent(mModel);
            }
        } else {
            StoryBrief storyBrief = (StoryBrief) arguments.getSerializable(StoryDetailFragment.ARG_BRIEF);
            mModel = new StoryDetailModel();
            mModel.setStoryBrief(storyBrief);
        }
    }
}
