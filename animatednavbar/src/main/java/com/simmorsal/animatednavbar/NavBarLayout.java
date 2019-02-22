package com.simmorsal.animatednavbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.simmorsal.animatednavbar.Interfaces.OnPageChangeListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class NavBarLayout extends LinearLayout {


    private List<NavView> listNavView = new ArrayList<>();
    private ViewPager viewPager;
    private boolean isViewPagerChangeListenerSet = false;
    private boolean isPageChanging = false;
    private boolean hasGettingViewsFromXMLFinished = false;
    private int indexDefaultTab = 0;
    private int indexCurrentTab;
    private int indexLastTab;
    private int lastSwipedToPosition = -1;
    private int lastSwipedToPositionCached = -1;

    private OnPageChangeListener onPageChangeListener;


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

    private void initializeWithAttrs(AttributeSet attrs) {
        // attrs:
        // defaultTab

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);

//                int listSizeBeforeXML = listNavView.size();
//                int count = 0;
//
//                List<NavView> list = new ArrayList<>(listNavView);
                listNavView.clear();

                for (int i = 0; i < getChildCount(); i++) {
                    if (getChildAt(i) instanceof NavView) {
                        ((NavView) getChildAt(i)).setPosition(listNavView.size());
                        listNavView.add((NavView) getChildAt(i));
//                        count++;
                    }
                }

//                Log.i("11111", "NavBarLayout => onGlobalLayout: " + listNavView.size());

                // moving all previously added items added by `addNavView` to the end of the `listNavView`
//                for (int i = 0; i < listSizeBeforeXML; i++) {
//                    Log.i("11111", "NavBarLayout => onGlobalLayout: " + listNavView.get(0).getPosition() + "  " + count);
//                    listNavView.get(0).setPosition(listNavView.get(0).getPosition() + count);
//                    listNavView.add(listNavView.get(0));
//                    listNavView.remove(0);
//                }
//                for (int i = 0; i < list.size(); i++) {
//                    NavView navView = list.get(i);
//                    navView.setPosition(listNavView.size());
//                    listNavView.add(navView);
//                    Log.i("11111", "NavBarLayout => onGlobalLayout: " + "HAPPENS");
//                }

//                Log.i("11111", "NavBarLayout => onGlobalLayout: " + "SIZE: " + listNavView.size() + "  " + list.size());
//                for (int i = 0; i < listNavView.size(); i++)
//                    Log.i("11111", "NavBarLayout => onGlobalLayout: " + listNavView.get(i).getId());

                hasGettingViewsFromXMLFinished = true;
                initializeViewPagerBehaviour();
            }
        });

    }

    private void initializeViewPagerBehaviour() {
        if (!isViewPagerChangeListenerSet && !listNavView.isEmpty() && viewPager != null && hasGettingViewsFromXMLFinished) {
            isViewPagerChangeListenerSet = true;
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (onPageChangeListener != null)
                        onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);


                    if (!isPageChanging) {
                        if (position != listNavView.size() - 1) {
                            listNavView.get(position).onViewPagerScroll(1f - positionOffset);
                            listNavView.get(position + 1).onViewPagerScroll(positionOffset);
                        }
                    } else if (positionOffset == 0.0)
                        isPageChanging = false;
                }

                @Override
                public void onPageSelected(int position) {
                    if (onPageChangeListener != null)
                        onPageChangeListener.onPageSelected(position);

                    listNavView.get(indexLastTab).deactivate(true);
                    listNavView.get(position).activate(true);
                    indexLastTab = position;
                    isPageChanging = true;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (onPageChangeListener != null)
                        onPageChangeListener.onPageScrollStateChanged(state);

                }
            });


            // starting the default tab
            viewPager.setCurrentItem(indexDefaultTab, false);
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
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;

        navView.setLayoutParams(layoutParams);
        addView(navView);

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

    public NavBarLayout addOnPageChangeListener(OnPageChangeListener l) {
        onPageChangeListener = l;
        return this;
    }
}





















