package com.mrrun.lib.mvpbase.mvpcommon;

import com.mrrun.lib.mvpbase.dagger.HasComponent;
import com.mrrun.lib.mvpbase.dagger.MainComponent;

/**
 * Created by lipin on 2017/10/2.
 */

public class MainActivity extends
        BaseMvpActivity<MvpView, MvpPresenter<MvpView>, MainComponent> implements MvpView, HasComponent<MainComponent> {

    @Override
    public MvpPresenter createPresenter() {
        return null;
    }

    @Override
    protected void injectDependencies() {
    }

    @Override
    protected void setupViewAndData() {

    }

    @Override
    protected int layoutId() {
        return 0;
    }
}
