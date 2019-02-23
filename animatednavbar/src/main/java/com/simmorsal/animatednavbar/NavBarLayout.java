package com.simmorsal.animatednavbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.simmorsal.animatednavbar.Interfaces.OnNavViewClickListener;
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
    private int indexLastTab;

    private OnPageChangeListener onPageChangeListener;
    private OnNavViewClickListener onNavViewClickListener;


    public NavBarLayout(Context context) {
        super(context);

        initialize();

        hasGettingViewsFromXMLFinished = true;
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

                // so that if a view is added to this layout with #addNavView, it would be removed
                // the list and added again within the for loop below.
                listNavView.clear();

                for (int i = 0; i < getChildCount(); i++) {
                    if (getChildAt(i) instanceof NavView) {
                        NavView navView = (NavView) getChildAt(i);
                        (navView).setPosition(listNavView.size());
                        listNavView.add(navView);

                        final int position = i;
                        navView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    // used try catch instead of checking if the listener is null so that if
                                    // the user initialized it later, this would still work
                                    onNavViewClickListener.onClick((NavView) v, position, indexLastTab == position);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                if (indexLastTab != position) {
                                    if (viewPager != null) {
                                        viewPager.setCurrentItem(position, true);
                                    } else {
                                        listNavView.get(indexLastTab).deactivate(true);
                                        listNavView.get(position).activate(true);
                                        indexLastTab = position;
                                    }
                                }
                            }
                        });
                    }
                }


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
                        listNavView.get(position).onViewPagerScroll(1f - positionOffset);
                        if (position != listNavView.size() - 1) {
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

        if (hasGettingViewsFromXMLFinished) {
            navView.setPosition(listNavView.size());
            listNavView.add(navView);

            final int position = navView.getPosition();
            navView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (indexLastTab != position) {
                        if (viewPager != null) {
                            viewPager.setCurrentItem(position, true);
                        } else {
                            listNavView.get(indexLastTab).deactivate(true);
                            listNavView.get(position).activate(true);
                            indexLastTab = position;
                        }
                    }

                    try {
                        onNavViewClickListener.onClick((NavView) v, position, indexLastTab == position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
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

    public NavBarLayout setOnNavViewClickListener(OnNavViewClickListener l) {
        onNavViewClickListener = l;
        return this;
    }
}





















