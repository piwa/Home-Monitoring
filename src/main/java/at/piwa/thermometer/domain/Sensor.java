package at.piwa.thermometer.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by philippwaibel on 18/01/16.
 */
@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Sensor {

    @XmlElement
    private String Id;

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "Type")
    private String type;

    @XmlElement(name = "Description")
    private String description;

    @XmlElement(name = "SensorConnection")
    private SensorConnection sensorConnection;

    @XmlElement(name = "HardwareID")
    private String hardwareID;

}
