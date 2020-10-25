package com.stlanikstudio.sheduleds;

import com.stlanikstudio.models.TidesDay;
import com.stlanikstudio.models.Weather;
import com.stlanikstudio.parsers.GismeteoParser;
import com.stlanikstudio.parsers.TidesParser;
import com.stlanikstudio.services.TidesDayService;
import com.stlanikstudio.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@EnableScheduling
@Component
public class SheduledTasks {

    private int countUpdateRow = 0;

    private String monthUpdateTable;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private TidesDayService tidesDayService;

    //обновление таблицы Weather каждые 30 мин
    @Scheduled(fixedRate = 1800000)
    public void updateCurrentWeatherTable(){
        ArrayList<String> currentWeatherList = (ArrayList<String>) GismeteoParser.getGismeteoWeatherDataList();
        Weather weather = new Weather(
                1,
                currentWeatherList.get(0),
                currentWeatherList.get(1),
                currentWeatherList.get(2),
                currentWeatherList.get(3));
        weatherService.updateWeather(weather);
    }


    @Scheduled(fixedRate = 1800000)
    public void updateTidesTable() {
        String currentMonth = LocalDateTime.now(ZoneId.of("Asia/Magadan")).getMonth().toString();
        //если месяца даты обновления таблицы приливов не совпадают - обновить таблицу приливов
        if(!monthUpdateTable.equals(currentMonth)) {
            tidesDayService.deleteAllTidesDay();
            List<String> tidesTable = TidesParser.getTidesTable();
            if (tidesTable != null) {
                for (int i = 0; i < tidesTable.size(); i += 11) {
                    TidesDay tidesDay = new TidesDay(
                            Integer.valueOf(tidesTable.get(i)),
                            tidesTable.get(i + 1),
                            tidesTable.get(i + 2),
                            tidesTable.get(i + 3),
                            tidesTable.get(i + 4),
                            tidesTable.get(i + 5),
                            tidesTable.get(i + 6),
                            tidesTable.get(i + 7),
                            tidesTable.get(i + 8),
                            tidesTable.get(i + 9),
                            tidesTable.get(i + 10)
                    );
                    tidesDayService.addTidesDay(tidesDay);
                    monthUpdateTable = currentMonth;
                }
            }
        }

    }
}
