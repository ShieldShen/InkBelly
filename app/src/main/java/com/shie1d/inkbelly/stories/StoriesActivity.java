package com.shie1d.inkbelly.stories;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.shie1d.inkbelly.base.BaseActivity;

public class StoriesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(StoriesFragment.create());
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, StoriesActivity.class);
        context.startActivity(intent);
    }
}
