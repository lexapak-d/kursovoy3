package com.PatrinKursovoy.ui.snackbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.PatrinKursovoy.R;
import com.google.android.material.snackbar.Snackbar;

/**
 * Создание Snackbar с предупреждениями
 */

public class SnackbarControl {

    private static Snackbar snackbar;

    public static void make(ViewGroup parent, String message, boolean isSuccess) {
        snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);
        configureSnackbarView(parent.getContext(), isSuccess);
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();
    }

    private static void configureSnackbarView(Context context, boolean isSuccess) {
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundResource(R.drawable.snackbar_background);
        snackbarView.setBackgroundTintList(getSuccessColor(context, isSuccess));
        snackbarView.setLayoutParams(getParams(snackbarView));
        snackbarView.setOnClickListener(v -> snackbar.dismiss());
    }

    private static ColorStateList getSuccessColor(Context context, boolean isSuccess) {
        int color;

        if (isSuccess) {
            color = R.color.light_green;
        } else {
            color = R.color.light_red;
        }

        return ColorStateList.valueOf(context.getResources().getColor(color));
    }

    private static FrameLayout.LayoutParams getParams(View view) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        return params;
    }

    public static void dismiss() {
        if (snackbar != null) snackbar.dismiss();
    }
}
