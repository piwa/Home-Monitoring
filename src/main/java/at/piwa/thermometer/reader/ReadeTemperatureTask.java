package at.piwa.thermometer.reader;

import at.piwa.thermometer.InMemoryCache;
import at.piwa.thermometer.domain.Sensor;
import at.piwa.thermometer.domain.SensorConnection;
import at.piwa.thermometer.domain.Temperature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by philippwaibel on 19/01/16.
 */
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
    private at.piwa.thermometer.MqttConnector mqttConnector;


    @Scheduled(fixedRateString = "${temperature.read.task.interval}")
    public void readTemperatureTask() {
        log.info("Read Temperatures");
        List<Temperature> temperatureList = new ArrayList<>();
        for (Sensor sensor : inMemoryCache.getSensors()) {
            Temperature temperature = null;
            if (sensor.getSensorConnection() == SensorConnection.I2C) {
                temperature = i2cReader.readTemperature(sensor);
            } else if (sensor.getSensorConnection() == SensorConnection.WIRE_1) {
                temperature = w1Reader.readTemperature(sensor);
            }

            log.info("New temperature reading: " + temperature);

            if(temperature != null) {
                temperatureList.add(temperature);
            }
        }

        if(temperatureList.size() > 0) {
            mqttConnector.sendTemperature(temperatureList);
        }

        log.info("Read Temperatures Done");
    }
}