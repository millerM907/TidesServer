package com.stlanikstudio;


import com.stlanikstudio.services.WeatherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Application {

    @Autowired
    private WeatherDao weatherDao;

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

    }

}
