package at.piwa.homemonitoring.network;

import at.piwa.homemonitoring.MqttConnector;
import at.piwa.homemonitoring.network.domain.NetworkStats;
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
public class MqttConnectorNetworkStats extends MqttConnector {
    @Value("${networkstats.mqtt.topic}")
    private String networkStatsTopic = "home_monitoring/network_stats";

    public void sendNetworkStats(NetworkStats networkStats) {

        try {
            log.debug("Send networkStats: " + networkStats);
            openMqttConnection();
            try {
                objectMapper.registerModule(new JodaModule());
                String payload = objectMapper.writeValueAsString(networkStats);

                MqttMessage msg = new MqttMessage(payload.getBytes());
                msg.setQos(0);
                msg.setRetained(true);
                mqttClient.publish(networkStatsTopic, msg);
            } catch (JsonProcessingException e) {
                log.error("Exception", e);
            }
            log.debug("Send NetworkStats done");

        } catch (MqttException e) {
            log.error("Exception", e);
        } finally {
            closeMqttConnection();
        }
    }

}
