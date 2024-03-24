package com.example.kafkaapplication;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;

public class KafkaProducerExample {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC = "test-topic";

    public static void main(String[] args) {
        Properties config = new Properties();
        config.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(config);

        try {
            // Send a few test messages
            for (int i = 0; i < 10; i++) {
                String message = "Test Message " + i;
                ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, message);
                producer.send(record);
                System.out.println("Sent message: " + message);
            }
        } finally {
            producer.flush();
            producer.close();
        }
    }
}