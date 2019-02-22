package com.simmorsal.animatednavbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public abstract class NavView extends View {
    public NavView(Context context) {
        super(context);
    }

    public NavView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NavView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NavView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    abstract public void setPosition(int position);

    abstract public void activate(boolean animate);

    abstract public void deactivate(boolean animate);

    abstract public void onViewPagerScroll(float scroll);
}
