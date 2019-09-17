package com.shie1d.inkbelly.stories;

import com.shie1d.inkbelly.net.zhihudaily.bean.StoryBrief;

/**
 * 监听显示位置
 */

public interface OnItemClickCallback {
    void onItemClicked(StoryBrief clickedItem);
}
