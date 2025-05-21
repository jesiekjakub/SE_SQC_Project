package pl.put.poznan.sqc.logic.model;

import pl.put.poznan.sqc.logic.visitor.Visitor;

import java.util.ArrayList;

public class IterativeStep extends NestedStep {

    public IterativeStep(String text) {
        super(text);
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
