# Kafka Consumer and Producer Examples
This repository provides simple Java code snippets for consuming and producing messages in Apache Kafka. The code demonstrates the basic functionality of Kafka consumers and producers using the Kafka client API.

## Prerequisites
Before running the application, make sure you have the following:

- Java 17
- Maven 3.8.8
- A recent version of the Git
- A recent version of Docker desktop for test class

### Getting Started
Clone or download the repository to your local machine.
Ensure you have the required Kafka dependencies in your project's classpath.
- Modify the Kafka connection details and topic information in the code as needed:
- BOOTSTRAP_SERVERS: Replace with the comma-separated list of Kafka bootstrap servers.
- TOPIC: Replace with the topic name from which you want to consume messages.
- GROUP_ID: Replace with a unique consumer group ID for your application.
Build and run the code using your preferred Java IDE or command-line tools.
Functionality
The code sets up a Kafka consumer that subscribes to a specified topic and continuously polls for new messages. When new messages are received, they are printed to the console. The consumer will keep running until it is interrupted or encounters a WakeupException.

## Configuration
The code uses the following configuration properties:

- bootstrap.servers: Specifies the Kafka bootstrap servers to connect to.
- group.id: Sets the consumer group ID for the Kafka consumer. Each consumer group processes messages independently.
- key.deserializer and value.deserializer: Specify the deserializer classes for the key and value of the consumed messages. The provided code assumes string key and value deserializers, but you can modify them based on your specific message types.

## Cleanup
The consumer is closed gracefully in the finally block to release any resources held by the consumer.

## Note
Make sure to have a running Kafka cluster with the specified topic and accessible bootstrap servers before executing this code.


#### Help for writing test class:
https://www.atomicjar.com/2023/06/testing-kafka-applications-with-testcontainers/

# 

## The following commands can be used to interact with Apache Kafka:

#### Kafka startup commands:
```diff
- .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
- .\bin\windows\kafka-server-start.bat .\config\server.properties

#### retrieves and describes the configuration of all brokers in the Kafka cluster.
- .\bin\windows\kafka-configs.bat --bootstrap-server localhost:9092 --entity-type brokers --describe --all

#### retrieves and describes the configuration of all topics in the Kafka cluster.
- .\bin\windows\kafka-configs.bat --bootstrap-server localhost:9092 --entity-type topics --describe --all

#### create a new topic named "test-topic" in the Kafka cluster.
- .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --create --topic test-topic

#### lists all the topics available in the Kafka cluster.
- .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --list

#### starts a console-based producer that allows you to send messages to the "test-topic" topic in the Kafka cluster.
- .\bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic test-topic

#### starts a console-based consumer that allows you to consume messages from the "test-topic" topic in the Kafka cluster, starting from the beginning of the topic.
- .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test-topic --from-beginning

#### request a list of consumer groups in the Kafka cluster. Each consumer group represents a logical grouping of Kafka consumers that work together to consume messages from Kafka topics.
- .\bin\windows\kafka-consumer-groups.bat --bootstrap-server localhost:9092 --list

#### create a new topic named "test-topic-three-partitions" with 3 partitions in the Kafka cluster.
- .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --create --topic test-topic-three-partitions --partitions 3

#### create a new topic named "input-topic" with 1 partitions in the Kafka cluster.
- .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --create --topic input-topic --partitions 1 --replication-factor 1

#### create a new topic named "output-topic" with 1 partitions in the Kafka cluster.
- .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --create --topic output-topic --partitions 1 --replication-factor 1