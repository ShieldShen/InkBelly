package com.shie1d.inkbelly.detail;

import android.os.Bundle;

import com.shie1d.inkbelly.base.mvp.BasePresenter;
import com.shie1d.inkbelly.base.mvp.BaseView;

/**
 * 详情页目录
 */

public interface StoryDetailContract {
    interface IStoryDetailPresenter extends BasePresenter<IStoryDetailView> {

        void pullCurrentStoryDetail();

        void setBrief(Bundle arguments);
    }

    interface IStoryDetailView extends BaseView<IStoryDetailPresenter> {

        void showErrorPage();

        void showContent(StoryDetailModel model);
    }
}
