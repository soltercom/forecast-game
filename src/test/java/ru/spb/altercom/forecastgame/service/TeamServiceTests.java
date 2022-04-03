package ru.spb.altercom.forecastgame.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.spb.altercom.forecastgame.form.TeamForm;
import ru.spb.altercom.forecastgame.form.TeamFormAdapter;
import ru.spb.altercom.forecastgame.repository.TeamRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.spb.altercom.forecastgame.helpers.Stubs.*;

@SpringBootTest
@Transactional
class TeamServiceTests {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamFormAdapter teamFormAdapter;

    @Autowired
    private TeamRepository teamRepo;

    @Test
    @DisplayName("#add()")
    void add() {
        var teamForm = getNewTeamForm();
        var id = teamService.add(teamForm);

        assertThat(id).isNotNull();
    }

    @Test
    @DisplayName("#edit()")
    void edit() {
        var id = getTeamForm(teamService).id();

        var teamForm = getTeamForm(teamService);
        var editedTeamForm = new TeamForm(id, teamForm.name());
        teamService.edit(editedTeamForm);

        var persistedTeamForm = teamService.findById(id);

        assertThat(persistedTeamForm)
            .hasFieldOrPropertyWithValue("id", editedTeamForm.id())
            .hasFieldOrPropertyWithValue("name", editedTeamForm.name());
    }

    @Test
    @DisplayName("#findAll()")
    void findAll() {
        var teamForms = getListOfTeamForms(teamService);
        var namesArray = teamForms.stream()
                .map(TeamForm::name)
                .toArray(String[]::new);
        var list = teamService.findAll();

        assertThat(list).extracting(TeamForm::name)
                .contains(namesArray);
    }

    @Test
    @DisplayName("#findById() successfully")
    void findById() {
        var teamForm = getTeamForm(teamService);

        var persistedTeamForm = teamService.findById(teamForm.id());
        assertThat(persistedTeamForm)
                .hasFieldOrPropertyWithValue("id", teamForm.id())
                .hasFieldOrPropertyWithValue("name", teamForm.name());
    }

    @Test
    @DisplayName("#findById() with non existed id should throw an exception")
    void findByNonExistedId() {
        var teamForm = getTeamForm(teamService);

        assertThatThrownBy(() -> teamService.findById(999L))
                .hasMessageContaining("999");
    }

    @Test
    @DisplayName("#delete()")
    void delete() {
        var id = getTeamForm(teamService).id();
        teamService.delete(id);

        assertThatThrownBy(() -> teamService.findById(id))
                .hasMessageContaining(id.toString());
    }

}
