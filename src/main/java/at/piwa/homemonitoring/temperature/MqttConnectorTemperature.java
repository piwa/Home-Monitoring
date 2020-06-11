package at.piwa.homemonitoring.temperature;

import at.piwa.homemonitoring.MqttConnector;
import at.piwa.homemonitoring.temperature.domain.Temperature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MqttConnectorTemperature extends MqttConnector {

    @Value("${temperature.mqtt.topic}")
    private String temperatureTopic = "home_monitoring/sensors/temperature";

    public void sendTemperature(List<Temperature> temperatures) {

        try {
            log.debug("Send all temperatures start");
            openMqttConnection();

            for (Temperature temperature : temperatures) {
                try {
                    log.debug("Send temperature: " + temperature);
                    objectMapper.registerModule(new JodaModule());
                    String payload = objectMapper.writeValueAsString(temperature);

                    MqttMessage msg = new MqttMessage(payload.getBytes());
                    msg.setQos(0);
                    msg.setRetained(true);
                    mqttClient.publish(temperatureTopic, msg);
                    log.debug("Send temperature done");
                } catch (JsonProcessingException e) {
                    log.error("Exception", e);
                }
            }

            log.debug("Send all temperatures done");

        } catch (MqttException e) {
            log.error("Exception", e);
        } finally {
            closeMqttConnection();
        }
    }

}
