package org.example;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;

@RestController
@RequestMapping("/tasks")
public class MqttPublisher {
    private static final String BROKER_URL = "tcp://localhost:1883";
    private static final String TOPIC = "task/processing";

    @PostMapping("/send")
    public String sendTask(@RequestBody int[] array) {
        try {
            MqttClient client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
            client.connect();
            String message = Arrays.toString(array);
            client.publish(TOPIC, new MqttMessage(message.getBytes()));
            client.disconnect();
            return "Task Sent: " + message;
        } catch (MqttException e) {
            e.printStackTrace();
            return "Error sending task!";
        }
    }
}
