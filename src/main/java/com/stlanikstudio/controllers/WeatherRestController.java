package com.stlanikstudio.controllers;

import com.stlanikstudio.models.Weather;
import com.stlanikstudio.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class WeatherRestController {

    /*@Autowired
    private WeatherDao weatherDao;*/

    /*@Autowired
    private WeatherDaoTwo weatherDaoTwo;*/

    @Autowired
    private WeatherService weatherService;

    /*private WeatherDaoImp weatherDaoImp;*/

    /*@GetMapping("/currentWeather")
    public Weather getCurrentWeather(){
        return weatherDao.getById(1);
    }*/

    @GetMapping("/currentWeather")
    public Optional<Weather> getCurrentWeather(){
        return weatherService.getWeatherById(1);
    }
}
