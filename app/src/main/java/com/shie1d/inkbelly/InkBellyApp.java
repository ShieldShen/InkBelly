package com.shie1d.inkbelly;

import android.app.Application;
import android.content.Context;


public class InkBellyApp extends Application {
    private static Context mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static Context get() {
        return mApp;
    }
}
