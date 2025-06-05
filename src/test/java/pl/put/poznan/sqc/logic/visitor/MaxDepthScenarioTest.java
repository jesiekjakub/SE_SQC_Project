package pl.put.poznan.sqc.logic.visitor;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;

import java.util.Collections;


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


    @AfterEach
    void tearDown() {
        visitor = null;
        mockScenario = null;
    }
}