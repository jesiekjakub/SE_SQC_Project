package pl.put.poznan.sqc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.visitor.Download;

import java.util.List;

@RestController
@RequestMapping("/download")
public class DownloadController {
    private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);

    @RequestMapping(method = RequestMethod.POST, produces = "text/plain")
    public String post(@RequestBody Scenario scenario) {
        logger.info("Preparing scenario for download: {}", scenario.getTitle());

        Download visitor = new Download();
        scenario.accept(visitor);

        @SuppressWarnings("unchecked")
        List<String> numberedSteps = (List<String>) visitor.getResult();

        String text = String.join("\n", numberedSteps);
        logger.debug("returned text: \n{}", text);
        logger.info("Downloading file");
        return text;
    }
}