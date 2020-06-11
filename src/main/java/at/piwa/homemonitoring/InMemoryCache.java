package at.piwa.homemonitoring;

import at.piwa.homemonitoring.temperature.domain.Sensor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class InMemoryCache {

    private List<Sensor> sensors = new ArrayList<>();

    public void addAllSensors(List<Sensor> sensors) {
        this.sensors.addAll(sensors);
    }

    public void addSensor(Sensor sensor) {
        this.sensors.add(sensor);
    }
}
