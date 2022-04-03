package ru.spb.altercom.forecastgame.adapter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.spb.altercom.forecastgame.domain.Match;
import ru.spb.altercom.forecastgame.form.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class MatchFormAdapterTests {

    @Test
    @DisplayName("Match -> MatchForm -> Match conversion")
    void conversion() {
        var home = new TeamForm(1L, "Home");
        var visitor = new TeamForm(2L, "Visitor");
        var match = new Match(1L, LocalDateTime.now(), home.id(), visitor.id(), "", 0, 0);

        var adapter = new MatchFormAdapter();
        var result = adapter.createMatchFromMatchForm(adapter.createMatchFormFromMatch(match, home, visitor));

        assertThat(match).isEqualTo(result)
            .hasFieldOrPropertyWithValue("id", result.getId())
            .hasFieldOrPropertyWithValue("date", result.getDate())
            .hasFieldOrPropertyWithValue("home", result.getHome())
            .hasFieldOrPropertyWithValue("visitor", result.getVisitor())
            .hasFieldOrPropertyWithValue("info", result.getInfo())
            .hasFieldOrPropertyWithValue("homeScore", result.getHomeScore())
            .hasFieldOrPropertyWithValue("visitorScore", result.getVisitorScore());
    }

    @Test
    @DisplayName("MatchForm -> Match -> MatchForm conversion")
    void reverseConversion() {
        var home = new TeamForm(1L, "Home");
        var visitor = new TeamForm(2L, "Visitor");
        var matchForm = new MatchForm(1L, LocalDate.now(), LocalTime.now(), home, visitor, "", 0, 0);

        var adapter = new MatchFormAdapter();
        var result = adapter.createMatchFormFromMatch(adapter.createMatchFromMatchForm(matchForm), home, visitor);

        assertThat(matchForm).isEqualTo(result)
            .hasFieldOrPropertyWithValue("id", result.id())
            .hasFieldOrPropertyWithValue("date", result.date())
            .hasFieldOrPropertyWithValue("time", result.time())
            .hasFieldOrPropertyWithValue("home", result.home())
            .hasFieldOrPropertyWithValue("visitor", result.visitor())
            .hasFieldOrPropertyWithValue("info", result.info())
            .hasFieldOrPropertyWithValue("homeScore", result.homeScore())
            .hasFieldOrPropertyWithValue("visitorScore", result.visitorScore());
    }

}
