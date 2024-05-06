package com.PatrinKursovoy.audio.recorder;

public interface RecorderCallback {
    void finishingRecording();

    void failedRecording();
}
