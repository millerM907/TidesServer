package com.stlanikstudio.controllers;

import com.stlanikstudio.models.Weather;
import com.stlanikstudio.services.WeatherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherRestController {

    @Autowired
    private WeatherDao weatherDao;

    /*private WeatherDaoImp weatherDaoImp;*/

    @GetMapping("/currentWeather")
    public Weather getCurrentWeather(){

        return weatherDao.getById(1);
    }
}
