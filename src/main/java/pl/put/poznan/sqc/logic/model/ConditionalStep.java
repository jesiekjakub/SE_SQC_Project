package pl.put.poznan.sqc.logic.model;

import pl.put.poznan.sqc.logic.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class ConditionalStep extends NestedStep{
    private List<Step> alternativeChildren;

    public ConditionalStep(String text) {
        super(text);
        this.alternativeChildren = new ArrayList<Step>();
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void addSubstep(Step step) {
        alternativeChildren.add(step);
    }

    public void removeSubstep(Step step) {
        alternativeChildren.remove(step);
    }

    public List<Step> getSubsteps() {
        return alternativeChildren;
    }
}
