package com.example.kafkaapplication;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;

public class KafkaConsumerExampleMultiplePartition {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC = "test-topic-three-partitions";
    private static final String GROUP_ID = "console-consumer-1093";

    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(config);

        // Assign the partitions to the consumer
        List<TopicPartition> partitions = Arrays.asList(
                new TopicPartition(TOPIC, 0),
                new TopicPartition(TOPIC, 1),
                new TopicPartition(TOPIC, 2)
        );
        consumer.assign(partitions);

        try {
            // Start consuming messages
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String message = record.value();
                    int partition = record.partition();
                    System.out.println("Received message: " + message + ", Partition: " + partition);
                }
            }
        } finally {
            consumer.close();
        }
    }
}