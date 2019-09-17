package com.shie1d.inkbelly.stories;

import com.shie1d.inkbelly.net.zhihudaily.ZhihuDailyHttp;
import com.shie1d.inkbelly.net.zhihudaily.bean.StoriesCollection;
import com.shie1d.inkbelly.net.zhihudaily.bean.StoryBrief;
import com.shie1d.inkbelly.story.StoryActivity;
import com.shie1d.moneta.Moneta;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Stories界面逻辑控制
 */

public class StoriesPresenter implements StoriesContract.IStoriesPresenter {
    private final static int AUTO_PULL_THRESHOLD = 3;
    private StoriesContract.IStoriesView mView;
    private Moneta mMoneta;
    private StoriesModel mModel;
    private Map<String, Boolean> mLoadingFlag;

    @Override
    public void init(StoriesContract.IStoriesView view) {
        this.mView = view;
        mModel = new StoriesModel();
        mMoneta = Moneta.use("Stories", Moneta.LIMIT.NO);
        mLoadingFlag = new HashMap<>();
    }

    @Override
    public void release() {
        mMoneta.release();
        mView = null;
    }

    @Override
    public boolean shouldAutoPullData(int position) {
        return mModel.getLastDate() != null && mModel.getStories() != null && mModel.getStories().size() - position < AUTO_PULL_THRESHOLD;
    }

    @Override
    public void enterStory(StoryBrief clickedItem) {
        StoryActivity.start(mView.getLogicContext(), mModel, clickedItem);
    }

    @Override
    public void pullPastStoriesFromServer() {
        generateRefreshChain(ZhihuDailyHttp.use().getPastStoriesCollection(mModel.getLastDate()), mModel.getLastDate());
    }

    @Override
    public void pullLatestStoriesFromServer() {
        generateRefreshChain(ZhihuDailyHttp.use().getLatestStoriesCollection(), mModel.getLastDate());

    }

    private void generateRefreshChain(Observable<StoriesCollection> observable, final String loadingDate) {
        if (mLoadingFlag.get(loadingDate) != null && mLoadingFlag.get(loadingDate)) return;
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (mView != null) mView.loading(true);
                        mLoadingFlag.put(loadingDate, true);
                    }
                })
                .doOnNext(new Consumer<StoriesCollection>() {
                    @Override
                    public void accept(StoriesCollection storiesCollection) throws Exception {
                        Moneta.use("load_tag").e("Data retrieved : " + storiesCollection.date);
                        mModel.appendLatestStories(storiesCollection);
                    }
                })
                .subscribe(new Consumer<StoriesCollection>() {
                    @Override
                    public void accept(StoriesCollection storiesCollection) throws Exception {
                        if (mView != null) {
                            mView.loading(false);
                            mView.refresh(mModel);
                            mLoadingFlag.put(loadingDate, false);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mMoneta.e(throwable.getMessage());
                    }
                });
    }
}
