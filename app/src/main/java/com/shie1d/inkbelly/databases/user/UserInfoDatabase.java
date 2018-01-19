package com.shie1d.inkbelly.databases.user;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shie1d.moneta.Moneta;

/**
 * 数据库存放用户信息
 */

public class UserInfoDatabase extends SQLiteOpenHelper {
    private static final int INIT_VERSION = 101;

    private static final int CURRENT_VERSION = 101;
    private final Moneta mMoneta;

    public UserInfoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, null, CURRENT_VERSION, null);
        mMoneta = Moneta.use("UserInfoDatabase", Moneta.LIMIT.NO);
        mMoneta.d("Database init");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable =
                "CREATE TABLE " + UserInfoContract.User.NAME + " (\n" +
                        UserInfoContract.User.COLUMN_ID + " INTEGER primary key AUTOINCREMENT,\n" +
                        UserInfoContract.User.COLUMN_USER_SERVER_ID + " str,\n" +
                        UserInfoContract.Login.COLUMN_LAST_LOGIN_TIME + " datetime default (datetime('now','localtime')),\n" +
                        UserInfoContract.User.COLUMN_FLAG + " default 0,\n" +
                        UserInfoContract.User.COLUMN_DEVICE_ID + " str,\n" +
                        UserInfoContract.User.COLUMN_PHONE_MODEL + " str,\n" +
                        UserInfoContract.User.COLUMN_PHONE_BRAND + " str,\n" +
                        UserInfoContract.User.COLUMN_PHONE_VERSION + " str,\n" +
                        UserInfoContract.User.COLUMN_EXTEND1 + " str,\n" +
                        UserInfoContract.User.COLUMN_EXTEND2 + " str,\n" +
                        UserInfoContract.User.COLUMN_EXTEND3 + " str,\n" +
                        UserInfoContract.User.COLUMN_DELETE + " str,\n" +
                        ")";

        String createLoginTable =
                "CREATE TABLE " + UserInfoContract.Login.NAME + " (\n" +
                        UserInfoContract.Login.COLUMN_ID + " INTEGER primary key AUTOINCREMENT,\n" +
                        UserInfoContract.Login.COLUMN_USER_ID + " INTEGER " +
                        UserInfoContract.Login.COLUMN_USER_LOCATION + " str,\n" +
                        UserInfoContract.Login.COLUMN_DEVICE_IP + " str,\n" +
                        UserInfoContract.Login.COLUMN_EXTEND1 + " str,\n" +
                        UserInfoContract.Login.COLUMN_EXTEND2 + " str,\n" +
                        UserInfoContract.Login.COLUMN_EXTEND3 + " str,\n" +
                        UserInfoContract.Login.COLUMN_DELETE + " str,\n" +
                        "FOREIGN KEY(" + UserInfoContract.Login.COLUMN_USER_ID + ") REFERENCES " +
                        UserInfoContract.User.NAME + "(" + UserInfoContract.User.COLUMN_ID + ")" +
                        ")";

        String createDelUserTrigger =
                "CREATE TRIGGER user_about BEFORE DELETE ON " + UserInfoContract.User.NAME + " \n" +
                        "BEGIN\n" +
                        "DELETE FROM " + UserInfoContract.Login.NAME + " WHERE " +
                        UserInfoContract.Login.NAME + "." + UserInfoContract.Login.COLUMN_USER_ID + " = " +
                        UserInfoContract.User.NAME + "." + UserInfoContract.User.COLUMN_ID + "\n" +
                        "END";

        db.execSQL(createUserTable);
        db.execSQL(createLoginTable);
        db.execSQL(createDelUserTrigger);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) return;
        mMoneta.d("Database upgrade old :" + oldVersion + "\tnew :" + newVersion);
        if (oldVersion == INIT_VERSION) {
            //从Init开始升级
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion <= newVersion) return;
        mMoneta.d("Database downgrade old :" + oldVersion + "\tnew :" + newVersion);
        if (oldVersion == INIT_VERSION) {
            // do nothing
        }
    }
}
