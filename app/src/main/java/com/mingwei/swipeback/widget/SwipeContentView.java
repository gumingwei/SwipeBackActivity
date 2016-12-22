package com.mingwei.swipeback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by mingwei on 12/23/16.
 */
public class SwipeContentView extends FrameLayout {

    public SwipeContentView(Context context) {
        this(context, null);
    }

    public SwipeContentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
