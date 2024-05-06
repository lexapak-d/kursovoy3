package com.PatrinKursovoy.files;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

/**
 * Работа с файлами и папками
 */

public class FileHelper {

    public static File createAudioFile(Date date) throws IOException {
        String path = pathFolderSave();
        File file = new File(path);

        if (hasFolderCreated(file)) {
            String name = getNowDateStringForTitle(date) + ".ulaw";
            return new File(path, name);
        } else {
            throw new IOException("Unable to create a file");
        }
    }

    private static String pathFolderSave() {
        String docs = Environment.DIRECTORY_DOCUMENTS;
        String pathDocs = Environment.getExternalStoragePublicDirectory(docs).getAbsolutePath();
        String pathSaveFolder = "Dictaphone";
        return pathDocs + File.separator + pathSaveFolder + File.separator;
    }

    private static boolean hasFolderCreated(File file) {
        boolean hasCreated = true;

        if (!file.exists()) {
            hasCreated = file.mkdirs();
        }

        return hasCreated;
    }

    private static String getNowDateStringForTitle(Date date) {
        int dateFormat = DateFormat.SHORT;
        String dateNowString = DateFormat.getDateTimeInstance(dateFormat, dateFormat).format(date);
        return replaceForbiddenCharacters(dateNowString);
    }

    private static String replaceForbiddenCharacters(String string) {
        string = string.replace("/", ".");
        string = string.replace(":", ".");
        return string;
    }

    public static File[] getFiles() {
        return getFolderSave().listFiles((dir, name)-> name.toLowerCase().endsWith(".ulaw"));
    }

    private static File getFolderSave() {
        return new File(pathFolderSave());
    }
}
