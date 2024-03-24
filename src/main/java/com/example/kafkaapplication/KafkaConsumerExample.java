package com.example.kafkaapplication;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.WakeupException;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerExample {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC = "test-topic";
    private static final String GROUP_ID = "console-consumer-1093";

    public static void main(String[] args) {
        Properties config = new Properties();
        config.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        config.put("group.id", GROUP_ID);
        config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(config);
        consumer.subscribe(Collections.singletonList(TOPIC));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("Received message: " + record.value());
                }
            }
        } catch (WakeupException e) {
            // Ignore, we're closing
        } finally {
            consumer.close();
        }
    }
}