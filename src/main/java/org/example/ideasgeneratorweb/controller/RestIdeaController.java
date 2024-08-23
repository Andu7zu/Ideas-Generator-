package org.example.ideasgeneratorweb.controller;

import org.example.ideasgeneratorweb.ideasGenerator.IdeasGeneratorService;
import org.example.ideasgeneratorweb.model.ProjectIdea;
import org.example.ideasgeneratorweb.pdfManager.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/ideas")
public class RestIdeaController {

    @Autowired
    private IdeasGeneratorService service;

    private PDFService pdfService = new PDFService();




    @RequestMapping("/generate")
    public ResponseEntity<List<ProjectIdea>> generateIdea() {
        return service.generateIdeasJSON();
    }

    @PostMapping("/download/{id}")
    public ResponseEntity<FileSystemResource> downloadIdea(@PathVariable int id) {

        String pdfText = service.generatePDFText(service.getIdea(id).getTitle(), service.getIdea(id));
        return pdfService.downloadPdf(pdfText, service.getIdea(id));

    }

    @PostMapping("/askLLM")
    public String askLLM(@RequestParam String context)
    {
        if(context.contains("idea")){
            return service.startConversationWithContext(context);
        }

        return service.askLLM(context);

    }





}
