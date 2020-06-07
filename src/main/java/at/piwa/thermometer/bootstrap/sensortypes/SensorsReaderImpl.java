package at.piwa.thermometer.bootstrap.sensortypes;

import at.piwa.thermometer.domain.Sensor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * Created by philippwaibel on 18/10/2016.
 */
@Component
@Slf4j
public class SensorsReaderImpl {

    @Value("${sensor.configurations.path}")
    private String sensorConfigurationsOriginalPath;

    public List<Sensor> readSensorConfigurations() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance( SensorConfigurations.class );
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(sensorConfigurationsOriginalPath)).getFile());

            SensorConfigurations sensorConfigurations = (SensorConfigurations) jaxbUnmarshaller.unmarshal(file);

            return sensorConfigurations.getSensors();
        } catch (JAXBException e) {
            log.error("Exception", e);
        }

        return null;
    }
}
