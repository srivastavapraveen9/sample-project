# RabbitMQ

# RabbitMQ through Docker

C:\Users\dell>docker-compose -f docker-compose-rabbitmq.yml up -d
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
 
 
# Save the below in docker-compose-rabbitmq.yml

version: '3.8'

services:
  rabbitmq:
    image: rabbitmq:3-management
    container_name: rabbitmq
    ports:
      - "5672:5672"      # AMQP port for Spring Boot
      - "15672:15672"    # Management UI
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest
    healthcheck:
      test: ["CMD", "rabbitmqctl", "status"]
      interval: 10s
      retries: 5

# Docker
docker ps
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

## Verify RabbitMQ

docker ps
docker logs rabbitmq

### custom virtual host

# Enter the container
docker exec -it rabbitmq bash

# Create virtual host
rabbitmqctl add_vhost /abc

# Grant permissions to user 'guest' for /abc
rabbitmqctl set_permissions -p /abc guest ".*" ".*" ".*"


## application.yml

spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    virtual-host: /abc   # Custom virtual host


## Verify Connection

Open Management UI: http://localhost:15672.

Switch to virtual host /abc.

You can now create queues and exchanges in /abc for your app.

## switch back to the default virtual host / in RabbitMQ

# application.yml

spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    virtual-host: /   # default vhost
# Remove or Ignore Custom VHost

docker exec -it rabbitmq bash
rabbitmqctl delete_vhost /abc

# management UI
http://localhost:15672
Default credentials: guest / guest

## Check firewall / network
Ensure nothing is blocking localhost:5672.

On Linux/Mac:

telnet localhost 5672
# or
nc -zv localhost 5672

## Docker example for Spring Boot + RabbitMQ
docker run -d --name rabbitmq \
  -p 5672:5672 -p 15672:15672 \
  rabbitmq:3-management
