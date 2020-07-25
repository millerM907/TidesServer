package com.stlanikstudio.services;

import com.stlanikstudio.models.Weather;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class WeatherDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Weather create(Weather weather) {
        entityManager.persist(weather);
        return weather;
    }

    public Weather update(int id, Weather weather) {
        Weather original = entityManager.find(Weather.class, id);
        if (original != null) {
            original.setTemperature(weather.getTemperature());
            original.setHumidity(weather.getHumidity());
            original.setWind_direction(weather.getWind_direction());
            original.setWind_force(weather.getWind_force());
            entityManager.merge(original);
        }
        return original;
    }

    public Weather getById(int id) {
        return entityManager.find(Weather.class, id);
    }
}
