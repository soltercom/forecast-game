package ru.spb.altercom.forecastgame.repository;

import org.springframework.data.repository.CrudRepository;
import ru.spb.altercom.forecastgame.domain.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    Player findByUsername(String username);
}
