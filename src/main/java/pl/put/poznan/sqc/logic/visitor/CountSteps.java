// src/main/java/pl/put/poznan/sqc/logic/visitor/CountSteps.java
package pl.put.poznan.sqc.logic.visitor;

import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;

public class CountSteps implements Visitor {
    private int totalSteps = 0;

    @Override
    public void visit(Scenario scenario) {
        for (Step step : scenario.getSteps()) {
            step.accept(this);
        }
    }

    @Override
    public void visit(Step step) {
        totalSteps++; // Count this step
        if (step.getSubsteps() != null) {
            for (Step substep : step.getSubsteps()) {
                substep.accept(this);
            }
        }
    }

    @Override
    public Object getResult() {
        return totalSteps;
    }
}