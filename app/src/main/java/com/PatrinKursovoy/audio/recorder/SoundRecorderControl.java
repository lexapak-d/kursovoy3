package com.PatrinKursovoy.audio.recorder;

import android.annotation.SuppressLint;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.PatrinKursovoy.audio.AudioSettings;

/**
 * Контроль записи звука
 */

public class SoundRecorderControl {

    private AudioRecord recorder;
    private SoundRecorderThread thread;

    public SoundRecorderControl() {
        initAudioRecorder();
        initRecordingThread();
    }

    @SuppressLint("MissingPermission")
    private void initAudioRecorder() {
        int audioSource = MediaRecorder.AudioSource.MIC;
        int sampleRate = AudioSettings.SAMPLE_RATE;
        int channel = AudioFormat.CHANNEL_IN_MONO;
        int format = AudioSettings.AUDIO_FORMAT;
        int minBufSize = AudioRecord.getMinBufferSize(sampleRate, channel, format);
        int internalBufferSize = minBufSize * 10;

        recorder = new AudioRecord(audioSource, sampleRate, channel, format, internalBufferSize);
    }

    private void initRecordingThread() {
        thread = new SoundRecorderThread(recorder);
    }

    public void startRecording() {
        thread.startRecording();
    }

    public void stopRecording() {
        thread.stopRecording();
    }
}
