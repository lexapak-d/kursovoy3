package com.PatrinKursovoy.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.PatrinKursovoy.R;
import com.PatrinKursovoy.audio.recorder.RecorderCallback;
import com.PatrinKursovoy.audio.recorder.SoundRecorderThread;
import com.PatrinKursovoy.audio.track.PlayingTrackThread;
import com.PatrinKursovoy.audio.track.TrackCallback;
import com.PatrinKursovoy.request.RequestPermissions;
import com.PatrinKursovoy.request.RequestResultInterface;
import com.PatrinKursovoy.ui.sheets.ListFilesSheetDialog;
import com.PatrinKursovoy.ui.sheets.VoiceRecordingSheetDialog;
import com.PatrinKursovoy.ui.snackbar.SnackbarControl;
import com.PatrinKursovoy.utils.OnSingleClick;
import com.google.android.material.button.MaterialButton;

/**
 * Основное окно с кнопками для записи и показа списка записанных файлов
 */

public class MainActivity extends AppCompatActivity
        implements RequestResultInterface,
        RecorderCallback, TrackCallback {

    private RequestPermissions requestPermissions;

    private ConstraintLayout cstLayout;
    private MaterialButton btnRecord;
    private MaterialButton btnList;

    private boolean isBtnRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        installCallbacks();
        configureRequestPermissions();
        setupViewDeclaration();
        installHandlers();
    }

    private void installCallbacks() {
        PlayingTrackThread.registerTrackCallback(this);
        SoundRecorderThread.registerCallback(this);
    }

    private void configureRequestPermissions() {
        requestPermissions = new RequestPermissions(this, this);
        requestPermissions.installingContracts();
    }

    private void setupViewDeclaration() {
        cstLayout = findViewById(R.id.cst_layout);
        btnRecord = findViewById(R.id.btn_record);
        btnList = findViewById(R.id.btn_list);
    }

    private void installHandlers() {
        btnRecord.setOnClickListener(new OnSingleClick(v -> clickingBtnRecord()));
        btnList.setOnClickListener(new OnSingleClick(v -> clickingBtnList()));
    }

    private void clickingBtnRecord() {
        SnackbarControl.dismiss();

        if (requestPermissions.hasAccess()) {
            showVoiceRecordingSheetDialog();
        } else {
            isBtnRecord = true;
        }
    }

    private void showVoiceRecordingSheetDialog() {
        new VoiceRecordingSheetDialog(this).show();
    }

    private void clickingBtnList() {
        SnackbarControl.dismiss();

        if (requestPermissions.hasAccess()) {
            showListFilesSheetDialog();
        } else {
            isBtnRecord = false;
        }
    }

    private void showListFilesSheetDialog() {
        new ListFilesSheetDialog(this).show();
    }

    @Override
    public void onRequestResult(boolean isGranted) {
        if (isGranted) {
            if (isBtnRecord) {
                showVoiceRecordingSheetDialog();
            } else {
                showListFilesSheetDialog();
            }
        } else {
            makeSnackbar(R.string.sb_no_permissions, false);
        }
    }

    private void makeSnackbar(int stringId, boolean isSuccess) {
        SnackbarControl.make(cstLayout, getString(stringId), isSuccess);
    }

    @Override
    public void finishingRecording() {
        makeSnackbar(R.string.sb_successfully_saved, true);
    }

    @Override
    public void failedRecording() {
        makeSnackbar(R.string.sb_failed_record, false);
    }

    @Override
    public void failedPlaying() {
        makeSnackbar(R.string.sb_failed_play, false);
    }
}