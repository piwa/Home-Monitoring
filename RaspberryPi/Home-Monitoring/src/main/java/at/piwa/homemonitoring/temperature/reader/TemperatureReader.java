package at.piwa.homemonitoring.temperature.reader;

import at.piwa.homemonitoring.temperature.domain.Sensor;
import at.piwa.homemonitoring.temperature.domain.Temperature;

public interface TemperatureReader {
    Temperature readTemperature(Sensor sensor);
}
