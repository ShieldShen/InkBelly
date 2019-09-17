package com.shie1d.inkbelly.net.zhihudaily.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 故事简介
 */

public class StoryBrief implements Serializable{
    public interface TYPE {
        int STORY_BRIEF = 0;
        int DATE = 1;
    }

    public int viewType = TYPE.STORY_BRIEF;
    public String date;
    public List<String> images;
    public String image;
    public int type;
    public long id;
    public String ga_prefix;
    public String title;

    public StoryBrief(String date) {
        this.date = date;
        this.viewType = TYPE.DATE;
    }
}
