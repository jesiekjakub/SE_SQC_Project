// Update Scenario.java
package pl.put.poznan.sqc.logic.model;

import pl.put.poznan.sqc.logic.visitor.Visitor;
import java.util.List;

public class Scenario {
    private String title;
    private List<String> actors;
    private String systemActor;
    private List<Step> steps;

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<String> getActors() { return actors; }
    public void setActors(List<String> actors) { this.actors = actors; }

    public String getSystemActor() { return systemActor; }
    public void setSystemActor(String systemActor) { this.systemActor = systemActor; }

    public List<Step> getSteps() { return steps; }
    public void setSteps(List<Step> steps) { this.steps = steps; }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}