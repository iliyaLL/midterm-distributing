package org.example;

import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TaskProcessor {

    private static final String RESULT_API_URL = "http://localhost:8080/results";

    public void processTask(String payload) {
        long startTime = System.currentTimeMillis();
        int[] array = Arrays.stream(payload.replaceAll("[\\[\\]]", "").split(", "))
                .mapToInt(Integer::parseInt)
                .toArray();
        Arrays.sort(array);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        Map<String, Object> resultData = new HashMap<>();
        resultData.put("sortedArray", array);
        resultData.put("cpuLoad", 35.5);
        resultData.put("processingTime", elapsedTime);

        new RestTemplate().postForObject(RESULT_API_URL, resultData, String.class);
        System.out.println("Processed and sent result: " + resultData);
    }
}
