package com.stlanikstudio.services;

import com.stlanikstudio.models.Weather;
import com.stlanikstudio.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    public void addWeather(Weather weather){
        weatherRepository.save(weather);
    }

    public void updateWeather(Weather weather){
        weatherRepository.save(weather);
    }

    public Optional<Weather> getWeatherById(Integer id){
        return weatherRepository.findById(id);
    }
}

