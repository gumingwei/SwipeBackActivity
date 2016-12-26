package com.mingwei.swipeback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by mingwei on 12/23/16.
 */
public class SwipeAboveView extends FrameLayout {

    private View mContentView;

    public SwipeAboveView(Context context) {
        this(context, null);
    }

    public SwipeAboveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeAboveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setContent(View view) {
        if (mContentView != null) {
            removeAllViews();
        }
        mContentView = view;
        addView(mContentView);
    }
}
