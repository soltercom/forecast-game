package ru.spb.altercom.forecastgame.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("FORECASTS")
public class Forecast {

    @Id
    private final Long id;

    private final Long match;

    private final Long player;

    private final int homeScore;

    private final int visitorScore;

    @PersistenceConstructor
    public Forecast(Long id, Long match, Long player, int homeScore, int visitorScore) {
        this.id = id;
        this.match = match;
        this.player = player;
        this.homeScore = homeScore;
        this.visitorScore = visitorScore;
    }

    public Long getId() {
        return id;
    }

    public Long getMatch() {
        return match;
    }

    public Long getPlayer() {
        return player;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getVisitorScore() {
        return visitorScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Forecast forecast)) return false;
        return Objects.equals(id, forecast.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "id=" + id +
                ", match=" + match +
                ", player=" + player +
                ", homeScore=" + homeScore +
                ", visitorScore=" + visitorScore +
                '}';
    }
}
