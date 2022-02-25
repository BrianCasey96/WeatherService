package com.storm.eunice.rest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storm.eunice.rest.api.controller.WeatherController;
import com.storm.eunice.rest.api.formatter.DateFormatter;
import com.storm.eunice.rest.api.repository.CustomisedWeatherSensorRepository;
import com.storm.eunice.rest.api.repository.CustomisedWeatherSensorRepositoryImpl;
import com.storm.eunice.rest.api.repository.WeatherSensorRepository;
import com.storm.eunice.rest.api.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WeatherSensorRepository weatherSensorRepository;

    @Bean
    public DateFormatter dateFormatter(){
        return new DateFormatter();
    };

    @Bean
    public WeatherService weatherService() {
        return new WeatherService(customisedWeatherSensorRepository());
    }

    @Bean
    public WeatherController weatherController() {
        return new WeatherController(weatherService(), objectMapper, dateFormatter());
    }

    @Bean
    public CustomisedWeatherSensorRepository customisedWeatherSensorRepository() {
        return new CustomisedWeatherSensorRepositoryImpl(weatherSensorRepository);
    }
}
