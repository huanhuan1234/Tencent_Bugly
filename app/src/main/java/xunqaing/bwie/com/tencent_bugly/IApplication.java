package xunqaing.bwie.com.tencent_bugly;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by : Xunqiang
 * 2017/6/16 17:18
 */
//    获取APP ID并将以下代码复制到项目Application类onCreate()中，Bugly会为自动检测环境并完成配置：
//    https://bugly.qq.com/v2/workbench/apps

public class IApplication extends Application {

    /**
     * 为了保证运营数据的准确性，建议不要在异步线程初始化Bugly。
     * <p>
     * 第三个参数为SDK调试模式开关，调试模式的行为特性如下：
     * <p>
     * 输出详细的Bugly SDK的Log；
     * 每一条Crash都会被立即上报；
     * 自定义日志将会在Logcat中输出。
     * <p>
     * 建议在测试阶段建议设置成true，发布时设置为false。
     */

    private static IApplication iApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        iApplication = this;

        //        如果App使用了多进程且各个进程都会初始化Bugly（例如在Application类onCreate()中初始化Bugly），那么每个进程下的Bugly都会进行数据上报，造成不必要的资源浪费。
        //        因此，为了节省流量、内存等资源，建议初始化的时候对上报进程进行控制，只在主进程下上报数据：判断是否是主进程（通过进程名是否为包名来判断），并在初始化Bugly时增加一个上报进程的策略配置。
        //主进程下就进行一次上报

        //使用再下面代码   注掉这些代码

//        CrashReport.initCrashReport(getApplicationContext(), "a9233db404", false);
//
////    默认进程名 ：包名   E/xunxun iApplication: xunqaing.bwie.com.tencent_bugly.IApplication@5280bc84
//        Log.e("xunxun iApplication",iApplication+"");
//
//        //将Service配置在子进程中
////        suiyijinchengming E/xunxun iApplication: xunqaing.bwie.com.tencent_bugly.IApplication@5280ca30
//        Log.e("xunxun ServiceiApplication",iApplication+"");



        //        增加上报进程控制
        //如果有多个进程 只在主进程中上报

        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new  CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, "a9233db404", true, strategy);
        // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
        // CrashReport.initCrashReport(context, strategy);

                Log.e("xunxun iApplication",iApplication+"");

    }

//    其中获取进程名的方法“getProcessName”有多种实现方法，推荐方法如下：
    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
