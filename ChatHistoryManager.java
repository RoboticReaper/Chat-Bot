import java.util.*;
import java.io.*;

public class ChatHistoryManager{
  private ArrayList<String> history = new ArrayList<>();
  private File convo = new File("conversation.txt");
  private File qna = new File("qna.txt");
  private File grammar = new File("grammar.txt");
  private File tldr = new File("tldr.txt");
  
  public ChatHistoryManager(){

    try {
      Scanner scan = new Scanner(convo);
      String convoHistory = "";

      while(scan.hasNextLine()){
        convoHistory += scan.nextLine()+"\n";
      }

      history.add(convoHistory);
    } catch (FileNotFoundException e){
      history.add("");
    }

    try {
      Scanner scan = new Scanner(qna);
      String qnaHistory = "";

      while(scan.hasNextLine()){
        qnaHistory += scan.nextLine()+"\n";
      }

      history.add(qnaHistory);
    } catch (FileNotFoundException e){
      history.add("I am a highly intelligent question answering bot. If you ask me a question that is rooted in truth, I will give you the answer. If you ask me a question that is nonsense, trickery, or has no clear answer, I will respond with \"Unknown\".\n\n");
    }

    try {
      Scanner scan = new Scanner(grammar);
      String grammarHistory = "";

      while(scan.hasNextLine()){
        grammarHistory += scan.nextLine()+"\n";
      }

      history.add(grammarHistory);
    } catch (FileNotFoundException e){
      history.add("");
    }

    try {
      Scanner scan = new Scanner(tldr);
      String tldrHistory = "";

      while(scan.hasNextLine()){
        tldrHistory += scan.nextLine()+"\n";
      }

      history.add(tldrHistory);
    } catch (FileNotFoundException e){
      history.add("");
    }
  }


  public String getConvo(){
    return history.get(0);
  }

  public String getQna(){
    return history.get(1);
  }

  public String getGrammar(){
    return history.get(2);
  }

  public String getTldr(){
    return history.get(3);
  }

  public void setConvo(String s){
    history.set(0, s);

    try{
      PrintWriter writer = new PrintWriter("conversation.txt", "UTF-8");
      writer.println(history.get(0));
      writer.close();
    } catch (Exception e){
      
    }
  }

  public void setQna(String s){
    if(s.equals("")){
      history.set(1, "I am a highly intelligent question answering bot. If you ask me a question that is rooted in truth, I will give you the answer. If you ask me a question that is nonsense, trickery, or has no clear answer, I will respond with \"Unknown\".\n\n");
    } else {
      history.set(1, s);
    }

    try{
      PrintWriter writer = new PrintWriter("qna.txt", "UTF-8");
      writer.println(history.get(1));
      writer.close();
    } catch (Exception e){
      
    }
  }

  public void setGrammar(String s){
    history.set(2, s);
    try{
      PrintWriter writer = new PrintWriter("grammar.txt", "UTF-8");
      writer.println(history.get(2));
      writer.close();
    } catch (Exception e){
      
    } 
  }

  public void setTldr(String s){
    history.set(3, s);
    try{
      PrintWriter writer = new PrintWriter("tldr.txt", "UTF-8");
      writer.println(history.get(3));
      writer.close();
    } catch (Exception e){
      
    }
  }

}