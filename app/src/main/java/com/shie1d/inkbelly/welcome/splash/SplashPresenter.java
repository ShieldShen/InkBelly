package com.shie1d.inkbelly.welcome.splash;

import com.shie1d.inkbelly.welcome.pre.PreStartActivity;
import com.shie1d.moneta.Moneta;
import com.shie1d.ophion.Ophion;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Splash展示页逻辑单元
 */

public class SplashPresenter implements SplashContract.ISplashPresenter {
    SplashContract.ISplashView mView;
    private Moneta mMoneta;
    private Disposable mCountingDownJob;

    @Override
    public void init(SplashContract.ISplashView view) {
        mView = view;
        mMoneta = Moneta.use("Splash", Moneta.LIMIT.NO);
    }

    @Override
    public void refresh(SplashModel model) {

    }

    @Override
    public void release() {
        mMoneta.release();
        mView = null;
    }

    @Override
    public void countingDown() {
        final int COUNT = getCountNum();
        mCountingDownJob = Observable.intervalRange(0, COUNT, 0, 1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long num) throws Exception {
                        mView.setSkipCounting(COUNT - num);
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        endOfSplash(EndMode.NORMAL);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        endOfSplash(EndMode.ERROR);
                    }
                })
                .subscribe();
    }

    @Override
    public void clickSkipSplash() {
        if (mCountingDownJob != null && !mCountingDownJob.isDisposed()) {
            mCountingDownJob.dispose();
        }
        endOfSplash(EndMode.FORCE);
    }

    @Override
    public void clickAd() {
        Ophion.sT(mView.getLogicContext(), "点击广告");
    }

    private int getCountNum() {
        return 3;
    }

    /**
     * @param endMode 0表示正常结束, 1表示倒计时是出错结束, 2表示用户跳过
     */
    private void endOfSplash(int endMode) {
        mMoneta.d("endOfSplash Code : " + endMode);
        PreStartActivity.start(mView.getLogicContext());
        mView.viewFinish();
    }

    private interface EndMode {
        int NORMAL = 0;
        int ERROR = 1;
        int FORCE = 2;
    }
}
