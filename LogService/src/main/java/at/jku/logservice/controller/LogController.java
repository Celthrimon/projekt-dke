package at.jku.logservice.controller;

import at.jku.logservice.model.Log;
import at.jku.logservice.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("mymood/loging")
public class LogController {

    @Autowired
    LogService logService;


    @RequestMapping(value = "/log", method = RequestMethod.POST)
    public ResponseEntity<String> saveLog(@RequestParam String message) throws IOException, EncodeException {

        return ResponseEntity.ok(logService.saveLog(new Log(message, new Date())));
    }

    @GetMapping("/all")
    List<Log> all() {
        return logService.getAllLogs();
    }

    @RequestMapping(value = "filter", method = RequestMethod.GET)
    public List<Log> getPosts(@RequestParam("filter") String filter) {
        return logService.getAllLogsBy(filter);
    }

    @GetMapping("/example")
    public Log example() {
        return new Log("beispiel", new Date());
    }


}
