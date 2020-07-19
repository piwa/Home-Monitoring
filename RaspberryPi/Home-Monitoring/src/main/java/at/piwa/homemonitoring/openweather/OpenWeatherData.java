package at.piwa.homemonitoring.openweather;


import at.piwa.homemonitoring.MeasuredEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OpenWeatherData {

    @JsonProperty("measured_entity")
    private MeasuredEntity measuredEntity;

    @JsonProperty("location")
    private String location;

    @JsonProperty("sensor_id")
    private String sensorId;

    @JsonProperty("value")
    private double temperature;

}
