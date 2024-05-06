package com.PatrinKursovoy.utils;

import android.os.SystemClock;
import android.view.View;

/**
 * Предотвращение двойного нажатия
 */

public class OnSingleClick implements View.OnClickListener {

    private static final long DEBOUNCE_INTERVAL = 600;

    private final View.OnClickListener clickListener;

    private long lastClickTime = 0;

    public OnSingleClick(final View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public final void onClick(View view) {
        if (SystemClock.elapsedRealtime() - lastClickTime < DEBOUNCE_INTERVAL) {
            return;
        }

        lastClickTime = SystemClock.elapsedRealtime();
        clickListener.onClick(view);
    }
}
