package com.PatrinKursovoy.ui.sheets;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.PatrinKursovoy.R;
import com.PatrinKursovoy.utils.OnSingleClick;
import com.PatrinKursovoy.utils.Utils;

/**
 * Единая форма для нижнего диалогового окна
 */

public class BaseSheetDialog extends BottomSheetDialog {

    private final Context context;
    private final BottomSheetBehavior<FrameLayout> sheetBehavior = getBehavior();

    private TextView tvTitle;
    private MaterialButton btnDialog;
    private LinearLayout linLayout;
    private View contentView;

    public BaseSheetDialog(@NonNull Context context) {
        super(context, R.style.RoundedSheetDialog);
        this.context = context;
        settingsBehavior();
        createContentView();
        installHandlers();
    }

    private void settingsBehavior() {
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        sheetBehavior.setDraggable(false);
        sheetBehavior.setHideable(false);
    }

    private void createContentView() {
        contentView = View.inflate(context, R.layout.base_sheet_dialog, null);
        tvTitle = contentView.findViewById(R.id.tv_base_dialog_title);
        btnDialog = contentView.findViewById(R.id.btn_base_dialog);
        linLayout = contentView.findViewById(R.id.container_base_dialog);
    }

    private void installHandlers() {
        btnDialog.setOnClickListener(new OnSingleClick(v -> cancel()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView);
        settingsBehavior();
    }

    public void setInsertView(View insertView) {
        insertView.setLayoutParams(getLayoutParams());
        if (insertView.getParent() != null) linLayout.removeView(insertView);
        linLayout.addView(insertView);
    }

    private LinearLayout.LayoutParams getLayoutParams() {
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        return new LinearLayout.LayoutParams(width, height);
    }

    public void setTextTitle(int stringId) {
        tvTitle.setText(context.getString(stringId));
    }

    public void setVisibilityBtnDialog(boolean isVisible) {
        Utils.setVisibleView(btnDialog, isVisible);
    }

    public MaterialButton getBtnDialog() {
        return btnDialog;
    }
}