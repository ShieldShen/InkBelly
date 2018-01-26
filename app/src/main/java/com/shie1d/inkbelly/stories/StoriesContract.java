package com.shie1d.inkbelly.stories;

import com.shie1d.inkbelly.base.mvp.BasePresenter;
import com.shie1d.inkbelly.base.mvp.BaseView;

/**
 * 故事列表页功能目录
 */

public interface StoriesContract {
    interface IStoriesPresenter extends BasePresenter<IStoriesView> {

        void pullDataFromServer();
    }

    interface IStoriesView extends BaseView<IStoriesPresenter> {

        void refresh(StoriesModel model);

        void loading(boolean isLoading);
    }
}
