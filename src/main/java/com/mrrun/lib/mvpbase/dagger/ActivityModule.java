package com.mrrun.lib.mvpbase.dagger;

import android.app.Activity;

import com.mrrun.lib.mvpbase.dagger.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lipin on 2017/10/2.
 */

@Module
public class ActivityModule {

    protected final Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     * 给构造函数需要Activity参数的对象提供参数
     */
    @ActivityScope
    @Provides
    Activity activity() {
        return this.mActivity;
    }
}
