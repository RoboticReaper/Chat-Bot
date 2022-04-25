package Game;
import Chat.*;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class GamePanel extends JPanel implements KeyListener{
  private GameFrame controller;
  private Player player;
  

  public GamePanel(GameFrame controller){
    setBackground(Color.WHITE);
    this.controller = controller;
    player = new Player(30, 30);    
  }

  public void run(){
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while(true){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                updates++;
                delta--;
            }
            updateGame();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println(updates + " Ticks, Fps " + frames);
                updates = 0;
                frames = 0;
            }

        }
    }

  public void updateGame(){
    player.setX(player.getX()+player.getVX());
    player.setY(player.getY()+player.getVY());
    repaint();
  }

  public void keyPressed(KeyEvent e){
    int key = e.getKeyCode();
    if(key == KeyEvent.VK_LEFT){
      player.setVX(-1);
    }
    if(key == KeyEvent.VK_RIGHT){
      player.setVX(1);
    }
    if(key == KeyEvent.VK_DOWN){
      player.setVY(1);
    }
    if(key == KeyEvent.VK_UP){
      player.setVY(-1);
    }
  }

  public void keyReleased(KeyEvent e){
    int key = e.getKeyCode();
    if(key == KeyEvent.VK_LEFT){
      player.setVX(0);
    }
    if(key == KeyEvent.VK_RIGHT){
      player.setVX(0);
      System.out.println("Move right by 5");                      repaint();
    }
    if(key == KeyEvent.VK_DOWN){
      player.setVY(0);
    }
    if(key == KeyEvent.VK_UP){
      player.setVY(0);
    }
  }

  public void keyTyped(KeyEvent e){
    
  }


  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    g.drawRect(player.getX(), player.getY(), 100, 100);

  }


}