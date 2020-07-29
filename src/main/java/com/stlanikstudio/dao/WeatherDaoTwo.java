package com.stlanikstudio.dao;

import com.stlanikstudio.dao.Dao;
import com.stlanikstudio.models.Weather;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class WeatherDaoTwo extends Dao<Weather> {

    @Override
    public void create(Weather entity) {
        super.create(entity);
    }

    @Override
    public void update(Weather entity) {
        super.update(entity);
    }

    @Override
    public Weather getById(Integer id) {
        return super.getById(id);
    }
}
