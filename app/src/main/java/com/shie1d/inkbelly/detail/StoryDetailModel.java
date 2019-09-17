package com.shie1d.inkbelly.detail;

import com.shie1d.inkbelly.base.mvp.BaseModel;
import com.shie1d.inkbelly.net.zhihudaily.bean.StoryBrief;
import com.shie1d.inkbelly.net.zhihudaily.bean.StoryDetail;

/**
 * 详情页数据
 */

class StoryDetailModel implements BaseModel {
    private StoryBrief mStoryBrief;

    private StoryDetail mStoryDetail;

    void setStoryBrief(StoryBrief storyBrief) {
        mStoryBrief = storyBrief;
    }

    void setStoryDetail(StoryDetail storyDetail) {
        mStoryDetail = storyDetail;
    }

    public StoryBrief getStoryBrief() {
        return mStoryBrief;
    }

    public StoryDetail getStoryDetail() {
        return mStoryDetail;
    }
}
