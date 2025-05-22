package pl.put.poznan.sqc.logic.model;

public class MaxDepthRequest {
    private Scenario scenario;
    private int maxDepth;

    public Scenario getScenario() {
        return scenario;
    }
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public int getMaxDepth() {
        return maxDepth;
    }
    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }
}

