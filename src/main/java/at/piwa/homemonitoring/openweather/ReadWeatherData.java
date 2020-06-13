package at.piwa.homemonitoring.openweather;

import at.piwa.homemonitoring.temperature.domain.MeasuredEntity;
import lombok.extern.slf4j.Slf4j;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReadWeatherData {

    @Value("${measuring.entities.openweather}")
    private Boolean queryOpenWeatherApi = false;
    @Value("${openweather.api.key}")
    private String apiKey;
    @Value("${openweather.city}")
    private String city;
    @Value("${openweather.country}")
    private String country;

    @Autowired
    private MqttConnectorOpenWeather mqttConnectorOpenWeather;

    @Scheduled(fixedRateString = "${openweather.read.task.interval}")
    public void readWeatherData() {

        if (queryOpenWeatherApi) {
            try {
                OWM owm = new OWM(apiKey);
                owm.setUnit(OWM.Unit.METRIC);

                CurrentWeather cwd = owm.currentWeatherByCityName(city, OWM.Country.valueOf(country));

                if (cwd.getMainData() != null && cwd.getMainData().getTemp() != null) {
                    log.info("OpenWeather Temperature: " + cwd.getMainData().getTempMin() + " / " + cwd.getMainData().getTemp() + " / " + cwd.getMainData().getTempMax() + " °C");

                    OpenWeatherData openWeatherData = new OpenWeatherData();
                    openWeatherData.setMeasuredEntity(MeasuredEntity.Temperature);
                    openWeatherData.setTemperature(cwd.getMainData().getTemp());
                    openWeatherData.setSensorId("0");
                    openWeatherData.setLocation("OpenWeather " + city + " " + country);

                    mqttConnectorOpenWeather.sendOpenWeatherData(openWeatherData);
                }

            } catch (APIException e) {
                log.error("Exception", e);
            }
        }
    }

}
