package com.simmorsal.animatednavbar;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class NavBarStripped extends NavView {


    private int position;


    public NavBarStripped(Context context) {
        super(context);
    }

    public NavBarStripped(Context context, @Nullable AttributeSet attrs) {
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
