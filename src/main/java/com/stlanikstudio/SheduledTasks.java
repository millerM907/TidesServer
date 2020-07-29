package com.stlanikstudio;

import com.stlanikstudio.models.Weather;
import com.stlanikstudio.dao.WeatherDaoTwo;
import com.stlanikstudio.services.WeatherDao;
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

    private WeatherService weatherService = new WeatherService(new WeatherDaoTwo());
    private int countUpdateRow = 0;

    //обновлять таблицу Weather по раписанию
    @Scheduled(fixedRate = 18000)
    public void updateCurrentWeatherTable(){


        ArrayList<String> currentWeatherList = (ArrayList<String>) GismeteoParser.getGismeteoWeatherDataList();
        Weather weather = new Weather(
                currentWeatherList.get(0),
                currentWeatherList.get(1),
                currentWeatherList.get(2),
                currentWeatherList.get(3));

        if(countUpdateRow != 0) {
            weatherService.update(weather);
            System.out.println("Update");
        } else {
            weatherService.create(weather);
            countUpdateRow++;
        }

    }
}
