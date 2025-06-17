package pl.put.poznan.sqc.logic.visitor;

import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;

import java.util.List;

/**
 * This visitor limits the depth of steps in a scenario by cutting off substeps
 * that go beyond a specified maximum depth level.
 * This class walks through all steps in a scenario and removes any substeps
 * that would exceed the maximum allowed depth. It uses the visitor pattern
 * to modify the scenario structure without changing the original class code.
 * 
 * The visitor tracks the current depth as it goes through the step hierarchy
 * and cuts off branches when they reach the maximum depth limit. This is
 * useful for simplifying complex scenarios or creating summary versions
 * that don't go too deep into detail levels.
 */
public class MaxDepthScenario implements Visitor {

    /**
     * The current depth level being processed in the step hierarchy.
     * This number tracks how deep the visitor is currently going through
     * the step tree structure. It starts at 0 for main steps and increases
     * as the visitor goes into substeps, then decreases when coming back up.
     */
    private int currentDepth;

    /**
     * The maximum allowed depth for steps in the scenario.
     * This is the limit that determines when substeps should be cut off.
     * Steps at this depth level will have their substeps removed to prevent
     * the scenario from going deeper than this limit.
     */
    private int maxDepth;

    /**
     * The scenario being processed and modified by this visitor.
     * This reference is kept so the visitor can return the modified scenario
     * after processing is complete. The scenario structure may be changed
     * if any steps exceed the maximum depth limit.
     */
    private Scenario scenario;

    /**
     * Creates a new MaxDepthScenario visitor with the specified depth limit.
     * This constructor sets up the visitor to cut off any substeps that would
     * go beyond the given maximum depth level in the scenario structure.
     * 
     * @param maxDepth the maximum allowed depth for steps in the scenario
     */
    public MaxDepthScenario(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    /**
     * Visits a scenario and starts processing its steps to limit depth.
     * This method resets the current depth to 0 and stores a reference
     * to the scenario, then goes through each main step to check and
     * limit the depth of the entire step hierarchy.
     * 
     * @param scenario the scenario to process and limit depth for
     */
    @Override
    public void visit(Scenario scenario) {
        this.currentDepth = 0;
        this.scenario = scenario;
        for (Step step : scenario.getSteps()) {
            step.accept(this);
        }
    }

    /**
     * Visits a step and limits its depth by removing substeps if needed.
     * This method increases the current depth counter and checks if the step
     * is at the maximum allowed depth. If so, it removes all substeps to
     * prevent going deeper. Otherwise, it continues processing any substeps
     * recursively, then decreases the depth counter when done.
     * 
     * @param step the step to process and potentially limit depth for
     */
    @Override
    public void visit(Step step) {
        currentDepth++;
        if (currentDepth == maxDepth) {
            step.setSubsteps(null);
        } else {
            if (step.getSubsteps() != null) {
                for (Step substep : step.getSubsteps()) {
                    substep.accept(this);
                }
            }
        }
        currentDepth--;
    }

    /**
     * Gets the modified scenario after depth limiting has been applied.
     * This method returns the scenario that was processed by this visitor,
     * which may have had some substeps removed if they exceeded the maximum
     * allowed depth. The returned scenario has the same main structure but
     * with limited depth levels.
     * 
     * @return the processed scenario with limited step depth
     */
    @Override
    public Object getResult() {
        return scenario;
    }
}