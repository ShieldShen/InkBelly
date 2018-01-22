package com.shie1d.inkbelly.databases.user;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.shie1d.Chaos;
import com.shie1d.inkbelly.databases.base.BaseDbUnit;
import com.shie1d.inkbelly.databases.base.BaseTableContract;
import com.shie1d.inkbelly.databases.base.SqlOperation;

/**
 * {@link UserInfoContentProvider} 的逻辑处理类
 */

class UserInfoLogic extends BaseDbUnit {

    UserInfoLogic(Context context, SQLiteDatabase database) {
        super(context, database);
    }

    Uri insertLogin(Uri uri, ContentValues values) {
        long insert = mDb.insert(UserInfoContract.Login.NAME, null, values);
        return ContentUris.withAppendedId(uri, insert);
    }

    Uri insertUser(Uri uri, ContentValues values) {
        long insert = mDb.insert(UserInfoContract.User.NAME, null, values);
        return ContentUris.withAppendedId(uri, insert);
    }

    int deleteLogin(Uri uri, String selection, String[] selectionArgs) {
        String idStr = uri.getLastPathSegment();
        try {
            long id = Long.parseLong(idStr);
            if (Chaos.isEmpty(selection)) {
                selection = SqlOperation.WHERE + BaseTableContract.COLUMN_ID + SqlOperation.EQUALS + id;
            } else {
                selection = selection + SqlOperation.AND + BaseTableContract.COLUMN_ID + SqlOperation.EQUALS + id;
            }
            return mDb.delete(UserInfoContract.Login.NAME, selection, selectionArgs);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int deleteUser(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    public Cursor queryLogin(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    public Cursor queryUser(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    public int updateLogin(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    public int updateUser(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
