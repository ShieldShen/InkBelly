package com.shie1d.inkbelly.welcome.splash;

import com.shie1d.inkbelly.base.mvp.BasePresenter;
import com.shie1d.inkbelly.base.mvp.BaseView;

/**
 * Splash的功能目录
 */

public interface SplashContract {
    interface ISplashView extends BaseView<ISplashPresenter> {
        void setSkipCounting(long counting);
    }

    interface ISplashPresenter extends BasePresenter<ISplashView> {

        void countingDown();

        void clickSkipSplash();

        void clickAd();
    }
}
