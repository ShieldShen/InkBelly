package com.shie1d.themis;

import android.os.Bundle;

import com.shie1d.moneta.Moneta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 忒弥斯（Θεμις / Themis，“法律”）是法律和正义的象征。 她是乌拉诺斯的女儿，十二泰坦（提坦）神之一。
 * <p>
 * 同步的工作流程
 */
// TODO 需要把它做成线程可以调度的

public abstract class Themis<P, R> {
    private final P product;
    private final List<Interceptor<P>> interceptors;
    private final List<Filter<P>> filters;
    private final List<Worker<P, R>> workers;
    private final Bundle bundle;
    private final int START_MODE;

    public interface Mode {
        int ALL_WORK = 0;
        int HANDLED_BREAK = 1;
    }

    public Themis(P product, int mode) {
        interceptors = new ArrayList<>();
        filters = new ArrayList<>();
        workers = new ArrayList<>();
        bundle = new Bundle();
        START_MODE = mode;
        this.product = product;
    }

    public Themis(P product) {
        this(product, Mode.ALL_WORK);
    }

    public void addInterceptor(Interceptor<P> interceptor) {
        interceptors.add(interceptor);
    }

    public void addFilter(Filter<P> filter) {
        filters.add(filter);
    }

    public void addWorker(Worker<P, R> worker) {
        workers.add(worker);
    }

    private List<R> process() throws Exception {
        List<Filter<P>> inheritFilters = new ArrayList<>();
        List<R> results = new ArrayList<>();
        onProcessStart();
        //先把处理的对象校验一遍
        for (Filter<P> filter : filters) {
            if (filter.isInherit()) {
                inheritFilters.add(filter);
                continue;
            }
            if (!filter.filter(product, bundle)) {
                onFilterInterrupted(filter);
                return null;
            }
        }

        for (Interceptor<P> interceptor : interceptors) {
            interceptor.intercept(product, bundle);
        }

        for (Worker<P, R> worker : workers) {
            worker.setFilters(inheritFilters);
            R result = worker.process(product, bundle, results);
            if (START_MODE == Mode.HANDLED_BREAK && result != null) {
                results.add(result);
                onProcessEnd();
                return results;
            }
        }
        return results;
    }

    protected void onProcessStart() {

    }

    protected void onProcessEnd() {

    }

    /**
     * 要处理的对象在首次运行时的校验就失败了
     */
    protected void onFilterInterrupted(Filter<P> filter) {

    }

    public List<R> run() {
        try {
            return process();
        } catch (Exception e) {
            Moneta.use("Themis").e("Some error occurs during working flow");
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
