package ru.spb.altercom.forecastgame.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Table("MATCHES")
public class Match {

    @Id
    private final Long id;

    @NotNull
    private final LocalDateTime date;

    @NotNull
    private final Long home;

    @NotNull
    private final Long visitor;

    private final String info;

    private final int homeScore;

    private final int visitorScore;

    @PersistenceConstructor
    public Match(Long id, LocalDateTime date, Long home, Long visitor, String info, int homeScore, int visitorScore) {
        this.id = id;
        this.date = date;
        this.home = home;
        this.visitor = visitor;
        this.info = info;
        this.homeScore = homeScore;
        this.visitorScore = visitorScore;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Long getHome() {
        return home;
    }

    public Long getVisitor() {
        return visitor;
    }

    public String getInfo() {
        return info;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getVisitorScore() {
        return visitorScore;
    }

    @Override
    public String toString() {
        return String.format("Match: %d, %s %s, [%s] vs [%s]  (%d:%d)",
            id, date, info, home, visitor, homeScore, visitorScore);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Match match)) return false;
        return Objects.equals(getId(), match.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
