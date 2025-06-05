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


    @AfterEach
    void tearDown() {
        visitor = null;
        mockScenario = null;
    }
}