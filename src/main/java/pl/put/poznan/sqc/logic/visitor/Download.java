package pl.put.poznan.sqc.logic.visitor;

import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Download implements Visitor {
    private final List<String> numberedSteps;
    private final Stack<Integer> levelCounters;
    private final Stack<String> prefixes;

    public Download() {
        this.numberedSteps = new ArrayList<>();
        this.levelCounters = new Stack<>();
        this.prefixes = new Stack<>();
    }

    @Override
    public void visit(Scenario scenario) {
        numberedSteps.add("Scenario: " + scenario.getTitle());

        if (scenario.getActors() != null && !scenario.getActors().isEmpty()) {
            numberedSteps.add("Actors: " + String.join(", ", scenario.getActors()));
        }

        if (scenario.getSystemActor() != null) {
            numberedSteps.add("System Actor: " + scenario.getSystemActor());
        }

        numberedSteps.add("");

        prefixes.push("");
        levelCounters.push(0);

        if (scenario.getSteps() != null) {
            for (Step step : scenario.getSteps()) {
                step.accept(this);
            }
        }

        levelCounters.pop();
        prefixes.pop();
    }

    @Override
    public void visit(Step step) {

        int currentCount = levelCounters.pop() + 1;
        levelCounters.push(currentCount);

        String currentPrefix = prefixes.peek() + currentCount + ".";

        numberedSteps.add(currentPrefix + " " + step.getText());

        if (step.getSubsteps() != null && !step.getSubsteps().isEmpty()) {
            prefixes.push(currentPrefix + " ");
            levelCounters.push(0);

            for (Step substep : step.getSubsteps()) {
                substep.accept(this);
            }

            levelCounters.pop();
            prefixes.pop();
        }
    }

    @Override
    public Object getResult() {
        return numberedSteps;
    }
}