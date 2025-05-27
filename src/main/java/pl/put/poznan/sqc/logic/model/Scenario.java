package pl.put.poznan.sqc.logic.model;

import pl.put.poznan.sqc.logic.visitor.Visitor;
import java.util.List;

/**
 * This class shows a complete software requirement scenario.
 * This class keeps all the important parts of a scenario including its title,
 * the actors involved, the system actor, and all the steps that make up
 * the scenario. It uses the visitor pattern to let different types of
 * quality checks work on the entire scenario.
 * 
 * A scenario represents a full use case or user story that describes how
 * actors interact with a system to achieve a specific goal. Each scenario
 * contains multiple steps that show the flow of actions from start to finish.
 */
public class Scenario {

    /**
     * The title or name of this scenario. Shows what the scenario is about
     * in a short, clear way. Usually describes the main goal or action
     * that the scenario covers.
     */
    private String title;

    /**
     * List of actors that take part in this scenario. Actors are the people,
     * systems, or other entities that interact with the system during this
     * scenario. Can include users, external systems, or other stakeholders.
     * Can be null if no actors are defined.
     */
    private List<String> actors;

    /**
     * The system actor for this scenario. Shows which system or component
     * is the main target of the interactions described in the scenario.
     * This is usually the system being developed or tested.
     */
    private String systemActor;

    /**
     * List of steps that make up this scenario. Each step shows one action
     * or interaction in the scenario flow. The steps are usually ordered
     * to show the sequence of events from start to finish.
     * Can be null if no steps are defined.
     */
    private List<Step> steps;

    /**
     * Gets the title of this scenario.
     * 
     * @return the scenario title, or null if no title is set
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title for this scenario.
     * 
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the list of actors that take part in this scenario.
     * 
     * @return the list of actors, or null if no actors are set
     */
    public List<String> getActors() {
        return actors;
    }

    /**
     * Sets the list of actors for this scenario.
     * 
     * @param actors the list of actors to set
     */
    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    /**
     * Gets the system actor for this scenario.
     * 
     * @return the system actor, or null if no system actor is set
     */
    public String getSystemActor() {
        return systemActor;
    }

    /**
     * Sets the system actor for this scenario.
     * 
     * @param systemActor the system actor to set
     */
    public void setSystemActor(String systemActor) {
        this.systemActor = systemActor;
    }

    /**
     * Gets the list of steps that make up this scenario.
     * 
     * @return the list of steps, or null if no steps are set
     */
    public List<Step> getSteps() {
        return steps;
    }

    /**
     * Sets the list of steps for this scenario.
     * 
     * @param steps the list of steps to set
     */
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    /**
     * Takes a visitor for working with this scenario. This method is part of the
     * visitor pattern and lets different types of quality checks or operations
     * work on this scenario without changing the Scenario class itself.
     * The visitor can check the scenario's title, actors, system actor, and
     * all its steps.
     * 
     * @param visitor the visitor that will work with this scenario
     */
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}