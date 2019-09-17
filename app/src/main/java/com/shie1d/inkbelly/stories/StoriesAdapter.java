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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.shie1d.Chaos;
import com.shie1d.inkbelly.R;
import com.shie1d.inkbelly.net.zhihudaily.bean.StoryBrief;
import com.shie1d.moneta.Moneta;

import java.util.ArrayList;
import java.util.List;

/**
 * Stories列表Adapter
 */

public class StoriesAdapter extends Adapter<StoriesAdapter.Holder> {


    private final OnItemShowCallback mOnItemShowCallback;
    private final OnItemClickCallback mOnItemClickCallback;

    private int mContainerWidth;
    private int mContainerHeight;
    private List<StoryBrief> mStories;

    StoriesAdapter(Context context,
                   OnItemShowCallback onItemShowCallback,
                   OnItemClickCallback onItemClickCallback) {
        mOnItemShowCallback = onItemShowCallback;
        mOnItemClickCallback = onItemClickCallback;
        mStories = new ArrayList<>();
        Resources resources = context.getResources();
        mContainerWidth = resources.getDisplayMetrics().widthPixels - 2 * resources.getDimensionPixelSize(R.dimen.general_margin_small);
        mContainerHeight = mContainerWidth * 2 / 3;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return Holder.create(parent,
                viewType, mContainerWidth, mContainerHeight,
                mOnItemShowCallback,
                mOnItemClickCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
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

    void inflateStories(StoriesModel model) {
        mStories = model.getStories();
    }

    static class Holder extends RecyclerView.ViewHolder {

        private int mViewType;
        private OnItemShowCallback mOnItemShowCallback;
        private OnItemClickCallback mOnItemClickCallback;
        private CardView mContainerView;
        private ImageView mIvBackground;
        private TextView mTVTitle;
        private TextView mTVDate;

        private int mContainerWidth;
        private int mContainerHeight;

        private StoryBrief mCurStoryBrief;

        private Holder(View itemView) {
            super(itemView);
        }

        static Holder create(ViewGroup parent, int viewType,
                             int containerWidth, int containerHeight,
                             OnItemShowCallback onItemShowCallback,
                             OnItemClickCallback onItemClickCallback) {
            int id;
            if (viewType == StoryBrief.TYPE.STORY_BRIEF) {
                id = R.layout.item_story_brief;
            } else if (viewType == StoryBrief.TYPE.DATE) {
                id = R.layout.item_stories_date;
            } else {
                throw new IllegalArgumentException("没有匹配的ViewType :" + viewType);
            }

            Holder holder = new Holder(LayoutInflater.from(parent.getContext()).inflate(id, null));
            holder.mViewType = viewType;
            holder.init(containerWidth, containerHeight, onItemShowCallback, onItemClickCallback);
            return holder;
        }

        private void init(int containerWidth, int containerHeight,
                          OnItemShowCallback onItemShowCallback,
                          OnItemClickCallback onItemClickCallback) {
            mContainerWidth = containerWidth;
            mContainerHeight = containerHeight;
            mOnItemShowCallback = onItemShowCallback;
            mOnItemClickCallback = onItemClickCallback;
            if (mViewType == StoryBrief.TYPE.STORY_BRIEF) {
                mContainerView = itemView.findViewById(R.id.cv_container);
                mIvBackground = itemView.findViewById(R.id.iv_background);
                mTVTitle = itemView.findViewById(R.id.tv_story_title);
                mContainerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickCallback != null) {
                            Moneta.q("click : " + mCurStoryBrief.title);
                            mOnItemClickCallback.onItemClicked(mCurStoryBrief);
                        }
                    }
                });
            } else if (mViewType == StoryBrief.TYPE.DATE) {
                mTVDate = itemView.findViewById(R.id.tv_date);
            }
        }

        void inflate(StoryBrief storyBrief, int position) {
            if (mOnItemShowCallback != null) {
                mOnItemShowCallback.onItemShowed(position);
            }
            if (storyBrief == null) return;
            if (mViewType == StoryBrief.TYPE.STORY_BRIEF) {
                mCurStoryBrief = storyBrief;
                Moneta.q("title : " + storyBrief.title + "\nimage : " + storyBrief.image + "\ncontainer :" + mContainerView + "\nwidth :" + mContainerWidth + "\nheight :" + mContainerHeight);
                if (storyBrief.images != null) {
                    Moneta.q("image in images");
                    for (String image :
                            storyBrief.images) {
                        Moneta.q("image in images : " + image);
                    }
                }
                String image = storyBrief.image;
                if (Chaos.isEmpty(image)
                        && storyBrief.images != null && storyBrief.images.size() > 0) {
                    image = storyBrief.images.get(0);
                }
                Glide.with(mIvBackground)
                        .load(image)
                        .override(mContainerWidth, mContainerHeight)
                        .centerCrop()
                        .into(new CustomViewTarget<ImageView, Drawable>(mIvBackground) {
                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {

                            }

                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                Moneta.q("bounds : " + resource.getIntrinsicHeight() + " : " + resource.getIntrinsicWidth());
                                resource.setBounds(0, 0, mContainerWidth, mContainerHeight);
                                mIvBackground.setBackground(resource);
                            }

                            @Override
                            protected void onResourceCleared(@Nullable Drawable placeholder) {

                            }
                        });
                mTVTitle.setText(storyBrief.title);
            }
        }
    }
}
