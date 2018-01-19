package com.shie1d.inkbelly.databases;

/**
 * Created by 沈力 on 2018/1/18.
 */

public interface UserInfoContract extends BaseTableContract {
    interface User extends BaseTableContract {
        String TABLE_USER = "user";

        String COLUMN_USER_SERVER_ID = "user_server_id";
        String COLUMN_FLAG = "flag";
        String COLUMN_DEVICE_ID = "device_id";
        String COLUMN_IMEI = "imei";
        String COLUMN_PHONE_NUMBER = "phone_number";
    }

    interface Login extends BaseTableContract {
        String TABLE_USER = "login";

        String COLUMN_USER_ID = "user_id";
        String COLUMN_LAST_LOGIN_TIME = "last_login_time";
        String COLUMN_USER_LOCATION = "user_location";
        String COLUMN_DEVICE_IP = "device_ip";

    }
}
