package com.shie1d.inkbelly.databases.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Database操作的逻辑基类
 */

public abstract class BaseDbUnit {
    protected final Context mContext;
    protected final SQLiteDatabase mDb;

    public BaseDbUnit(Context context, SQLiteDatabase database) {
        this.mContext = context;
        this.mDb = database;
    }
}
