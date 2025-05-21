package pl.put.poznan.sqc.logic.model;

import pl.put.poznan.sqc.logic.visitor.Visitor;

public class Step {
    private String text;

    public Step(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }


}
