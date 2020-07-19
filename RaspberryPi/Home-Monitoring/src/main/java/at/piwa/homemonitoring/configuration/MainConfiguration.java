package at.piwa.homemonitoring.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@EnableAutoConfiguration
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:network-config/network.properties"),
        @PropertySource("classpath:openweather-config/openweather.properties"),
        @PropertySource("classpath:sensor-config/sensor.properties")
})
public class MainConfiguration {


}
