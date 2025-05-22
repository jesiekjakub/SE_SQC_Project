// Update Step.java
package pl.put.poznan.sqc.logic.model;

import pl.put.poznan.sqc.logic.visitor.Visitor;
import java.util.List;

public class Step {
    private String text;
    private List<Step> substeps;
    public Step() {
    }
    public Step(String text) {
        this.text = text;
    }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public List<Step> getSubsteps() { return substeps; }
    public void setSubsteps(List<Step> substeps) { this.substeps = substeps; }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}