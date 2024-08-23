package org.example.ideasgeneratorweb.model;

import java.util.ArrayList;
import java.util.List;

public class ProjectIdea {
    private String title;
    private String description;
    private List<String> apis;
    public ProjectIdea() {
        this.apis = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getApis() {
        return apis;
    }

    public void setApis(List<String> apis) {
        this.apis = apis;
    }

    public String toString(){
        return title + " > " + description + " > " + apis;
    }
}

