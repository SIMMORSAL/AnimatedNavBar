package com.simmorsal.animatednavbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class NavBarSlideFromTop extends NavView {

    int width;
    int height;
    private int animationPace;
    private Long animationChangeSpeed = 16L;
    private int titleSize = 15;
    private int position;
    private int animationSpeed = 200;
    private int sizeIcon = -1;

    private Paint paintBackgroundUnder = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintBackgroundOver = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintIcon = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintTitle = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
    private RectF rectFBackgroundOver = new RectF();
    private RectF rectFIcon = new RectF();
    private Rect rectTitle = new Rect();
    private Path pathTitle = new Path();

    private Bitmap icon = null;
    private String title = null;

    private boolean isActive = false;
    private boolean isAnimating = false;
    private boolean isFirstRunPassed = false;
    private int bottomBGOver = -1;

    private float posTitleX;
    private float posTitleY;

    private Handler handlerAnimation = new Handler(Looper.getMainLooper());

    public NavBarSlideFromTop(Context context) {
        super(context);

        initializeStuff();
    }

    public NavBarSlideFromTop(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initializeStuff();
    }
    private Runnable runnableActivation = new Runnable() {
        @Override
        public void run() {
            bottomBGOver += animationPace;
            rectFBackgroundOver.bottom = bottomBGOver;
            invalidate();
            if (rectFBackgroundOver.bottom < getMeasuredHeight())
                handlerAnimation.postDelayed(this, animationChangeSpeed);
            else
                isAnimating = false;
        }
    };

    private void initializeStuff() {
        paintBackgroundUnder.setColor(Color.DKGRAY);
        paintBackgroundOver.setColor(Color.MAGENTA);
        paintIcon.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP));
        paintTitle.setColor(Color.WHITE);
        paintTitle.setFakeBoldText(true);

        // setting proper animation change speed based on phone's frame rate
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            animationChangeSpeed = (long) Math.round(1000f / windowManager.getDefaultDisplay().getRefreshRate());
        }
    }

    private Runnable runnableDeactivation = new Runnable() {
        @Override
        public void run() {
            bottomBGOver -= animationPace;
            rectFBackgroundOver.bottom = bottomBGOver;
            invalidate();
            if (rectFBackgroundOver.bottom > 0)
                handlerAnimation.postDelayed(this, animationChangeSpeed);
            else
                isAnimating = false;
        }
    };

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getMeasuredWidth();
        height = getMeasuredHeight();
        int shortestBorder = height < width ? height : width;
        int longestBorder = height > width ? height : width;


        animationPace = (int) (height / ((float) animationSpeed / (float) animationChangeSpeed));

        // the reason im performing this check is that the parent, which is most likely the NavBarLayout, extended from LinearLayout
        // for some reason calls onMeasure on itself, which causes this onMeasure to be called as well,
        if (bottomBGOver == -1 || rectFBackgroundOver.right == 0.0) {
            if (isActive) {
                rectFBackgroundOver.set(0, 0, width, height);
                bottomBGOver = getMeasuredHeight();
            } else {
                rectFBackgroundOver.set(0, 0, width, 0);
                bottomBGOver = 0;
            }
        }

        int widthIcon;
        if (icon != null) {
            if (title == null) {
                widthIcon = (int) (shortestBorder * .75f);
                if (sizeIcon != -1 && sizeIcon < widthIcon)
                    widthIcon = sizeIcon;
                rectFIcon.set(
                        (width - widthIcon) / 2f,
                        (height - widthIcon) / 2f,
                        ((width - widthIcon) / 2f) + widthIcon,
                        ((height - widthIcon) / 2f) + widthIcon
                );
            } else {
                widthIcon = (int) (shortestBorder * .4f);
                if (sizeIcon != -1 && sizeIcon < widthIcon)
                    widthIcon = sizeIcon;
                rectFIcon.set(
                        (width - widthIcon) / 2f,
                        height * .12f,
                        ((width - widthIcon) / 2f) + widthIcon,
                        (height * .12f) + widthIcon
                );
            }
        }

        if (title != null && !title.isEmpty()) {

            paintTitle.getTextBounds("a", 0, 1, rectTitle);

            posTitleY = height - ((height - (rectTitle.bottom - rectTitle.top)) / 2f);
            if (icon != null) {
                posTitleY += rectFIcon.height() / 2;
            }

            paintTitle.setTextSize(Tools.spToPx(titleSize));
            posTitleX = (width - paintTitle.measureText(title)) / 2;
            pathTitle.addRect(rectTitle.left, rectTitle.top, rectTitle.right, rectTitle.bottom, Path.Direction.CCW);


            isFirstRunPassed = true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paintBackgroundUnder);
        canvas.drawRect(rectFBackgroundOver, paintBackgroundOver);
        if (icon != null) {
            canvas.drawBitmap(icon, null, rectFIcon, paintIcon);
        }
        if (title != null) {
            canvas.drawText(title, posTitleX, posTitleY, paintTitle);
        }
    }

    @Override
    public void activate(boolean animate) {
        isActive = true;

        if (isAnimating)
            handlerAnimation.removeCallbacksAndMessages(null);

        if (animate) {
            isAnimating = true;
            handlerAnimation.post(runnableActivation);
        } else {
            rectFBackgroundOver.bottom = getMeasuredHeight();
            bottomBGOver = getMeasuredHeight();
            invalidate();
        }
    }

    @Override
    public void deactivate(boolean animate) {
        isActive = false;

        if (isAnimating)
            handlerAnimation.removeCallbacksAndMessages(null);

        if (animate) {
            isAnimating = true;
            handlerAnimation.post(runnableDeactivation);
        } else {
            rectFBackgroundOver.bottom = 0;
            bottomBGOver = 0;
            invalidate();
        }
    }

    @Override
    public void onViewPagerScroll(float scroll) {
        if (!isAnimating) {
            bottomBGOver = (int) (scroll * height);
            rectFBackgroundOver.bottom = bottomBGOver;
            invalidate();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    //                    //
    //   PUBLIC METHODS   //
    //                    //
    ////////////////////////////////////////////////////////////////////////////////

    public NavBarSlideFromTop setTitleSize(int titleSizeInPx) {
        this.titleSize = titleSizeInPx;
        requestLayout();
        return this;
    }

    public NavBarSlideFromTop setAnimationSpeed(int animationSpeed) {
        this.animationSpeed = animationSpeed;
        return this;
    }

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
                else {
                    posTitleX = (rectTitle.width() - paintTitle.measureText(title)) / 2;
                    invalidate();
                }
            }
        }
        return this;
    }

    public NavBarSlideFromTop setIcon(int resourceId) {
        return setIcon(ContextCompat.getDrawable(getContext(), resourceId));
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

    public NavBarSlideFromTop setIcon(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return setIcon(((BitmapDrawable) drawable).getBitmap());
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return setIcon(bitmap);
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

    public NavBarSlideFromTop setIconColorFilter(int color) {
        return setIconColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
    }

    public NavBarSlideFromTop setIconColorFilter(ColorFilter colorFilter) {
        paintIcon.setColorFilter(colorFilter);
        if (isFirstRunPassed)
            invalidate();
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public NavBarSlideFromTop setIconSize(int iconSizeDp) {
        sizeIcon = Tools.dpToPx(iconSizeDp);
        return this;
    }
}




















