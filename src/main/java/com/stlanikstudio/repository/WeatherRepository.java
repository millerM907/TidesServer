package com.stlanikstudio.repository;

import com.stlanikstudio.models.Weather;
import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends CrudRepository<Weather, Integer> {


}
