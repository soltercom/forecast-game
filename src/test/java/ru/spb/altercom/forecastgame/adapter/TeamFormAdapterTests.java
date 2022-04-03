package ru.spb.altercom.forecastgame.adapter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.spb.altercom.forecastgame.domain.Team;
import ru.spb.altercom.forecastgame.form.TeamForm;
import ru.spb.altercom.forecastgame.form.TeamFormAdapter;

import static org.assertj.core.api.Assertions.assertThat;

class TeamFormAdapterTests {

    @Test
    @DisplayName("Team -> TeamForm -> Team conversion")
    void conversion() {
        var team = new Team(1L, "Test");

        var adapter = new TeamFormAdapter();
        var result = adapter.createTeamFromTeamForm(adapter.createTeamFormFromTeam(team));

        assertThat(team).isEqualTo(result)
            .hasFieldOrPropertyWithValue("id", result.getId())
            .hasFieldOrPropertyWithValue("name", result.getName());
    }

    @Test
    @DisplayName("TeamForm -> Team -> TeamForm conversion")
    void reverseConversion() {
        var teamForm = new TeamForm(1L, "Test");

        var adapter = new TeamFormAdapter();
        var result = adapter.createTeamFormFromTeam(adapter.createTeamFromTeamForm(teamForm));

        assertThat(teamForm).isEqualTo(result)
            .hasFieldOrPropertyWithValue("id", result.id())
            .hasFieldOrPropertyWithValue("name", result.name());
    }

}
