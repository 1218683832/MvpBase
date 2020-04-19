package com.mrrun.lib.mvpbase.executor;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;

import com.mrrun.lib.mvpbase.executor.base.BackgroundCallRunnable;
import com.mrrun.lib.mvpbase.executor.base.BackgroundExecutor;
import com.mrrun.lib.mvpbase.executor.base.NetworkCallRunnable;

import retrofit.RetrofitError;

public class MRBackgroundExecutor implements BackgroundExecutor {

    private static final Handler sHandler = new Handler(Looper.getMainLooper());

    private final ThreadPool mThreadPool;

    public MRBackgroundExecutor(ThreadPool threadPool) {
        mThreadPool = threadPool;
    }

    // 使用代理的方式
    @Override
    public <R> void execute(NetworkCallRunnable<R> runnable) {
        mThreadPool.execute(new TraktNetworkRunner<R>(runnable));
    }

    // 使用代理的方式
    @Override
    public <R> void execute(BackgroundCallRunnable<R> runnable) {
        mThreadPool.execute(new BackgroundCallRunner<R>(runnable));
    }

    private class BackgroundCallRunner<R> implements Runnable {

        private final BackgroundCallRunnable<R> mBackgroundRunnable;

        BackgroundCallRunner(BackgroundCallRunnable<R> runnable) {
            mBackgroundRunnable = runnable;
        }

        @Override
        public final void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    mBackgroundRunnable.preExecute();
                }
            });

            R result = mBackgroundRunnable.runAsync();

            sHandler.post(new ResultCallback(result));
        }

        private class ResultCallback implements Runnable {
            private final R mResult;

            private ResultCallback(R result) {
                mResult = result;
            }

            @Override
            public void run() {
                mBackgroundRunnable.postExecute(mResult);
            }
        }
    }

    class TraktNetworkRunner<R> implements Runnable {

        private final NetworkCallRunnable<R> mBackgroundRunnable;

        TraktNetworkRunner(NetworkCallRunnable<R> runnable) {
            mBackgroundRunnable = runnable;
        }

        @Override
        public final void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

            // 进行网络请求之前的操作
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    mBackgroundRunnable.onPreTraktCall();
                }
            });

            R result = null;
            RetrofitError retrofitError = null;

            // 进行网络请求时的操作
            try {
                result = mBackgroundRunnable.doBackgroundCall();
            } catch (RetrofitError re) {
                retrofitError = re;
            }

            // 进行网络请求之后的操作
            sHandler.post(new ResultCallback(result, retrofitError));
        }

        // 网络请求结果
        private class ResultCallback implements Runnable {
            private final R mResult;
            private final RetrofitError mRetrofitError;

            private ResultCallback(R result, RetrofitError retrofitError) {
                mResult = result;
                mRetrofitError = retrofitError;
            }

            @Override
            public void run() {
                if (mResult != null) {
                    mBackgroundRunnable.onSuccess(mResult);
                } else if (mRetrofitError != null) {// 网络请求失败相关错误信息
                    mBackgroundRunnable.onError(mRetrofitError);
                }
                // 网络请求结束
                mBackgroundRunnable.onFinished();
            }
        }
    }
}
