package pl.put.poznan.sqc.logic.model;

import pl.put.poznan.sqc.logic.visitor.Visitor;
import java.util.List;

/**
 * This class shows one step in a software requirement scenario.
 * This class keeps the text content of a step and can have many substeps
 * for more detailed parts of the requirement. It uses the visitor pattern
 * to let different types of quality checks work on the step data.
 * 
 * Each step can be part of a tree structure where steps have other steps
 * inside,
 * making it good for showing complex requirements with many levels of details.
 */
public class Step {

    /**
     * The text content of this step. Has the actual requirement description
     * or instruction that this step shows.
     */
    private String text;

    /**
     * List of substeps that are part of this step. Used to make a tree
     * structure where a main step can be split into smaller, more specific steps.
     * Can be null if this step has no substeps.
     */
    private List<Step> substeps;

    /**
     * Makes a new empty Step with no text content.
     */
    public Step() {
    }

    /**
     * Makes a new Step with the given text content.
     * 
     * @param text the text content for this step
     */
    public Step(String text) {
        this.text = text;
    }

    /**
     * Gets the text content of this step.
     * 
     * @return the text content, or null if no text is set
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text content for this step.
     * 
     * @param text the text content to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the list of substeps that are part of this step.
     * 
     * @return the list of substeps, or null if no substeps are set
     */
    public List<Step> getSubsteps() {
        return substeps;
    }

    /**
     * Sets the list of substeps for this step.
     * 
     * @param substeps the list of substeps to set
     */
    public void setSubsteps(List<Step> substeps) {
        this.substeps = substeps;
    }

    /**
     * Takes a visitor for working with this step. This method is part of the
     * visitor
     * pattern and lets different types of quality checks or operations
     * work on this step without changing the Step class itself.
     * 
     * @param visitor the visitor that will work with this step
     */
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}