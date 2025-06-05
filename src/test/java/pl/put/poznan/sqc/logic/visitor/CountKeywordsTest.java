package pl.put.poznan.sqc.logic.visitor;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;


import java.util.Collections;


import static org.junit.jupiter.api.Assertions.*;

class CountKeywordsTest {
    private CountKeywords visitor;
    private Scenario mockScenario;

    @BeforeEach
    void setUp() {
        visitor = new CountKeywords();
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
    void noKeywordsInScenario() {
        Step step = mock(Step.class);
        when(step.getText()).thenReturn("No keywords here");
        when(step.getSubsteps()).thenReturn(null);
        when(mockScenario.getSteps()).thenReturn(Collections.singletonList(step));

        doAnswer(invocation -> {
            Visitor v = invocation.getArgument(0);
            v.visit(step);
            return null;
        }).when(step).accept(any(Visitor.class));

        visitor.visit(mockScenario);
        Integer count = (Integer) visitor.getResult();

        assertEquals(0, count);
        verify(mockScenario).getSteps();
    }
    @Test
    void singleKeywordInStep() {
        Step step = mock(Step.class);
        when(step.getText()).thenReturn("IF: one keyword here");
        when(step.getSubsteps()).thenReturn(null);
        when(mockScenario.getSteps()).thenReturn(Collections.singletonList(step));

        doAnswer(invocation -> {
            Visitor v = invocation.getArgument(0);
            v.visit(step);
            return null;
        }).when(step).accept(any(Visitor.class));

        visitor.visit(mockScenario);
        Integer count = (Integer) visitor.getResult();

        assertEquals(1, count);
        verify(mockScenario).getSteps();
    }




    @AfterEach
    void tearDown() {
        visitor = null;
        mockScenario = null;
    }
}