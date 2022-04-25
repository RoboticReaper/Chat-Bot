package Game;

public class Player {
  private int x;
  private int y;
  private int vx;
  private int vy;
  // add an image for the player later

  public Player(int x, int y){
    this.x = x;
    this.y = y;
    vx = 0;
    vy = 0;
  }

  public int getX() {
	return x;
}

  public int getY() {
	return y;
}
  public int getVX() {
	return vx;
}

  public int getVY() {
	return vy;
}

  public void setY(int y){
    this.y = y;
  }

  public void setX(int x){
    this.x = x;
  }

  public void setVY(int vy){
    this.vy = vy;
  }

  public void setVX(int vx){
    this.vx = vx;
  }

}