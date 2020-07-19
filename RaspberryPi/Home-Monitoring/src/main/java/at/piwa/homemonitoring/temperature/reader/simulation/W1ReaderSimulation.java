package at.piwa.homemonitoring.temperature.reader.simulation;

import at.piwa.homemonitoring.temperature.domain.Sensor;
import at.piwa.homemonitoring.temperature.domain.Temperature;
import at.piwa.homemonitoring.temperature.reader.TemperatureReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("dev-simulation")
public class W1ReaderSimulation implements TemperatureReader {

    public Temperature readTemperature(Sensor sensor) {
        log.debug("Read Wire-1 sensor: " + sensor);
        Temperature temp = SimulationUtilities.createSimulationTemperature(sensor);
        log.debug("Read Wire-1 sensor done: " + sensor);
        return temp;
    }
}
