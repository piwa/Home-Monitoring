package at.piwa.homemonitoring.bootstrap.sensortypes;

import at.piwa.homemonitoring.temperature.domain.Sensor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

@Component
@Slf4j
public class SensorsReaderImpl {

    @Value("${sensor.configurations.path}")
    private String sensorConfigurationsOriginalPath;

    public List<Sensor> readSensorConfigurations() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance( SensorConfigurations.class );
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            File file = new File(sensorConfigurationsOriginalPath);

            SensorConfigurations sensorConfigurations = (SensorConfigurations) jaxbUnmarshaller.unmarshal(file);

            return sensorConfigurations.getSensors();
        } catch (JAXBException e) {
            log.error("Exception", e);
        }

        return null;
    }
}
