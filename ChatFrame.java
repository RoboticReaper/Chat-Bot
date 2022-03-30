import javax.swing.*;
import java.awt.event.*;

public class ChatFrame{
  public JPanel panel = new JPanel();
  JTextArea area = new JTextArea();
  JTextField input = new JTextField();
  
  public ChatFrame(String title, JFrame frame){


    // Create GUI elements
    JLabel welcome = new JLabel("Chat History", SwingConstants.CENTER);
    welcome.setBounds(0, 0, 500, 30);
    
    JButton back = new JButton("Back");
    back.setBounds(5, 5, 100, 25);
    back.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.setTitle("Chat Bot");
        frame.getContentPane().removeAll();
        frame.add(MenuFrame.panel);
        frame.repaint();
        frame.revalidate();
      }
    });
    
    JSeparator s = new JSeparator();
    s.setOrientation(SwingConstants.HORIZONTAL);
    s.setBounds(0, 35, 500, 5);

    String description = "";
    if(title.equals("conversation")){
      description = "";
    } else if (title.equals("qna")){
      description = "I am a highly intelligent question answering bot. If you ask me a question that is rooted in truth, I will give you the answer. If you ask me a question that is nonsense, trickery, or has no clear answer, I will respond with \"Unknown\".\n\n";
    } else if (title.equals("tldr")){
      description = "";
    }
    area.setText(description);
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

    OpenAiAPI a = new OpenAiAPI();

    input.addKeyListener(new KeyAdapter(){
      @Override
      public void keyPressed(KeyEvent e) {
          if(e.getKeyCode() == KeyEvent.VK_ENTER){
            String text = input.getText().trim();
            if(!text.equals("")){
              if(title.equals("conversation")){
                area.append("You: "+text+"\n");
                area.append("Friend: ");
                String response = a.getResult(area);
                area.append(response.substring(2)+"\n");
              }
              else if(title.equals("qna")){
                area.append("Q: "+text+"\n");
              }
              else if(title.equals("tldr")){
                area.append(text+"\n\ntl;dr\n\n");
              }
              else if(title.equals("grammar")){
                area.append("Correct this to standard English: \n\n"+text+"\n\n");
              }
              
              
            }
            input.setText("");
          }
        }
    });

    input.setBounds(10, 350, 480, 40);

    
    panel.setLayout(null);
    panel.add(welcome);
    panel.add(back);
    panel.add(s);
    panel.add(scroll);
    panel.add(s2);
    panel.add(hint);
    panel.add(input);
  }
}