package at.piwa.homemonitoring.temperature.reader.simulation;

import at.piwa.homemonitoring.temperature.domain.Sensor;
import at.piwa.homemonitoring.temperature.domain.Temperature;
import at.piwa.homemonitoring.temperature.reader.TemperatureReader;
import com.pi4j.io.i2c.I2CFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@Profile("dev-simulation")
public class I2cReaderSimulation implements TemperatureReader {

    public I2cReaderSimulation() {
    }

    public Temperature readTemperature(Sensor sensor) {
        log.debug("Read I2C sensor: " + sensor);
        Temperature temp = SimulationUtilities.createSimulationTemperature(sensor);
        log.debug("Read I2C sensor done: " + sensor);
        return temp;
    }


    public void init(Sensor sensor) throws IOException, I2CFactory.UnsupportedBusNumberException, InterruptedException {

    }
}
