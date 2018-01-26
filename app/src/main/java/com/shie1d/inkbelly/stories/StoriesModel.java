package com.shie1d.inkbelly.stories;

import com.shie1d.inkbelly.base.mvp.BaseModel;
import com.shie1d.inkbelly.net.zhihudaily.bean.StoryBrief;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Stories界面的数据模型
 */

public class StoriesModel implements BaseModel {
    private List<StoryBrief> mTopStories;
    private ArrayList<StoryBrief> mStories;
    private final SimpleDateFormat mDateParser;
    private final SimpleDateFormat mFormatter;
    private String mRawDate;
    private String mBakeDate;
    private long mDateTimeMillies;

    StoriesModel() {
        mDateParser = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        mFormatter = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
    }

    void setTopStories(List<StoryBrief> topStories) {
        this.mTopStories = topStories;
    }

    void appendStories(List<StoryBrief> stories) {
        if (mStories == null) {
            mStories = new ArrayList<>();
        }
        mStories.addAll(stories);
    }

    void setDate(String date) {
        mRawDate = date;
        try {
            Date parse = mDateParser.parse(date);
            mDateTimeMillies = parse.getTime();
            mBakeDate = mFormatter.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public long getDateMillis() {
        return mDateTimeMillies;
    }

    public ArrayList<StoryBrief> getStories() {
        return mStories;
    }

    public List<StoryBrief> getTopStories() {
        return mTopStories;
    }

    public String getBakeDate() {
        return mBakeDate;
    }

    @Override
    public String toString() {
        return "StoriesModel{" +
                "mTopStories=" + mTopStories +
                ", mStories=" + mStories +
                ", mDateParser=" + mDateParser +
                ", mFormatter=" + mFormatter +
                ", mRawDate='" + mRawDate + '\'' +
                ", mBakeDate='" + mBakeDate + '\'' +
                '}';
    }
}
