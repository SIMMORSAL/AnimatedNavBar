package com.simmorsal.animatednavbar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class NavBarLayout extends LinearLayout {


    private List<NavView> listNavView = new ArrayList<>();
    private ViewPager viewPager;
    private boolean isViewPagerChangeListenerSet = false;
    private boolean isPageChanging = false;
    private int indexDefaultTab = 0;
    private int indexCurrentTab;
    private int indexLastTab;
    private int lastSwipedToPosition = -1;
    private int lastSwipedToPositionCached = -1;


    public NavBarLayout(Context context) {
        super(context);

        initialize();
    }

    public NavBarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initialize();
        initializeWithAttrs(attrs);
    }

    private void initialize() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.i("11111", "NavBarLayout => onMeasure: " + "MEASURED LAYOUT");
    }

    private void initializeWithAttrs(AttributeSet attrs) {
        // attrs:
        // defaultTab

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);

                for (int i = 0; i < getChildCount(); i++) {
                    if (getChildAt(i) instanceof NavView) {
                        ((NavView) getChildAt(i)).setPosition(listNavView.size());
                        listNavView.add((NavView) getChildAt(i));
                    }
                }

                initializeViewPagerBehaviour();
            }
        });

    }

    private void initializeViewPagerBehaviour() {
        if (!isViewPagerChangeListenerSet && !listNavView.isEmpty() && viewPager != null) {
            isViewPagerChangeListenerSet = true;
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//                    if (positionOffset == 0) {
//                    for (int i = 0; i < listNavView.size(); i++)
//                        if (i != position)
//                            listNavView.get(i).onViewPagerScroll(0);
//                    }


//                    if (position == indexCurrentTab) {
//                        lastSwipedToPosition = position + 1;
//                    } else {
//                        lastSwipedToPosition = position;
//                    }
//
//                    if (lastSwipedToPositionCached != -1
//                            && lastSwipedToPositionCached != lastSwipedToPosition) {
//                        listNavView.get(lastSwipedToPositionCached).deactivate(false);
//                    }
                    if (!isPageChanging) {

                        listNavView.get(position).onViewPagerScroll(1f - positionOffset);
                        if (position != listNavView.size() - 1)
                            listNavView.get(position + 1).onViewPagerScroll(positionOffset);
//                    Log.i("11111", "NavBarLayout => onPageScrolled: " + positionOffset + "   " + position);
                    } else if (positionOffset == 0.0)
                        isPageChanging = false;
                }

                @Override
                public void onPageSelected(int position) {
                    // TODO add listener here to be used in MainActivity
                    listNavView.get(indexLastTab).deactivate(true);
                    listNavView.get(position).activate(true);
                    indexLastTab = position;
                    isPageChanging = true;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


            // starting the default tab
            listNavView.get(indexDefaultTab).activate(false);
            for (int i = 0; i < listNavView.size(); i++)
                if (i != indexDefaultTab)
                    listNavView.get(i).deactivate(false);
            indexLastTab = indexDefaultTab;
        }
    }


    ////////////////////////////////////////////////////////////////////////////////
    //                    //
    //   PUBLIC METHODS   //
    //                    //
    ////////////////////////////////////////////////////////////////////////////////

    public NavBarLayout addNavView(NavView navView) {
        // TODO create layout params and give it to the navView and add it to NavBarLayout
        navView.setPosition(listNavView.size());
        listNavView.add(navView);
        initializeViewPagerBehaviour();
        return this;
    }

    public NavBarLayout setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        initializeViewPagerBehaviour();
        return this;
    }

    public NavView getViewWithId(int id) {
        for (int i = 0; i < listNavView.size(); i++)
            if (listNavView.get(i).getId() == id)
                return listNavView.get(i);

        return null;
    }

    public NavBarLayout setDefaultTab(int position) {
        indexDefaultTab = position;
        return this;
    }
}





















