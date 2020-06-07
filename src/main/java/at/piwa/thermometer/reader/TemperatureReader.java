package at.piwa.thermometer.reader;

import at.piwa.thermometer.domain.Sensor;
import at.piwa.thermometer.domain.Temperature;

/**
 * Created by philippwaibel on 19/06/16.
 */
public interface TemperatureReader {
    Temperature readTemperature(Sensor sensor);
}
