package com.stlanikstudio.services;

import com.stlanikstudio.dao.Dao;
import com.stlanikstudio.models.Weather;

@org.springframework.stereotype.Service
public class WeatherService extends Service<Weather> {

    public WeatherService(Dao dao) {
        super(dao);
    }

    @Override
    public void create(Weather entity) {
        super.create(entity);
    }

    @Override
    public void update(Weather entity) {
        super.update(entity);
    }

    public Weather getById(Integer id) {
        return super.getById(id);
    }
}
