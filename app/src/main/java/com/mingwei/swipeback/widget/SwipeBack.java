package com.mingwei.swipeback.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by mingwei on 12/23/16.
 */
public class SwipeBack extends RelativeLayout {

    private SwipeBehindView mBehindView;

    private SwipeAboveView mAboveView;

    public SwipeBack(Context context) {
        this(context, null);
    }

    public SwipeBack(Activity activity) {
        this(activity, null);
        attachToActivity(activity);
    }

    public SwipeBack(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBack(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutParams behindLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mBehindView = new SwipeBehindView(context);
        addView(mBehindView, behindLayoutParams);

        LayoutParams contentLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mAboveView = new SwipeAboveView(context);
        mAboveView.setBackgroundColor(Color.RED);
        addView(mAboveView, contentLayoutParams);
    }

    private void attachToActivity(Activity activity) {
        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decor.removeView(decorChild);
        decor.addView(this);
        setContent(decorChild);
    }

    public void setContent(int id) {
        setContent(LayoutInflater.from(getContext()).inflate(id, null));
    }

    public void setContent(View view) {
        mAboveView.setContent(view);
    }

    public void setBehindView(View view) {
        //mBehindView.setContent(view);
    }


    public void setA() {
        mAboveView.setX(100);
    }

}
