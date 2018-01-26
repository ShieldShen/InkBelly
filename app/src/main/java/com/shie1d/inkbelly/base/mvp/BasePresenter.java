package com.shie1d.inkbelly.base.mvp;

/**
 * 业务逻辑处理单元
 */

public interface BasePresenter<V extends BaseView> {
    void init(V view);

    void release();
}
