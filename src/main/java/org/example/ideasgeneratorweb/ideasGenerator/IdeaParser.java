package org.example.ideasgeneratorweb.ideasGenerator;

import org.example.ideasgeneratorweb.model.ProjectIdea;

import java.util.ArrayList;
import java.util.List;

public class IdeaParser {
    List<ProjectIdea> ideas;

    public List<ProjectIdea> parseIdeas(String input) {
        ideas = new ArrayList<>();
        String[] lines = input.split("\n");

        ProjectIdea currentIdea = null;

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("**Idea")) {
                if (currentIdea != null) {
                    ideas.add(currentIdea);
                }
                currentIdea = new ProjectIdea();
            } else if (line.startsWith("#Title:")) {
                currentIdea.setTitle(line.substring(7).trim());
            } else if (line.startsWith("#Short description:")) {
                currentIdea.setDescription(line.substring(19).trim());
            } else if (line.startsWith("*")) {
                currentIdea.getApis().add(line.substring(2).trim());
            }
        }

        if (currentIdea != null) {
            ideas.add(currentIdea);
        }

        return ideas;
    }
}
