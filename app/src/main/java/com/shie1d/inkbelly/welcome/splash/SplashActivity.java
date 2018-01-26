package com.shie1d.inkbelly.welcome.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shie1d.inkbelly.base.FullScreenActivity;

/**
 * 展示各种Splash的容器
 */

public class SplashActivity extends FullScreenActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(ZoomSplashFragment.create());
    }
}
