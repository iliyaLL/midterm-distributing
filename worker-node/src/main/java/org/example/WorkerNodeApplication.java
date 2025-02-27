package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkerNodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkerNodeApplication.class, args);
        new MqttSubscriber().start();
    }
}
