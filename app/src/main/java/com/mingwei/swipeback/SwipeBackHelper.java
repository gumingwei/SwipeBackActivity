package com.mingwei.swipeback;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

/**
 * Created by mingwei on 12/23/16.
 */
public class SwipeBackHelper {

    private Window mWindows;

    private Activity mCurrentActivity;

    private Activity mPreviousActivity;

    private FrameLayout mCurrentContentView;

    private View mPreviousContentView;

    public SwipeBackHelper(Window window) {
        mWindows = window;
        mCurrentContentView = getContentView(mWindows);
    }

    /**
     * 把Activity栈中的上一个Activity的ContentView拿出来添加到当前的Activity的ContentView中
     */
    public void addPreviousView() {
        if (mCurrentContentView.getChildCount() == 0) {
            mCurrentActivity = null;
            mPreviousActivity = null;
        }

        SwipeBackApplication application = (SwipeBackApplication) mWindows.getContext().getApplicationContext();
        mPreviousActivity = application.getBackHelper().getPreActivty();
        if (mPreviousActivity == null) {
            mCurrentActivity = null;
            mPreviousActivity = null;
            return;
        }
        ViewGroup previousContainer = getContentView(mPreviousActivity.getWindow());
        if (previousContainer == null || previousContainer.getChildCount() == 0) {
            return;
        }
        mPreviousContentView = previousContainer.getChildAt(0);
        previousContainer.removeView(mPreviousContentView);
        mCurrentContentView.addView(mPreviousContentView, 0);
    }

    /**
     * 退出时要把之前添加在当前Acticity的上一个Activity的ContentView移除并归还给之前的容器
     */
    public void removePreviousView() {
        if (mPreviousContentView == null) {
            return;
        }
        View view = mPreviousContentView;
        view.setX(0);
        mCurrentContentView.removeView(view);
        if (mPreviousActivity == null || mPreviousActivity.isFinishing()) {
            return;
        }
        ViewGroup previousContainer = getContentView(mPreviousActivity.getWindow());
        previousContainer.addView(view);
        mPreviousContentView = null;
        mPreviousActivity = null;
    }

    /**
     * 根据{@android.R.id.content}从Window中获取Activity的根局部
     *
     * @param window
     * @return
     */
    private FrameLayout getContentView(Window window) {
        if (window == null) return null;
        return (FrameLayout) window.findViewById(Window.ID_ANDROID_CONTENT);
    }

    public int getSize() {
        return mCurrentContentView.getChildCount();
    }
}
