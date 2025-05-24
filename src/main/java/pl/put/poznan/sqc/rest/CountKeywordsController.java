package pl.put.poznan.sqc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.visitor.CountKeywords;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/count_keywords")
public class CountKeywordsController {
    private static final Logger logger = LoggerFactory.getLogger(CountKeywordsController.class);

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> post(@RequestBody Scenario scenario) {
        logger.info("Received scenario: {}", scenario.getTitle());

        CountKeywords visitor = new CountKeywords();
        scenario.accept(visitor);
        int keywordSteps = (int) visitor.getResult();

        Map<String, Object> response = new HashMap<>();
        response.put("keywordCount", keywordSteps);
        logger.debug("Keyword Count: {}", keywordSteps);
        logger.info("Returning response");
        return response;
    }
}