package ru.spb.altercom.forecastgame.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.spb.altercom.forecastgame.domain.Forecast;
import ru.spb.altercom.forecastgame.form.ForecastData;

import java.util.List;
import java.util.Optional;

public interface ForecastRepository extends CrudRepository<Forecast, Long> {

    @Query("""
        SELECT m.id as match_id,
               m.date as date,
               m.home_score as home_score,
               m.visitor_score as visitor_score,
               m.info as info,
               h.id as home_id,
               h.name as home_name,
               v.id as visitor_id,
               v.name as visitor_name,
               p.id as player_id,
               p.username as player_name,
               f.id as forecast_id,
               IFNULL(f.home_score, 0) as forecast_home_score,
               IFNULL(f.visitor_score, 0) as forecast_visitor_score
        FROM MATCHES as m
        LEFT JOIN TEAMS as h ON m.home = h.id
        LEFT JOIN TEAMS as v ON m.visitor = v.id
        JOIN PLAYERS as p ON true
        LEFT JOIN FORECASTS as f ON m.id = f.match AND p.id = f.player
        WHERE p.is_admin = false
    """)
    List<ForecastData> getForecastList();

    Optional<Forecast> findByMatchAndPlayer(Long match, Long player);

}
