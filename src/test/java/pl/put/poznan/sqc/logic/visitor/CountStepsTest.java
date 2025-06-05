package pl.put.poznan.sqc.logic.visitor;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import pl.put.poznan.sqc.logic.model.Scenario;

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
        //mockScenario.accept(visitor);
        Integer count = (Integer) visitor.getResult();
        assertEquals(0, count);
        verify(mockScenario).getSteps();
    }

    @AfterEach
    void tearDown() {
        visitor = null;
        mockScenario = null;
    }
}