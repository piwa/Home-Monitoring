package at.piwa.thermometer.bootstrap.sensortypes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;

/**
 * Created by philippwaibel on 18/10/2016.
 */
@Component
@Slf4j
public class SensorsWriteImpl {

    @Value("${sensor.configurations.path}")
    private String sensorConfigurationsPath;

    public void writeSensorConfigurations(SensorConfigurations sensorConfigurations) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance( SensorConfigurations.class );
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            File file = new File(sensorConfigurationsPath);

            if(!file.exists()) {
                file.createNewFile();
            }

            jaxbMarshaller.marshal(sensorConfigurations, file);

        } catch (JAXBException | IOException e) {
            log.error("Exception", e);
        }
    }
}
