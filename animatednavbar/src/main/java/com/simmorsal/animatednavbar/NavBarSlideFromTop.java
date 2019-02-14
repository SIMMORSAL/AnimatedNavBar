package com.simmorsal.animatednavbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class NavBarSlideFromTop extends NavView {


    private Paint paintBackgroundUnder = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintBackgroundOver = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintIcon = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintTitle = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF rectFBackgroundOver = new RectF();
    private RectF rectFIcon = new RectF();
    private RectF rectFTitle = new RectF();

    private Bitmap icon = null;
    private String title = null;

    private boolean isFirstRunPassed = false;


    public NavBarSlideFromTop(Context context) {
        super(context);

        initializeStuff();
    }

    public NavBarSlideFromTop(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initializeStuff();
    }

    private void initializeStuff() {
        paintBackgroundUnder.setColor(Color.DKGRAY);
        paintBackgroundOver.setColor(Color.MAGENTA);
        paintIcon.setColor(Color.WHITE);
        paintTitle.setColor(Color.BLACK);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        isFirstRunPassed = true;


        rectFBackgroundOver.set(0, 0, widthMeasureSpec, heightMeasureSpec);

        int shortestBorder = heightMeasureSpec < widthMeasureSpec ? heightMeasureSpec : widthMeasureSpec;
        int longestBorder = heightMeasureSpec > widthMeasureSpec ? heightMeasureSpec : widthMeasureSpec;

        int widthIcon;
        if (icon != null) {
            if (title == null) {
                widthIcon = (int) (shortestBorder * .75f);
                rectFIcon.set(
                        (widthMeasureSpec - widthIcon) / 2f,
                        (heightMeasureSpec - widthIcon) / 2f,
                        ((widthMeasureSpec - widthIcon) / 2f) + widthIcon,
                        ((heightMeasureSpec - widthIcon) / 2f) + widthIcon
                );
            } else {
                widthIcon = (int) (shortestBorder * .4f);
                rectFIcon.set(
                        (widthMeasureSpec - widthIcon) / 2f,
                        heightMeasureSpec * .12f,
                        ((widthMeasureSpec - widthIcon) / 2f) + widthIcon,
                        (heightMeasureSpec * .12f) + widthIcon
                );
            }
        }

        if (title != null && !title.isEmpty()) {
            // int widthTitle = (int) (widthMeasureSpec * .9f);
            if (icon == null) {
                rectFTitle.set(
                        widthMeasureSpec * .05f,
                        heightMeasureSpec * .2f,
                        widthMeasureSpec * .95f,
                        heightMeasureSpec * .8f
                );
            } else {
                rectFTitle.set(
                        widthMeasureSpec * .05f,
                        heightMeasureSpec * .6f,
                        widthMeasureSpec * .95f,
                        heightMeasureSpec * .88f
                );
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void onViewPagerScroll(float scroll) {

    }

    ////////////////////////////////////////////////////////////////////////////////
    //                    //
    //   PUBLIC METHODS   //
    //                    //
    ////////////////////////////////////////////////////////////////////////////////

    public NavBarSlideFromTop setTypeface(Typeface typeface) {
        paintTitle.setTypeface(typeface);
        if (isFirstRunPassed)
            invalidate();
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NavBarSlideFromTop setTitle(String title) {
        if (title != null && !title.trim().isEmpty()) {
            boolean requiresRemeasure = this.title == null || this.title.isEmpty();

            this.title = title;

            if (isFirstRunPassed) {
                if (requiresRemeasure)
                    requestLayout();
                else
                    invalidate();
            }
        }
        return this;
    }

    public NavBarSlideFromTop setIcon(int resourceId) {
        return setIcon(BitmapFactory.decodeResource(getResources(), resourceId));
    }

    public Bitmap getIcon() {
        return icon;
    }

    public NavBarSlideFromTop setIcon(Bitmap icon) {
        if (icon != null) {
            boolean requiresRemeasure = this.icon == null;

            this.icon = icon;

            if (isFirstRunPassed) {
                if (requiresRemeasure)
                    requestLayout();
                else
                    invalidate();
            }
        }
        return this;
    }

    public NavBarSlideFromTop setBackgroundOverColor(int color) {
        paintBackgroundOver.setColor(color);
        if (isFirstRunPassed)
            invalidate();
        return this;
    }

    public NavBarSlideFromTop setBackgroundUnderColor(int color) {
        paintBackgroundUnder.setColor(color);
        if (isFirstRunPassed)
            invalidate();
        return this;
    }

    public NavBarSlideFromTop setTitleColor(int color) {
        paintTitle.setColor(color);
        if (isFirstRunPassed)
            invalidate();
        return this;
    }

    public NavBarSlideFromTop setIconColor(int color) {
        paintIcon.setColor(color);
        if (isFirstRunPassed)
            invalidate();
        return this;
    }
}




















