package com.stlanikstudio.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GismeteoParser {

    private static final String WEATHER_SITE_URL = "https://www.gismeteo.ru/weather-magadan-4063/now/";
    private static final String BROWSER_URL = "Chrome/86.0.4240.75 Safari/12.1.1";
    private static final String SEARCH_SITE_URL = "http://www.google.com";
    private static final String ERROR_VALUE = "-200";

    private static List getGeneralParametra(){
        List<Object> generalParametra = new ArrayList<>();

        Document doc;
        try {
            doc = Jsoup.connect(WEATHER_SITE_URL)
                    .userAgent(BROWSER_URL)
                    .referrer(SEARCH_SITE_URL)
                    .get();

        } catch (IOException e) {
            e.printStackTrace();
            generalParametra.add(ERROR_VALUE);
            return generalParametra;
        }

        generalParametra.add(doc);
        return generalParametra;
    }

    public static List getGismeteoWeatherDataList(){
        List generalParametra = getGeneralParametra();
        List<String> gismeteoWeatherDataList = new ArrayList<>();

        Document doc;
        if(!generalParametra.get(0).equals(ERROR_VALUE)){
            doc = (Document) generalParametra.get(0);
        } else {
            for(int i = 0; i < 4; i++){
                gismeteoWeatherDataList.add(i, ERROR_VALUE);
            }
            return gismeteoWeatherDataList;
        }

        //Температура в градусах Цельсия
        String tempValue = ERROR_VALUE;
        try {
            Elements tempContent = doc.select("span.js_value");
            String temperature = tempContent.get(0).text().replaceAll("^[\\n]?[+]?[\\n]?", "");
            tempValue = temperature.replace(",", ".");
            tempValue = tempValue.replaceAll("[−]", "-");
            tempValue = String.valueOf((int)Float.parseFloat(tempValue));
        } catch (NullPointerException e) {
            System.out.println("Temperature element not found on this web-page");
            e.printStackTrace();
        } finally {
            gismeteoWeatherDataList.add(tempValue);
        }

        try {
            Elements infoContent = doc.select("div.now__info.nowinfo");

            try {
                Elements windContent = infoContent.select("div.nowinfo__item.nowinfo__item_wind");

                //Скорость ветра в м/с
                String windValue = ERROR_VALUE;
                try {
                    Elements windSpeedContent = windContent.select("div.nowinfo__value");
                    windValue = windSpeedContent.get(0).text().replaceAll("[\\n]?", "");
                } catch (NullPointerException e) {
                    System.out.println("Wind element not found on this web-page");
                    e.printStackTrace();
                } catch (java.lang.NumberFormatException e) {
                    String[] windDataArray = windValue.split("-");
                    short firstWindValueSh = Short.parseShort(windDataArray[0]);
                    short secondWindValueSh = Short.parseShort(windDataArray[1]);
                    int averageWindValueI = (firstWindValueSh + secondWindValueSh) / 2;
                    windValue = String.valueOf(averageWindValueI);
                } finally {
                    gismeteoWeatherDataList.add(windValue);
                }

                //Направление ветра
                String windDirection = ERROR_VALUE;
                try {
                    Elements windDirectionContent = windContent.select("div.nowinfo__measure.nowinfo__measure_wind");
                    windDirection = windDirectionContent.get(0).text().replaceAll("(м/с)?", "").replaceAll(" ", "");
                    String[] directionShortName = {"С", "B", "З", "Ю"};
                    switch (windDirection){
                        case "Северный":
                            windDirection = directionShortName[0];
                            break;
                        case "Восточный":
                            windDirection = directionShortName[1];
                            break;
                        case "Западаный":
                            windDirection = directionShortName[2];
                            break;
                        case "Южный":
                            windDirection = directionShortName[3];
                            break;
                        default:
                            break;
                    }
                } catch (NullPointerException e) {
                    System.out.println("Wind direction element not found on this web-page");
                    e.printStackTrace();
                } finally {
                    gismeteoWeatherDataList.add(windDirection);
                }


            } catch (NullPointerException e) {
                System.out.println("Winds element not found on this web-page");
                e.printStackTrace();
                gismeteoWeatherDataList.add(1, ERROR_VALUE);
                gismeteoWeatherDataList.add(2, ERROR_VALUE);
            }

            //Влажность в %
            String humidityValue = ERROR_VALUE;
            try {
                Elements humidityContent = infoContent.select("div.nowinfo__item.nowinfo__item_humidity");
                Elements humidityValueContent = humidityContent.select("div.nowinfo__value");
                humidityValue = humidityValueContent.get(0).text().replaceAll("[\\n]", "");
            } catch (NullPointerException e) {
                System.out.println("Humidity element not found on this web-page");
                e.printStackTrace();
            } finally {
                gismeteoWeatherDataList.add(humidityValue);
            }
        } catch (NullPointerException e) {
            System.out.println("Winds and humidity element not found on this web-page");
            e.printStackTrace();
            gismeteoWeatherDataList.add(1, ERROR_VALUE);
            gismeteoWeatherDataList.add(2, ERROR_VALUE);
            gismeteoWeatherDataList.add(3, ERROR_VALUE);
        }

        return gismeteoWeatherDataList;
    }

    public static List getGismeteoSunActivityDataList(){

        List generalParametra = getGeneralParametra();
        List<String> gismeteoSunActivityDataList = new ArrayList<>();

        Document doc;
        if(!generalParametra.get(0).equals("-200")){
            doc = (Document) generalParametra.get(0);
        } else {
            for(int i = 0; i < 2; i++){
                gismeteoSunActivityDataList.add(i, ERROR_VALUE);
            }
            return gismeteoSunActivityDataList;
        }

        //Парсинг времени рассвета и заката
        try {
            Elements sunContent = doc.select("div.nowastro__time");
            String rising = sunContent.get(0).text().replaceAll("[\\n]?", "");
            String sunset = sunContent.get(1).text().replaceAll("[\\n]?", "");
            if(rising.length() == 4){
                rising = "0" + rising;
            }
            if(sunset.length() == 4){
                sunset = "0" + sunset;
                String exchange = sunset;
                sunset = rising;
                rising = exchange;
            }

            LocalDateTime localTodayTime = LocalDateTime.now(ZoneId.of("Asia/Magadan"));

            //шаблон для подстановки времени
            String currentYearMonthDay = localTodayTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            rising = currentYearMonthDay + "T" + rising + ":00.000000Z";
            sunset = currentYearMonthDay + "T" + sunset + ":00.000000Z";

            gismeteoSunActivityDataList.add(rising);
            gismeteoSunActivityDataList.add(sunset);

        } catch (NullPointerException e) {
            System.out.println("Sun activity element not found on this web-page");
            e.printStackTrace();
            gismeteoSunActivityDataList.add(ERROR_VALUE);
            gismeteoSunActivityDataList.add(ERROR_VALUE);
        }
        return gismeteoSunActivityDataList;
    }
}
