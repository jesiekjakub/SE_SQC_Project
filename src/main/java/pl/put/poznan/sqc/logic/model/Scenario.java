package pl.put.poznan.sqc.logic.model;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private String title;
    private List<String> actors;
    private String systemActor;
    private List<Step> steps;

    public Scenario(String title, List<String> actors, String systemActor, List<Step> steps) {
        this.title = title;
        this.actors = actors;
        this.systemActor = systemActor;
        this.steps = new ArrayList<Step>();

    }
    public void addStep(Step step) {
        steps.add(step);
    }
    public void removeStep(Step step) {
        steps.remove(step);
    }
    public String getTitle() {
        return title;
    }
    public List<String> getActors() {
        return actors;
    }
    public String getSystemActor() {
        return systemActor;
    }
    public List<Step> getSteps() {
        return steps;
    }
}
