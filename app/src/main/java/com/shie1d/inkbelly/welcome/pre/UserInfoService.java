package com.shie1d.inkbelly.welcome.pre;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import com.shie1d.inkbelly.databases.user.UserInfoContract;
import com.shie1d.moneta.Moneta;
import com.shie1d.themis.Themis;
import com.shie1d.themis.Worker;

import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class UserInfoService extends Service {

    private Moneta mMoneta;

    public UserInfoService() {
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, UserInfoService.class);
        context.startService(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("不需要去绑定,纯后台运行就可以");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMoneta = Moneta.use("UserInfoService");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMoneta.release();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Observable.just(this)
                .map(new Function<Context, UserInfoSignInWorks>() {
                    @Override
                    public UserInfoSignInWorks apply(Context context) throws Exception {
                        return new UserInfoSignInWorks(context);
                    }
                })
                .map(new Function<UserInfoSignInWorks, Boolean>() {
                    @Override
                    public Boolean apply(UserInfoSignInWorks userInfoSignInWorks) throws Exception {
                        List<Boolean> results = userInfoSignInWorks.run();
                        return results != null && results.size() > 0 && results.get(0);
                    }
                })
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean result) throws Exception {
                        mMoneta.d("用户信息录入工作结束 : \n结果 ：" + result);
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe();
        return START_STICKY_COMPATIBILITY;
    }

    private class UserInfoSignInWorks extends Themis<Context, Boolean> {

        UserInfoSignInWorks(Context context) {
            super(context, Mode.HANDLED_BREAK);
            addWorker(new CheckUserWorker());
            addWorker(new CheckInWorker());
        }
    }

    private class CheckUserWorker extends Worker<Context, Boolean> {

        @Override
        protected Boolean run(Context context, Bundle bundle, List<Boolean> lastResult) throws Exception {
            ContentResolver contentResolver = context.getContentResolver();
            Cursor c = null;
            try {
                c = contentResolver.query(UserInfoContract.User.CONTENT_URI, new String[]{UserInfoContract.User.COLUMN_ID}, null, null, null);
                if (c != null && c.getCount() > 0) {
                    return true;
                }
            } finally {
                if (c != null && !c.isClosed()) {
                    c.close();
                }
            }
            return null;
        }
    }

    private class CheckInWorker extends Worker<Context, Boolean> {

        @Override
        protected Boolean run(Context context, Bundle bundle, List<Boolean> lastResult) throws Exception {
            UUID uuid = UUID.randomUUID();
            ContentResolver contentResolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(UserInfoContract.User.COLUMN_DEVICE_ID, uuid.toString());
            contentValues.put(UserInfoContract.User.COLUMN_PHONE_BRAND, Build.BRAND);
            contentValues.put(UserInfoContract.User.COLUMN_PHONE_MODEL, Build.MODEL);
            contentValues.put(UserInfoContract.User.COLUMN_PHONE_VERSION, String.valueOf(Build.VERSION.SDK_INT));
            Uri insert = contentResolver.insert(UserInfoContract.User.CONTENT_URI, contentValues);
            if (insert != null) {
                return true;
            }
            return null;
        }
    }
}
