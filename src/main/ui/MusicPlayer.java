package ui;

import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Singleton for playing music in the game.
 */

public class MusicPlayer {
    private static MusicPlayer instance = null;

    // EFFECTS: private since it is a singleton
    private MusicPlayer() {}

    // MODIFIES: this
    // EFFECTS: returns the instance, if null it creates it first
    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }

        return instance;
    }

    // EFFECTS: continuously plays the music file at the given path
    public void playMusicContinuously(String path) {
        try {
            AudioData data = new AudioStream(new FileInputStream(path)).getData();
            ContinuousAudioDataStream sound = new ContinuousAudioDataStream(data);
            AudioPlayer.player.start(sound);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
