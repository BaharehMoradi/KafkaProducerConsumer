package com.example.kafkaapplication;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaStreamsProducer {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC = "input-topic";

    public static void main(String[] args) {
        Properties config = new Properties();
        config.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(config);

        String[] names = {"Mike", "Jack", "Joe", "Emma", "Sophia", "Liam", "Olivia", "Noah", "Ava", "William",
                "Isabella", "James", "Mia", "Benjamin", "Charlotte", "Elijah", "Amelia", "Lucas", "Harper", "Henry"};
        try {
            // Send a few test messages
            for (int i = 0; i < 20; i++) {
                String message = "Hi my friend, " + names[i] + ". Are you OK?";
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
