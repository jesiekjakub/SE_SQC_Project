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

import java.util.Arrays;
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
        verify(mockScenario).getSteps();
        verify(step).getSubsteps();
        verify(step).accept(visitor);
    }

    @Test
    void twoStepsZeroLevel() {
        // Configuration
        Step step1 = spy(new Step());
        when(step1.getSubsteps()).thenReturn(null);
        Step step2 = spy(new Step());
        when(step2.getSubsteps()).thenReturn(null);
        when(mockScenario.getSteps()).thenReturn(Arrays.asList(step1,step2));

        // Interaction
        visitor.visit(mockScenario);
        Integer count = (Integer) visitor.getResult();

        // Verification
        assertEquals(2, count);
        verify(mockScenario).getSteps();
        verify(step1).getSubsteps();
        verify(step1).accept(visitor);
        verify(step2).getSubsteps();
        verify(step2).accept(visitor);
    }

    @Test
    void singleNestedStep() {
        // Configuration
        Step step = spy(new Step());
        Step subStep = spy(new Step());
        when(subStep.getSubsteps()).thenReturn(null);
        when(step.getSubsteps()).thenReturn(Collections.singletonList(subStep));
        when(mockScenario.getSteps()).thenReturn(Collections.singletonList(step));

        // Interaction
        visitor.visit(mockScenario);
        Integer count = (Integer) visitor.getResult();

        // Verification
        assertEquals(2, count);
        verify(mockScenario).getSteps();
        verify(step).accept(visitor);
        verify(step,times(2)).getSubsteps();
        verify(subStep).accept(visitor);
        verify(subStep).getSubsteps();
    }

    @Test
    void doublyNestedSteps() {
        // Configuration
        Step step = spy(new Step());
        Step subStep = spy(new Step());
        Step subsubStep = spy(new Step());
        when(subsubStep.getSubsteps()).thenReturn(null);
        when(subStep.getSubsteps()).thenReturn(Collections.singletonList(subsubStep));
        when(step.getSubsteps()).thenReturn(Collections.singletonList(subStep));
        when(mockScenario.getSteps()).thenReturn(Collections.singletonList(step));

        // Interaction
        visitor.visit(mockScenario);
        Integer count = (Integer) visitor.getResult();

        // Verification
        assertEquals(3, count);
        verify(mockScenario).getSteps();
        verify(step).accept(visitor);
        verify(step,times(2)).getSubsteps();
        verify(subStep).accept(visitor);
        verify(subStep,times(2)).getSubsteps();
        verify(subsubStep).accept(visitor);
        verify(subsubStep).getSubsteps();
    }



    @AfterEach
    void tearDown() {
        visitor = null;
        mockScenario = null;
    }
}