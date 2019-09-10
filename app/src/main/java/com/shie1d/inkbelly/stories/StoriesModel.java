package com.shie1d.inkbelly.stories;

import com.shie1d.inkbelly.base.mvp.BaseModel;
import com.shie1d.inkbelly.net.zhihudaily.bean.StoriesCollection;
import com.shie1d.inkbelly.net.zhihudaily.bean.StoryBrief;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Stories界面的数据模型
 */

public class StoriesModel implements BaseModel {
    private static final long DAY_MILLIS = 1000 * 60 * 60 * 24;
    private List<StoriesCollection> mStoriesCollections;
    private List<StoryBrief> mTopStories;
    private List<StoryBrief> mStories;
    private final SimpleDateFormat mDateParser;
    private final SimpleDateFormat mFormatter;

    StoriesModel() {
        mDateParser = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        mFormatter = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
        mStories = new ArrayList<>();
        mTopStories = new ArrayList<>();
    }

    void appendLatestStories(StoriesCollection storiesCollection) {
        if (mStoriesCollections == null) {
            mStoriesCollections = new ArrayList<>();
        }
        if (storiesCollection == null || storiesCollection.stories.size() <= 0)
            return;

        if (mStoriesCollections.size() <= 0) {
            mStoriesCollections.add(storiesCollection);
            mTopStories = storiesCollection.top_stories;
            onStoriesChanged();
            return;
        }

        long timeMillis = parseDate2TimeMillis(storiesCollection.date);

        StoriesCollection latestStoriesCollection = mStoriesCollections.get(0);

        long curLatestTimeMillis = parseDate2TimeMillis(latestStoriesCollection.date);

        if (timeMillis > curLatestTimeMillis) {
            if (DAY_MILLIS < timeMillis - curLatestTimeMillis) {
                mStoriesCollections.clear();
            }
            mStoriesCollections.add(0, storiesCollection);
            mTopStories = storiesCollection.top_stories;
            onStoriesChanged();
            return;
        }

        if (curLatestTimeMillis == timeMillis) {
            List<StoryBrief> curStories = latestStoriesCollection.stories;
            if (curStories == null || curStories.size() >= storiesCollection.stories.size())
                return;

            mStoriesCollections.remove(0);
            mStoriesCollections.add(0, storiesCollection);
            mTopStories = storiesCollection.top_stories;
            onStoriesChanged();
            return;
        }

        int insertPosition = mStoriesCollections.size() - 1;

        for (int i = insertPosition; i >= 0; i--) {
            long curTimeMillis = parseDate2TimeMillis(mStoriesCollections.get(i).date);
            if (curTimeMillis > timeMillis) {
                mStoriesCollections.add(i + 1, storiesCollection);
                onStoriesChanged();
                break;
            }
        }

    }

    private void onStoriesChanged() {
        mStories.clear();
        for (StoriesCollection storiesCollection : mStoriesCollections) {
            mStories.add(new StoryBrief(getBakedDate(storiesCollection.date)));
            if (storiesCollection.stories != null) {
                mStories.addAll(storiesCollection.stories);
            }
        }
    }

    private String getBakedDate(String rawDate) {
        try {
            return mFormatter.format(mDateParser.parse(rawDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private long parseDate2TimeMillis(String date) {
        try {
            return mDateParser.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<StoryBrief> getStories() {
        return mStories;
    }

    public List<StoryBrief> getTopStories() {
        return mTopStories;
    }

    String getLastDate() {
        if (mStoriesCollections == null || mStoriesCollections.size() == 0) {
            return null;
        }
        return mStoriesCollections.get(mStoriesCollections.size() - 1).date;
    }

    @Override
    public String toString() {
        return "StoriesModel{" +
                "mTopStories=" + mTopStories +
                ", mStories=" + mStories +
                '}';
    }
}
