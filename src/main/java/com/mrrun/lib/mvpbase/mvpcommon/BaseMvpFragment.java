package com.mrrun.lib.mvpbase.mvpcommon;

import android.os.Bundle;

import com.mrrun.lib.androidbase.base.fragment.BaseFragment;
import com.mrrun.lib.mvpbase.app.MvpApplication;
import com.mrrun.lib.mvpbase.app.Navigator;
import com.mrrun.lib.mvpbase.dagger.ActivityComponent;
import com.mrrun.lib.mvpbase.dagger.ApplicationComponent;
import com.mrrun.lib.mvpbase.dagger.HasComponent;

import javax.inject.Inject;

public abstract class BaseMvpFragment
		<V extends MvpView, P extends MvpPresenter<V>, C extends ActivityComponent>
		extends BaseFragment implements MvpView, HasComponent<C> {

	protected C mComponent;
	@Inject
	protected P mPresenter;
	@Inject
	protected Navigator mNavigator;
	/**
	 * 是否保存View实例
	 */
	protected boolean mRetainView = false;
	/**
	 * 是MVP模式Fragment还是普通Fragment
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
	public void onCreate(Bundle savedInstanceState) {
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
	public void onDestroy() {
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

	@Override
	public C getComponent() {
		mComponent = ((HasComponent<C>)getActivity()).getComponent();
		return mComponent;
	}

	protected ApplicationComponent getApplicationComponent() {
		return ((MvpApplication)getActivity().getApplication()).getApplicationComponent();
	}
}
