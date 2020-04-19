package com.mrrun.lib.mvpbase.dagger;

import com.mrrun.lib.mvpbase.dagger.scope.ActivityScope;

import dagger.Component;

/**
 * Created by lipin on 2017/10/2.
 */

@ActivityScope
@Component (modules = {ActivityModule.class},
        dependencies = ApplicationComponent.class)
public interface MainComponent extends ActivityComponent{
}
