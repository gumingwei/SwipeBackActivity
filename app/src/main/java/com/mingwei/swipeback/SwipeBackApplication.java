package com.mingwei.swipeback;

import android.app.Application;

/**
 * Created by mingwei on 12/23/16.
 */
public class SwipeBackApplication extends Application {

    private ActivityBackStack mBackStack;

    @Override
    public void onCreate() {
        super.onCreate();
        mBackStack = new ActivityBackStack();
        registerActivityLifecycleCallbacks(mBackStack);
    }

    public ActivityBackStack getBackHelper() {
        return mBackStack;
    }
}
