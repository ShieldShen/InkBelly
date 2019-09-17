package com.shie1d.inkbelly.net.zhihudaily.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 最新故事简介集合
 */

public class StoriesCollection implements Serializable{
    public String date;
    public List<StoryBrief> stories;
    public List<StoryBrief> top_stories;
}
