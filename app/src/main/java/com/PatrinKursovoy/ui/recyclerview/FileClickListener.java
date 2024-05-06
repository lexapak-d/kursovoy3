package com.PatrinKursovoy.ui.recyclerview;

import java.io.File;

public interface FileClickListener {
    void onClickFile(File file);

    void checkEmptyList(boolean hasEmpty);
}
