package at.piwa.homemonitoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

@Slf4j
public abstract class MqttConnector {

    @Value("${mqtt.uri.tcp}")
    private String mqttUri;

    protected ObjectMapper objectMapper = new ObjectMapper();
    protected IMqttClient mqttClient;

    public MqttConnector() {
        objectMapper.registerModule(new JodaModule());
    }

    protected void sendMessage(String topic, String payload) throws MqttException {
        MqttMessage msg = new MqttMessage(payload.getBytes());
        msg.setQos(0);
        msg.setRetained(true);
        mqttClient.publish(topic, msg);
    }

    protected void closeMqttConnection() {
        if (mqttClient != null) {
            try {
                mqttClient.disconnect();
                mqttClient.close();
            } catch (MqttException e) {
                log.error("Exception", e);
            }
        }
    }

    protected void openMqttConnection() throws MqttException {

        String publisherId = UUID.randomUUID().toString();
        mqttClient = new MqttClient(mqttUri, publisherId, new MemoryPersistence());

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        mqttClient.connect(options);
    }

}