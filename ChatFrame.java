import javax.swing.*;
import java.awt.event.*;

public class ChatFrame{
  public JPanel panel = new JPanel();
  JTextArea area = new JTextArea();
  JTextField input = new JTextField();
  
  public ChatFrame(String title, JFrame frame, ChatHistoryManager history){


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

    JButton clear = new JButton("Clear");
    clear.setBounds(395, 5, 100, 25);
    clear.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
    
        if(title.equals("conversation")){
          history.setConvo("");
        } else if (title.equals("qna")){
          history.setQna("");
        } else if (title.equals("tldr")){
          history.setTldr("");
        } else if (title.equals("grammar")){
          history.setGrammar("");
        }

        area.setText(loadTextArea(title, history));
      }
    });
    
    JSeparator s = new JSeparator();
    s.setOrientation(SwingConstants.HORIZONTAL);
    s.setBounds(0, 35, 500, 5);

    
    
    area.setText(loadTextArea(title, history));
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
                area.append(response.trim()+"\n");
              }
              else if(title.equals("qna")){
                area.append("Q: "+text+"\n");
                area.append("A: ");
                String response = a.getResult(area);
                area.append(response.trim()+"\n");
              }
              else if(title.equals("tldr")){
                area.setText("");
                area.append(text+"\n\ntl;dr\n\n");
                String response = a.getResult(area);
                area.append(response.trim());
              }
              else if(title.equals("grammar")){
                area.append("Correct this to standard English: \n"+text+"\n");
                String response = a.getResult(area);
                area.append("\n"+response.trim()+"\n\n");
              }
              
              
            }
            input.setText("");
          }

        // save chat history
        if(title.equals("conversation")){
          history.setConvo(area.getText());
        } else if (title.equals("qna")){
          history.setQna(area.getText());
        } else if (title.equals("tldr")){
          history.setTldr(area.getText());
        } else if (title.equals("grammar")){
          history.setGrammar(area.getText());
        }
        }
    });

    input.setBounds(10, 350, 480, 40);

    
    panel.setLayout(null);
    panel.add(welcome);
    panel.add(back);
    panel.add(clear);
    panel.add(s);
    panel.add(scroll);
    panel.add(s2);
    panel.add(hint);
    panel.add(input);
  }

  private String loadTextArea(String title, ChatHistoryManager history){
    String description = "";
    if(title.equals("conversation")){
      description = history.getConvo();
    } else if (title.equals("qna")){
      description = history.getQna();
    } else if (title.equals("tldr")){
      description = history.getTldr();
    } else if (title.equals("grammar")){
      description = history.getGrammar();
    }

    return description;
  }
}