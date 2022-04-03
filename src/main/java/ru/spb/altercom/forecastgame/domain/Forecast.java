package ru.spb.altercom.forecastgame.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("FORECASTS")
public class Forecast {

    @Id
    private final Long id;

    private final Match match;

    private final int homeScore;

    private final int visitorScore;

    @PersistenceConstructor
    public Forecast(Long id, Match match, int homeScore, int visitorScore) {
        this.id = id;
        this.match = match;
        this.homeScore = homeScore;
        this.visitorScore = visitorScore;
    }

    public Forecast(Match match, int homeScore, int visitorScore) {
        this(null, match, homeScore, visitorScore);
    }
}
