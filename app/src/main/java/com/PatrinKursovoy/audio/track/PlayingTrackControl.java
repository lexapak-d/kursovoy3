package com.PatrinKursovoy.audio.track;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import com.PatrinKursovoy.audio.AudioSettings;

import java.io.File;

/**
 * контроль воспроизведения записи
 */

public class PlayingTrackControl extends AudioSettings {

    private AudioTrack track;
    private PlayingTrackThread thread;

    public PlayingTrackControl() {
        initAudioTrack();
    }

    private void initAudioTrack() {
        int streamType = AudioManager.STREAM_MUSIC;
        int sampleRate = AudioSettings.SAMPLE_RATE;
        int channel = AudioFormat.CHANNEL_OUT_MONO;
        int format = AudioSettings.AUDIO_FORMAT;
        int minBufSize = AudioTrack.getMinBufferSize(sampleRate, channel, format);
        int internalBufferSize = minBufSize * 10;
        int mode = AudioTrack.MODE_STREAM;

        track = new AudioTrack(streamType, sampleRate, channel, format, internalBufferSize, mode);
    }

    public void startRecording(File file) {
        initTrackingThread();
        thread.setFileToPlay(file);
        thread.startRecording();
    }

    private void initTrackingThread() {
        stopPlaying();
        thread = new PlayingTrackThread(track);
    }

    public void stopPlaying() {
        if (thread != null) thread.stopPlaying();
    }
}
