package pl.put.poznan.sqc.logic.model;

import pl.put.poznan.sqc.logic.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class NestedStep extends Step {
    private List<Step> children;

    public NestedStep(String text) {
        super(text);
        this.children = new ArrayList<Step>();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void addSubstep(Step step) {
        children.add(step);
    }

    public void removeSubstep(Step step) {
        children.remove(step);
    }

    public List<Step> getSubsteps() {
        return children;
    }
}
