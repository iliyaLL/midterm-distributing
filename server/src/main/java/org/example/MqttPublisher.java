package org.example;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;

@RestController
@RequestMapping("/tasks")
public class MqttPublisher {
    private static final String BROKER_URL = "tcp://localhost:1883";

    @PostMapping("/send/{nodeId}")
    public String sendTask(@PathVariable String nodeId, @RequestBody int[] array) {
        String topic = "task/" + nodeId;
        try {
            MqttClient client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
            client.connect();
            String message = Arrays.toString(array);
            client.publish(topic, new MqttMessage(message.getBytes()));
            client.disconnect();
            return "Task sent to " + nodeId + ". Array:" + message;
        } catch (MqttException e) {
            e.printStackTrace();
            return "Error sending task!";
        }
    }
}
