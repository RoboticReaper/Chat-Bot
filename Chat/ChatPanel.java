package Chat;
import Game.*;
import javax.swing.*;
import java.awt.event.*;

public class ChatPanel extends JPanel{
  private JTextArea area = new JTextArea();
  private JTextField input = new JTextField();
  
  public ChatPanel(GameFrame controller, ChatHistoryManager history){


    // Create GUI elements
    JLabel welcome = new JLabel("Chat History", SwingConstants.CENTER);
    welcome.setBounds(0, 0, 500, 30);
    
    JButton back = new JButton("Back");
    back.setBounds(5, 5, 100, 25);
    back.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        controller.replaceScreen("GAME");
      }
    });

    JButton clear = new JButton("Clear");
    clear.setBounds(395, 5, 100, 25);
    clear.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        history.setConvo("");

        area.setText(history.getConvo());
      }
    });
    
    JSeparator s = new JSeparator();
    s.setOrientation(SwingConstants.HORIZONTAL);
    s.setBounds(0, 35, 500, 5);

    
    
    area.setText(history.getConvo());
    area.setWrapStyleWord(true);
    area.setLineWrap(true);
    area.setEditable(false);

    JScrollPane scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scroll.setBounds(10, 45, 480, 270);

    JSeparator s2 = new JSeparator();
    s2.setOrientation(SwingConstants.HORIZONTAL);
    s2.setBounds(0, 320, 500, 5);

    JLabel hint = new JLabel("Type your message below, press Enter to send", SwingConstants.CENTER);
    hint.setBounds(0, 325, 500, 15);

    OpenAiAPI api = new OpenAiAPI();

    input.addKeyListener(new KeyAdapter(){
      @Override
      public void keyPressed(KeyEvent e) {
          if(e.getKeyCode() == KeyEvent.VK_ENTER){
            // user sends the message
            String text = input.getText().trim();
            
            if(!text.equals("")){
              // format the text and add it at the end of the chat history
              area.append("You: "+text+"\n");
              area.append("Friend: ");
              String response = api.getResult(area);
              area.append(response.trim()+"\n");
            }
            input.setText("");
          }

        // save chat history
        history.setConvo(area.getText());
      }
    });

    input.setBounds(10, 350, 480, 40);

    
    this.setLayout(null);
    this.add(welcome);
    this.add(back);
    this.add(clear);
    this.add(s);
    this.add(scroll);
    this.add(s2);
    this.add(hint);
    this.add(input);
  }

}