package Game;
import Chat.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class GameFrame extends JFrame{
  private GamePanel gamePanel;
  private JPanel panelCards;
  private ChatPanel chat;

  public GameFrame(){
    super();
    this.panelCards = new JPanel(new CardLayout());
    gamePanel = new GamePanel(this);
    chat = new ChatPanel(this, new ChatHistoryManager());
    
    setupFrame();
    replaceScreen("CHAT");
  }

  private void setupFrame(){
    panelCards.add(gamePanel, "GAME");
    panelCards.add(chat, "CHAT");
    this.add(panelCards);

    this.setSize(500, 500);
    this.setTitle("Game");
    
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
    this.setVisible(true);
    this.setFocusable(true);

    this.addKeyListener(gamePanel);
  }

  public void replaceScreen(String screen){
    ((CardLayout)panelCards.getLayout()).show(panelCards , screen);	
  }
}