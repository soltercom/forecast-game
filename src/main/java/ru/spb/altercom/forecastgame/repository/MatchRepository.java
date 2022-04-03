package ru.spb.altercom.forecastgame.repository;

import org.springframework.data.repository.CrudRepository;
import ru.spb.altercom.forecastgame.domain.Match;

public interface MatchRepository extends CrudRepository<Match, Long> {
}
