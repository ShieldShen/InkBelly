package com.shie1d.inkbelly.story;

import android.os.Bundle;

import com.shie1d.inkbelly.base.mvp.BasePresenter;
import com.shie1d.inkbelly.base.mvp.BaseView;

/**
 * 故事详情承载界面功能目录
 */

public interface StoryContract {
    interface IStoryPresenter extends BasePresenter<IStoryView> {

        void setData(Bundle arguments);

    }

    interface IStoryView extends BaseView<IStoryPresenter> {

        void refresh(StoryModel model);

        void loading(boolean isLoading);

        void showErrorPage();

        void switchToCurPage(StoryModel model);
    }

}
