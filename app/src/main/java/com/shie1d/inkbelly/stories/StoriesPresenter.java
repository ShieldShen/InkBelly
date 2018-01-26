package com.shie1d.inkbelly.stories;

import com.shie1d.inkbelly.net.zhihudaily.ZhihuDailyHttp;
import com.shie1d.inkbelly.net.zhihudaily.bean.LatestStoriesCollection;
import com.shie1d.moneta.Moneta;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Stories界面逻辑控制
 */

public class StoriesPresenter implements StoriesContract.IStoriesPresenter {
    private StoriesContract.IStoriesView mView;
    private Moneta mMoneta;
    private StoriesModel mModel;

    @Override
    public void init(StoriesContract.IStoriesView view) {
        this.mView = view;
        mModel = new StoriesModel();
        mMoneta = Moneta.use("Stories", Moneta.LIMIT.NO);
    }

    @Override
    public void release() {
        mMoneta.release();
        mView = null;
    }

    @Override
    public void pullDataFromServer() {
        ZhihuDailyHttp.use().getLatestStoriesCollection()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (mView != null) mView.loading(true);
                    }
                })
                .subscribe(new Consumer<LatestStoriesCollection>() {
                    @Override
                    public void accept(LatestStoriesCollection latestStoriesCollection) throws Exception {
                        mModel.setTopStories(latestStoriesCollection.top_stories);
                        mModel.appendStories(latestStoriesCollection.stories);
                        mModel.setDate(latestStoriesCollection.date);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mMoneta.e(throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (mView != null) {
                            mView.loading(false);
                            mView.refresh(mModel);
                        }
                    }
                });
    }
}
