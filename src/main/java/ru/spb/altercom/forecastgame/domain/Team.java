package ru.spb.altercom.forecastgame.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;
import ru.spb.altercom.forecastgame.form.TeamForm;

import java.util.Objects;

@Table("TEAMS")
public class Team {

    @Id
    private final Long id;

    private final String name;

    @PersistenceConstructor
    public Team(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Team(String name) {
        this(null, name);
    }

    public Team(TeamForm teamDto) {
        this(teamDto.id(), teamDto.name());
    }

    public Team withId(Long id) {
        return new Team(id, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return Objects.equals(getId(), team.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
