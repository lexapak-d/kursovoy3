package com.PatrinKursovoy.audio.track;

import android.media.AudioTrack;
import android.os.Process;

import com.PatrinKursovoy.ulaw.DecodingInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Воспроизведение записи в потоке
 */

public class PlayingTrackThread extends Thread {

    private final AudioTrack track;
    private File file;
    private boolean hasPlayingTrack = false;

    public PlayingTrackThread(AudioTrack track) {
        this.track = track;
    }

    @Override
    public void run() {
        performRecording();
    }

    private void performRecording() {
        try {
            Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
            byte[] buffer = new byte[4096];
            int length;

            track.play();

            InputStream inputStream = new FileInputStream(file);
            InputStream decodingInputStream = new DecodingInputStream(inputStream);

            while (((length = decodingInputStream.read(buffer)) != -1) && hasPlayingTrack) {
                track.write(buffer, 0, length);
            }

            inputStream.close();
            decodingInputStream.close();
            if (listCallback != null && hasPlayingTrack) listCallback.finishedPlaying();
        } catch (Exception exception) {
            exception.printStackTrace();
            if (trackCallback != null) trackCallback.failedPlaying();
        }
    }

    public void setFileToPlay(File file) {
        this.file = file;
    }

    public void startRecording() {
        hasPlayingTrack = true;
        start();
    }

    public void stopPlaying() {
        hasPlayingTrack = false;
        track.stop();
        interrupt();
    }

    private static TrackListCallback listCallback;
    private static TrackCallback trackCallback;

    public static void registerTrackListCallback(TrackListCallback finishedPlaying) {
        PlayingTrackThread.listCallback = finishedPlaying;
    }

    public static void registerTrackCallback(TrackCallback playingSuccess) {
        PlayingTrackThread.trackCallback = playingSuccess;
    }
}
