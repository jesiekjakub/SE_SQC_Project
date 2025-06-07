package pl.put.poznan.sqc.logic.model;

import pl.put.poznan.sqc.logic.visitor.Visitor;

public interface Element {
    public void accept(Visitor visitor);
}
