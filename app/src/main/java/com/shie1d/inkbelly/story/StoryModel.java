package com.shie1d.inkbelly.story;

import com.shie1d.inkbelly.base.mvp.BaseModel;
import com.shie1d.inkbelly.net.zhihudaily.bean.StoryBrief;
import com.shie1d.inkbelly.stories.StoriesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 故事界面Model
 */

public class StoryModel implements BaseModel {

    private List<StoryBrief> mAllStoriesBrief;
    private StoryBrief mCurStory;
    private int mCurPosition;

    StoryModel(StoriesModel storiesModel, StoryBrief curStory) {
        this.mAllStoriesBrief = new ArrayList<>();
        List<StoryBrief> topStories = storiesModel.getTopStories();
        int positionInAllStories = -1;
        boolean findPosition = false;
        for (int curPosition = 0; curPosition < topStories.size(); curPosition++) {
            StoryBrief storyBrief = topStories.get(curPosition);
            if (!findPosition) positionInAllStories++;
            if (curStory.id == storyBrief.id) {
                findPosition = true;
            }
            mAllStoriesBrief.add(storyBrief);
        }

        List<StoryBrief> stories = storiesModel.getStories();
        for (int position = 0; position < stories.size(); position++) {
            StoryBrief storyBrief = stories.get(position);
            if (storyBrief.type != StoryBrief.TYPE.DATE) {
                if (!findPosition) positionInAllStories++;
                if (curStory.id == storyBrief.id) {
                    findPosition = true;
                }
                mAllStoriesBrief.add(storyBrief);
            }
        }

        mCurPosition = positionInAllStories;
        mCurStory = mAllStoriesBrief.get(mCurPosition);
    }


    public List<StoryBrief> getAllStoriesBrief() {
        return mAllStoriesBrief;
    }

    public StoryBrief getCurStory() {
        return mCurStory;
    }

    public int getCurPosition() {
        return mCurPosition;
    }

}
