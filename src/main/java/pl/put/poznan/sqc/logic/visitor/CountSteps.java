package pl.put.poznan.sqc.logic.visitor;

import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;

/**
 * This visitor counts the total number of steps in a scenario.
 * This class goes through all steps in a scenario, including substeps
 * at any level, and counts them to give the total number of steps.
 * It uses the visitor pattern to work with scenario and step objects
 * without changing their code.
 * 
 * The counting includes all main steps and all their substeps, no matter
 * how deep the step tree structure goes. This is useful for checking
 * scenario complexity and getting metrics about requirement size.
 */
public class CountSteps implements Visitor {

    /**
     * The total count of steps found so far. This number goes up each time
     * a step is visited, including both main steps and substeps.
     * Starts at 0 and increases as the visitor goes through the scenario.
     */
    private int totalSteps = 0;

    /**
     * Visits a scenario and starts counting all its steps.
     * This method goes through each main step in the scenario and
     * calls the step visitor to count that step and any substeps it has.
     * 
     * @param scenario the scenario to count steps in
     */
    @Override
    public void visit(Scenario scenario) {
        for (Step step : scenario.getSteps()) {
            step.accept(this);
        }
    }

    /**
     * Visits a step and counts it, then counts any substeps it has.
     * This method adds 1 to the total count for the current step, then
     * goes through all substeps (if any) and counts them too.
     * This works recursively to count steps at any depth level.
     * 
     * @param step the step to count and check for substeps
     */
    @Override
    public void visit(Step step) {
        totalSteps++;
        if (step.getSubsteps() != null) {
            for (Step substep : step.getSubsteps()) {
                substep.accept(this);
            }
        }
    }

    /**
     * Gets the total number of steps counted by this visitor.
     * This method returns the final count after the visitor has
     * gone through all steps in a scenario. The result includes
     * all main steps and substeps that were found.
     * 
     * @return the total number of steps as an Integer object
     */
    @Override
    public Object getResult() {
        return totalSteps;
    }
}