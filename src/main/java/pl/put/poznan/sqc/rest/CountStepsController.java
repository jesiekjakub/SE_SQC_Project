package pl.put.poznan.sqc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.sqc.logic.model.Step;
import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.visitor.CountSteps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/count_steps")
public class CountStepsController {

    private static final Logger logger = LoggerFactory.getLogger(CountStepsController.class);

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> post(@RequestBody Scenario scenario) {
        logger.info("Received scenario: {}", scenario.getTitle());

        CountSteps visitor = new CountSteps();
        scenario.accept(visitor);
        int totalSteps = (int) visitor.getResult();

        Map<String, Object> response = new HashMap<>();
        response.put("title", scenario.getTitle());
        response.put("totalStepCount", totalSteps);
        return response;
    }


}
