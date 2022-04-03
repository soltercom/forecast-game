package ru.spb.altercom.forecastgame.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.spb.altercom.forecastgame.form.MatchForm;
import ru.spb.altercom.forecastgame.form.MatchFormAdapter;
import ru.spb.altercom.forecastgame.form.TeamForm;
import ru.spb.altercom.forecastgame.repository.MatchRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.spb.altercom.forecastgame.helpers.Stubs.getListOfTeamForms;
import static ru.spb.altercom.forecastgame.helpers.Stubs.getMatchForm;

@SpringBootTest
@Transactional
class MatchServiceTests {

    @Autowired
    private MatchService matchService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private MatchFormAdapter matchFormAdapter;

    @Autowired
    private MatchRepository matchRepo;

    @Test
    @DisplayName("#add()")
    void add() {
        var matchForm = getMatchForm(matchService, teamService);
        var id = matchService.add(matchForm);

        assertThat(id).isNotNull();
    }

    @Test
    @DisplayName("#edit()")
    void edit() {
        var matchForm = getMatchForm(matchService, teamService);
        var id = matchService.add(matchForm);

        matchForm = getMatchForm(matchService, teamService);
        var editedMatchForm = new MatchForm(id,
                matchForm.date(), matchForm.time(),
                matchForm.home(), matchForm.visitor(),
                matchForm.info(),
                matchForm.homeScore(), matchForm.visitorScore());
        matchService.edit(editedMatchForm);

        var persistedTeamForm = matchService.findById(id);

        assertThat(persistedTeamForm)
            .hasFieldOrPropertyWithValue("id", id)
            .hasFieldOrPropertyWithValue("date", editedMatchForm.date())
            .hasFieldOrPropertyWithValue("time", editedMatchForm.time())
            .hasFieldOrPropertyWithValue("home", editedMatchForm.home())
            .hasFieldOrPropertyWithValue("visitor", editedMatchForm.visitor())
            .hasFieldOrPropertyWithValue("info", editedMatchForm.info())
            .hasFieldOrPropertyWithValue("homeScore", editedMatchForm.homeScore())
            .hasFieldOrPropertyWithValue("visitorScore", editedMatchForm.visitorScore());
    }

    @Test
    @DisplayName("#findAll()")
    void findAll() {
        var teamForms = getListOfTeamForms(teamService)
            .stream().map(teamService::add)
            .map(teamService::findById)
            .toArray(TeamForm[]::new);

        for (var i = 0; i < teamForms.length; i++) {
            var matchForm = getMatchForm(matchService, teamForms[0], teamForms[i]);
            matchService.add(matchForm);
        }

        var list = matchService.findAll();

        assertThat(list).extracting(MatchForm::visitor)
                .contains(teamForms);
    }

    @Test
    @DisplayName("#findById() successfully")
    void findById() {
        var matchForm = getMatchForm(matchService, teamService);
        var id = matchService.add(matchForm);

        var persistedMatchForm = matchService.findById(id);
        assertThat(persistedMatchForm)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("date", matchForm.date())
                .hasFieldOrPropertyWithValue("time", matchForm.time())
                .hasFieldOrPropertyWithValue("home", matchForm.home())
                .hasFieldOrPropertyWithValue("visitor", matchForm.visitor())
                .hasFieldOrPropertyWithValue("info", matchForm.info())
                .hasFieldOrPropertyWithValue("homeScore", matchForm.homeScore())
                .hasFieldOrPropertyWithValue("visitorScore", matchForm.visitorScore());
    }

    @Test
    @DisplayName("#findById() with non existed id should throw an exception")
    void findByNonExistedId() {
        var matchForm = getMatchForm(matchService, teamService);
        var id = matchService.add(matchForm);

        assertThatThrownBy(() -> matchService.findById(999L))
            .hasMessageContaining("999");
    }

    @Test
    @DisplayName("#delete()")
    void delete() {
        var matchForm = getMatchForm(matchService, teamService);
        var id = matchService.add(matchForm);

        matchService.delete(id);

        assertThatThrownBy(() -> matchService.findById(id))
                .hasMessageContaining(id.toString());
    }

}
