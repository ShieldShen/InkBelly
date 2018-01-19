package com.shie1d.inkbelly.databases.user;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

public class UserInfoContentProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher;

    private interface MATCH {
        int USER_ALL = 0;
        int USER_ID = 1;
        int LOGIN_ALL = 2;
        int LOGIN_ID = 3;
    }

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(UserInfoContract.AUTHORITY, UserInfoContract.User.NAME, MATCH.USER_ALL);
        sUriMatcher.addURI(UserInfoContract.AUTHORITY, UserInfoContract.User.NAME + "/#", MATCH.USER_ID);
        sUriMatcher.addURI(UserInfoContract.AUTHORITY, UserInfoContract.Login.NAME, MATCH.LOGIN_ALL);
        sUriMatcher.addURI(UserInfoContract.AUTHORITY, UserInfoContract.Login.NAME + "/#", MATCH.LOGIN_ID);
    }

    public UserInfoContentProvider() {
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case MATCH.USER_ALL:
                return "vnd.android.cursor.dir/user";
            case MATCH.USER_ID:
                return "vnd.android.cursor.item/user";
            case MATCH.LOGIN_ALL:
                return "vnd.android.cursor.dir/user";
            case MATCH.LOGIN_ID:
                return "vnd.android.cursor.dir/user";
            default:
                throw new IllegalArgumentException("no match type for " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
