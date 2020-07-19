package at.piwa.homemonitoring.temperature.reader;

import at.piwa.homemonitoring.InMemoryCache;
import at.piwa.homemonitoring.temperature.MqttConnectorTemperature;
import at.piwa.homemonitoring.temperature.domain.Sensor;
import at.piwa.homemonitoring.temperature.domain.SensorConnection;
import at.piwa.homemonitoring.temperature.domain.Temperature;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ReadeTemperatureTask {

    @Autowired
    private I2cReader i2cReader;
    @Autowired
    private W1Reader w1Reader;
    @Autowired
    private InMemoryCache inMemoryCache;
    @Autowired
    private MqttConnectorTemperature mqttConnector;

    @Value("${measuring.entities.temperature}")
    private Boolean measureTemperature = false;


    @Scheduled(fixedRateString = "${temperature.read.task.interval}")
    public void readTemperatureTask() {

        if(measureTemperature) {

            log.info("Read Temperatures");
            List<Temperature> temperatureList = new ArrayList<>();

            DateTime measuringTime = DateTime.now();

            for (Sensor sensor : inMemoryCache.getSensors()) {
                Temperature temperature = null;
                if (sensor.getSensorConnection() == SensorConnection.I2C) {
                    temperature = i2cReader.readTemperature(sensor);
                } else if (sensor.getSensorConnection() == SensorConnection.WIRE_1) {
                    temperature = w1Reader.readTemperature(sensor);
                }

                temperature.setTime(measuringTime.getMillis());

                log.info("New temperature reading: " + temperature);
                temperatureList.add(temperature);

            }

            if (temperatureList.size() > 0) {
                mqttConnector.sendTemperature(temperatureList);
            }

            log.info("Read Temperatures Done");
        }
    }
}