package com.PatrinKursovoy.ui.recyclerview;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.PatrinKursovoy.R;

/**
 * Предоставление доступа View-компонентам
 */

public class FileViewHolder extends RecyclerView.ViewHolder {

    public CardView cardView;
    public TextView nameFiles;
    public RadioButton radioButton;

    public FileViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.card_view_file);
        nameFiles = itemView.findViewById(R.id.tv_title_file);
        radioButton = itemView.findViewById(R.id.radio_btn_file);
    }
}