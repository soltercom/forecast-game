package ru.spb.altercom.forecastgame.form;

import org.springframework.stereotype.Service;
import ru.spb.altercom.forecastgame.domain.Match;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class MatchFormAdapter {

    public Match createMatchFromMatchForm(MatchForm matchForm) {
        Objects.requireNonNull(matchForm);
        var date = LocalDateTime.of(matchForm.date(), matchForm.time());
        return new Match(
            matchForm.id(),
            date,
            matchForm.home().id(),
            matchForm.visitor().id(),
            matchForm.info(),
            matchForm.homeScore(),
            matchForm.visitorScore());
    }

    public MatchForm createMatchFormFromMatch(Match match,
                                              TeamForm homeForm,
                                              TeamForm visitorForm) {
        Objects.requireNonNull(match);
        return new MatchForm(
            match.getId(),
            match.getDate().toLocalDate(),
            match.getDate().toLocalTime(),
            homeForm,
            visitorForm,
            match.getInfo(),
            match.getHomeScore(),
            match.getVisitorScore());
    }

}
