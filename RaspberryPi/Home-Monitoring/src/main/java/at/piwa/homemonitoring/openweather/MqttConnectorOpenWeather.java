package at.piwa.homemonitoring.openweather;

import at.piwa.homemonitoring.MqttConnector;
import at.piwa.homemonitoring.network.domain.NetworkStats;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqttConnectorOpenWeather  extends MqttConnector {

    @Value("${openweather.mqtt.topic}")
    private String networkStatsTopic = "home_monitoring/open_weather";

    public MqttConnectorOpenWeather() {
        super();
    }

    public void sendOpenWeatherData(OpenWeatherData openWeatherData) {

        try {
            log.debug("Send OpenWeather Data: " + openWeatherData);
            openMqttConnection();
            try {
                String payload = objectMapper.writeValueAsString(openWeatherData);

                sendMessage(networkStatsTopic, payload);
            } catch (JsonProcessingException e) {
                log.error("Exception", e);
            }
            log.debug("Send OpenWeather Data done");

        } catch (MqttException e) {
            log.error("Exception", e);
        } finally {
            closeMqttConnection();
        }
    }

}
