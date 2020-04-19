package com.mrrun.lib.mvpbase.dagger;

import android.content.Context;

import com.mrrun.lib.mvpbase.app.MvpApplication;
import com.mrrun.lib.mvpbase.app.Navigator;
import com.mrrun.lib.mvpbase.dagger.scope.ApplicationScope;
//import com.mrrun.lib.mvpbase.executor.MRBackgroundExecutor;
//import com.mrrun.lib.mvpbase.executor.ThreadPool;
//import com.mrrun.lib.mvpbase.executor.base.BackgroundExecutor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lipin on 2017/10/2.
 * 说明：在具体的项目中继承ApplicationModule，编写符合实际的类与接口，这里提供一些必要的模板方法；
 * 当然你也可以复制到项目中直接使用它们。
 */

@Module
public class ApplicationModule {

    private final MvpApplication mApplication;

    public ApplicationModule(MvpApplication application) {
        this.mApplication = application;
    }

    @ApplicationScope
    @Provides
    Context provideApplicationContext() {
        return this.mApplication;
    }

//    @ApplicationScope
//    @Provides
//    public BackgroundExecutor provideBackgroundExecutor() {
//        return new MRBackgroundExecutor(ThreadPool.getThreadPool());
//    }

    @ApplicationScope
    @Provides
    public Navigator provideNavigator() {
        return new Navigator();
    }
}
