package at.piwa.thermometer.bootstrap;

import at.piwa.thermometer.InMemoryCache;
import at.piwa.thermometer.bootstrap.sensortypes.SensorsReaderImpl;
import at.piwa.thermometer.domain.Sensor;
import at.piwa.thermometer.domain.SensorConnection;
import at.piwa.thermometer.reader.I2cReader;
import at.piwa.thermometer.reader.ReadeTemperatureTask;
import com.pi4j.io.i2c.I2CFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Created by philippwaibel on 17/01/16.
 */
@Component
@Slf4j
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private SensorsReaderImpl sensorsReader;
    @Autowired
    private InMemoryCache inMemoryCache;
    @Autowired
    private ReadeTemperatureTask readeTemperatureTask;
    @Autowired
    private I2cReader i2cReader;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        List<Sensor> sensorConfigurations = sensorsReader.readSensorConfigurations();

        for (Sensor sensor : sensorConfigurations) {
            log.debug("Try to register sensor: " + sensor.toString());
            inMemoryCache.addSensor(sensor);
            log.debug("Sensor registered: " + sensor.toString());

            if(sensor.getSensorConnection() == SensorConnection.I2C) {
                try {
                    i2cReader.init(sensor);
                } catch (IOException | I2CFactory.UnsupportedBusNumberException | InterruptedException e) {
                    log.error("Exception while initializing the I2C sensor");
                }
            }
        }

        readeTemperatureTask.readTemperatureTask();

    }
}
