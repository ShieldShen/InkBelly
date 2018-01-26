package com.shie1d.inkbelly.stories;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.shie1d.inkbelly.R;
import com.shie1d.inkbelly.net.zhihudaily.bean.StoryBrief;
import com.shie1d.moneta.Moneta;

import java.util.ArrayList;
import java.util.List;

/**
 * Stories列表Adapter
 */

public class StoriesAdapter extends Adapter<StoriesAdapter.Holder> {
    public interface ViewType {
        int STORY_BRIEF = 0;
        int DATE = 1;
    }

    private int mContainerWidth;
    private int mContainerHeight;
    private final List<StoryBrief> mStories;
    private long mLastDataDate = -1;

    StoriesAdapter(Context context) {
        mStories = new ArrayList<>();

        Resources resources = context.getResources();
        mContainerWidth = resources.getDisplayMetrics().widthPixels - 2 * resources.getDimensionPixelSize(R.dimen.general_margin_small);
        mContainerHeight = resources.getDimensionPixelSize(R.dimen.card_view_height);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return Holder.create(parent, viewType, mContainerWidth, mContainerHeight);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.inflate(mStories.get(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        return mStories.get(position).viewType;
    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }

    void inflateStories(List<StoryBrief> stories, long currentDataDate) {
        if (mLastDataDate == -1 || mLastDataDate > currentDataDate) {
            mLastDataDate = currentDataDate;
            mStories.add(new StoryBrief(currentDataDate));
        }
        mStories.addAll(stories);
    }

    static class Holder extends RecyclerView.ViewHolder {

        private int mViewType;
        private CardView mContainerView;
        private TextView mTVTitle;
        private TextView mTVDate;

        private int mContainerWidth;
        private int mContainerHeight;

        private Holder(View itemView) {
            super(itemView);
        }

        static Holder create(ViewGroup parent, int viewType, int containerWidth, int containerHeight) {
            int id;
            if (viewType == ViewType.STORY_BRIEF) {
                id = R.layout.item_story_brief;
            } else if (viewType == ViewType.DATE) {
                id = R.layout.item_stories_date;
            } else {
                throw new IllegalArgumentException("没有匹配的ViewType :" + viewType);
            }

            Holder holder = new Holder(LayoutInflater.from(parent.getContext()).inflate(id, null));
            holder.mViewType = viewType;
            holder.init(containerWidth, containerHeight);
            return holder;
        }

        private void init(int containerWidth, int containerHeight) {
            mContainerWidth = containerWidth;
            mContainerHeight = containerHeight;
            if (mViewType == ViewType.STORY_BRIEF) {
                mContainerView = itemView.findViewById(R.id.cv_container);
                mTVTitle = itemView.findViewById(R.id.tv_story_title);
            } else if (mViewType == ViewType.DATE) {
                mTVDate = itemView.findViewById(R.id.tv_date);
            }
        }

        void inflate(StoryBrief storyBrief, int position) {
            Moneta.q("image : " + storyBrief.image + "\ncontainer :" + mContainerView + "\nwidth :" + mContainerWidth + "\nheight :" + mContainerHeight);
            if (mViewType == ViewType.STORY_BRIEF) {
                Glide.with(mContainerView)
                        .load(storyBrief.image)
                        .into(new SimpleTarget<Drawable>(mContainerWidth, mContainerHeight) {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                mContainerView.setBackground(resource);
                            }
                        });
                mTVTitle.setText(storyBrief.title);
            }
        }
    }
}
