package com.shie1d.moneta;

import android.support.annotation.NonNull;
import android.util.Log;

import com.shie1d.Chaos;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * 希腊神话里司记忆、语言、文字的女神 ，十二泰坦之一
 * <p>
 * 日志管理系统入口
 */

public class Moneta {
    private static boolean isDebug = BuildConfig.DEBUG;
    private final static String DEV_TAG = "shie1d";
    private static final int DEFAULT_LIMIT = LIMIT.NO;

    public interface LIMIT {
        int NO = 0;
        int D = 1;
        int W = 2;
        int E = 3;
    }

    private int mLimit = DEFAULT_LIMIT;
    private final String mTag;

    public static void q(String msg) {
        if (!isDebug) return;
        Log.e(DEV_TAG, msg);
    }

    private static WeakHashMap<String, WeakReference<Moneta>> mMap = new WeakHashMap<>();

    private Moneta(String tag) {
        mTag = tag;
    }

    public static Moneta use(String tag) {
        return use(tag, DEFAULT_LIMIT);
    }

    public static Moneta use(String tag, int limit) {
        WeakReference<Moneta> r = mMap.get(tag);
        Moneta moneta;
        if (r != null) {
            moneta = r.get();
            if (moneta == null) {
                moneta = createNew(tag);
            }
        } else {
            moneta = createNew(tag);
        }
        moneta.setLimit(limit);

        return moneta;
    }

    public static void release(String tag) {
        if (mMap.containsKey(tag)) {
            mMap.remove(tag);
        }
    }

    @NonNull
    private static Moneta createNew(String tag) {
        Moneta moneta = new Moneta(tag);
        WeakReference<Moneta> r = new WeakReference<>(moneta);
        mMap.put(tag, r);
        return moneta;
    }

    private void setLimit(int limit) {
        mLimit = limit;
    }

    private boolean isForbidden(int currentLogLevel) {
        return !isDebug && mLimit >= currentLogLevel;
    }

    private boolean isValidMsg(String msg) {
        return !Chaos.isEmpty(mTag) && !Chaos.isEmpty(msg);
    }


    public void d(String msg) {
        if (isForbidden(LIMIT.D)) return;
        if (isValidMsg(msg))
            Log.d(mTag, msg);
    }

    public void w(String msg) {
        if (isForbidden(LIMIT.W)) return;
        if (isValidMsg(msg))
            Log.d(mTag, msg);
    }

    public void e(String msg) {
        if (isForbidden(LIMIT.E)) return;
        if (isValidMsg(msg))
            Log.d(mTag, msg);
    }
}
