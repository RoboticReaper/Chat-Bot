package Game;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.*;

public class Player {
  private double x;
  private double y;
  private double vx;
  private double vy;
  private double speed;
  private int size;
  private BufferedImage textureRight;
  private BufferedImage textureLeft;

  public Player(double x, double y){
    this.x = x;
    this.y = y;
    vx = 0;
    vy = 0;
    speed = 1;
    size = 30;

    try {
      textureRight = ImageIO.read(new File("Game/Textures/playerRight.png"));
      textureLeft = ImageIO.read(new File("Game/Textures/playerLeft.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public double getX() {
	  return x;
  }

  public BufferedImage getTextureLeft(){
    return textureLeft;
  }

  public BufferedImage getTextureRight(){
    return textureRight;
  }

  public void setSize(int s){
    size = s;
  }

  public int getSize(){
    return size;
  }

  public double getY() {
	return y;
}
  public double getVX() {
	return vx;
}

  public double getVY() {
	return vy;
}

  public double getSpeed(){
    return speed;
  }

  public void setY(double y){
    this.y = y;
  }

  public void setX(double x){
    this.x = x;
  }

  public void setVY(double vy){
    this.vy = vy;
  }

  public void setVX(double vx){
    this.vx = vx;
  }

  public void setSpeed(double speed){
    this.speed = speed;
  }

}