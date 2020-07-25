package com.stlanikstudio.models;

import javax.persistence.*;

@Entity
@Table(name = "weather")
public class Weather {

    @Id @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "temperature")
    private String temperature;

    @Column(name = "humidity")
    private String humidity;

    @Column(name = "wind_direction")
    private String wind_direction;

    @Column(name = "wind_force")
    private String wind_force;

    public Weather(){}

    public Weather(String temperature, String wind_force, String wind_direction, String humidity){
        this.id = id;
        this.temperature = temperature;
        this.humidity = humidity;
        this.wind_direction = wind_direction;
        this.wind_force = wind_force;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getWind_force() {
        return wind_force;
    }

    public void setWind_force(String wind_force) {
        this.wind_force = wind_force;
    }
}
