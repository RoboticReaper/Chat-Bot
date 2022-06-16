package Game;
import Chat.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class GameFrame extends JFrame{
  // this game frame is the window the player will be interacting with.
  // also responsible for switching screens.
  private GamePanel gamePanel;
  private JPanel panelCards;
  private ChatPanel chat;
  private HousePanel housePanel;
  private DrinkPanel drinkPanel;

  public GameFrame(){
    super();

    // set up different screens that will need to be switched to
    this.panelCards = new JPanel(new CardLayout());
    gamePanel = new GamePanel(this);
    chat = new ChatPanel(this, new ChatHistoryManager());
    housePanel = new HousePanel(this);
    drinkPanel = new DrinkPanel(this);
    
    setupFrame();
    replaceScreen("GAME");
  }

  private void setupFrame(){
    panelCards.add(gamePanel, "GAME");
    panelCards.add(chat, "CHAT");
    panelCards.add(housePanel, "HOUSE");
    panelCards.add(drinkPanel, "DRINK");
    this.add(panelCards);

    this.setSize(500, 500);
    this.setTitle("Game");
    
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
    this.setVisible(true);
    this.setFocusable(true);

    
    
  }

  public void replaceScreen(String screen){
    ((CardLayout)panelCards.getLayout()).show(panelCards , screen);
    
    if(screen.equals("GAME")){
      housePanel.pause();
      this.addKeyListener(gamePanel);
      this.removeKeyListener(housePanel);
      gamePanel.start();
      gamePanel.resume();
    }
    if(screen.equals("HOUSE")){
      gamePanel.pause();
      this.addKeyListener(housePanel);
      this.removeKeyListener(gamePanel);
      housePanel.start();
      housePanel.resume();
    }
    if(screen.equals("DRINK")){
      drinkPanel = new DrinkPanel(this);
      drinkPanel.start();
    }
  }
}