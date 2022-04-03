package ru.spb.altercom.forecastgame.repository;

import org.springframework.data.repository.CrudRepository;
import ru.spb.altercom.forecastgame.domain.Team;

public interface TeamRepository extends CrudRepository<Team, Long> {
}
