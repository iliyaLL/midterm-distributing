package org.example;

import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;

public class TaskProcessor {

    private static final String RESULT_API_URL = "http://localhost:8080/results";

    public void processTask(String payload) {
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        String cpuModel = processor.toString();

        long startTime = System.currentTimeMillis();
        int[] array = Arrays.stream(payload.replaceAll("[\\[\\]]", "").split(", "))
                .mapToInt(Integer::parseInt)
                .toArray();
        Arrays.sort(array);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        double cpuLoad = getCpuLoad(processor);

        Map<String, Object> resultData = new HashMap<>();
        resultData.put("cpuLoadAfter", cpuLoad);
        resultData.put("sortedArray", array);
        resultData.put("processingTime", elapsedTime);
        resultData.put("cpu", cpuModel);

        new RestTemplate().postForObject(RESULT_API_URL, resultData, String.class);
        System.out.println("Processed and sent result: " + resultData);
    }

    private static double getCpuLoad(CentralProcessor processor) {
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long[] ticks = processor.getSystemCpuLoadTicks();
        long totalCpu = Arrays.stream(TickType.values()).mapToLong(type -> ticks[type.getIndex()] - prevTicks[type.getIndex()]).sum();
        long idleCpu = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        return 1.0 - ((double) idleCpu / totalCpu);
    }
}
