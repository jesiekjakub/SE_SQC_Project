package pl.put.poznan.sqc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/max_depth")
public class MaxDepthScenarioController {

    private static final Logger logger = LoggerFactory.getLogger(CountStepsController.class);

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String post(@RequestBody Map<String, List<String>> body) {
        List<String> steps = body.get("steps");
        // count number of steps
        return steps.toString();
    }

}
