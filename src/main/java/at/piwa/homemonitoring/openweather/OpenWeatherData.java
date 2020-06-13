package at.piwa.homemonitoring.openweather;


import at.piwa.homemonitoring.temperature.domain.MeasuredEntity;
import at.piwa.homemonitoring.temperature.domain.Sensor;
import at.piwa.homemonitoring.temperature.domain.SensorConnection;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by philippwaibel on 17/01/16.
 */
@Data
public class OpenWeatherData {

    @JsonProperty("measured_entity")
    private MeasuredEntity measuredEntity;

    @JsonProperty("value")
    private double temperature;

}
