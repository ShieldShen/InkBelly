package com.shie1d.themis;

import android.os.Bundle;

/**
 * 工作流过滤器
 */

public abstract class Filter<P> {
    abstract boolean filter(P product, Bundle bundle);

    abstract boolean isInherit();
}
