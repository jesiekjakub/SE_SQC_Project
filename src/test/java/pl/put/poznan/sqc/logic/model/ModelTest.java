package pl.put.poznan.sqc.logic.model;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void testStep() {
        Step emptyStep = new Step();
        assertNull(emptyStep.getText());
        assertNull(emptyStep.getSubsteps());

        String testText = "Test step";
        Step step = new Step(testText);
        assertEquals(testText, step.getText());
        assertNull(step.getSubsteps());

        String newText = "Updated text";
        step.setText(newText);
        assertEquals(newText, step.getText());

        Step substep1 = new Step("Substep 1");
        Step substep2 = new Step("Substep 2");
        List<Step> substeps = Arrays.asList(substep1, substep2);

        step.setSubsteps(substeps);
        assertEquals(substeps, step.getSubsteps());
        assertEquals(2, step.getSubsteps().size());
    }

    @Test
    void testScenario() {
        Scenario scenario = new Scenario();

        assertNull(scenario.getTitle());
        assertNull(scenario.getActors());
        assertNull(scenario.getSystemActor());
        assertNull(scenario.getSteps());

        String title = "Login Process";
        scenario.setTitle(title);
        assertEquals(title, scenario.getTitle());
        List<String> actors = Arrays.asList("User", "Administrator");
        scenario.setActors(actors);
        assertEquals(actors, scenario.getActors());
        assertEquals(2, scenario.getActors().size());
        String systemActor = "Authentication System";
        scenario.setSystemActor(systemActor);
        assertEquals(systemActor, scenario.getSystemActor());
        Step step1 = new Step("User enters login data");
        Step step2 = new Step("System validates login data");
        List<Step> steps = Arrays.asList(step1, step2);

        scenario.setSteps(steps);
        assertEquals(steps, scenario.getSteps());
        assertEquals(2, scenario.getSteps().size());
    }


}