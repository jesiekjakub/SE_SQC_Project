package pl.put.poznan.sqc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.model.Step;
import pl.put.poznan.sqc.logic.visitor.ActorAction;
import pl.put.poznan.sqc.logic.visitor.CountKeywords;
import pl.put.poznan.sqc.logic.visitor.CountSteps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/actor_action")
public class ActorActionController {
    private static final Logger logger = LoggerFactory.getLogger(ActorActionController.class);

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> post(@RequestBody Scenario scenario) {
        logger.info("Received scenario: {}", scenario.getTitle());

        ActorAction visitor = new ActorAction();
        scenario.accept(visitor);
        List<Step> invalidSteps = (List<Step>) visitor.getResult();
        //int totalSteps = (int) visitor.getResult();

        Map<String, Object> response = new HashMap<>();
        response.put("keywordCount", invalidSteps);
        return response;
    }
}