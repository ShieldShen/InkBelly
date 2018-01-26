package com.shie1d.inkbelly.base;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * 所有Fragment的基类
 */
public abstract class BaseFragment extends Fragment {
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachInner(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onAttachInner(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onDetachInner();
    }

    public boolean isAlive() {
        return !isDetached() && isAdded() && validRecycleFields();
    }

    /**
     * 用来获取界面的字段对象是否可用
     */
    protected abstract boolean validRecycleFields();

    /**
     * 在onAttach时调用
     */
    protected abstract void onAttachInner(Context context);

    /**
     * 在onDetach时调用
     */
    protected abstract void onDetachInner();
}
