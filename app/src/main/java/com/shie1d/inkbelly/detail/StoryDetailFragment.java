package com.shie1d.inkbelly.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shie1d.inkbelly.R;
import com.shie1d.inkbelly.base.BaseFragment;
import com.shie1d.inkbelly.net.zhihudaily.bean.StoryBrief;
import com.shie1d.inkbelly.net.zhihudaily.bean.StoryDetail;
import com.shie1d.moneta.Moneta;

/**
 * 详情页
 */

public class StoryDetailFragment extends BaseFragment implements StoryDetailContract.IStoryDetailView {
    static final String ARG_BRIEF = "ARG_BRIEF";
    private StoryDetailContract.IStoryDetailPresenter mPresenter;
    private TextView mTv;

    public static StoryDetailFragment create(StoryBrief storyBrief) {
        StoryDetailFragment storyDetailFragment = new StoryDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_BRIEF, storyBrief);
        storyDetailFragment.setArguments(bundle);
        return storyDetailFragment;
    }


    @Override
    protected boolean validRecycleFields() {
        return false;
    }

    @Override
    protected void onAttachInner(Context context) {
        setPresenter(new StoryDetailPresenter());
    }

    @Override
    protected void onDetachInner() {
        mPresenter.release();
        mPresenter = null;
    }

    @Override
    public void setPresenter(StoryDetailContract.IStoryDetailPresenter presenter) {
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
    public void onResume() {
        super.onResume();
        mPresenter.setBrief(getArguments());
        mPresenter.pullCurrentStoryDetail();
    }

    @Override
    public void showErrorPage() {

    }

    @Override
    public void showContent(StoryDetailModel model) {
        StoryDetail storyDetail = model.getStoryDetail();
        mTv.setText(storyDetail.toString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Moneta.q("Detail Page create" + this);
        return inflater.inflate(R.layout.fragment_story_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mTv = view.findViewById(R.id.tv);
    }
}
