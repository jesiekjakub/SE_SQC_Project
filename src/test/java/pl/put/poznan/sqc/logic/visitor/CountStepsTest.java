package pl.put.poznan.sqc.logic.visitor;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
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
        // Configuration
        when(mockScenario.getSteps()).thenReturn(Collections.emptyList());

        // Interaction
        visitor.visit(mockScenario);
        Integer count = (Integer) visitor.getResult();

        // Verification
        assertEquals(0, count);
        verify(mockScenario).getSteps();
    }

    @Test
    void singleStepScenario() {
        // Configuration
        Step step = spy(new Step());
        when(step.getSubsteps()).thenReturn(null);
        when(mockScenario.getSteps()).thenReturn(Collections.singletonList(step));

        // Interaction
        visitor.visit(mockScenario);
        Integer count = (Integer) visitor.getResult();

        // Verification
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