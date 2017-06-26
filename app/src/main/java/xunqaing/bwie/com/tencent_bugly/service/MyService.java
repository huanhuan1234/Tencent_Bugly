package xunqaing.bwie.com.tencent_bugly.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 *
 *  android:process=":suiyijinchengming"
 *
 * android:process=":"  固定格式 为当前Service配置在其他子进程中
 */


public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
