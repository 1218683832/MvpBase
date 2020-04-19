package com.mrrun.lib.mvpbase.dagger;

import android.app.Activity;

import com.mrrun.lib.mvpbase.dagger.scope.ActivityScope;

import dagger.Component;

/**
 * Created by lipin on 2017/10/2.
 */

@ActivityScope
@Component (modules = {ActivityModule.class},
        dependencies = ApplicationComponent.class)
public interface ActivityComponent {

    Activity activity();
}
