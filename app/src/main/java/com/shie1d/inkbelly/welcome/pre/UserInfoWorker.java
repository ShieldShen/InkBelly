package com.shie1d.inkbelly.welcome.pre;

import android.content.Context;
import android.os.Bundle;

import com.shie1d.themis.Worker;

import java.util.List;

/**
 * 启动用户信息相关工作
 */

public class UserInfoWorker extends Worker<Context, Boolean> {
    @Override
    protected Boolean run(Context context, Bundle bundle, List<Boolean> lastResult) throws Exception {
        UserInfoService.start(context);
        return true;
    }
}
