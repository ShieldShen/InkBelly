package com.shie1d.inkbelly.welcome.pre;

import android.content.Context;
import android.os.Bundle;

import com.shie1d.themis.Worker;

import java.util.List;

/**
 * 启动用户信息相关工作
 */

public class UserInfoWorker extends Worker<Context, Void> {
    @Override
    protected Void run(Context product, Bundle bundle, List<Void> lastResult) throws Exception {
        synchronized (product) {

        }
        return null;
    }
}
