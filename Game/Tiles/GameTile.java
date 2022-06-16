package Game;
import java.io.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import javax.imageio.ImageIO;


public class GameTile{
// this class holds information about each tile/block of the game
  
  private BufferedImage texture;
  private BufferedImage structure;
  private int x;
  private int y;

  public GameTile(String texturePath, int x, int y){
    this.x = x;
    this.y = y;
    
    try {
      texture = ImageIO.read(new File(texturePath));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setStructure(String path){
    try {
      structure = ImageIO.read(new File(path));
    } catch (IOException e){
      e.printStackTrace();
    }
  }

  public BufferedImage getStructure(){
    return structure;
  }


  public BufferedImage getTexture(){
    return texture;
  }
}