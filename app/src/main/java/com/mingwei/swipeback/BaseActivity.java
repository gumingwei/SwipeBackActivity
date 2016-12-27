package com.mingwei.swipeback;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

/**
 * Created by mingwei on 12/23/16.
 */
public class BaseActivity extends AppCompatActivity {

    private SwipeBackHelper mBackHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBackHelper = new SwipeBackHelper(getWindow());
    }

    public SwipeBackHelper getHelper() {
        return mBackHelper;
    }

    @Override
    protected void onStop() {
        //mBackHelper.removePreviousView();
        super.onStop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mBackHelper.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }
}