package com.shie1d.themis;

import android.os.Bundle;

import java.util.List;

/**
 * 工作单元
 */

public abstract class Worker<P, R> {

    private List<Filter<P>> filters;

    public Worker() {
    }

    abstract R run(P product, Bundle bundle, R lastResult) throws Exception;

    R process(P product, Bundle bundle, R lastResult) throws Exception {
        if (filters != null) {
            for (Filter<P> filter : filters) {
                if (!filter.filter(product, bundle))
                    return null;
            }
        }
        return run(product, bundle, lastResult);
    }

    void setFilters(List<Filter<P>> inheritFilters) {
        filters = inheritFilters;
    }
}
