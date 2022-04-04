import javax.swing.*;
import java.awt.event.*;

public class MenuFrame{
  public static JPanel panel = new JPanel();
  public static JFrame frame = new JFrame();


  public MenuFrame() {
    frame.setTitle("Chat Bot");
    frame.setSize(500, 430);

    // Load past conversations
    ChatHistoryManager history = new ChatHistoryManager();

    // Create GUI elements

    JLabel welcome = new JLabel("Welcome! Please select what you would like to do with the bot:", SwingConstants.CENTER);
    welcome.setBounds(0, 10, 500, 30);

    JButton convo = new JButton("Have a conversation");
    convo.setBounds(10, 50, 235, 50);
    
    convo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ChatFrame c = new ChatFrame("conversation", frame, history);
        frame.setTitle("Conversation");
        frame.getContentPane().removeAll();
        frame.add(c.panel);
        frame.repaint();
        frame.revalidate();
      }
    });

    JButton qna = new JButton("QnA session");
    qna.setBounds(255, 50, 235, 50);

    qna.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ChatFrame c = new ChatFrame("qna",frame, history);
        frame.setTitle("QnA Session");
        frame.getContentPane().removeAll();
        frame.add(c.panel);
        frame.repaint();
        frame.revalidate();
      }
    });

    JButton summary = new JButton("TL;DR Summarization");
    summary.setBounds(10, 110, 235, 50);

    summary.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ChatFrame c = new ChatFrame("tldr",frame, history);
        frame.setTitle("TL;DR Summarization");
        frame.getContentPane().removeAll();
        frame.add(c.panel);
        frame.repaint();
        frame.revalidate();
      }
    });

    JButton grammar = new JButton("Grammar correction");
    grammar.setBounds(255, 110, 235, 50);

    grammar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ChatFrame c = new ChatFrame("grammar",frame, history);
        frame.setTitle("Grammar Correction");
        frame.getContentPane().removeAll();
        frame.add(c.panel);
        frame.repaint();
        frame.revalidate();
      }
    });

    panel.setLayout(null);
    panel.add(convo);
    panel.add(welcome);
    panel.add(qna);
    panel.add(summary);
    panel.add(grammar);

    frame.getContentPane().add(panel);

    frame.setVisible(true);
    frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
  }
}