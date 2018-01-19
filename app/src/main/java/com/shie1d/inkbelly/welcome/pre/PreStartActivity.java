package com.shie1d.inkbelly.welcome.pre;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shie1d.inkbelly.R;
import com.shie1d.inkbelly.base.FullScreenActivity;

/**
 * 在StoriesActivity之前的业务处理页面
 */

public class PreStartActivity extends FullScreenActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pre_start);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, PreStartActivity.class);
        context.startActivity(intent);
    }
}
