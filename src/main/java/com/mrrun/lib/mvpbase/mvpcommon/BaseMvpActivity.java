package com.mrrun.lib.mvpbase.mvpcommon;

import android.os.Bundle;

import com.mrrun.lib.androidbase.base.activity.BaseActivity;
import com.mrrun.lib.mvpbase.app.MvpApplication;
import com.mrrun.lib.mvpbase.app.Navigator;
import com.mrrun.lib.mvpbase.dagger.ActivityComponent;
import com.mrrun.lib.mvpbase.dagger.ActivityModule;
import com.mrrun.lib.mvpbase.dagger.ApplicationComponent;
import com.mrrun.lib.mvpbase.dagger.HasComponent;

import javax.inject.Inject;

public abstract class BaseMvpActivity
		<V extends MvpView, P extends MvpPresenter<V>, C extends ActivityComponent>
		extends BaseActivity implements MvpView, HasComponent<C> {

	protected C mComponent;
    // 用@Inject注解创建对象来源有两种方式，先到Module中找，没有找到会找有@Inject的构造函数。
	@Inject
	protected P mPresenter;
	@Inject
	protected Navigator mNavigator;
	/**
	 * 是否保存View实例
	 */
	protected boolean mRetainView = false;
	/**
	 * 是MVP模式Activity还是普通Activity
	 */
	protected boolean isMvpMode = false;
	/**
	 * <b>方法功能描述:</b><br>
	 * 用于创建Presenter和判断是否使用MVP模式(由子类实现)
	 * 
	 * @return P
	 */
	public abstract P createPresenter();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		injectDependencies();
		super.onCreate(savedInstanceState);
		setRetainView(mRetainView);
		mPresenter = createPresenter();
		isMvpMode();
	}

	/**
	 * 判断是MVP模式Activity还是普通Activity
	 */
	private void isMvpMode() {
        isMvpMode = mPresenter != null;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null != mPresenter) {
			mPresenter.detachView(mRetainView);
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 注入依赖关系
	 */
	protected abstract void injectDependencies();

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取是否保留View实例
	 * 
	 * @return
	 */
	public final boolean isRetainView() {
		return mRetainView;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 设置是否在Presenter中保留View实例
	 * 
	 * @param retainView
	 */
	public final void setRetainView(boolean retainView) {
		this.mRetainView = retainView;
	}

	/**
	 * 这是给Fragment用的,目的获取Component组件注入Fragment
	 *
	 * @return
	 */
	@Override
	public C getComponent() {
		return mComponent;
	}

	/**
     * 获得应用Component
	 *
	 * @return
     */
	protected ApplicationComponent getApplicationComponent() {
		return ((MvpApplication) getApplication()).getApplicationComponent();
	}

	/**
     * 获得该Activity的Module
	 *
	 * @return
     */
	protected ActivityModule getActivityModule() {
		return new ActivityModule(this);
	}
}
