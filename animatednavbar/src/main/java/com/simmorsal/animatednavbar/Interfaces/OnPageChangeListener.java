package com.simmorsal.animatednavbar.Interfaces;

public interface OnPageChangeListener {
    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onPageSelected(int position);

    void onPageScrollStateChanged(int state);
}
