package pl.put.poznan.sqc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;
import pl.put.poznan.sqc.logic.visitor.ActorAction;
import pl.put.poznan.sqc.logic.model.MaxDepthRequest;
import pl.put.poznan.sqc.logic.visitor.MaxDepthScenario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/max_depth")
public class MaxDepthScenarioController {
    private static final Logger logger = LoggerFactory.getLogger(MaxDepthScenarioController.class);

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
        return response;
    }
}
