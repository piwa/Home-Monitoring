package at.piwa.homemonitoring.bootstrap.sensortypes;

import at.piwa.homemonitoring.temperature.domain.Sensor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name="SensorConfigurations")
@XmlAccessorType(XmlAccessType.FIELD)
public class SensorConfigurations {

    @XmlElement(name = "Sensor")
    private List<Sensor> sensors = new ArrayList<>();

}
