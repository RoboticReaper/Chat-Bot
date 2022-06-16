package Game;
import Chat.*;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;
import java.util.*;

public class GamePanel extends JPanel implements KeyListener, Runnable {
  // this class controls the main map
  private GameFrame controller;
  private Player player = new Player(320, 320);
  private ArrayList<ArrayList<GameTile>> map;
  private ArrayList<ArrayList<Boolean>> crossable;
  private int[] villagerXY;
  private int[] houseXY;
  private Thread t;
  private String mapPath = "Game/Maps/world.txt";
  private boolean playerOrientationIsRight = true;

  public GamePanel(GameFrame controller){
    setBackground(Color.WHITE);
    this.controller = controller;
    map = new ArrayList<ArrayList<GameTile>>();
    crossable = new ArrayList<ArrayList<Boolean>>();

    // a separate thread that controls game loop
    t = new Thread(this, "GAMELOOP");
    
  }

  public void run(){
    loadMap();
    
    // game loop
    while(true){
      updateGame();
      try{
        // 60 fps
        Thread.sleep(16, 66);
      } catch (Exception e){}
    }
  }

  public void start(){
    try{
      t.start();
    } catch (Exception e){}
  }

  public void pause(){
    try{
      t.suspend();
    } catch (Exception e){}
  }

  public void resume(){
    t.resume();
  }

  public void updateGame(){
    // player's location in the next frame
    double nextX = player.getX()+player.getVX();
    double nextY = player.getY()+player.getVY();

    for(int i = 0; i < map.size(); i++){
      for(int j = 0; j < map.get(i).size(); j++){
        // check if the player is about to step into uncrossable tile
        if(!crossable.get(i).get(j)){
          Rectangle p = new Rectangle((int)nextX, (int)nextY, player.getSize(), player.getSize());

          Rectangle tile = new Rectangle(j*40, i*40, 40, 40);


          if(p.intersects(tile)){
            // player is gonna step into the uncrossable tile. stop them
            nextX = player.getX();
            nextY = player.getY();
          }
        }

        
      }
      
    }

    

    player.setX(nextX);
    player.setY(nextY);
    
    repaint();
  }

  public void loadMap(){
    try{
      Scanner scan = new Scanner(new File(mapPath));
      int lineIndex = 0;
      
      while(scan.hasNextLine()){
        String[] line = scan.nextLine().split(" ");
        
        for(int i = 0; i < line.length; i++){
          String tile = line[i].substring(0, 1);
          String structure = line[i].substring(1, 2);
          
          if(lineIndex == 0){
            map.add(new ArrayList<GameTile>());
            crossable.add(new ArrayList<Boolean>());
          }

          GameTile t = new GrassTile(i*40, lineIndex*40);
          boolean cross = true;
          
          // determine the background tile
          if(tile.equals("g")){
            t = new GrassTile(i*40, lineIndex*40);
          }
          if(tile.equals("d")){
            t = new DirtTile(i*40, lineIndex*40);
          }
          if(tile.equals("w")){
            t = new WaterTile(i*40, lineIndex*40);
            cross = false;
          }

          // determine the structure on top of this tile
          if(structure.equals("s")){
            // stone
            t.setStructure("Game/Textures/stone.png");
            cross = false;
          }
          if(structure.equals("t")){
            // tree
            t.setStructure("Game/Textures/tree.png");
            cross = false;
            
          }
          if(structure.equals("h")){
            // house
            t.setStructure("Game/Textures/house.png");
            cross = false;
            houseXY = new int[] {(i-1)*40, lineIndex*40};

            // since house is large, disable crossing other tiles too
            crossable.get(lineIndex).set(i-1, false);
            crossable.get(lineIndex).set(i-2, false);
            crossable.get(lineIndex-1).set(i, false);
            crossable.get(lineIndex-1).set(i-1, false);
            crossable.get(lineIndex-1).set(i-2, false);
          }
          if(structure.equals("v")){
            // villager
           villagerXY = new int[] {i*40, (lineIndex-1)*40};
            t.setStructure("Game/Textures/villager.png");
            cross = false;
          }

          map.get(lineIndex).add(t);
          crossable.get(lineIndex).add(cross);
        }

        lineIndex++;
      }
    } catch (Exception e){}
  }

  
  public void keyPressed(KeyEvent e){
    if(player == null || villagerXY == null){
      return;
    }
    int key = e.getKeyCode();
    double playerSpeed = player.getSpeed();
    
    if(key == KeyEvent.VK_LEFT){
      playerOrientationIsRight = false;
      if(player.getVY() != 0){
        // if player is moving diagonally, use pythagorean theorem to determine x/y velocity
        player.setVX(-Math.sqrt(playerSpeed));
        if(player.getVY() > 0){
          player.setVY(Math.sqrt(playerSpeed));
        } else {
          player.setVY(-Math.sqrt(playerSpeed));
        }
      } else {
        player.setVX(-playerSpeed);
      }
    }
    if(key == KeyEvent.VK_RIGHT){
      playerOrientationIsRight = true;
      if(player.getVY() != 0){
        // if player is moving diagonally, use pythagorean theorem to determine x/y velocity
        player.setVX(Math.sqrt(playerSpeed));
        if(player.getVY() > 0){
          player.setVY(Math.sqrt(playerSpeed));
        } else {
          player.setVY(-Math.sqrt(playerSpeed));
          }
      } else {
        player.setVX(playerSpeed);
      }
    }
    if(key == KeyEvent.VK_DOWN){
      if(player.getVX() != 0){
        player.setVY(Math.sqrt(playerSpeed));
        if(player.getVX() > 0){
          player.setVX(Math.sqrt(playerSpeed));
        } else {
          player.setVX(-Math.sqrt(playerSpeed));
          }
      } else {
        player.setVY(playerSpeed);
      }
    }
    if(key == KeyEvent.VK_UP){
      if(player.getVX() != 0){
        player.setVY(-Math.sqrt(playerSpeed));
        if(player.getVX() > 0){
          player.setVX(Math.sqrt(playerSpeed));
        } else {
          player.setVX(-Math.sqrt(playerSpeed));
          }
      } else {
        player.setVY(-playerSpeed);
      }
    }

    // interactions
    if(e.getKeyChar() == 'f'){
      // if standing near the villager
      if(Math.sqrt(Math.pow(Math.abs(player.getX()-villagerXY[0]), 2) + Math.pow(Math.abs(player.getY()-villagerXY[1]), 2)) <= 85){
        pause();
        controller.replaceScreen("CHAT");
      }

      // entering the house
      else if (Math.sqrt(Math.pow(Math.abs(player.getX()-houseXY[0]), 2) + Math.pow(Math.abs(player.getY()-houseXY[1]), 2)) <= 60){
        controller.replaceScreen("HOUSE");
      }
    }
    
  }

