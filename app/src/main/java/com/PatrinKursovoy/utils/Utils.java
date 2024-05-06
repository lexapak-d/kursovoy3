package com.PatrinKursovoy.utils;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

/**
 * Утилита для помощи в работе с вью
 */

public class Utils {

    public static void setVisibleView(View view, boolean isVisible) {
        int visibleId;

        if (isVisible) {
            visibleId = View.VISIBLE;
        } else {
            visibleId = View.GONE;
        }

        view.setVisibility(visibleId);
    }

    public static void removeFlickerList(RecyclerView recyclerView) {
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }
}
