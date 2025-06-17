package pl.put.poznan.sqc.logic.visitor;

import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This visitor converts a scenario into a formatted text list with numbered
 * steps.
 * It creates a hierarchical numbering system where main steps get numbers like
 * "1.", "2."
 * and substeps get numbers like "1.1.", "1.2.", etc. The visitor uses stacks to
 * track
 * numbering at different levels and builds a complete text representation.
 * 
 * The output includes scenario title, actors, and all steps with proper
 * indentation
 * and numbering that reflects the step hierarchy structure.
 */
public class Download implements Visitor {

    /**
     * List of formatted text lines representing the complete scenario.
     * Each line contains either scenario information or a numbered step
     * with appropriate hierarchical numbering and indentation.
     */
    private final List<String> numberedSteps;

    /**
     * Stack tracking step counters at different hierarchy levels.
     * Each level maintains its own counter to generate proper numbering
     * like 1., 2., then 1.1., 1.2., etc.
     */
    private final Stack<Integer> levelCounters;

    /**
     * Stack storing number prefixes for each hierarchy level.
     * These prefixes build up the complete numbering like "1. " or "1.1. "
     * as the visitor goes deeper into substeps.
     */
    private final Stack<String> prefixes;

    /**
     * Creates a new Download visitor and initializes the formatting structures.
     * This sets up empty collections for storing the formatted output and
     * the stacks needed to track hierarchical numbering.
     */
    public Download() {
        this.numberedSteps = new ArrayList<>();
        this.levelCounters = new Stack<>();
        this.prefixes = new Stack<>();
    }

    /**
     * Visits a scenario and formats its header information and steps.
     * This method adds the scenario title, actors, and system actor to the output,
     * then processes all main steps with proper numbering initialization.
     * 
     * @param scenario the scenario to format into numbered text
     */
    @Override
    public void visit(Scenario scenario) {
        numberedSteps.add("Scenario: " + scenario.getTitle());

        if (scenario.getActors() != null && !scenario.getActors().isEmpty()) {
            numberedSteps.add("Actors: " + String.join(", ", scenario.getActors()));
        }

        if (scenario.getSystemActor() != null) {
            numberedSteps.add("System Actor: " + scenario.getSystemActor());
        }

        numberedSteps.add("");

        prefixes.push("");
        levelCounters.push(0);

        if (scenario.getSteps() != null) {
            for (Step step : scenario.getSteps()) {
                step.accept(this);
            }
        }

        levelCounters.pop();
        prefixes.pop();
    }

    /**
     * Visits a step and adds it to the formatted output with proper numbering.
     * This method generates the hierarchical number for the step, adds it to
     * the output list, then processes any substeps with deeper numbering levels.
     * 
     * @param step the step to format and add to the numbered list
     */
    @Override
    public void visit(Step step) {

        int currentCount = levelCounters.pop() + 1;
        levelCounters.push(currentCount);

        String currentPrefix = prefixes.peek() + currentCount + ".";

        numberedSteps.add(currentPrefix + " " + step.getText());

        if (step.getSubsteps() != null && !step.getSubsteps().isEmpty()) {
            prefixes.push(currentPrefix + " ");
            levelCounters.push(0);

            for (Step substep : step.getSubsteps()) {
                substep.accept(this);
            }

            levelCounters.pop();
            prefixes.pop();
        }
    }

    /**
     * Gets the complete formatted scenario as a list of numbered text lines.
     * This method returns all the formatted content including scenario header
     * and hierarchically numbered steps ready for display or export.
     * 
     * @return list of formatted text lines representing the complete scenario
     */
    @Override
    public Object getResult() {
        return numberedSteps;
    }
}