package at.piwa.thermometer.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by philippwaibel on 17/01/16.
 */
@Data
public class Temperature {

    @JsonProperty("measured_object")
    private String measuredObject;

    private String location;

    @JsonProperty("sensor_type")
    private String sensorType;

    @JsonProperty("value")
    private double temperature;


    public void setSensor(Sensor sensor) {
        this.measuredObject = "temperature";
        this.location = sensor.getName();
        this.sensorType = sensor.getType();
    }
}
