package com.shie1d.inkbelly;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.shie1d.inkbelly.base.BaseActivity;

public class StoriesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, StoriesActivity.class);
        context.startActivity(intent);
    }
}
