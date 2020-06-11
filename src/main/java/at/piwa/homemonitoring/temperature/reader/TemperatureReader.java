package at.piwa.homemonitoring.temperature.reader;

import at.piwa.homemonitoring.temperature.domain.Sensor;
import at.piwa.homemonitoring.temperature.domain.Temperature;

/**
 * Created by philippwaibel on 19/06/16.
 */
public interface TemperatureReader {
    Temperature readTemperature(Sensor sensor);
}
