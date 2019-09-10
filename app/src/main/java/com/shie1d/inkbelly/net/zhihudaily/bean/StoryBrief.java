package com.shie1d.inkbelly.net.zhihudaily.bean;

import com.shie1d.inkbelly.stories.StoriesAdapter;

import java.util.List;

/**
 * 故事简介
 */

public class StoryBrief {
    public int viewType = StoriesAdapter.ViewType.STORY_BRIEF;
    public String date;
    public List<String> images;
    public String image;
    public int type;
    public long id;
    public String ga_prefix;
    public String title;

    public StoryBrief(String date) {
        this.date = date;
        this.viewType = StoriesAdapter.ViewType.DATE;
    }
}
