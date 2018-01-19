package com.shie1d.inkbelly.welcome.pre;

import android.content.Context;

import com.shie1d.themis.Themis;

/**
 * 启动的准备工作流
 */

class PrepareStudio extends Themis<Context, Void> {

    PrepareStudio(Context context) {
        super(context);
        addWorker(new UserInfoWorker());
    }
}
