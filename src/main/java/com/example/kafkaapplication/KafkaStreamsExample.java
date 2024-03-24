package com.example.kafkaapplication;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class KafkaStreamsExample {

    private  String bootstrapServers = "localhost:9092";
    private  String inputTopic = "input-topic";
    private  String outputTopic = "output-topic";
// create a Kafka Streams application that reads from an input topic, transforms the messages by converting them
// to uppercase, and writes the transformed messages to an output topic.
    public void kafkaStreamsMethod() {

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams-example");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();

        // Define the processing topology
        KStream<String, String> inputStream = builder.stream(inputTopic);
        KStream<String, String> outputStream = inputStream.mapValues(value -> value.toUpperCase());
        outputStream.to(outputTopic);

        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();

        // Gracefully shut down the application
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }


    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public void setInputTopicName(String topicName) {
        this.inputTopic = inputTopic;
    }


    public void setOutputTopicName(String topicName) {
        this.outputTopic = outputTopic;
    }

}