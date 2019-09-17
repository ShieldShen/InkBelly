package com.shie1d.inkbelly.net.zhihudaily.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 故事详情
 */

public class StoryDetail implements Serializable{

    public interface TYPE {
        int ORIGINAL_SOURCE = 1;
        int OTHER_SOURCE = 0;
    }

    private String body;
    private String theme_name;
    private String editor_name;
    private String theme_id;
    private String imageHue;
    private String imageSource;
    private String title;
    private String url;
    private String image;
    private String shareUrl;
    private List<Object> js = new ArrayList<Object>();
    private String gaPrefix;
    private List<String> images = new ArrayList<String>();
    private Integer type;
    private Integer id;
    private List<String> css = new ArrayList<String>();

    @Override
    public String toString() {
        return "StoryDetail{" +
                "body='" + body + '\'' +
                ", theme_name='" + theme_name + '\'' +
                ", editor_name='" + editor_name + '\'' +
                ", theme_id='" + theme_id + '\'' +
                ", imageHue='" + imageHue + '\'' +
                ", imageSource='" + imageSource + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", js=" + js +
                ", gaPrefix='" + gaPrefix + '\'' +
                ", images=" + images +
                ", type=" + type +
                ", id=" + id +
                ", css=" + css +
                '}';
    }
}
