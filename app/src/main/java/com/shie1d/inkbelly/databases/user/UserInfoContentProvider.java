package com.shie1d.inkbelly.databases.user;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.shie1d.inkbelly.databases.base.BaseContentProvider;

public class UserInfoContentProvider extends BaseContentProvider<UserInfoLogic> {

    private static final UriMatcher mUriMatcher;

    @Override
    protected String getTag() {
        return UserInfoContract.DATABASE_NAME;
    }

    @Override
    protected SQLiteOpenHelper onCreateDatabaseOpenHelper(Context context) {
        return new UserInfoDatabaseOpenHelper(context);
    }

    @Override
    protected UserInfoLogic onCreateLogicUnit(Context context, SQLiteDatabase database) {
        return new UserInfoLogic(context, database);
    }

    @Override
    protected Uri getContentUri() {
        return UserInfoContract.CONTENT_URI;
    }

    private interface MATCH {
        int USER = 0;
        int USER_ID = 1;
        int LOGIN = 2;
        int LOGIN_ID = 3;
    }

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(UserInfoContract.AUTHORITY, UserInfoContract.User.NAME + "/#", MATCH.USER_ID);
        mUriMatcher.addURI(UserInfoContract.AUTHORITY, UserInfoContract.Login.NAME + "/#", MATCH.LOGIN_ID);
    }

    public UserInfoContentProvider() {
    }


    @Override
    public String getType(@NonNull Uri uri) {
        int match = mUriMatcher.match(uri);
        switch (match) {
            case MATCH.USER_ID:
                return "vnd.android.cursor.item/user";
            case MATCH.LOGIN_ID:
                return "vnd.android.cursor.item/user";
            default:
                throw new IllegalArgumentException("no match type for " + uri);
        }
    }

    @Override
    protected Uri insertInner(Uri uri, ContentValues values) {
        int match = mUriMatcher.match(uri);
        switch (match) {
            case MATCH.LOGIN:
                return unit().insertLogin(uri, values);
            case MATCH.USER:
                return unit().insertUser(uri, values);
            default:
                throw new IllegalArgumentException("错误的insert uri :" + uri);
        }
    }

    @Override
    protected int deleteInner(Uri uri, String selection, String[] selectionArgs) {
        int match = mUriMatcher.match(uri);
        switch (match) {
            case MATCH.LOGIN_ID:
                return unit().deleteLogin(uri, selection, selectionArgs);
            case MATCH.USER_ID:
                return unit().deleteUser(uri, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("错误的delete uri :" + uri);
        }
    }

    @Override
    protected Cursor queryInner(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = mUriMatcher.match(uri);
        switch (match) {
            case MATCH.LOGIN_ID:
                return unit().queryLogin(uri, projection, selection, selectionArgs, sortOrder);
            case MATCH.USER_ID:
                return unit().queryUser(uri, projection, selection, selectionArgs, sortOrder);
            default:
                throw new IllegalArgumentException("错误的query uri :" + uri);
        }
    }

    @Override
    protected int updateInner(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int match = mUriMatcher.match(uri);
        switch (match) {
            case MATCH.LOGIN_ID:
                return unit().updateLogin(uri, values, selection, selectionArgs);
            case MATCH.USER_ID:
                return unit().updateUser(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("错误的update uri :" + uri);
        }
    }

}
