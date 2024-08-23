package org.example.ideasgeneratorweb.ideasGenerator;

import dev.langchain4j.model.chat.ChatLanguageModel;
import org.example.ideasgeneratorweb.ChatModelConnector.OllamaIntegration;
import org.example.ideasgeneratorweb.model.ApiData;
import org.example.ideasgeneratorweb.model.ProjectIdea;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Service
public class IdeasGeneratorService {

    private final ChatLanguageModel model;
    private static final int NO_OF_PICKS = 10;
    private static final int NO_OF_APIS = 1433;
    private String[] apisList = new String[NO_OF_APIS];
    private List<ProjectIdea> projectIdeas;

    public IdeasGeneratorService(){
        this.model = new OllamaIntegration().connectModel();
    }

    private void readAndStoreFile(){

        int index = 0;

        try {
            File apiFile = new File("src/main/resources/static/apis.txt");
            Scanner reader = new Scanner(apiFile);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                apisList[index++] = data;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }



    public ProjectIdea getIdea(int index){
        return projectIdeas.get(index);
    }

    private ArrayList<ApiData> pickRandomApis(){
        Random rand = new Random();
        ArrayList<ApiData> apis = new ArrayList<>();
        ArrayList<Integer> chosenIndexes = new ArrayList<>();


        for(int i = 0; i < NO_OF_PICKS; i++){
            int tempVal = rand.nextInt(apisList.length);
            if(chosenIndexes.contains(tempVal)){
                i--;
            }
            else{
                String[] temp = apisList[tempVal].split("__");
                apis.add(new ApiData(temp[0], temp[1], temp[2]));
                chosenIndexes.add(tempVal);
            }
        }

        return apis;
    }

    public String generateIdeas(){
        readAndStoreFile();
        String prompt = formPromptLLM(pickRandomApis());
        return askLLM(prompt);

    }

    public ResponseEntity<List<ProjectIdea>> generateIdeasJSON(){
        IdeaParser parser = new IdeaParser();
        projectIdeas = new ArrayList<>();
        projectIdeas = parser.parseIdeas(generateIdeas());
        return ResponseEntity.ok(projectIdeas);
    }


    public String formPromptLLM(ArrayList<ApiData> apiData){

        String userPrompt = """
                    Generate 10 fun project ideas that are not already implemented, combining 2-5 APIs from the list below. The ideas must be
                    suitable for a junior programmer and they should be formatted like this.

                    Mark each idea like : Idea 1, Idea 2, Idea 3 etc.

                    Each unique idea must have the following:

                    #Title: [TITLE]

                    #Short description: [Short Description]

                    The APIs used and their URLs
                    (Mark every API with * at the beggining of the line)
                    * API1Name (URL)
                    * API2Name (URL)
                    .
                    .
                    .

                    Grade their difficulty (On a scale from 1-5)

                    Grade the Bussiness possible success (On a scale from 1-5)

                    -------------------------------------------------------------

                    Here's the list:
                    """;
        for(ApiData data : apiData){
            userPrompt += "\n" + data.toString();
        }

        return userPrompt;
    }

    public String startConversationWithContext(String input) {
        // Build the context string from project ideas
        StringBuilder contextBuilder = new StringBuilder();
        contextBuilder.append("Remember, these are the ideas you generated with their APIs, Title and Description: ");

        for (ProjectIdea p : projectIdeas) {
            contextBuilder.append("\n").append(p.toString());
        }

        // Convert StringBuilder to String
        String context = contextBuilder.toString();

        // Combine context and input
        String fullPrompt = context + "\n\n" + input;

        // Generate response using model
        String output = model.generate(fullPrompt);
        System.out.println( output);
        return output;
    }

    public String askLLM(String input){
        var model = new OllamaIntegration().connectModel();
        String output = model.generate(input);
        System.out.println( output);
        return output;
    }

    public String generatePDFText(String name, ProjectIdea idea){
        var model = new OllamaIntegration().connectModel();
        String input = """
                Generate a PDF styled String with this bussiness idea: %s.
                Use "**" for main titles, "*" for subtitles with bullet points and leave as it is normal text.
                Make a base structure for the project as well and give me a starting point with every java file I'd need to develop further.
                """.formatted(idea.toString());
        String output = model.generate(input);

        return output;
    }
}
