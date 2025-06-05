package pl.put.poznan.sqc.logic.visitor;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;

import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class MaxDepthScenarioTest {
    private MaxDepthScenario visitor;
    private Scenario mockScenario;

    @BeforeEach
    void setUp() {
        mockScenario = mock(Scenario.class);
    }

    @Test
    void emptyScenario() {
        visitor = new MaxDepthScenario(1);
        when(mockScenario.getSteps()).thenReturn(Collections.emptyList());

        visitor.visit(mockScenario);
        Scenario result = (Scenario) visitor.getResult();

        assertEquals(mockScenario, result);
        verify(mockScenario).getSteps();
    }

    @Test
    void depthOne() {
        visitor = new MaxDepthScenario(1);
        Step step = mock(Step.class);
        List<Step> substeps = Collections.singletonList(mock(Step.class));

        when(step.getSubsteps()).thenReturn(substeps);
        when(mockScenario.getSteps()).thenReturn(Collections.singletonList(step));

        doAnswer(invocation -> {
            Visitor v = invocation.getArgument(0);
            v.visit(step);
            return null;
        }).when(step).accept(any(Visitor.class));

        visitor.visit(mockScenario);

        verify(step).setSubsteps(null);
        verify(mockScenario).getSteps();
    }


    @AfterEach
    void tearDown() {
        visitor = null;
        mockScenario = null;
    }
}