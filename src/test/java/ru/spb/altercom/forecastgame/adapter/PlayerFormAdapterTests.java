package ru.spb.altercom.forecastgame.adapter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.spb.altercom.forecastgame.domain.Player;
import ru.spb.altercom.forecastgame.form.PlayerForm;
import ru.spb.altercom.forecastgame.form.PlayerFormAdapter;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerFormAdapterTests {

    @Test
    @DisplayName("Player -> PlayerForm -> Player conversion")
    void conversion() {
        var player = new Player(1L, "Test");

        var adapter = new PlayerFormAdapter();
        var result = adapter.createPlayerFromPlayerForm(adapter.createPlayerFormFromPlayer(player));

        assertThat(player).isEqualTo(result)
            .hasFieldOrPropertyWithValue("id", result.getId())
            .hasFieldOrPropertyWithValue("name", result.getName());
    }

    @Test
    @DisplayName("PlayerForm -> Player -> PlayerForm conversion")
    void reverseConversion() {
        var playerForm = new PlayerForm(1L, "Test");

        var adapter = new PlayerFormAdapter();
        var result = adapter.createPlayerFormFromPlayer(adapter.createPlayerFromPlayerForm(playerForm));

        assertThat(playerForm).isEqualTo(result)
            .hasFieldOrPropertyWithValue("id", playerForm.id())
            .hasFieldOrPropertyWithValue("name", playerForm.name());
    }

}
