package com.example.kafkaapplication;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;


import org.apache.kafka.streams.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rnorth.ducttape.unreliables.Unreliables;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Testcontainers
public class KafkaStreamsExampleTest {

    @Container
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"));

    @Test
    public void testKafkaStreamsExample() throws InterruptedException {
        String bootstrapServers = kafka.getBootstrapServers();
        String inputTopic = "input-topic";
        String outputTopic = "output-topic";

        // Create properties for Kafka Streams configuration
        Properties producerProps = new Properties();
        producerProps.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams-example");
        producerProps.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProps.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        producerProps.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaStreamsExample kafkaStreamsExampleObject = new KafkaStreamsExample();
        kafkaStreamsExampleObject.setBootstrapServers(bootstrapServers);
        kafkaStreamsExampleObject.setInputTopicName(inputTopic);
        kafkaStreamsExampleObject.setOutputTopicName(outputTopic);

        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps);

//        Map inputStringArray = new HashMap<String, String>();
        Map<String, String> inputString = Map.of("key1", "value1", "key2", "value2");
        Map<String, String> expectedOutputString = Map.of("key1", "VALUE1", "key2", "VALUE2");


        producer.send(new ProducerRecord<>(inputTopic, "key1", inputString.get("key1")));
        producer.send(new ProducerRecord<>(inputTopic, "key2", inputString.get("key2")));

        kafkaStreamsExampleObject.kafkaStreamsMethod();

        producer.flush();
        producer.close();


        // Set up Kafka consumer to consume messages from the output topic
        Properties consumerProps = new Properties();
        consumerProps.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerProps.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        consumerProps.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singleton(outputTopic));

        Unreliables.retryUntilTrue(10, TimeUnit.SECONDS, () -> {

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

            if (records.isEmpty()) {
                return false;
            }

            // Assert the consumed messages
            Assertions.assertEquals(2, records.count());

            for (ConsumerRecord<String, String> record : records) {
                String key = record.key();
                String producedValue = inputString.get(key);
                String expectedValue = expectedOutputString.get(key);

                System.out.println("produced value is: "+ producedValue);
                System.out.println("consumed value is: "+ record.value());

                System.out.println("expected value is: "+ expectedValue);

                Assertions.assertEquals(expectedValue, record.value());
            }

            return true;
        });
                consumer.close();

    }
}


