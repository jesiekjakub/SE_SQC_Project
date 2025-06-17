package pl.put.poznan.sqc.logic.visitor;

import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;

/**
 * This visitor counts the number of control flow keywords in a scenario.
 * This class looks for specific keywords that control program flow like
 * IF, ELSE, and FOR EACH statements in step text. It goes through all
 * steps in a scenario, including substeps at any level, and counts how
 * many times these keywords appear.
 * 
 * The visitor recognizes three types of keywords: "IF:", "ELSE:", and
 * "FOR EACH:" which must appear at the start of step text. This helps
 * measure scenario complexity by counting conditional logic and loops.
 * It uses the visitor pattern to work with scenario and step objects
 * without changing their code.
 */
public class CountKeywords implements Visitor {

    /**
     * The total count of keywords found so far. This number goes up each time
     * a step with a recognized keyword is visited. The keywords counted are
     * IF:, ELSE:, and FOR EACH: when they appear at the start of step text.
     * Starts at 0 and increases as the visitor finds matching keywords.
     */
    private int keywordCount = 0;

    /**
     * Visits a scenario and starts counting keywords in all its steps.
     * This method goes through each main step in the scenario and
     * calls the step visitor to check that step and any substeps it has
     * for control flow keywords.
     * 
     * @param scenario the scenario to count keywords in
     */
    @Override
    public void visit(Scenario scenario) {
        for (Step step : scenario.getSteps()) {
            step.accept(this);
        }
    }

    /**
     * Visits a step and checks if it contains control flow keywords.
     * This method looks at the step text and counts it if it starts with
     * "IF:", "ELSE:", or "FOR EACH:". Then it goes through all substeps
     * (if any) and checks them too. This works recursively to find
     * keywords at any depth level in the step tree structure.
     * 
     * @param step the step to check for keywords and examine substeps
     */
    @Override
    public void visit(Step step) {
        String text = step.getText();
        if (text != null && (text.startsWith("IF:") || text.startsWith("ELSE:") || text.startsWith("FOR EACH:"))) {
            keywordCount++;
        }

        if (step.getSubsteps() != null) {
            for (Step substep : step.getSubsteps()) {
                substep.accept(this);
            }
        }
    }

    /**
     * Gets the total number of keywords counted by this visitor.
     * This method returns the final count after the visitor has
     * gone through all steps in a scenario. The result includes
     * all control flow keywords (IF:, ELSE:, FOR EACH:) that were
     * found at the start of step text.
     * 
     * @return the total number of keywords as an Integer object
     */
    @Override
    public Object getResult() {
        return keywordCount;
    }
}