package com.stlanikstudio.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TidesParser {

    private static final String TIDES_SITE_URL = "https://tides4fishing.com/as/northeast-russia/nagaeva-bay-tauiskaya-bay";
    private static final String BROWSER_URL = "Chrome/77.0.3865.90 Safari/12.1.1";
    private static final String SEARCH_SITE_URL = "http://www.google.com";

    /**
     * This method connect to TIDES_SITE_URL, download html page and parse her.
     * @return List<String> tidesTable, which contains a table of tides for the current month for Nagaev Bay.
     */
    public static List<String> getTidesTable(){

        List<String> tidesTable = new ArrayList<>();

        String postficsTime = "-01T00:10:00.000000Z";

        LocalDateTime currentLocalDateTime = LocalDateTime.now(ZoneId.of("Asia/Magadan"));
        String prefixCurrentDate = currentLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String time = prefixCurrentDate + postficsTime;

        Instant instantConstTime = Instant.parse(time);
        long constTimeNumber = Instant.ofEpochSecond(0L).until(instantConstTime, ChronoUnit.SECONDS);

        Instant instantCurrentTime = currentLocalDateTime.toInstant(ZoneOffset.UTC);
        long currentTimeNumber = Instant.ofEpochSecond(0L).until(instantCurrentTime, ChronoUnit.SECONDS);

        /*Если сегодня 1 число и текущее время меньше 00 ч 10 мин, то мы возвращаем null,
         *иначе загружаем страницу для парсинга по URL TIDES_SITE_URL.
         */
        if(currentTimeNumber < constTimeNumber){
            return null;
        } else {
            Document doc;
            try {
                doc = Jsoup.connect(TIDES_SITE_URL)
                        .userAgent(BROWSER_URL)
                        .referrer(SEARCH_SITE_URL)
                        .get();


                LocalDate local = LocalDate.now(ZoneId.of("Asia/Magadan"));
                int maxIndexOfTable = (local.lengthOfMonth() + 4) * 2;

                for (int i = 4; i <= maxIndexOfTable; i += 2) {
                    String cssQuery = "#tabla_mareas > tbody > tr:nth-child(" + i + ")";
                    Elements dataContent = doc.select(cssQuery);

                    String waveDataContent = dataContent.text().replaceAll("(h)?(m)?", "");
                    waveDataContent = waveDataContent.replaceAll("[A-Za-z]?", "");
                    String[] waveDataArray = waveDataContent.split("  ");

                    //записываем в список значения массива строк (строка из таблицы)
                    tidesTable.addAll(Arrays.asList(waveDataArray).subList(0, waveDataArray.length - 1));

                    //если у нас 3 цикла прилив/отлив в день, то добавляем две пустые строки
                    if (waveDataArray.length == 10) {
                        tidesTable.add("");
                        tidesTable.add("");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        return tidesTable;
    }
}
