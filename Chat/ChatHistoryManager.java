package Chat;
import Game.*;
import java.util.*;
import java.io.*;

public class ChatHistoryManager{
  private String history="";
  private File convo = new File("conversation.txt");
  
  public ChatHistoryManager(){
    

    try {
      Scanner scan = new Scanner(convo);
      String convoHistory = "";

      while(scan.hasNextLine()){
        convoHistory += scan.nextLine()+"\n";
      }

      history += convoHistory;
      
    } catch (FileNotFoundException e){
      history = "";
    }
  }


  public String getConvo(){
    return history;
  }

  public void setConvo(String s){
    history = s;

    try{
      PrintWriter writer = new PrintWriter("conversation.txt", "UTF-8");
      writer.println(history);
      writer.close();
    } catch (Exception e){
      
    }
  }

}