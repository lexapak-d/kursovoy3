package com.PatrinKursovoy.ui.sheets;

import android.content.Context;
import android.view.View;

import com.PatrinKursovoy.R;
import com.PatrinKursovoy.audio.recorder.SoundRecorderControl;
import com.PatrinKursovoy.utils.OnSingleClick;
import com.google.android.material.button.MaterialButton;

/**
 * Окно для записи
 */

public class VoiceRecordingSheetDialog extends BaseSheetDialog {

    private final Context context;

    private MaterialButton btnStopRecording;

    private final SoundRecorderControl recorderControl = new SoundRecorderControl();

    public VoiceRecordingSheetDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void show() {
        configureDialogViews();
        installHandlersForViews();
        startRecording();
        super.show();
    }

    private void configureDialogViews() {
        setTextTitle(R.string.sheet_voice__title);
        setVisibilityBtnDialog(false);
        addViewToDialog();
    }

    private void addViewToDialog() {
        View view = View.inflate(context, R.layout.sheet_voice_recording, null);
        btnStopRecording = view.findViewById(R.id.stop_recording);
        setInsertView(view);
    }

    private void installHandlersForViews() {
        btnStopRecording.setOnClickListener(new OnSingleClick(v -> dialogCancel()));
        setOnCancelListener(dialogInterface -> stopRecording());
    }

    private void dialogCancel() {
        stopRecording();
        cancel();
    }

    private void stopRecording() {
        recorderControl.stopRecording();
    }

    private void startRecording() {
        recorderControl.startRecording();
    }
}