  public void keyReleased(KeyEvent e){
    int key = e.getKeyCode();
    if(key == KeyEvent.VK_LEFT){
      player.setVX(0);
    }
    if(key == KeyEvent.VK_RIGHT){
      player.setVX(0);
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

    paintGameTile(g);

    // get corresponding texture based on which direction player is facing
    BufferedImage texture = playerOrientationIsRight? player.getTextureRight() : player.getTextureLeft();

    // paint player in the center
    // when player moves they stay at screen center, it's the map that moves
    g.drawImage(texture, 250-20, 250-20, player.getSize(), player.getSize(), null);
    
    paintGameStructure(g);

    paintGameUI(g);


  }

  private void paintGameStructure(Graphics g){
    for(int i = 0; i < map.size(); i++){
      for(int j = 0; j < map.get(i).size(); j++)
      {
        // calculating structure's absolute location on the screen relative to the player location
        int tileX = j*40-(int)player.getX()+230;
        int tileY = i*40-(int)player.getY()+230;

        // check if the structure is inside the camera range to be more efficient
        if(tileX+40 > -300 && tileX < 800 && tileY+40 > -300 && tileY < 800){
          BufferedImage structure = map.get(i).get(j).getStructure();

          if(structure != null){
            g.drawImage(structure, tileX+40-structure.getWidth(), tileY+40-structure.getHeight(), structure.getWidth(), structure.getHeight(), null);
          }
          
        }
      }
    }
  }

  private void paintGameTile(Graphics g){
    
    for(int i = 0; i < map.size(); i++){
      for(int j = 0; j < map.get(i).size(); j++)
      {
        int tileX = j*40-(int)player.getX()+230;
        int tileY = i*40-(int)player.getY()+230;

        // check if the tile is inside the camera range
        if(tileX+40 > 0 && tileX < 500 && tileY+40 > 0 && tileY < 500){
          g.drawImage(map.get(i).get(j).getTexture(), tileX, tileY, 40, 40, null);

          
        }
      }
    }

  }

  private void paintGameUI(Graphics g){
    
    if(player == null || villagerXY == null){
      return;
    }
    double pX = player.getX();
    double pY = player.getY();
    double Xdistance = Math.abs(pX-villagerXY[0]);
    double Ydistance = Math.abs(pY-villagerXY[1]);
    double distance = Math.pow(Xdistance, 2) + Math.pow(Ydistance, 2);
    
    // villager dialog option
    if(Math.sqrt(distance) <= 85){
      g.drawString("Press F to chat with the villager", 250, 300);
    }

    // enter the house option
    if(Math.sqrt(Math.pow(Math.abs(player.getX()-houseXY[0]), 2) + Math.pow(Math.abs(player.getY()-houseXY[1]), 2)) <= 60){
      g.drawString("Press F to enter the house", 250, 300);
    }
  }

}