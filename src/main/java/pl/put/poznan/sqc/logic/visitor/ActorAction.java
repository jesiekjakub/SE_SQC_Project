package pl.put.poznan.sqc.logic.visitor;
import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;

import java.util.ArrayList;
import java.util.List;

public class ActorAction implements Visitor {
    private List<Step> invalidSteps;
    private List<String> actors;
    private int numberOfActors;

    @Override
    public void visit(Scenario scenario) {
        invalidSteps = new ArrayList<>();
        actors = new ArrayList<>(scenario.getActors());
        actors.add(scenario.getSystemActor());
        numberOfActors = actors.size();
        for (Step step : scenario.getSteps()) {
            step.accept(this);
        }
    }

    @Override
    public void visit(Step step) {
        String text = step.getText();
        boolean isValid = false;
        if (text != null && (text.startsWith("IF:") || text.startsWith("ELSE:") || text.startsWith("FOR EACH:"))) {
            isValid = true;
        }

        int index = 0;
        while ((!isValid) && index<numberOfActors) {
            if (text.startsWith(actors.get(index))){
                isValid = true;
            }
            index += 1;
        }

        if (!isValid) {
            invalidSteps.add(step);
        }

        if (step.getSubsteps() != null) {
            for (Step substep : step.getSubsteps()) {
                substep.accept(this);
            }
        }
    }

    @Override
    public Object getResult() {
        return invalidSteps;
    }
}
