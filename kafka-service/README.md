# Kafka

# RabbitMQ through Docker

C:\Users\dell>docker-compose -f docker-compose-kafka.yml up -d
# it will come look like in first time

time="2025-09-28T16:18:43+05:30" level=warning msg="C:\\Users\\dell\\docker-compose-rabbitmq.yml: the attribute `version` is obsolete, it will be ignored, please remove it to avoid potential confusion"
[+] Running 11/11
 ✔ rabbitmq Pulled                                                                                                                                                    43.0s
   ✔ abfb6e8cade6 Pull complete                                                                                                                                       37.8s
   ✔ 99fcc698c617 Pull complete                                                                                                                                       36.1s
   ✔ 9a2f9d1d259b Pull complete                                                                                                                                       35.7s
   ✔ f95e7cc1b747 Pull complete                                                                                                                                       36.2s
   ✔ b886bb8a2a67 Pull complete                                                                                                                                        1.3s
   ✔ b7df8e56f867 Pull complete                                                                                                                                       38.8s
   ✔ 3b67390743d5 Pull complete                                                                                                                                       37.7s
   ✔ 9f0fe8aad8db Pull complete                                                                                                                                       37.9s
   ✔ f2ea798b5ae0 Pull complete                                                                                                                                        1.5s
   ✔ 953cdd413371 Pull complete                                                                                                                                       34.1s
time="2025-09-28T16:19:27+05:30" level=warning msg="Found orphan containers ([kafka-ui kafka redis-ui redis]) for this project. If you removed or renamed this service in your compose file, you can run this command with the --remove-orphans flag to clean it up."
[+] Running 1/1
 ✔ Container rabbitmq  Started
 
 
# Save the below in docker-compose-kafka.yml

version: '3.8'
services:
  kafka:
    image: bitnami/kafka:3.6.0
    container_name: kafka
    ports:
      - "9092:9092"
    environment:
      # Run in KRaft mode (no Zookeeper)
      KAFKA_CFG_PROCESS_ROLES: broker,controller
      KAFKA_CFG_NODE_ID: 1
      KAFKA_CFG_CONTROLLER_QUORUM_VOTERS: 1@kafka:9093
      KAFKA_CFG_LISTENERS: PLAINTEXT://:9092,CONTROLLER://:9093
      KAFKA_CFG_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,CONTROLLER:PLAINTEXT
      KAFKA_CFG_CONTROLLER_LISTENER_NAMES: CONTROLLER
      KAFKA_CFG_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      # ✅ Auto create topic
      #KAFKA_CFG_AUTO_CREATE_TOPICS_ENABLE: "true"
      #KAFKA_CREATE_TOPICS: "test-topic:1:1"
      # format: <topic-name>:<partitions>:<replication-factor>

  kafka-ui:
    image: provectuslabs/kafka-ui:latest
    container_name: kafka-ui
    ports:
      - "8085:8080"
    depends_on:
      - kafka
    environment:
      KAFKA_CLUSTERS_0_NAME: local
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: kafka:9092


The only difference is how you run it. By default, Docker Compose looks for a file named docker-compose-kafka.yml.
 If you use a different name, like docker-compose-kafka.yml, you need to pass -f when starting:
docker-compose -f docker-compose-kafka.yml up -d
Some useful commands with your custom file:
Start in detached mode (background):

 docker-compose -f docker-compose-kafka.yml up -d


Stop containers:

 docker-compose -f docker-compose-kafka.yml down


View logs (Kafka only):

 docker-compose -f docker-compose-kafka.yml logs kafka


Check running containers:

 docker ps


So as long as you use the -f docker-compose-kafka.yml flag, it works exactly the same as the default docker-compose.yml.

If you need to run Kafka CLI commands (producer, consumer, topics):
docker exec -it kafka bash
Check topic:
kafka-topics.sh --list --bootstrap-server localhost:9092


#4. Send Messages (Producer)
Still inside Kafka container:
kafka-console-producer.sh --broker-list localhost:9092 --topic test-topic

Type messages (each line = one message). Example:
Hello Kafka
Kafka works!


#5. Read Messages (Consumer)
Open another terminal into the container and run:
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test-topic --from-beginning

You should see all the messages you typed with the producer.

#6. Using Kafka from Java (Spring Boot)
In your Spring Boot microservice, add Kafka dependency:
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>

In application.yml:
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: sample-group
      auto-offset-reset: earliest
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer

Producer example:
@Autowired
private KafkaTemplate<String, String> kafkaTemplate;

public void sendMessage(String msg) {
    kafkaTemplate.send("test-topic", msg);
}

Consumer example:
@KafkaListener(topics = "test-topic", groupId = "sample-group")
public void listen(String message) {
    System.out.println("Received: " + message);
}


✅ With this, you can produce & consume messages both via CLI (inside Docker) and via Java.
Do you want me to adapt this guide specifically for KRaft mode (no Zookeeper), so your docker-compose-kafka.yml works directly?

#1. Verify Kafka is running
Check running containers:
docker ps

#2. Connect to Kafka container
If you need to run Kafka CLI commands (producer, consumer, topics):
docker exec -it kafka bash

#3. Create a Topic
Inside the container:
kafka-topics.sh --create \
  --topic test-topic \
  --bootstrap-server localhost:9092 \
  --partitions 1 \
  --replication-factor 1

Check topic:
kafka-topics.sh --list --bootstrap-server localhost:9092

#4. Send Messages (Producer)
Still inside Kafka container:
kafka-console-producer.sh --broker-list localhost:9092 --topic test-topic

Type messages (each line = one message). Example:
Hello Kafka
Kafka works!

#5. Read Messages (Consumer)
Open another terminal into the container and run:
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test-topic --from-beginning

#6. Using Kafka from Java (Spring Boot)
In your Spring Boot microservice, add Kafka dependency:
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>

In application.yml:
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: sample-group
      auto-offset-reset: earliest
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer

Producer example:
@Autowired
private KafkaTemplate<String, String> kafkaTemplate;

public void sendMessage(String msg) {
    kafkaTemplate.send("test-topic", msg);
}

Consumer example:
@KafkaListener(topics = "test-topic", groupId = "sample-group")
public void listen(String message) {
    System.out.println("Received: " + message);
}


✅ With this, you can produce & consume messages both via CLI (inside Docker) and via Java.
