package com.PatrinKursovoy.audio.recorder;

import android.media.AudioRecord;
import android.os.Process;

import com.PatrinKursovoy.files.FileHelper;
import com.PatrinKursovoy.ulaw.EncodingOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Запись звука в потоке
 */

public class SoundRecorderThread extends Thread {

    private final AudioRecord recorder;

    private final Date dateStartRecording = new Date();
    private boolean hasRecordingAudio = false;

    public SoundRecorderThread(AudioRecord recorder) {
        this.recorder = recorder;
    }

    @Override
    public void run() {
        performRecording();
    }

    private void performRecording() {
        try {
            Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
            byte[] buffer = new byte[4096];
            byte[] outBuffer = new byte[4096];

            recorder.startRecording();

            File file = FileHelper.createAudioFile(dateStartRecording);
            FileOutputStream outputStream = new FileOutputStream(file);
            EncodingOutputStream encodingStream = new EncodingOutputStream(outputStream);

            int length;

            while (hasRecordingAudio) {
                length = recorder.read(buffer, 0, buffer.length);
                encodingStream.write(buffer, length, outBuffer);
            }

            outputStream.close();
            encodingStream.close();

            if (callback != null && !hasRecordingAudio) callback.finishingRecording();
        } catch (Exception exception) {
            exception.printStackTrace();
            if (callback != null) callback.failedRecording();
        }
    }

    public void startRecording() {
        hasRecordingAudio = true;
        start();
    }

    public void stopRecording() {
        hasRecordingAudio = false;
        recorder.stop();
        interrupt();
    }

    private static RecorderCallback callback;

    public static void registerCallback(RecorderCallback callback) {
        SoundRecorderThread.callback = callback;
    }
}
