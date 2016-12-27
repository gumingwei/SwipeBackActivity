package com.mingwei.swipeback;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Stack;

/**
 * Created by mingwei on 12/22/16.
 */
public class ActivityBackStack implements Application.ActivityLifecycleCallbacks {

    private Stack<Activity> mActivityStack;

    public ActivityBackStack() {
        mActivityStack = new Stack<>();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mActivityStack.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
        }
    }

    /**
     * 获取栈顶的Activity
     *
     * @return
     */
    public Activity getLastActivity() {
        return mActivityStack.lastElement();
    }

    /**
     * 获取倒数第二个Activity
     *
     * @return
     */
    public Activity getPreActivty() {
        int size = mActivityStack.size();
        if (size < 2) {
            return null;
        }
        return mActivityStack.get(size - 2);
    }
}
