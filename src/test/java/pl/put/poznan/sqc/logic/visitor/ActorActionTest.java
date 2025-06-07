package pl.put.poznan.sqc.logic.visitor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;

@ExtendWith(MockitoExtension.class)
class ActorActionTest {
    private ActorAction visitor;
    private Scenario    mockScenario;

    @BeforeEach
    void setUp() {
        visitor = new ActorAction();
        mockScenario = mock(Scenario.class);
        when(mockScenario.getSystemActor()).thenReturn("System");
    }

    @AfterEach
    void tearDown() {
        visitor = null;
        mockScenario = null;
    }

    @Test
    void oneValidActorSingleStep() {
        // Configuration
        Step step = spy(new Step());
        when(step.getSubsteps()).thenReturn(null);
        when(step.getText()).thenReturn("Librarian selects options to add a new book item");
        when(mockScenario.getActors()).thenReturn(Collections.singletonList("Librarian"));
        when(mockScenario.getSteps()).thenReturn(Collections.singletonList(step));

        // Interaction
        visitor.visit(mockScenario);
        List<Step> invalidSteps = (List<Step>) visitor.getResult();

        // Verification
        assertTrue(invalidSteps.isEmpty());
        verify(mockScenario).getSteps();
        verify(mockScenario).getSystemActor();
        verify(mockScenario).getActors();
        verify(step).getSubsteps();
        verify(step).getText();
        verify(step).accept(visitor);
    }

    @Test
    void oneInvalidActorSingleStep() {
        // Configuration
        Step step = spy(new Step());
        when(step.getSubsteps()).thenReturn(null);
        when(step.getText()).thenReturn("Librarian selects options to add a new book item");
        when(mockScenario.getActors()).thenReturn(Collections.singletonList("Teacher"));
        when(mockScenario.getSteps()).thenReturn(Collections.singletonList(step));

        // Interaction
        visitor.visit(mockScenario);
        List<Step> invalidSteps = (List<Step>) visitor.getResult();

        // Verification
        assertEquals(1, invalidSteps.size());
        assertSame(step, invalidSteps.get(0));
        verify(mockScenario).getSteps();
        verify(mockScenario).getSystemActor();
        verify(mockScenario).getActors();
        verify(step).getSubsteps();
        verify(step).getText();
        verify(step).accept(visitor);
    }

    @Test
    void multipleValidActorsSingleStep() {
        // Configuration
        Step step = spy(new Step());
        when(step.getSubsteps()).thenReturn(null);
        when(step.getText()).thenReturn("Librarian selects options to add a new book item");
        when(mockScenario.getActors()).thenReturn(Arrays.asList("Teacher", "Librarian"));
        when(mockScenario.getSteps()).thenReturn(Collections.singletonList(step));

        // Interaction
        visitor.visit(mockScenario);
        List<Step> invalidSteps = (List<Step>) visitor.getResult();

        // Verification
        assertTrue(invalidSteps.isEmpty());
        verify(mockScenario).getSteps();
        verify(mockScenario).getSystemActor();
        verify(mockScenario).getActors();
        verify(step).getSubsteps();
        verify(step).getText();
        verify(step).accept(visitor);
    }

    @Test
    void multipleInvalidActorsSingleStep() {
        // Configuration
        Step step = spy(new Step());
        when(step.getSubsteps()).thenReturn(null);
        when(step.getText()).thenReturn("Librarian selects options to add a new book item");
        when(mockScenario.getActors()).thenReturn(Arrays.asList("Teacher", "Cleaner"));
        when(mockScenario.getSteps()).thenReturn(Collections.singletonList(step));

        // Interaction
        visitor.visit(mockScenario);
        @SuppressWarnings("unchecked")
        List<Step> invalidSteps = (List<Step>) visitor.getResult();

        // Verification
        assertEquals(1, invalidSteps.size());
        assertSame(step, invalidSteps.get(0));
        verify(mockScenario).getSteps();
        verify(mockScenario).getSystemActor();
        verify(mockScenario).getActors();
        verify(step).getSubsteps();
        verify(step).getText();
        verify(step).accept(visitor);
    }

