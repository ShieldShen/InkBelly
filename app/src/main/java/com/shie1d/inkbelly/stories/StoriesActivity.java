package com.shie1d.inkbelly.stories;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.shie1d.inkbelly.R;
import com.shie1d.inkbelly.base.BaseActivity;
import com.shie1d.moneta.Moneta;
import com.shie1d.ophion.Ophion;

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
