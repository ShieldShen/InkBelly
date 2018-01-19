package com.shie1d.ophion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.shie1d.Chaos;

/**
 * 奥菲昂（Ophion）是希腊神话中的一个蛇形的神祇 代表天空的神
 * <p>
 * Toast的管理类
 */

public class Ophion {

    private static Toast mToast;

    private interface Holder {
        Ophion mOphion = new Ophion();
    }

    public static Ophion use() {
        return Holder.mOphion;
    }

    public static void sT(Context context, String msg) {
        t(context, msg, Toast.LENGTH_SHORT);
    }

    public static void lT(Context context, String msg) {
        t(context, msg, Toast.LENGTH_LONG);
    }

    @SuppressLint("ShowToast")
    private static void t(Context context, String msg, int duration) {
        if (context == null || Chaos.isEmpty(msg)) return;
        if (mToast == null) {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext == null) return;
            mToast = Toast.makeText(applicationContext, msg, duration);
        } else {
            mToast.setText(msg);
            mToast.setDuration(duration);
        }
        mToast.show();
    }


}
