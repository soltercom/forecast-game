package ru.spb.altercom.forecastgame.form;

import java.util.Objects;

public record PlayerForm(Long id, String name) {

    public static PlayerForm create() {
        return new PlayerForm();
    }

    private PlayerForm() {
        this(null, "");
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerForm that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
