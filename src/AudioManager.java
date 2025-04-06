import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {
    private static AudioManager instance;
    private Clip clip;
    private String currentlyPlaying = "";

    private AudioManager() {}

    public static synchronized AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public synchronized void playMusic(String filePath, boolean loop) {
        if (filePath.equals(currentlyPlaying)) {
            return; // Don't restart if already playing this track
        }

        stop(); // Stop whatever is playing

        try {
            File audioFile = new File(filePath);
            if (!audioFile.exists()) {
                System.err.println("Audio file not found: " + filePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            currentlyPlaying = filePath;

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error playing audio: " + e.getMessage());
        }
    }

    public synchronized void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
            currentlyPlaying = "";
        }
    }

    public synchronized boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}
