package Chat;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.engine.Engine;
import com.theokanning.openai.search.SearchRequest;
import javax.swing.*;
import java.util.ArrayList;

public class OpenAiAPI{
  private OpenAiService service = new OpenAiService(System.getenv("apiKey"));
  public OpenAiAPI(){

    
    System.out.println("\nGetting available engines...");
    
    service.getEngines();

    System.out.println("\nGetting text-davinci-002 engine...");
    Engine ada = service.getEngine("text-davinci-002");
    System.out.println("OpenAi Engine is ready");

  }

  public String getResult(JTextArea area){
    System.out.println("\nCreating completion...");
    ArrayList<String> stop = new ArrayList<>();
    stop.add("You:");

        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(area.getText())
                .echo(false)
                .maxTokens(500)
                .temperature(0.9)
                .presencePenalty(0.8)
                .frequencyPenalty(0.8)
                .stop(stop)
                .build();
    return service.createCompletion("text-davinci-002", completionRequest).getChoices().get(0).getText();
  }
}