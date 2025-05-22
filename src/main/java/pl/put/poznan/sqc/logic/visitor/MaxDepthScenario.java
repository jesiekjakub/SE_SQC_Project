package pl.put.poznan.sqc.logic.visitor;

import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;

import java.util.List;

public class MaxDepthScenario implements Visitor {
    private int currentDepth;
    private int maxDepth;
    private Scenario scenario;

    public MaxDepthScenario(int maxDepth){
        this.maxDepth = maxDepth;
    }

    @Override
    public void visit(Scenario scenario) {
        this.currentDepth = 0;
        this.scenario = scenario;
        for (Step step : scenario.getSteps()) {
            step.accept(this);
        }
    }

    @Override
    public void visit(Step step) {
        currentDepth++;
        if (currentDepth==maxDepth) {
            step.setSubsteps(null);
        }
        else {
            if (step.getSubsteps() != null) {
                for (Step substep : step.getSubsteps()) {
                    substep.accept(this);
                }
            }
        }
        currentDepth--;
    }

    @Override
    public Object getResult() {
        return scenario;
    }
}
