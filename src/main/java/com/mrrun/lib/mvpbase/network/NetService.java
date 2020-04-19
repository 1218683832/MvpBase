package com.mrrun.lib.mvpbase.network;

import android.content.Context;

import com.mrrun.lib.mvpbase.executor.base.BackgroundExecutor;

import javax.inject.Inject;

/**
 * Created by mehao on 2017/8/22.
 */

public class NetService {

    private Context mContext;
    private final BackgroundExecutor mExecutor;

    @Inject
    public NetService(Context context, BackgroundExecutor executor) {
        mContext = context;
        mExecutor = executor;
    }
}
