package com.shie1d.inkbelly.databases.user;

import android.net.Uri;

import com.shie1d.inkbelly.databases.base.BaseTableContract;

/**
 * User数据库目录
 */

public interface UserInfoContract extends BaseTableContract {
    String AUTHORITY = "com.shie1d.inkbelly.provider.user";
    Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    String DATABASE_NAME = "UserInfo";

    interface User extends BaseTableContract {
        String NAME = "user";

        String COLUMN_USER_SERVER_ID = "user_server_id";
        String COLUMN_FLAG = "flag";
        String COLUMN_DEVICE_ID = "device_id";
        String COLUMN_PHONE_BRAND = "phone_brand";
        String COLUMN_PHONE_MODEL = "phone_model";
        String COLUMN_PHONE_VERSION = "phone_version";
    }

    interface Login extends BaseTableContract {
        String NAME = "login";

        String COLUMN_USER_ID = "user_id";
        String COLUMN_LAST_LOGIN_TIME = "last_login_time";
        String COLUMN_USER_LOCATION = "user_location";
        String COLUMN_DEVICE_IP = "device_ip";

    }
}
