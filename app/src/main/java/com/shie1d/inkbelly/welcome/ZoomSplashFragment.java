package com.shie1d.inkbelly.welcome;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shie1d.inkbelly.R;
import com.shie1d.inkbelly.base.BaseFragment;

/**
 * 放大动画Splash页面
 */

public class ZoomSplashFragment extends BaseFragment implements SplashContract.ISplashView, View.OnClickListener {


    FrameLayout mFLAdPlaceholder;
    TextView mTVSkipSplash;
    private SplashContract.ISplashPresenter mPresenter;

    @Override
    public void setPresenter(SplashContract.ISplashPresenter presenter) {
        mPresenter = presenter;
        mPresenter.init(this);
    }

    @Override
    protected void onAttachInner(Context context) {
        setPresenter(new SplashPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_zoom_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mFLAdPlaceholder = view.findViewById(R.id.fl_ad_placeholder);
        mTVSkipSplash = view.findViewById(R.id.tv_skip_splash);
        mTVSkipSplash.setOnClickListener(this);
        mFLAdPlaceholder.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        zoomStart();
        mPresenter.countingDown();
    }

    private void zoomStart() {
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 1.25f);
        animator.setDuration(2000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mFLAdPlaceholder.setScaleX(value);
                mFLAdPlaceholder.setScaleY(value);
            }
        });
        animator.start();
    }

    @Override
    protected void onDetachInner() {
        mPresenter.release();
        mPresenter = null;
    }

    @Override
    protected boolean validRecycleFields() {
        return mPresenter != null;
    }

    @Override
    public Context getLogicContext() {
        return getActivity();
    }

    public void setSkipCounting(long counting) {
        mTVSkipSplash.setText(getString(R.string.skip_with_count_down, counting));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_ad_placeholder:
                mPresenter.clickAd();
                break;
            case R.id.tv_skip_splash:
                mPresenter.clickSkipSplash();
                break;
        }
    }
}
