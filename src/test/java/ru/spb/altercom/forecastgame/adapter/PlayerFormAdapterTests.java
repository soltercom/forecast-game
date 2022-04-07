package ru.spb.altercom.forecastgame.adapter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.spb.altercom.forecastgame.domain.Player;
import ru.spb.altercom.forecastgame.form.PlayerForm;
import ru.spb.altercom.forecastgame.form.PlayerFormAdapter;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.spb.altercom.forecastgame.helpers.Stubs.getNewPlayerForm;
import static ru.spb.altercom.forecastgame.helpers.Stubs.getPlayer;

class PlayerFormAdapterTests {

    @Test
    @DisplayName("Player -> PlayerForm -> Player conversion")
    void conversion() {
        var player = getPlayer();

        var adapter = new PlayerFormAdapter();
        var result = adapter.createPlayerFromPlayerForm(adapter.createPlayerFormFromPlayer(player));

        assertThat(player).isEqualTo(result)
            .hasFieldOrPropertyWithValue("id", result.getId())
            .hasFieldOrPropertyWithValue("username", result.getUsername())
            .hasFieldOrPropertyWithValue("password", result.getPassword())
            .hasFieldOrPropertyWithValue("isAdmin", result.isAdmin());
    }

    @Test
    @DisplayName("PlayerForm -> Player -> PlayerForm conversion")
    void reverseConversion() {
        var playerForm = getNewPlayerForm();

        var adapter = new PlayerFormAdapter();
        var result = adapter.createPlayerFormFromPlayer(adapter.createPlayerFromPlayerForm(playerForm));

        assertThat(playerForm).isEqualTo(result)
            .hasFieldOrPropertyWithValue("id", playerForm.id())
            .hasFieldOrPropertyWithValue("name", playerForm.name());
    }

}
