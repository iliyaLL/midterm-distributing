package org.example;

import org.eclipse.paho.client.mqttv3.*;

public class MqttSubscriber {
    private static final String BROKER_URL = "tcp://localhost:1883";
    private static final String TOPIC = "task/node2";

    public void start() {
        try {
            MqttClient client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
            client.connect();
            client.subscribe(TOPIC, (topic, message) -> {
                String payload = new String(message.getPayload());
                System.out.println("Received Task: " + payload);
                new TaskProcessor().processTask(payload);
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
