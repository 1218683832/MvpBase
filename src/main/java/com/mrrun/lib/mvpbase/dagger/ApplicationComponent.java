package com.mrrun.lib.mvpbase.dagger;

import android.content.Context;

import com.mrrun.lib.mvpbase.app.Navigator;
import com.mrrun.lib.mvpbase.dagger.scope.ApplicationScope;

import dagger.Component;

/**
 * Created by lipin on 2017/10/2.
 * Application注入组件
 * 说明：在具体的项目中继承ApplicationComponent，编写符合实际的类与接口，这里提供一些必要的模板方法；
 * 当然你也可以复制到项目中直接使用它们。
 */
@ApplicationScope
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    /**
     * 提供Applicaiton
     *
     * @return
     */
    Context context();
//    /**
//     * 提供后台线程执行器
//     *
//     * @return
//     */
//    BackgroundExecutor backgroundExecutor();
//    /**
//     * 提供网络服务
//     *
//     * @return
//     */
//    NetService netService();
    /**
     * 提供界面导航(跳转)
     *
     * @return
     */
    Navigator navigator();
    // 提供消息事件总线
    // EventBus eventBus();
}
