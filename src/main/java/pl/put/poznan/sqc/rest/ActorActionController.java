package pl.put.poznan.sqc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;
import pl.put.poznan.sqc.logic.visitor.ActorAction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/actor_action")
public class ActorActionController {
    private static final Logger logger = LoggerFactory.getLogger(ActorActionController.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> post(@RequestBody Scenario scenario) {
        logger.info("Received scenario: {}", scenario.getTitle());
        ActorAction visitor = new ActorAction();
        scenario.accept(visitor);
        List<Step> invalidSteps = (List<Step>) visitor.getResult();

        Map<String, Object> response = new HashMap<>();
        response.put("invalidSteps", invalidSteps);

        try {
            String responseJson = objectMapper.writeValueAsString(response);
            logger.debug("Returned response: {}", responseJson);
        } catch (JsonProcessingException e) {
            logger.error("Failed to convert response to JSON string", e);
        }
        logger.info("Returning response");
        return response;
    }
}