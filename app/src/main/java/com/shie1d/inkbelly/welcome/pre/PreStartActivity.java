package com.shie1d.inkbelly.welcome.pre;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shie1d.inkbelly.R;
import com.shie1d.inkbelly.stories.StoriesActivity;
import com.shie1d.inkbelly.base.FullScreenActivity;
import com.shie1d.ophion.Ophion;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * 在StoriesActivity之前的业务处理页面
 */

public class PreStartActivity extends FullScreenActivity implements PrepareStudio.ProcessCallback {

    private Disposable mProcessDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pre_start);
        PrepareStudio prepareStudio = new PrepareStudio(this);
        prepareStudio.setProcessCallback(this);
        prepareStudio.run();
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, PreStartActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onProcessStart() {
        mProcessDisposable = Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Ophion.sT(PreStartActivity.this, R.string.waiting_for_prepare);
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void onProcessEnd() {
        if (mProcessDisposable != null && !mProcessDisposable.isDisposed()) {
            mProcessDisposable.dispose();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StoriesActivity.start(PreStartActivity.this);
                finish();
            }
        });

    }
}
