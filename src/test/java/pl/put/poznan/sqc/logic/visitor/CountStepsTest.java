package pl.put.poznan.sqc.logic.visitor;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CountStepsTest {
    private CountSteps visitor;
    private Scenario mockScenario;

    @BeforeEach
    void setUp() {
        visitor = new CountSteps();
        mockScenario = mock(Scenario.class);
    }

    @Test
    void emptyScenario() {
        when(mockScenario.getSteps()).thenReturn(Collections.emptyList());
        visitor.visit(mockScenario);
        Integer count = (Integer) visitor.getResult();
        assertEquals(0, count);
        verify(mockScenario).getSteps();

    }

    @Test
    void singleStepScenario() {
        Step step = mock(Step.class);

        when(step.getSubsteps()).thenReturn(null);
        doAnswer(invocation -> {
            Visitor v = invocation.getArgument(0);
            v.visit(step);
            return null;
        }).when(step).accept(any());
        when(mockScenario.getSteps()).thenReturn(Collections.singletonList(step));

        visitor.visit(mockScenario);
        Integer count = (Integer) visitor.getResult();
        assertEquals(1, count);
        verify(step).getSubsteps();
        verify(step).accept(visitor);
    }

    @AfterEach
    void tearDown() {
        visitor = null;
        mockScenario = null;
    }
}