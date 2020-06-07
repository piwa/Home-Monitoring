package at.piwa.thermometer;

import at.piwa.thermometer.domain.Temperature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class MqttConnector {

    @Value("${temperature.mqtt.topic}")
    private String topic = "home_monitoring/sensors/temperature";
    @Value("${mqtt.uri.tcp}")
    private String mqttUri;

    private IMqttClient mqttClient;

    public void sendTemperature(List<Temperature> temperatures) {

        try {
            log.debug("Send all temperatures start");
            openMqttConnection();

            for (Temperature temperature : temperatures) {
                try {
                    log.debug("Send temperature: " + temperature);
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JodaModule());
                    String payload = objectMapper.writeValueAsString(temperature);

                    MqttMessage msg = new MqttMessage(payload.getBytes());
                    msg.setQos(0);
                    msg.setRetained(true);
                    mqttClient.publish(topic,msg);
                    log.debug("Send temperature done");
                } catch (JsonProcessingException e) {
                    log.error("Exception", e);
                }
            }

            log.debug("Send all temperatures done");

        } catch (MqttException e) {
            log.error("Exception", e);
        }
        finally {
            closeMqttConnection();
        }
    }

    private void closeMqttConnection() {
        if(mqttClient != null) {
            try {
                mqttClient.disconnect();
                mqttClient.close();
            } catch (MqttException e) {
                log.error("Exception", e);
            }
        }
    }

    private void openMqttConnection() throws MqttException {

        String publisherId = UUID.randomUUID().toString();
        mqttClient = new MqttClient(mqttUri,publisherId, new MemoryPersistence());

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        mqttClient.connect(options);

    }


}