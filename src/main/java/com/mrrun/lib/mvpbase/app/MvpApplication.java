package com.mrrun.lib.mvpbase.app;

import com.mrrun.lib.androidbase.base.app.AppManager;
import com.mrrun.lib.androidbase.base.app.BaseApp;
import com.mrrun.lib.mvpbase.dagger.ApplicationComponent;
import com.mrrun.lib.mvpbase.dagger.ApplicationModule;
import com.mrrun.lib.mvpbase.dagger.DaggerApplicationComponent;

/**
 * Created by lipin on 2017/6/28.
 */

public abstract class MvpApplication extends BaseApp implements Thread.UncaughtExceptionHandler {

    private static final String TAG = MvpApplication.class.getSimpleName();

    protected ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        // 为所有的Thread设置一个默认的UncaughtExceptionHandler,有效防止线程泄露问题
        Thread.setDefaultUncaughtExceptionHandler(this);
        // 注入ApplicationComponent
        initializeInjector();
    }

    /**
     * <b>方法功能描述:</b><br>
     * Dagger2-初始化ApplicationComponent组件注射器
     */
    protected void initializeInjector() {
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    /**
     * 提供ApplicationComponent
     *
     * @return
     */
    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AppManager.getAppManager().finishAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
