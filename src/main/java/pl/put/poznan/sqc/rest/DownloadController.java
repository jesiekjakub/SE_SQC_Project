package pl.put.poznan.sqc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.sqc.logic.model.Scenario;
import pl.put.poznan.sqc.logic.visitor.Download;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/download")
public class DownloadController {
    private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<byte[]> post(@RequestBody Scenario scenario) {
        logger.info("Preparing scenario for download: {}", scenario.getTitle());

        Download visitor = new Download();
        scenario.accept(visitor);

        @SuppressWarnings("unchecked")
        List<String> numberedSteps = (List<String>) visitor.getResult();

        String text = String.join("\n", numberedSteps);
        logger.debug("Returned text: \n{}", text);

        byte[] content = text.getBytes(StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "scenario.txt");
        headers.setContentLength(content.length);
        logger.info("Downloading text");

        return ResponseEntity.ok()
                .headers(headers)
                .body(content);
    }
}
