package com.shie1d.inkbelly.databases.base;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.shie1d.moneta.Moneta;

import java.util.ArrayList;

/**
 * ContentProvider的基类
 */

public abstract class BaseContentProvider<U extends BaseDbUnit> extends ContentProvider {

    private SQLiteOpenHelper mDbHelper;
    private SQLiteDatabase mDb;
    private Moneta mMoneta;
    private U mLogicUnit;

    private ThreadLocal<Boolean> isApplyingBatch = new ThreadLocal<>();

    protected abstract String getTag();

    protected abstract SQLiteOpenHelper onCreateDatabaseOpenHelper(Context context);

    protected abstract U onCreateLogicUnit(Context context, SQLiteDatabase database);

    protected abstract Uri getContentUri();

    @Override
    public boolean onCreate() {
        mMoneta = Moneta.use(getTag());
        mDbHelper = onCreateDatabaseOpenHelper(getContext());
        return true;
    }

    protected boolean isInitialized() {
        return mDb != null && mLogicUnit != null;
    }

    protected void initialize() {
        if (mDb == null) {
            mDb = mDbHelper.getWritableDatabase();
        }
        if (mLogicUnit == null) {
            mLogicUnit = onCreateLogicUnit(getContext(), mDb);
        }
    }

    protected Moneta log() {
        return mMoneta;
    }

    protected U unit() {
        return mLogicUnit;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (!isInitialized()) initialize();
        boolean applyingBatch = isApplyingBatch();
        try {
            log().d("insert start : " + uri);
            return insertInner(uri, values);
        } finally {
            log().d("insert end : " + uri);
            if (!applyingBatch) {
                sendActionCompleteNotice(uri);
            }
        }
    }

    protected abstract Uri insertInner(Uri uri, ContentValues values);

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (!isInitialized()) initialize();
        boolean applyingBatch = isApplyingBatch();
        try {
            log().d("delete start : " + uri);
            return deleteInner(uri, selection, selectionArgs);
        } finally {
            log().d("delete end : " + uri);
            if (!applyingBatch) {
                sendActionCompleteNotice(uri);
            }
        }
    }

    protected abstract int deleteInner(Uri uri, String selection, String[] selectionArgs);

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if (!isInitialized()) initialize();
        boolean applyingBatch = isApplyingBatch();
        try {
            log().d("query start : " + uri);
            return queryInner(uri, projection, selection, selectionArgs, sortOrder);
        } finally {
            log().d("query end : " + uri);
            if (!applyingBatch) {
                sendActionCompleteNotice(uri);
            }
        }
    }

    protected abstract Cursor queryInner(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (!isInitialized()) initialize();
        boolean applyingBatch = isApplyingBatch();
        try {
            log().d("update start : " + uri);
            return updateInner(uri, values, selection, selectionArgs);
        } finally {
            log().d("update end : " + uri);
            if (!applyingBatch) {
                sendActionCompleteNotice(uri);
            }
        }
    }

    protected abstract int updateInner(Uri uri, ContentValues values, String selection, String[] selectionArgs);

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        try {
            isApplyingBatch.set(true);
            mDb.beginTransaction();
            ContentProviderResult[] contentProviderResults = super.applyBatch(operations);
            mDb.setTransactionSuccessful();
            return contentProviderResults;
        } finally {
            isApplyingBatch.set(false);
            mDb.endTransaction();
            Uri batch = getContentUri().buildUpon().appendPath("batch").build();
            sendActionCompleteNotice(batch);
        }
    }

    private boolean isApplyingBatch() {
        return isApplyingBatch.get() != null && isApplyingBatch.get();
    }

    protected void sendActionCompleteNotice(Uri uri) {
        //TODO 在用户一次操作的时候发送通知
    }
}
