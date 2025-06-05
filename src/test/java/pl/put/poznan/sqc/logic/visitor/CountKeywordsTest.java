package pl.put.poznan.sqc.logic.visitor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

    @Test
    void multipleKeywordsInSteps() {
        Step step1 = mock(Step.class);
        Step step2 = mock(Step.class);

        when(step1.getText()).thenReturn("IF: condition is true");
        when(step1.getSubsteps()).thenReturn(null);
        when(step2.getText()).thenReturn("ELSE: do something else");
        when(step2.getSubsteps()).thenReturn(null);
        when(mockScenario.getSteps()).thenReturn(Arrays.asList(step1, step2));

        doAnswer(invocation -> {
            Visitor v = invocation.getArgument(0);
            v.visit(step1);
            return null;
        }).when(step1).accept(any(Visitor.class));

        doAnswer(invocation -> {
            Visitor v = invocation.getArgument(0);
            v.visit(step2);
            return null;
        }).when(step2).accept(any(Visitor.class));

        visitor.visit(mockScenario);
        Integer count = (Integer) visitor.getResult();

        assertEquals(2, count);
        verify(mockScenario).getSteps();
    }

    @Test
    void nestedStepsWithKeywords() {
        Step parentStep = mock(Step.class);
        Step childStep = mock(Step.class);

        when(parentStep.getText()).thenReturn("IF: parent condition");
        when(childStep.getText()).thenReturn("FOR EACH: item in list");
        when(childStep.getSubsteps()).thenReturn(null);
        when(parentStep.getSubsteps()).thenReturn(Collections.singletonList(childStep));
        when(mockScenario.getSteps()).thenReturn(Collections.singletonList(parentStep));

        doAnswer(invocation -> {
            Visitor v = invocation.getArgument(0);
            v.visit(parentStep);
            return null;
        }).when(parentStep).accept(any(Visitor.class));

        doAnswer(invocation -> {
            Visitor v = invocation.getArgument(0);
            v.visit(childStep);
            return null;
        }).when(childStep).accept(any(Visitor.class));

        visitor.visit(mockScenario);
        Integer count = (Integer) visitor.getResult();

        assertEquals(2, count);
        verify(mockScenario).getSteps();
    }

    @AfterEach
    void tearDown() {
        visitor = null;
        mockScenario = null;
    }
}