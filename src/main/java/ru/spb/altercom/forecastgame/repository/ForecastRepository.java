package ru.spb.altercom.forecastgame.repository;

import org.springframework.data.repository.CrudRepository;
import ru.spb.altercom.forecastgame.domain.Forecast;

public interface ForecastRepository extends CrudRepository<Forecast, Long> {
}
