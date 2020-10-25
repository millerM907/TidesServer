package com.stlanikstudio.controllers;

import com.stlanikstudio.models.TidesDay;
import com.stlanikstudio.models.Weather;
import com.stlanikstudio.services.TidesDayService;
import com.stlanikstudio.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0")
public class GlobalRestController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private TidesDayService tidesDayService;

    @GetMapping("/getCurrentWeather")
    public Optional<Weather> getCurrentWeather(){
        return weatherService.getWeatherById(1);
    }

    @GetMapping("/getTidesTable")
    public Iterable<TidesDay> getTidesTable() {
        return tidesDayService.getAllTidesDay();
    }
}
