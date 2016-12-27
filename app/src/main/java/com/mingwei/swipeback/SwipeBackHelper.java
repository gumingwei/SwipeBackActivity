package com.mingwei.swipeback;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

/**
 * Created by mingwei on 12/23/16.
 */
public class SwipeBackHelper {

    private final static String TAG = "Gmw";

    private Window mWindows;

    private Activity mCurrentActivity;

    private Activity mPreviousActivity;

    private FrameLayout mCurrentContentView;

    private View mPreviousContentView;

    private float mLastX;

    private float mDistanceX;

    private boolean isSliding;

    private int mWidth;

    private static final int DEFAULT_POINT_X = 40;

    private static final int DEFAULT_DURATION = 150;

    public SwipeBackHelper(Window window) {
        mWindows = window;
        mCurrentContentView = getContentView(mWindows);
        mWidth = mWindows.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 把Activity栈中的上一个Activity的ContentView拿出来添加到当前的Activity的ContentView中,并且Index=0
     * 即，在FrameLayout的最底层。
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

    public void drawPreviousView() {
        FrameLayout currentContentView = mCurrentContentView;
        View previousContentView = mPreviousContentView;
        DrawView drawView = new DrawView(getContext());
        currentContentView.addView(drawView, 0);
        drawView.setView(previousContentView);
    }

    /**
     * 根据{@android.R.id.content}从Window中获取Activity的根局部
     *
     * @param window
     * @return
     */
    private FrameLayout getContentView(Window window) {
        if (window == null) return null;
        return (FrameLayout) window.getDecorView();
    }

    private int getWindowBackgroundColor() {
        TypedArray array = null;
        try {
            Context context = getContext();
            array = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowBackground});
            return array.getColor(0, ContextCompat.getColor(context, android.R.color.transparent));
        } finally {
            if (array != null) {
                array.recycle();
            }
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = ev.getRawX();
                /**
                 * 判定是否在触发区域
                 */
                boolean canSlid = mLastX >= 0 && mLastX <= DEFAULT_POINT_X;
                if (canSlid && isSliding) {
                    return true;
                } else if (canSlid && !isSliding) {
                    /**
                     * 可滑动
                     */
                    isSliding = true;
                    /**
                     * 添加PreActivity的ContentView
                     */
                    addPreviousView();
                    /**
                     * 添加背景
                     */
                    if (mCurrentContentView.getChildCount() >= 2) {
                        View aboveView = getAboveView();
                        if (aboveView.getBackground() == null) {
                            aboveView.setBackgroundColor(getWindowBackgroundColor());
                        }
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                /**
                 * 第二个手指事件假加入。如果正在滑动则直接消费事件
                 */
                if (isSliding) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "isSliding=" + isSliding);
                if (isSliding) {
                    sliding(ev.getRawX());
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isSliding = false;
                Log.i(TAG, "up_x=" + mLastX);
                if (mDistanceX == 0) {
                    removePreviousView();
                } else if (mDistanceX > mWidth / 4) {
                    toggleAnimator(true);
                } else {
                    toggleAnimator(false);
                }
                break;
        }
        return false;
    }

    public int getSize() {
        return mCurrentContentView.getChildCount();
    }

    /**
     * 滑动
     *
     * @param x
     */
    private void sliding(float x) {
        View aboveView = getAboveView();
        View behindView = mPreviousContentView;
        float diffX = x - mLastX;
        mDistanceX = mDistanceX + diffX;
        mLastX = x;
        if (mDistanceX < 0) {
            mDistanceX = 0;
        }
        aboveView.setX(mDistanceX);
        behindView.setX((mDistanceX - mWidth) / 3);
    }

    /**
     * 获取最上层的View
     *
     * @return
     */
    private View getAboveView() {
        int index = mCurrentContentView.getChildCount() - 1;
        return mCurrentContentView.getChildAt(index);
    }

    private Context getContext() {
        return mWindows == null ? null : mWindows.getContext();
    }

    /**
     * args为真，关闭Activity
     *
     * @param args
     */
    private void toggleAnimator(final boolean args) {
        final View aboveView = getAboveView();
        final View behindView = mPreviousContentView;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mDistanceX, args ? mWidth : 0);
        valueAnimator.setDuration(DEFAULT_DURATION);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDistanceX = (float) animation.getAnimatedValue();
                aboveView.setX(mDistanceX);
                behindView.setX((mDistanceX - mWidth) / 3);
            }

        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (args) {
                    drawPreviousView();
                    removePreviousView();
                    if (getContext() instanceof Activity) {
                        Activity activity = (Activity) getContext();
                        activity.finish();
                        activity.overridePendingTransition(0, 0);
                    } else if (getContext() instanceof ContextWrapper) {
                        Context context = ((ContextWrapper) getContext()).getBaseContext();
                        if (context instanceof Activity) {
                            Activity activity = (Activity) context;
                            activity.finish();
                            activity.overridePendingTransition(0, 0);
                        }
                    }
                } else {

                }
            }
        });
        valueAnimator.start();
    }

    class DrawView extends View {

        private View mView;

        public DrawView(Context context) {
            super(context);
        }

        public void setView(View view) {
            mView = view;
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (mView != null) {
                mView.draw(canvas);
                mView = null;
            }
        }
    }
}
