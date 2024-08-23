package org.example.ideasgeneratorweb.controller;

import org.example.ideasgeneratorweb.ideasGenerator.IdeasGeneratorService;
import org.springframework.ui.Model;
import org.example.ideasgeneratorweb.ideasGenerator.IdeaParser;
import org.example.ideasgeneratorweb.model.ProjectIdea;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController {

    IdeasGeneratorService service = new IdeasGeneratorService();

    @RequestMapping()
    public String indexPage() {
        return "test";
    }

    @GetMapping("/ideas")
    public String getIdeas(Model model) {

        IdeaParser parser = new IdeaParser();
        List<ProjectIdea> ideas = parser.parseIdeas(service.generateIdeas());

        model.addAttribute("ideas", ideas);

        return "ideas";
    }
}
