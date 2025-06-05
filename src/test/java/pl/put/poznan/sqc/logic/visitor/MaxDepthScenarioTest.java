package pl.put.poznan.sqc.logic.visitor;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;

import java.util.Arrays;
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

    @Test
    void depthTwo() {
        visitor = new MaxDepthScenario(2);
        Step parentStep = mock(Step.class);
        Step childStep = mock(Step.class);
        Step grandchildStep = mock(Step.class);

        when(grandchildStep.getSubsteps()).thenReturn(null);
        when(childStep.getSubsteps()).thenReturn(Collections.singletonList(grandchildStep));
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

        doAnswer(invocation -> {
            Visitor v = invocation.getArgument(0);
            v.visit(grandchildStep);
            return null;
        }).when(grandchildStep).accept(any(Visitor.class));

        visitor.visit(mockScenario);

        verify(childStep).setSubsteps(null);
        verify(parentStep, never()).setSubsteps(null);
        verify(mockScenario).getSteps();
    }

    @Test
    void multipleDepths() {
        visitor = new MaxDepthScenario(2);

        Step root = mock(Step.class);
        Step child1 = mock(Step.class);
        Step grandchild1 = mock(Step.class);
        Step child2 = mock(Step.class);

        when(grandchild1.getSubsteps()).thenReturn(null);
        when(child1.getSubsteps()).thenReturn(Collections.singletonList(grandchild1));
        when(child2.getSubsteps()).thenReturn(null);
        when(root.getSubsteps()).thenReturn(Arrays.asList(child1, child2));
        when(mockScenario.getSteps()).thenReturn(Collections.singletonList(root));

        doAnswer(i -> { i.<Visitor>getArgument(0).visit(root); return null; })
                .when(root).accept(any());
        doAnswer(i -> { i.<Visitor>getArgument(0).visit(child1); return null; })
                .when(child1).accept(any());
        doAnswer(i -> { i.<Visitor>getArgument(0).visit(child2); return null; })
                .when(child2).accept(any());
        doAnswer(i -> { i.<Visitor>getArgument(0).visit(grandchild1); return null; })
                .when(grandchild1).accept(any());

        visitor.visit(mockScenario);

        verify(child1).setSubsteps(null);
        verify(child2).setSubsteps(null);
        verify(root, never()).setSubsteps(null);
    }
    @AfterEach
    void tearDown() {
        visitor = null;
        mockScenario = null;
    }
}