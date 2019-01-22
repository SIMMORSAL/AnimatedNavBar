package com.simmorsal.animatednavbar;

import android.content.res.Resources;

class Tools {
    static public int dpToPx(float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    static public int pxToDp(float dp) {
        return (int) (dp / Resources.getSystem().getDisplayMetrics().density);
    }
}
