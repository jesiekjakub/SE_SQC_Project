package pl.put.poznan.sqc.logic.visitor;

import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;

public class CountKeywords implements Visitor {
    private int keywordCount = 0;

    @Override
    public void visit(Scenario scenario) {
        for (Step step : scenario.getSteps()) {
            step.accept(this);
        }
    }

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

    @Override
    public Object getResult() {
        return keywordCount;
    }
}