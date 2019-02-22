package com.simmorsal.animatednavbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class NavBarRoundedTop extends NavView {

    private int position;
    private TypedArray attr;
    private Paint paintBlock;
    private Paint paintText;
    private Paint paintIcon;
    private Path pathRoundTop;
    private float shareInactiveLayout;

    ////////////////////////////////////////////////////////////////////////////////////
    //   LOGIC   //
    ////////////////////////////////////////////////////////////////////////////////////
    private float shareImage;
    private float shareArc;
    private boolean showSideDividers;
    private int colorActive;
    private int colorInactive;
    private RectF rectFImage;
    private String title = "";
    private int sizeTitle;
    private Bitmap icon;
    private RectF oval;
    private float strokeWidth;

    public NavBarRoundedTop(Context context) {
        super(context);
    }

    public NavBarRoundedTop(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        attr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NavBarRoundedTop, 0, 0);
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        initializeStuff();
        measureStuff();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        doDrawStuff(canvas);
        super.onDraw(canvas);
    }

    private void initializeStuff() {
        paintBlock = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintIcon = new Paint(Paint.ANTI_ALIAS_FLAG);

        shareInactiveLayout = .40f;
        shareImage = .25f;
        shareArc = .3f;


//        Log.i("11111", "NavBarRoundedTop => initializeStuff: attr " + attr.getDimension(R.styleable.NavBarRoundedTop_strokeWidth, 2));
        try {
            strokeWidth = attr.getDimension(R.styleable.NavBarRoundedTop_strokeWidth, 2);
            if (strokeWidth != 2)
                strokeWidth = Tools.pxToDp(strokeWidth);
//            Log.i("11111", "NavBarRoundedTop => initializeStuff: " + attr.getBoolean(R.styleable.NavBarRoundedTop_showSideDividers, false));
            showSideDividers = attr.getBoolean(R.styleable.NavBarRoundedTop_showSideDividers, false);
        } catch (Exception e) {
            e.printStackTrace();
        }


        colorActive = ContextCompat.getColor(getContext(), R.color.activated);
        colorInactive = ContextCompat.getColor(getContext(), R.color.deactivated);

        paintBlock.setColor(colorActive);
        paintText.setColor(colorActive);
        paintIcon.setColor(colorActive);

//        paintBlock.setColorFilter(new PorterDuffColorFilter(colorActive, PorterDuff.Mode.MULTIPLY));
        paintBlock.setStyle(Paint.Style.STROKE);
        paintBlock.setStrokeWidth(Tools.dpToPx(strokeWidth));
    }

    private void measureStuff() {
        // setting text
        paintText.setTextSize(getHeight() * .15f);

        // setting arc
        pathRoundTop = new Path();
        float offset = Tools.dpToPx(strokeWidth / 2);
        oval = new RectF(offset, offset, getMeasuredWidth() - offset, (getMeasuredHeight() * 2) * shareArc/* getMeasuredWidth()*/);
        pathRoundTop.addArc(oval, 180, 180);
//        pathRoundTop.arcTo(oval, 0, 360);

        // setting imageSize
        if (getHeight() * shareImage < getWidth())
            rectFImage = new RectF(0, 0, getHeight() * shareImage, getHeight() * shareImage);
        else rectFImage = new RectF(0, 0, getWidth() * .9f, getWidth() * .9f);
    }

    private void doDrawStuff(Canvas canvas) {
        if (showSideDividers) {
//            canvas.drawLine();
        }

        canvas.drawPath(pathRoundTop, paintBlock);
//        canvas.drawLine(0, 0 , getWidth(), getHeight(), paintBlock);
//        canvas.path
//        canvas.drawArc(oval, 180, 180, false, paintBlock);
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

    ////////////////////////////////////////////////////////////////////////////////
    //                    //
    //   PUBLIC METHODS   //
    //                    //
    ////////////////////////////////////////////////////////////////////////////////

    public NavBarRoundedTop setTypeface(Typeface typeface) {
        paintText.setTypeface(typeface);
        invalidate();
        return this;
    }

    public NavBarRoundedTop showSideDividers(boolean show) {
        showSideDividers = show;
        invalidate();
        return this;
    }

    public NavBarRoundedTop setTitle(String title) {
        this.title = title;
        sizeTitle = (int) paintText.measureText(title);
        invalidate();
        return this;
    }

    public NavBarRoundedTop setIcon(Bitmap icon) {
        this.icon = icon;
        invalidate();
        return this;
    }

    public NavBarRoundedTop setStrokeWidth(float dp) {
        strokeWidth = dp;
        paintBlock.setStrokeWidth(Tools.dpToPx(dp));
        float offset = Tools.dpToPx(dp / 2f);
        oval = new RectF(offset, offset, getMeasuredWidth() - offset, (getMeasuredHeight() * 2) * shareArc);
        pathRoundTop.reset();
        pathRoundTop.addArc(oval, 180, 180);
        invalidate();
        return this;
    }
}











