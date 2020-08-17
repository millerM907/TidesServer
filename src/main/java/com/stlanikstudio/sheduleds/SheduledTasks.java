package com.stlanikstudio.sheduleds;

import com.stlanikstudio.models.Weather;
import com.stlanikstudio.parsers.GismeteoParser;
import com.stlanikstudio.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@EnableScheduling
@Component
public class SheduledTasks {

    /*@Autowired
    private WeatherDao weatherDao;*/

    /*@Autowired
    private WeatherDaoTwo weatherDaoTwo;*/

    private int countUpdateRow = 0;

    @Autowired
    private WeatherService weatherService;

    //обновлять таблицу Weather по раписанию
    @Scheduled(fixedRate = 18000)
    public void updateCurrentWeatherTable(){
        ArrayList<String> currentWeatherList = (ArrayList<String>) GismeteoParser.getGismeteoWeatherDataList();
        Weather weather = new Weather(
                1,
                currentWeatherList.get(0),
                currentWeatherList.get(1),
                currentWeatherList.get(2),
                currentWeatherList.get(3));

        if(countUpdateRow != 0) {
            weatherService.updateWeather(weather);
            System.out.println("Update");
        } else {
            weatherService.addWeather(weather);
            countUpdateRow++;
        }
    }
}
