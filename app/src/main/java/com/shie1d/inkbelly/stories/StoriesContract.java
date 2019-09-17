package com.shie1d.inkbelly.stories;

import com.shie1d.inkbelly.base.mvp.BasePresenter;
import com.shie1d.inkbelly.base.mvp.BaseView;
import com.shie1d.inkbelly.net.zhihudaily.bean.StoryBrief;

/**
 * 故事列表页功能目录
 */

public interface StoriesContract {
    interface IStoriesPresenter extends BasePresenter<IStoriesView> {

        void pullLatestStoriesFromServer();

        void pullPastStoriesFromServer();

        boolean shouldAutoPullData(int position);

        void enterStory(StoryBrief clickedItem);
    }

    interface IStoriesView extends BaseView<IStoriesPresenter> {

        void refresh(StoriesModel model);

        void loading(boolean isLoading);
    }
}
