package org.dhsdev.flowerknight.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.FloatControl;

/**
 * A music player
 * @author Shuzhengz
 */
public class AudioPlayer {

    private static String audioFile;

    // Store current position in audio
    private long currentPosition;
    private Clip clip;

    // The state of the player
    private AudioPlayerState audioPlayerState;

    private AudioInputStream audioInputStream;

    private float volume;

    /**
     * Initialize streams and clip
     * @param audioFile The audio file location
     */
    public AudioPlayer(String audioFile) {
        try {
            // Audio File URL
            URL path = getClass().getResource(audioFile);
            assert path != null;
            Logger.log(path.toString(), Severity.DEBUG);

            // Create AudioInputStream object
            audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource(audioFile)));

            // Create clip
            clip = AudioSystem.getClip();

            // Open audioInputStream to the clip
            clip.open(audioInputStream);

            clip.loop(Clip.LOOP_CONTINUOUSLY);

            audioPlayerState = AudioPlayerState.PLAY;

            setVolume(-20.0f);

        }

        catch (Exception ex) {
            Logger.log("Error with playing sound.", Severity.ERROR);
            ex.printStackTrace();
        }
    }

    /**
     * Changes the volume of the audio
     * @param change The magnitude of volume to change
     */
    public void changeVolume(float change) {
        setVolume(volume + change);
    }

    /**
     * Sets the volume of the track
     * @param set The volume to set to
     */
    public void setVolume(float set) {
        if (set > -80.0f && set < 6.0f) {
            if (audioPlayerState == AudioPlayerState.PLAY) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(set);
                clip.start();
            }
            volume = set;
        }
    }

    /**
     * Plays the audio
     */
    public void play() {
        // Start the clip
        clip.start();
        audioPlayerState = AudioPlayerState.PLAY;
        setVolume(volume);
        Logger.log("Audio started", Severity.DEBUG);
    }

    /**
     * Pauses the audio
     */
    public void pause() {
        if (audioPlayerState == AudioPlayerState.PAUSED) {
            Logger.log("Audio is already paused", Severity.DEBUG);
            return;
        }
        this.currentPosition = this.clip.getMicrosecondPosition();
        clip.stop();
        audioPlayerState = AudioPlayerState.PAUSED;
        Logger.log("Audio paused", Severity.DEBUG);
    }

    /**
     * Resumes the audio
     * @throws UnsupportedAudioFileException Unsupported audio file
     * @throws IOException IO Exception
     * @throws LineUnavailableException Line unavailable
     */
    public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (audioPlayerState == AudioPlayerState.PLAY) {
            Logger.log("Audio is already being played", Severity.DEBUG);
            return;
        }
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentPosition);
        this.play();
        Logger.log("Audio resumed", Severity.DEBUG);
    }

    /**
     * Restarts the audio
     * @throws UnsupportedAudioFileException Unsupported audio file
     * @throws IOException IO Exception
     * @throws LineUnavailableException Line unavailable
     */
    public void restart() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentPosition = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
        Logger.log("Audio restarted", Severity.DEBUG);
    }

    /**
     * Stops the audio
     */
    public void stop() {
        currentPosition = 0L;
        clip.stop();
        clip.close();
        Logger.log("Audio stopped", Severity.DEBUG);
    }

    /**
     * Jump to a location
     * @param location The location to jump to
     * @throws UnsupportedAudioFileException Unsupported audio file
     * @throws IOException IO Exception
     * @throws LineUnavailableException Line unavailable
     */
    public void jump(long location) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (location > 0 && location < clip.getMicrosecondLength()) {
            clip.stop();
            clip.close();
            resetAudioStream();
            currentPosition = location;
            clip.setMicrosecondPosition(location);
            this.play();
            Logger.log("is jumped", Severity.DEBUG);
        }
    }

    /**
     * Reset the audio stream
     * @throws UnsupportedAudioFileException Unsupported audio file
     * @throws IOException IO Exception
     * @throws LineUnavailableException Line unavailable
     */
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(audioFile).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        Logger.log("AudioStream restarted", Severity.DEBUG);
    }

    public float getVolume() {
        return volume;
    }

    public AudioPlayerState getAudioPlayerState() {
        return audioPlayerState;
    }

    public static String getAudioFile() {
        return audioFile;
    }
}
