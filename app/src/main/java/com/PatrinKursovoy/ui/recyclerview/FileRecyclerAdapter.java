package com.PatrinKursovoy.ui.recyclerview;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.PatrinKursovoy.R;
import com.PatrinKursovoy.utils.OnSingleClick;

import java.io.File;
import java.util.List;

/**
 * Адаптер для работы со списком файлов
 */

public class FileRecyclerAdapter extends RecyclerView.Adapter<FileViewHolder> {

    private FileClickListener clickListener;

    private List<File> list;

    private int lastCheckedPosition = -1;

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_file, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        File file = list.get(position);

        holder.nameFiles.setText(getFileName(file));
        holder.radioButton.setChecked(position == lastCheckedPosition);
        holder.cardView.setOnClickListener(new OnSingleClick(v -> onItemClick(file, holder)));
    }

    private String getFileName(File file) {
        return file.getName().replace(".ulaw", "");
    }

    private void onItemClick(File file, RecyclerView.ViewHolder holder) {
        lastCheckedPosition = holder.getAdapterPosition();
        notifyItemRangeChanged(0, getItemCount());
        clickListener.onClickFile(file);
    }

    //----------------------------------------------------------------------------------------------
    public void setFilesList(List<File> newList) {
        list = newList;
        checkEmptyFilesList();
        updateChangedList();
    }

    private void checkEmptyFilesList() {
        clickListener.checkEmptyList(list.size() == 0);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateChangedList() {
        notifyDataSetChanged();
    }

    //----------------------------------------------------------------------------------------------
    public void hidePlayIcon() {
        lastCheckedPosition = -1;
        notifyItemRangeChanged(0, getItemCount());
    }

    //----------------------------------------------------------------------------------------------
    public void registerFileClickListener(FileClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
