package com.shie1d.inkbelly.story;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shie1d.inkbelly.R;
import com.shie1d.inkbelly.base.BaseFragment;

/**
 * 详情页ViewPager载体
 */

public class StoryFragment extends BaseFragment
        implements StoryContract.IStoryView {

    private StoryContract.IStoryPresenter mPresenter;
    private ViewPager mVPContainer;
    private StoryAdapter mAdapter;

    public static StoryFragment create() {
        return new StoryFragment();
    }

    @Override
    public void setPresenter(StoryContract.IStoryPresenter presenter) {
        mPresenter = presenter;
        mPresenter.init(this);
    }

    @Override
    public Context getLogicContext() {
        return getActivity();
    }

    @Override
    public void viewFinish() {
        if (isAlive()) {
            FragmentActivity activity = getActivity();
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    @Override
    public void refresh(StoryModel model) {

    }

    @Override
    public void loading(boolean isLoading) {

    }

    @Override
    protected boolean validRecycleFields() {
        return mPresenter != null;
    }

    @Override
    protected void onAttachInner(Context context) {
        setPresenter(new StoryPresenter());
    }

    @Override
    protected void onDetachInner() {
        mPresenter.release();
        mPresenter = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_story, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mVPContainer = view.findViewById(R.id.vp_container);
        mAdapter = new StoryAdapter(getChildFragmentManager());
        mVPContainer.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.setData(getArguments());
    }

    public void showErrorPage() {

    }

    @Override
    public void switchToCurPage(StoryModel model) {
        mAdapter.setData(model.getAllStoriesBrief());
        mVPContainer.setCurrentItem(model.getCurPosition());
    }
}
