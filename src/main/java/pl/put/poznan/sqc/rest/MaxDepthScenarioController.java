package pl.put.poznan.sqc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;
import pl.put.poznan.sqc.logic.visitor.ActorAction;
import pl.put.poznan.sqc.logic.model.MaxDepthRequest;
import pl.put.poznan.sqc.logic.visitor.MaxDepthScenario;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/max_depth")
public class MaxDepthScenarioController {
    private static final Logger logger = LoggerFactory.getLogger(MaxDepthScenarioController.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> post(@RequestBody MaxDepthRequest maxDepthRequest) {
        Scenario scenario = maxDepthRequest.getScenario();
        int maxDepth = maxDepthRequest.getMaxDepth();
        logger.info("Received scenario: {}", scenario.getTitle());
        logger.info("Received max depth: {}", maxDepth);

        MaxDepthScenario visitor = new MaxDepthScenario(maxDepth);
        scenario.accept(visitor);
        Scenario resultScenario = (Scenario) visitor.getResult();

        Map<String, Object> response = new HashMap<>();
        response.put("maxDepthScenario", resultScenario);
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
