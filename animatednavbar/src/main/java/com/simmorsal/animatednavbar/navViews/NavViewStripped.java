package com.simmorsal.animatednavbar.navViews;

import android.content.Context;
import android.util.AttributeSet;

import com.simmorsal.animatednavbar.NavView;

import androidx.annotation.Nullable;

public class NavViewStripped extends NavView {


    private int position;


    public NavViewStripped(Context context) {
        super(context);
    }

    public NavViewStripped(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void activate(boolean animate) {

    }

    @Override
    public void deactivate(boolean animate) {

    }

    @Override
    public void onViewPagerScroll(float scroll) {

    }
}
