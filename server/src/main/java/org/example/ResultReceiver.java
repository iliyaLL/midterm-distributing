package org.example;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/results")
public class ResultReceiver {

    @PostMapping
    public String receiveResults(@RequestBody Map<String, Object> resultData) {
        System.out.println("Received Result: " + resultData);
        return "Result received successfully";
    }
}