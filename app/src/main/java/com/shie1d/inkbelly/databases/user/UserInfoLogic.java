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
        if (insert <= 0) {
            return null;
        }
        return ContentUris.withAppendedId(uri, insert);
    }

    Uri insertUser(Uri uri, ContentValues values) {
        long insert = mDb.insert(UserInfoContract.User.NAME, null, values);
        if (insert <= 0) {
            return null;
        }
        return ContentUris.withAppendedId(uri, insert);
    }

    int deleteLogin(Uri uri, String selection, String[] selectionArgs) {
        return mDb.delete(UserInfoContract.Login.NAME, extractIdSelectionFromUri(uri, selection), selectionArgs);
    }

    int deleteUser(Uri uri, String selection, String[] selectionArgs) {
        return mDb.delete(UserInfoContract.User.NAME, extractIdSelectionFromUri(uri, selection), selectionArgs);
    }

    Cursor queryLogin(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return mDb.query(UserInfoContract.Login.NAME, projection, extractIdSelectionFromUri(uri, selection), selectionArgs, null, null, sortOrder);
    }

    Cursor queryUser(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return mDb.query(UserInfoContract.User.NAME, projection, extractIdSelectionFromUri(uri, selection), selectionArgs, null, null, sortOrder);
    }

    int updateLogin(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return mDb.update(UserInfoContract.Login.NAME, values, extractIdSelectionFromUri(uri, selection), selectionArgs);
    }

    int updateUser(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return mDb.update(UserInfoContract.User.NAME, values, extractIdSelectionFromUri(uri, selection), selectionArgs);
    }

    private String extractIdSelectionFromUri(Uri uri, String selection) {
        String idStr = uri.getLastPathSegment();
        if (Chaos.isEmpty(idStr)) {
            return selection;
        }
        try {
            long id = Long.parseLong(idStr);
            if (Chaos.isEmpty(selection)) {
                selection = SqlOperation.WHERE + BaseTableContract.COLUMN_ID + SqlOperation.EQUALS + id;
            } else {
                selection = selection + SqlOperation.AND + BaseTableContract.COLUMN_ID + SqlOperation.EQUALS + id;
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return selection;
    }
}