    @Test
    void invalidSystemActorSingleStep() {
        // Configuration
        Step step = spy(new Step());
        when(step.getSubsteps()).thenReturn(null);
        when(step.getText()).thenReturn("A form is displayed");
        when(mockScenario.getActors()).thenReturn(Collections.singletonList("Librarian"));
        when(mockScenario.getSteps()).thenReturn(Collections.singletonList(step));

        // Interaction
        visitor.visit(mockScenario);
        List<Step> invalidSteps = (List<Step>) visitor.getResult();

        // Verification
        assertEquals(1, invalidSteps.size());
        assertSame(step, invalidSteps.get(0));
        verify(mockScenario).getSteps();
        verify(mockScenario).getSystemActor();
        verify(mockScenario).getActors();
        verify(step).getSubsteps();
        verify(step).getText();
        verify(step).accept(visitor);
    }

    @Test
    void validSystemActorSingleStep() {
        // Configuration
        Step step = spy(new Step());
        when(step.getSubsteps()).thenReturn(null);
        when(step.getText()).thenReturn("System displays a form");
        when(mockScenario.getActors()).thenReturn(Collections.singletonList("Librarian"));
        when(mockScenario.getSteps()).thenReturn(Collections.singletonList(step));

        // Interaction
        visitor.visit(mockScenario);
        List<Step> invalidSteps = (List<Step>) visitor.getResult();

        // Verification
        assertTrue(invalidSteps.isEmpty());
        verify(mockScenario).getSteps();
        verify(mockScenario).getSystemActor();
        verify(mockScenario).getActors();
        verify(step).getSubsteps();
        verify(step).getText();
        verify(step).accept(visitor);
    }

    @Test
    void validActorsNestedStep() {
        // Configuration
        Step step = spy(new Step());
        Step subStep = spy(new Step());
        when(subStep.getSubsteps()).thenReturn(null);
        when(step.getSubsteps()).thenReturn(Collections.singletonList(subStep));
        when(subStep.getText()).thenReturn("Intern enters the instance details and confirms them.");
        when(step.getText()).thenReturn("Librarian chooses to add an instance");
        when(mockScenario.getActors()).thenReturn(Arrays.asList("Librarian", "Intern"));
        when(mockScenario.getSteps()).thenReturn(Collections.singletonList(step));

        // Interaction
        visitor.visit(mockScenario);
        List<Step> invalidSteps = (List<Step>) visitor.getResult();

        // Verification
        assertTrue(invalidSteps.isEmpty());
        verify(mockScenario).getSteps();
        verify(mockScenario).getSystemActor();
        verify(mockScenario).getActors();
        verify(step,times(2)).getSubsteps();
        verify(step).getText();
        verify(step).accept(visitor);
        verify(subStep).getSubsteps();
        verify(subStep).getText();
        verify(subStep).accept(visitor);
    }

    @Test
    void oneInvalidActorNestedStep() {
        // Configuration
        Step step = spy(new Step());
        Step subStep = spy(new Step());
        when(subStep.getSubsteps()).thenReturn(null);
        when(step.getSubsteps()).thenReturn(Collections.singletonList(subStep));
        when(subStep.getText()).thenReturn("Intern enters the instance details and confirms them.");
        when(step.getText()).thenReturn("Librarian chooses to add an instance");
        when(mockScenario.getActors()).thenReturn(Arrays.asList("Librarian", "JuniorDev"));
        when(mockScenario.getSteps()).thenReturn(Collections.singletonList(step));

        // Interaction
        visitor.visit(mockScenario);
        List<Step> invalidSteps = (List<Step>) visitor.getResult();

        // Verification
        assertEquals(1,invalidSteps.size());
        assertSame(subStep,invalidSteps.get(0));
        verify(mockScenario).getSteps();
        verify(mockScenario).getSystemActor();
        verify(mockScenario).getActors();
        verify(step,times(2)).getSubsteps();
        verify(step).getText();
        verify(step).accept(visitor);
        verify(subStep).getSubsteps();
        verify(subStep).getText();
        verify(subStep).accept(visitor);
    }
}
