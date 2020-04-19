package com.mrrun.lib.mvpbase.executor.base;

public interface BackgroundExecutor {

	<R> void execute(NetworkCallRunnable<R> runnable);

    <R> void execute(BackgroundCallRunnable<R> runnable);
}
