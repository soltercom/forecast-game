package ru.spb.altercom.forecastgame.form;

import org.springframework.stereotype.Service;
import ru.spb.altercom.forecastgame.domain.Player;

@Service
public class PlayerFormAdapter {

    public PlayerForm createPlayerFormFromPlayer(Player player) {
        return new PlayerForm(player.getId(), player.getName());
    }

    public Player createPlayerFromPlayerForm(PlayerForm playerForm) {
        return new Player(playerForm.id(), playerForm.name());
    }

}
