package com.shie1d.inkbelly.welcome.pre;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.shie1d.inkbelly.databases.user.UserInfoDatabase;

public class UserInfoService extends Service {
    public UserInfoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY_COMPATIBILITY;
    }
}
