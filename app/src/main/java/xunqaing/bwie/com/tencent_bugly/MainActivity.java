package xunqaing.bwie.com.tencent_bugly;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tencent.bugly.crashreport.CrashReport;

import xunqaing.bwie.com.tencent_bugly.service.MyService;

//https://bugly.qq.com/docs/user-guide/instruction-manual-android/?v=20170613194500#_2

// 符号表  ttps://bugly.qq.com/docs/user-guide/symbol-configuration-android/

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //集成第三方 元素都放在 读取 <application 下 <meta-data
        //http://blog.csdn.net/zhanghao_hulk/article/details/8662917

        //        <meta-data    一般通过代码动态改变
        //        android:name="BUGLY_APP_VERSION"
        //        android:value="${变量}" />

        try {
            ApplicationInfo info = this.getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);

            String msg1 = info.metaData.getString("BUGLY_APPID");
            String msg2 = info.metaData.getString("BUGLY_APP_VERSION");
            String msg3 = info.metaData.getString("BUGLY_APP_CHANNEL");
            Boolean msg4 = info.metaData.getBoolean("BUGLY_ENABLE_DEBUG");

            Log.e("xunxun msg = ", msg1 + "");
            Log.e("xunxun msg = ", msg2 + "");
            Log.e("xunxun msg = ", msg3 + "");
            Log.e("xunxun msg = ", msg4 + "");

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        //启动服务
        startService(new Intent(this, MyService.class));


    }


    //    现在您可以制造一个Crash（建议通过“按键”来触发），来体验Bugly的能力了。在初始化Bugly的之后，调用Bugly测Java Crash接口。
    public void A(View v) {

        switch (v.getId()) {

            case R.id.bt1:   //上层闪退
                a();
                break;
            case R.id.bt2:
                b();
                break;
        }

    }

    private void a() {

        CrashReport.testJavaCrash();

    }

    private void b() {
        //我们在实际Android开发的时候，可能会引入第三方的一些so库或者自己开发相应的so库供程序使用，然而so库一般是通过c或者c++开发的。android开发者通过java层的JNI机制调用Native语言写的函数，然而Natice语言也可以调用java层的函数。 如果有同学不明白的话，建议先去了解下JNI的相应技术，总的来说通过JNI技术，就让我们让Java世界跟Native世界可以联系在一起，也因为这个特性，让Java具有跨平台的特性。
        CrashReport.testNativeCrash();

    }
}
