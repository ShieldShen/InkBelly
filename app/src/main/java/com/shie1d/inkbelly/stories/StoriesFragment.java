package com.shie1d.inkbelly.stories;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ProgressBar;

import com.shie1d.inkbelly.R;
import com.shie1d.inkbelly.base.BaseFragment;
import com.shie1d.moneta.Moneta;

/**
 * Stories 界面显示
 */

public class StoriesFragment extends BaseFragment implements StoriesContract.IStoriesView {

    private StoriesContract.IStoriesPresenter mPresenter;
    private RecyclerView mRVStories;
    private ProgressBar mPGBarLoading;
    private StoriesAdapter mStoriesAdapter;

    public static StoriesFragment create() {
        return new StoriesFragment();
    }

    @Override
    public void setPresenter(StoriesContract.IStoriesPresenter presenter) {
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
    protected boolean validRecycleFields() {
        return mPresenter != null;
    }

    @Override
    protected void onAttachInner(Context context) {
        setPresenter(new StoriesPresenter());
    }

    @Override
    protected void onDetachInner() {
        mPresenter.release();
        mPresenter = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stories_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRVStories = view.findViewById(R.id.rv_stories);
        mRVStories.setLayoutManager(new LinearLayoutManager(getActivity()));
        mStoriesAdapter = new StoriesAdapter(getActivity());
        mRVStories.setAdapter(mStoriesAdapter);
        mPGBarLoading = view.findViewById(R.id.pg_bar_loading_stories);
        mPGBarLoading.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.pullDataFromServer();
    }

    @Override
    public void refresh(StoriesModel model) {
//        Moneta.q("result: " + model.toString());
        mStoriesAdapter.inflateStories(model.getStories(), model.getDateMillis());
        mStoriesAdapter.notifyDataSetChanged();
    }

    @Override
    public void loading(boolean isLoading) {
        mPGBarLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}
