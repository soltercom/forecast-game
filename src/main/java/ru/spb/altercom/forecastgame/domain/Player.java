package ru.spb.altercom.forecastgame.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;
import ru.spb.altercom.forecastgame.form.PlayerForm;

import java.util.Objects;

@Table("PLAYERS")
public class Player {

    @Id
    private final Long id;

    private final String name;

    @PersistenceConstructor
    public Player(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Player(String name) {
        this(null, name);
    }

    public Player(PlayerForm playerDto) {
        this(playerDto.id(), playerDto.name());
    }

    public Player withId(Long id) {
        return new Player(id, name);
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
        if (!(o instanceof Player player)) return false;
        return Objects.equals(getId(), player.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
