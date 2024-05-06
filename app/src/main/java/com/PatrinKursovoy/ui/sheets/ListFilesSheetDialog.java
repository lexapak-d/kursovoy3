package com.PatrinKursovoy.ui.sheets;

import static java.util.Collections.emptyList;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.PatrinKursovoy.R;
import com.PatrinKursovoy.audio.track.PlayingTrackControl;
import com.PatrinKursovoy.audio.track.PlayingTrackThread;
import com.PatrinKursovoy.audio.track.TrackListCallback;
import com.PatrinKursovoy.files.FileHelper;
import com.PatrinKursovoy.files.SortFileDate;
import com.PatrinKursovoy.ui.recyclerview.FileClickListener;
import com.PatrinKursovoy.ui.recyclerview.FileRecyclerAdapter;
import com.PatrinKursovoy.utils.OnSingleClick;
import com.PatrinKursovoy.utils.Utils;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Окно со списком файлов
 */

public class ListFilesSheetDialog extends BaseSheetDialog
        implements FileClickListener, TrackListCallback {

    private final Context context;

    private RecyclerView recyclerView;
    private TextView tvEmpty;

    private final FileRecyclerAdapter adapter = new FileRecyclerAdapter();
    private final PlayingTrackControl trackControl = new PlayingTrackControl();

    public ListFilesSheetDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void show() {
        installCallbacks();
        configureDialogViews();
        configureRecyclerAdapter();
        configureRecyclerView();
        updateFilesList();
        installHandlersForViews();
        super.show();
    }

    private void installCallbacks() {
        PlayingTrackThread.registerTrackListCallback(this);
    }

    private void configureDialogViews() {
        setTextTitle(R.string.sheet_list_title);
        addViewToDialog();
    }

    private void addViewToDialog() {
        View view = View.inflate(context, R.layout.sheet_list_files, null);
        recyclerView = view.findViewById(R.id.recycler_view_files);
        tvEmpty = view.findViewById(R.id.tv_empty_list_files);
        setInsertView(view);
    }

    private void configureRecyclerAdapter() {
        adapter.registerFileClickListener(this);
    }

    private void configureRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        Utils.removeFlickerList(recyclerView);
    }

    private void updateFilesList() {
        List<File> listFiles = getListFiles();
        adapter.setFilesList(listFiles);
    }

    private List<File> getListFiles() {
        File[] files = FileHelper.getFiles();
        List<File> listFiles = emptyList();

        if (files != null) {
            listFiles = Arrays.asList(files);
            Collections.sort(listFiles, new SortFileDate());
        }

        return listFiles;
    }

    private void installHandlersForViews() {
        getBtnDialog().setOnClickListener(new OnSingleClick(v -> dialogCancel()));
        setOnCancelListener(dialogInterface -> stopTracking());
    }

    private void dialogCancel() {
        stopTracking();
        cancel();
    }

    private void stopTracking() {
        trackControl.stopPlaying();
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void onClickFile(File file) {
        trackControl.startRecording(file);
    }

    @Override
    public void checkEmptyList(boolean hasEmpty) {
        Utils.setVisibleView(tvEmpty, hasEmpty);
    }

    @Override
    public void finishedPlaying() {
        adapter.hidePlayIcon();
    }

    //----------------------------------------------------------------------------------------------
    @Override
    protected void onStop() {
        removeCallback();
        super.onStop();
    }

    private void removeCallback() {
        PlayingTrackThread.registerTrackCallback(null);
    }
}