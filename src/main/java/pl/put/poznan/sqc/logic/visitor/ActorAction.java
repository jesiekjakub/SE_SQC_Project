package pl.put.poznan.sqc.logic.visitor;

import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * This visitor finds steps that don't follow proper actor-action format
 * in a scenario. It checks if each step starts with a valid actor name
 * or control structure keyword, and collects any steps that don't match
 * these rules. This uses the visitor pattern to validate step format
 * without changing the original scenario classes.
 * 
 * Valid steps must start with an actor name from the scenario or with
 * control keywords like "IF:", "ELSE:", or "FOR EACH:". Steps that don't
 * follow this pattern are considered invalid and collected for review.
 */
public class ActorAction implements Visitor {

    /**
     * List of steps that don't follow the proper actor-action format.
     * This collection grows as the visitor finds steps that don't start
     * with valid actor names or control structure keywords.
     */
    private List<Step> invalidSteps;

    /**
     * Complete list of valid actors for this scenario.
     * This includes all regular actors plus the system actor, and is used
     * to check if step text starts with a recognized actor name.
     */
    private List<String> actors;

    /**
     * Total number of actors available in the scenario.
     * This count includes both regular actors and the system actor,
     * and helps optimize the validation loop for each step.
     */
    private int numberOfActors;

    /**
     * Visits a scenario and starts validating all its steps.
     * This method sets up the actor list including the system actor,
     * initializes the invalid steps collection, then processes each
     * main step to check for proper formatting.
     * 
     * @param scenario the scenario to validate step formats for
     */
    @Override
    public void visit(Scenario scenario) {
        invalidSteps = new ArrayList<>();
        actors = new ArrayList<>(scenario.getActors());
        actors.add(scenario.getSystemActor());
        numberOfActors = actors.size();
        for (Step step : scenario.getSteps()) {
            step.accept(this);
        }
    }

    /**
     * Visits a step and checks if it follows proper actor-action format.
     * This method validates that the step text starts with either a valid
     * actor name or a control structure keyword. Steps that don't match
     * are added to the invalid list. Then it processes any substeps.
     * 
     * @param step the step to validate and check for substeps
     */
    @Override
    public void visit(Step step) {
        String text = step.getText();
        boolean isValid = false;
        if (text != null && (text.startsWith("IF:") || text.startsWith("ELSE:") || text.startsWith("FOR EACH:"))) {
            isValid = true;
        }

        int index = 0;
        while ((!isValid) && index < numberOfActors) {
            if (text.startsWith(actors.get(index))) {
                isValid = true;
            }
            index += 1;
        }

        if (!isValid) {
            invalidSteps.add(step);
        }

        if (step.getSubsteps() != null) {
            for (Step substep : step.getSubsteps()) {
                substep.accept(this);
            }
        }
    }

    /**
     * Gets the list of steps that don't follow proper actor-action format.
     * This method returns all steps found during validation that don't
     * start with valid actor names or control keywords. The list can be
     * used to identify and fix formatting problems in the scenario.
     * 
     * @return list of invalid steps that need format correction
     */
    @Override
    public Object getResult() {
        return invalidSteps;
    }
}
