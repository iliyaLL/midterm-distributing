# MQTT Task Management System

## 📦 Project Overview
This project implements a **Task Management System** using an **MQTT Broker** to distribute tasks among multiple nodes. Each node can receive tasks, process them, and send results back to a **Central Server** using a REST API. Performance metrics such as **CPU load**, **processing time**, and **start/end time** are also captured and reported.

---

## 📚 Technologies Used
- **Java**: Core programming language for both server and worker nodes.
- **Spring Boot 3.1.3**: For building RESTful APIs and running the central server.
- **Eclipse Paho MQTT Client 1.2.5**: For MQTT communication between the central server and nodes.
- **Mosquitto MQTT Broker**: Message broker for MQTT communication.
- **Maven**: Build tool and dependency manager.

---

## 🏗️ Project Structure
```plaintext
mqtt-task-manager/
├── central-server/
│   ├── src/main/java/com/example/centralserver/
│   │   ├── CentralServerApplication.java
│   │   ├── MqttPublisher.java
│   │   └── ResultReceiver.java
│   └── pom.xml
│
├── worker-node/
│   ├── src/main/java/com/example/workernode/
│   │   ├── WorkerNodeApplication.java
│   │   ├── MqttSubscriber.java
│   │   └── TaskProcessor.java
│   └── pom.xml
│
└── README.md
```

---

## 🚀 How to Run the Program

### **Step 1: Start the MQTT Broker (Mosquitto)**
Ensure the Mosquitto MQTT broker is running on port **1883**.
- Direct Installation (Linux Debian based):
  ```bash
  sudo apt update
  sudo apt install mosquitto
  sudo systemctl start mosquitto
  mosquitto -v
  ```

### **Step 2: Build and Run the Central Server**
```bash
cd server
mvn clean install
mvn spring-boot:run
```
- The server will start at **http://localhost:8080**.

### **Step 3: Build and Run the Worker Node**
Configure the url string in TaskProcessor.java line 10 and in MqttSubscriber line 6  (set ipv4 of your server on local network)

```bash
cd worker-node
mvn clean install
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port={port}
```

### **Step 4: Send a Task to a Specific Node**
You can send tasks using **cURL** (two nodes ):
```bash
curl -X POST http://localhost:8080/tasks/send/node1 \
-H "Content-Type: application/json" \
-d '[5,3,8,1,4,7]'
```

### **Step 5: Verify Results in the Central Server Log**
The results, including **sorted array, CPU load, start time, end time, and processing time**, will be logged on the server console.

---

## 📡 How It Works (System Flow)
1. **Central Server** publishes a task to a specific node topic (e.g., `task/node1`).
2. **Worker Nodes** are subscribed to unique topics (like `task/node1`).
3. Nodes process the task, capture **CPU load**, and send results back using a **REST POST request** to `/results`.
4. The **Central Server** receives and logs the results.

---

## 📊 Performance Metrics Captured
- **CPU Load**
- **Processing Time (in milliseconds)**
- **Sorted Array Results**

---

## 📧 Support
For any issues, feel free to open an issue on the repository.

