package com.shie1d.inkbelly.story;

import android.os.Bundle;

import com.shie1d.inkbelly.net.zhihudaily.bean.StoryBrief;
import com.shie1d.inkbelly.stories.StoriesModel;

/**
 * Created by shie1 on 2019/9/11.shie1 on
 */

public class StoryPresenter implements StoryContract.IStoryPresenter {
    private StoryContract.IStoryView mView;
    private StoryModel mModel;

    @Override
    public void init(StoryContract.IStoryView view) {
        mView = view;

    }

    @Override
    public void release() {

    }

    @Override
    public void setData(Bundle arguments) {
        if (arguments == null) {
            if (mModel == null) {
                mView.showErrorPage();
            } else {
                mView.switchToCurPage(mModel);
            }
            return;
        }
        StoriesModel stories = (StoriesModel) arguments.getSerializable(StoryActivity.ARG_STORY);
        StoryBrief curStory = (StoryBrief) arguments.getSerializable(StoryActivity.ARG_CLICK_ITEM);
        mModel = new StoryModel(stories, curStory);
        mView.switchToCurPage(mModel);
    }
}
