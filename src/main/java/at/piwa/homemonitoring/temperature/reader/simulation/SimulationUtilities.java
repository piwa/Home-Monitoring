package at.piwa.homemonitoring.temperature.reader.simulation;

import at.piwa.homemonitoring.temperature.domain.Sensor;
import at.piwa.homemonitoring.temperature.domain.Temperature;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class SimulationUtilities {

    public static Temperature createSimulationTemperature(Sensor sensor) {
        int min = 20;
        int max = 26;

        Temperature temp = new Temperature();

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        temp.setTemperature(randomNum);
        temp.setSensor(sensor);

        return temp;
    }

}
