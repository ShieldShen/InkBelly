package com.shie1d.inkbelly.welcome.pre;

import android.content.Context;

import com.shie1d.themis.Themis;

/**
 * 启动的准备工作流
 */

class PrepareStudio extends Themis<Context, Boolean> {

    private ProcessCallback mCallback;

    PrepareStudio(Context context) {
        super(context);
        addWorker(new UserInfoWorker());
    }

    interface ProcessCallback {
        void onProcessStart();

        void onProcessEnd();
    }

    void setProcessCallback(ProcessCallback callback) {
        this.mCallback = callback;
    }

    @Override
    protected void onProcessStart() {
        if (mCallback != null) mCallback.onProcessStart();
    }

    @Override
    protected void onProcessEnd() {
        if (mCallback != null) mCallback.onProcessEnd();
    }
}
