import javax.sound.sampled.*;
import java.net.URL;
import java.util.HashMap;

public class AudioManager
{
    private static AudioManager instance;
    private Clip currentClip;
    private final HashMap<String, Clip> loadedClips = new HashMap<>();

    private AudioManager() {}

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void playMusic(String filename, boolean loop) {
        stop();

        try
        {
            URL soundURL = getClass().getClassLoader().getResource("music/" + filename);
            if (soundURL == null)
            {
                System.err.println("file not found: music/" + filename);
                return;
            }

            System.out.println("Loaded audio: " + soundURL);

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }

            currentClip = clip;
            loadedClips.put(filename, clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop()
    {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
            currentClip.close();
        }
    }

    public void pause()
    {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
        }
    }

    public void resume()
    {
        if (currentClip != null) {
            currentClip.start();
        }
    }
}
