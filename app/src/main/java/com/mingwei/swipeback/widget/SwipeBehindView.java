package com.mingwei.swipeback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by mingwei on 12/23/16.
 */
public class SwipeBehindView extends ViewGroup {

    public SwipeBehindView(Context context) {
        super(context);
    }

    public SwipeBehindView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeBehindView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }


}
