package com.shie1d.inkbelly.base.mvp;

/**
 * 业务逻辑处理单元
 */

public interface BasePresenter<V extends BaseView, M extends BaseModel> {
    void init(V view);

    void refresh(M model);

    void release();
}
