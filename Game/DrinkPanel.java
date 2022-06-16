package Game;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;

public class DrinkPanel extends JPanel implements Runnable {
  // shows a gif of drinking
    // meanwhile play audio that syncs with it
  private GameFrame controller;
  private Thread t;
  private JLabel imageLabel = new JLabel();
  private ImageIcon ii = new ImageIcon(this.getClass().getResource("Textures/noice.gif"));

  private boolean soundInitialized = false;
  private Clip clip;
  private AudioInputStream sound;
  
  public DrinkPanel (GameFrame controller){
    // load audio and gif
    this.controller = controller;
    imageLabel.setIcon(ii);
    imageLabel.setBounds(0, 0, 400, 400);
    this.setLayout(null);
    this.add(imageLabel);

    if(!soundInitialized){
      try{
        File file = new File("Game/Textures/noice.wav");
        sound = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(sound);
      } catch (Exception e){e.printStackTrace();}
    }
    
    t = new Thread(this, "DRINKING");
  }

  public void start(){
    clip.start();
    t.start();
  }

  public void run(){
    try{
      // wait for gif & audio to finish playing then switch back
      Thread.sleep(7600);
      controller.replaceScreen("HOUSE");
      t.stop();
      
    } catch (Exception e){}
  }


}