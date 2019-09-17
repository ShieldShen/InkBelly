package com.shie1d.inkbelly.story;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.shie1d.inkbelly.base.BaseActivity;
import com.shie1d.inkbelly.net.zhihudaily.bean.StoryBrief;
import com.shie1d.inkbelly.stories.StoriesModel;

/**
 * 详情页
 */

public class StoryActivity extends BaseActivity {
    static final String ARG_STORY = "arg_story";
    static final String ARG_CLICK_ITEM = "arg_click_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StoryFragment storyFragment = StoryFragment.create();
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            storyFragment.setArguments(extras);
        }
        initFragment(storyFragment);
    }

    public static void start(Context context, StoriesModel stories, StoryBrief clickedItem) {
        Intent intent = new Intent();
        intent.setClass(context, StoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_STORY, stories);
        bundle.putSerializable(ARG_CLICK_ITEM, clickedItem);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
