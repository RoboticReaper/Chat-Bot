package Chat;
import Game.*;
import java.util.*;
import java.io.*;

public class ChatHistoryManager{
  private String history="";
  private File convo = new File("Chat/conversation.txt");
  
  public ChatHistoryManager(){
    // load chat history

    try {
      Scanner scan = new Scanner(convo);
      String convoHistory = "";

      while(scan.hasNextLine()){
        convoHistory += scan.nextLine()+"\n";
      }

      history += convoHistory;
      
    } catch (FileNotFoundException e){
      e.printStackTrace();
      history = "";
    }
  }


  public String getConvo(){
    return history;
  }

  public void setConvo(String s){
    // write the history to file
    history = s;

    try{
      PrintWriter writer = new PrintWriter(convo, "UTF-8");
      writer.println(history);
      writer.close();
    } catch (Exception e){
      e.printStackTrace();
    }
  }

}