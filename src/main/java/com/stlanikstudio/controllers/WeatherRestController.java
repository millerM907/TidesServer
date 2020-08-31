package com.stlanikstudio.controllers;

import com.stlanikstudio.models.Weather;
import com.stlanikstudio.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0")
public class WeatherRestController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/getCurrentWeather")
    public Optional<Weather> getCurrentWeather(){
        return weatherService.getWeatherById(1);
    }
}
