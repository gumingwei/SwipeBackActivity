package com.mingwei.swipeback;

import android.app.Application;

/**
 * Created by mingwei on 12/23/16.
 */
public class SwipeBackApplication extends Application {

    private SwipeBackHelper mBackHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        mBackHelper = new SwipeBackHelper();
    }

    public SwipeBackHelper getBackHelper() {
        return mBackHelper;
    }
}
