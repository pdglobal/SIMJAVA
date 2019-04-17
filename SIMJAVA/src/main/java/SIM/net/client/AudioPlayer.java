package SIM.net.client;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;






public class AudioPlayer
{
  public static URL MESSAGE;
  public static URL ONLINE;
  public static URL NUDGE;
  public static URL ALERT;
  public static URL FILE;
  public boolean complete = true;
  
  public AudioPlayer() {
    MESSAGE = AudioPlayer.class.getResource("/sounds/type.wav");
    ONLINE = AudioPlayer.class.getResource("/sounds/online.wav");
    NUDGE = AudioPlayer.class.getResource("/sounds/nudge.wav");
    ALERT = AudioPlayer.class.getResource("/sounds/newalert.wav");
    FILE = AudioPlayer.class.getResource("/sounds/vimdone.wav");
  }
  
  public void play(URL t) {
    playClip(t);
  }
  
  private void playClip(URL clipFile) {
    complete = false;
    


















    try
    {
      LineListener listener = new LineListener()
      {
        private boolean done = false;
        
        public synchronized void update(LineEvent event)
        {
          LineEvent.Type eventType = event.getType();
          if ((eventType == LineEvent.Type.STOP) || (eventType == LineEvent.Type.CLOSE)) {
            done = true;
            notifyAll();
          }
        }
        
        public synchronized void waitUntilDone() throws InterruptedException {
          while (!done) {
            wait();
          }
          
        }
        
      };
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clipFile);
      
      Clip clip = AudioSystem.getClip();
      clip.addLineListener(listener);
      clip.open(audioInputStream);
      clip.start();
      Thread.sleep(5);
      clip.close();
      audioInputStream.close();
      complete = true;
    } catch (Exception e) {
      e.printStackTrace();
      complete = true;
    }
  }
}
