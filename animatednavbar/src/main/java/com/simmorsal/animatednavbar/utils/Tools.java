package com.simmorsal.animatednavbar.utils;

import android.content.res.Resources;

public class Tools {
    static public int dpToPx(float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    static public int pxToDp(float dp) {
        return (int) (dp / Resources.getSystem().getDisplayMetrics().density);
    }

    static public int spToPx(float sp) {
        return (int) (sp * Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

    static public int pxToSp(float px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().scaledDensity);
    }
}
