package com.stlanikstudio.services;

import com.stlanikstudio.models.TidesDay;
import com.stlanikstudio.repository.TidesDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TidesDayService {

    @Autowired
    private TidesDayRepository tidesDayRepository;

    public void addTidesDay(TidesDay tidesDay) {
        tidesDayRepository.save(tidesDay);
    }

    public Optional<TidesDay> getTidesDayById(Integer id) {
        return tidesDayRepository.findById(id);
    }
}
