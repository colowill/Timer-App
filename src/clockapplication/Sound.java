package clockapplication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author 2headaxe
 */
public class Sound {
    
    private Clip clip;
    
    public void playSound(String soundFileName, boolean loop) {
        try {
            /*
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("/Users/2headaxe/Documents/NetBeansProjects/ClockApplication/src/" + soundFileName).getAbsoluteFile());
            */
            InputStream audioSrc = getClass().getResourceAsStream("/" + soundFileName);
            InputStream bufferedIn = new java.io.BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.setFramePosition(0); // Start from the beginning
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop continuously
            } else {
                clip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    public void stopSound() {
        if (clip != null && clip.isRunning())
            clip.stop();
    }
}


