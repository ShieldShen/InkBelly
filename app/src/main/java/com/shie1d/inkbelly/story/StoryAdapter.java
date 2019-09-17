package com.shie1d.inkbelly.story;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shie1d.inkbelly.detail.StoryDetailFragment;
import com.shie1d.inkbelly.net.zhihudaily.bean.StoryBrief;

import java.util.ArrayList;
import java.util.List;

/**
 * 故事页.
 */

class StoryAdapter extends FragmentStatePagerAdapter {
    List<StoryBrief> mStories;

    StoryAdapter(FragmentManager fm) {
        super(fm);
        mStories = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mStories.size();
    }

    @Override
    public Fragment getItem(int position) {
        return StoryDetailFragment.create(mStories
                .get(position));
    }

    public void setData(List<StoryBrief> allStoriesBrief) {
        mStories.clear();
        mStories.addAll(allStoriesBrief);
        notifyDataSetChanged();
    }
}
