package com.shie1d.inkbelly.base.mvp;

import android.content.Context;

/**
 * 业务逻辑页面基类
 */

public interface BaseView<P extends BasePresenter> {

    void setPresenter(P presenter);

    Context getLogicContext();
}
