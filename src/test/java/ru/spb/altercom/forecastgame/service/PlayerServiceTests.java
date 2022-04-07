package ru.spb.altercom.forecastgame.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.spb.altercom.forecastgame.form.PlayerForm;
import ru.spb.altercom.forecastgame.form.PlayerFormAdapter;
import ru.spb.altercom.forecastgame.repository.PlayerRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.spb.altercom.forecastgame.helpers.Stubs.*;

@SpringBootTest
@Transactional
class PlayerServiceTests {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerFormAdapter playerFormAdapter;

    @Autowired
    private PlayerRepository playerRepo;

    @Test
    @DisplayName("#add()")
    void add() {
        var playerForm = getNewPlayerForm();
        var id = playerService.add(playerForm);

        assertThat(id).isNotNull();
    }

    @Test
    @DisplayName("#edit()")
    void edit() {
        var playerForm = getNewPlayerForm();
        var id = playerService.add(playerForm);

        playerForm = getNewPlayerForm();
        var editedPlayerForm = new PlayerForm(id, playerForm.name(),
                playerForm.password(), playerForm.isAdmin());
        playerService.edit(editedPlayerForm);

        var persistedPlayerForm = playerService.findById(id);

        assertThat(persistedPlayerForm)
            .hasFieldOrPropertyWithValue("id", editedPlayerForm.id())
            .hasFieldOrPropertyWithValue("name", editedPlayerForm.name())
            .hasFieldOrPropertyWithValue("password", editedPlayerForm.password())
            .hasFieldOrPropertyWithValue("isAdmin", editedPlayerForm.isAdmin());
    }

    @Test
    @DisplayName("#findAll()")
    void findAll() {
        var playerForms = getListOfPlayerForms(playerService);
        var namesArray = playerForms.stream()
                .map(PlayerForm::name)
                .toArray(String[]::new);
        var list = playerService.findAll();

        assertThat(list).extracting(PlayerForm::name)
            .contains(namesArray);
    }

    @Test
    @DisplayName("#findById() successfully")
    void findById() {
        var playerForm = getPlayerForm(playerService);

        var persistedPlayerForm = playerService.findById(playerForm.id());
        assertThat(persistedPlayerForm)
                .hasFieldOrPropertyWithValue("id", playerForm.id())
                .hasFieldOrPropertyWithValue("name", playerForm.name());
    }

    @Test
    @DisplayName("#findById() with non existed id should throw an exception")
    void findByNonExistedId() {
        var playerForm = getPlayerForm(playerService);

        assertThatThrownBy(() -> playerService.findById(999L))
            .hasMessageContaining("999");
    }

    @Test
    @DisplayName("#delete()")
    void delete() {
        var id = getPlayerForm(playerService).id();
        playerService.delete(id);

        assertThatThrownBy(() -> playerService.findById(id))
                .hasMessageContaining(id.toString());
    }

}
