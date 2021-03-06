package at.piwa.homemonitoring.temperature.domain;


import at.piwa.homemonitoring.MeasuredEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Temperature {

    @JsonProperty("time")
    private long time;

    @JsonProperty("measured_entity")
    private MeasuredEntity measuredEntity;

    @JsonProperty("sensor_id")
    private String sensorId;

    @JsonProperty("sensor_type")
    private String sensorType;

    @JsonProperty("location")
    private String location;

    @JsonProperty("sensor_connection")
    private SensorConnection sensorConnection;

    @JsonProperty("value")
    private double temperature;

    public void setSensor(Sensor sensor) {
        this.sensorId = sensor.getId();
        this.measuredEntity = sensor.getMeasuredEntity();
        this.sensorType = sensor.getType();
        this.location = sensor.getLocation();
        this.sensorConnection = sensor.getSensorConnection();
    }
}
